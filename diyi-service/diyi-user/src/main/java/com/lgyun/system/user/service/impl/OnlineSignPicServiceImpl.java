package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
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

    private IMakerService makerService;
    private AliyunOssService ossService;
    private IPartnerService partnerService;
    private IAgreementService agreementService;
    private IOnlineAgreementTemplateService onlineAgreementTemplateService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveOnlineSignPic(Long objectId, ObjectType objectType, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {

        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectId, objectId).eq(OnlineSignPicEntity::getObjectType, objectType);
        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);

        if (null == onlineSignPicEntity) {
            onlineSignPicEntity = new OnlineSignPicEntity();
            onlineSignPicEntity.setObjectType(objectType);
            onlineSignPicEntity.setObjectId(objectId);
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
            AgreementEntity agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(onlineAgreementTemplateEntity.getAgreementType());
            agreementEntity.setSignType(SignType.PLATFORMAGREEMENT);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setValidState(ValidState.VALIDING);
            agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
            agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateId);
            agreementEntity.setAgreementUrl(pdf);
            agreementEntity.setPartyBId(objectId);
            if (ObjectType.MAKERPEOPLE.equals(objectType)) {
                agreementEntity.setPartyB(ObjectType.MAKERPEOPLE);
            }
            if (ObjectType.PARTNERPEOPLE.equals(objectType)) {
                agreementEntity.setPartyB(ObjectType.PARTNERPEOPLE);
            }

            agreementService.save(agreementEntity);
        } catch (Exception e) {
            return R.fail("签名失败");
        }

        MakerEntity makerEntity;
        if (ObjectType.MAKERPEOPLE.equals(objectType)) {
            makerEntity = makerService.getById(objectId);
            if (AgreementType.MAKERJOINAGREEMENT.equals(onlineAgreementTemplateEntity.getAgreementType()) && VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
                //判断创客是否有有效的创客授权书
                int makerPowerAttorneyNum = agreementService.queryValidAgreementNum(null, null, ObjectType.MAKERPEOPLE, objectId, AgreementType.MAKERPOWERATTORNEY);
                if (makerPowerAttorneyNum > 0) {
                    makerEntity.setCertificationState(CertificationState.CERTIFIED);
                    makerEntity.setCertificationDate(new Date());
                }
            }

            if (AgreementType.MAKERPOWERATTORNEY.equals(onlineAgreementTemplateEntity.getAgreementType()) && VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
                //判断创客是否有有效的创客加盟协议
                int makerJoinAgreementNum = agreementService.queryValidAgreementNum(null, null, ObjectType.MAKERPEOPLE, objectId, AgreementType.MAKERJOINAGREEMENT);
                if (makerJoinAgreementNum > 0) {
                    makerEntity.setCertificationState(CertificationState.CERTIFIED);
                    makerEntity.setCertificationDate(new Date());
                }
            }
            makerService.saveOrUpdate(makerEntity);
        }

        PartnerEntity partnerEntity;
        if (ObjectType.PARTNERPEOPLE.equals(objectType)) {
            partnerEntity = partnerService.getById(objectId);
            partnerService.saveOrUpdate(partnerEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
