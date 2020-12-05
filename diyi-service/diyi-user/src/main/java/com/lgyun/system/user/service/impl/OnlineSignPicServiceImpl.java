package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.PDFUtil;
import com.lgyun.common.tool.SnowflakeIdWorker;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.OnlineSignPicMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Map;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-18 15:59:14
 */
@Slf4j
@Service
@AllArgsConstructor
public class OnlineSignPicServiceImpl extends BaseServiceImpl<OnlineSignPicMapper, OnlineSignPicEntity> implements IOnlineSignPicService {

    private IOnlineAgreementTemplateService onlineAgreementTemplateService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private AliyunOssService ossService;
    private IMakerService makerService;
    private IAgreementService agreementService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveOnlineSignPic(Long ObjectId, ObjectType objectType, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {

        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectId, ObjectId).eq(OnlineSignPicEntity::getObjectType, objectType);
        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);

        if (null == onlineSignPicEntity) {
            onlineSignPicEntity = new OnlineSignPicEntity();
            onlineSignPicEntity.setObjectType(objectType);
            onlineSignPicEntity.setObjectId(ObjectId);
            onlineSignPicEntity.setSignPic(signPic);
            save(onlineSignPicEntity);
        }

        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.getById(onlineAgreementTemplateId);
        if (null == onlineAgreementTemplateEntity) {
            return R.fail("协议模板不存在");
        }

        PDFUtil pdfUtil = new PDFUtil();
        try {
            Map map = pdfUtil.addPdf(onlineAgreementTemplateEntity.getAgreementTemplate(), onlineAgreementTemplateEntity.getTemplateCount(), signPic, onlineAgreementTemplateEntity.getXCoordinate(), onlineAgreementTemplateEntity.getYCoordinate());
            FileInputStream fileInputStream = (FileInputStream) map.get("fileInputStream");
            File file = (File) map.get("htmlFile");
            String pdf = ossService.uploadSuffix(fileInputStream, ".pdf");
            fileInputStream.close();
            file.delete();
            OnlineAgreementNeedSignEntity onlineAgreementNeedSignEntity = onlineAgreementNeedSignService.getById(onlineAgreementNeedSignId);
            onlineAgreementNeedSignEntity.setSignState(SignState.SIGNED);
            onlineAgreementNeedSignService.saveOrUpdate(onlineAgreementNeedSignEntity);
            MakerEntity makerEntity = makerService.getById(ObjectId);
            AgreementEntity agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(onlineAgreementTemplateEntity.getAgreementType());
            agreementEntity.setSignType(SignType.PLATFORMAGREEMENT);
            agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
            agreementEntity.setMakerId(ObjectId);
            agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateId);
            agreementEntity.setOnlineAgreementUrl(pdf);
            agreementEntity.setFirstSideSignPerson("地衣众包平台");
            agreementEntity.setSecondSideSignPerson(makerEntity.getName());
            agreementService.save(agreementEntity);
        } catch (Exception e) {
            return R.fail("签名失败");
        }

        if (ObjectType.MAKERPEOPLE.equals(objectType)) {
            MakerEntity makerEntity = makerService.getById(ObjectId);
            if (onlineAgreementTemplateEntity.getAgreementType().equals(AgreementType.MAKERJOINAGREEMENT)) {
                makerEntity.setJoinSignState(SignState.SIGNED);
                if (makerEntity.getEmpowerSignState().equals(SignState.SIGNED) && makerEntity.getIdcardVerifyStatus().equals(VerifyStatus.VERIFYPASS)) {
                    makerEntity.setCertificationState(CertificationState.CERTIFIED);
                    makerEntity.setCertificationDate(new Date());
                }
            }
            if (onlineAgreementTemplateEntity.getAgreementType().equals(AgreementType.MAKERPOWERATTORNEY)) {
                makerEntity.setEmpowerSignState(SignState.SIGNED);
                if (makerEntity.getJoinSignState().equals(SignState.SIGNED) && makerEntity.getIdcardVerifyStatus().equals(VerifyStatus.VERIFYPASS)) {
                    makerEntity.setCertificationState(CertificationState.CERTIFIED);
                    makerEntity.setCertificationDate(new Date());
                }
            }
            makerService.saveOrUpdate(makerEntity);
        }
        return R.success("保存成功");
    }

}
