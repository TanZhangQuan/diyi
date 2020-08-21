package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.common.tool.PDFUtil;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.OnlineSignPicMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
    @Transactional
    public R<String> saveOnlineSignPic(Long ObjectId, ObjectType objectType, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {
        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectId, ObjectId)
                .eq(OnlineSignPicEntity::getObjectType, ObjectType.MAKERPEOPLE);
        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);

        if (null == onlineSignPicEntity) {
            onlineSignPicEntity = new OnlineSignPicEntity();
            onlineSignPicEntity.setObjectType(objectType);
            onlineSignPicEntity.setObjectId(ObjectId);
            onlineSignPicEntity.setSignPic(signPic);
            onlineSignPicEntity.setSignDatetime(new Date());
            onlineSignPicEntity.setWorkerSex(ObjectId);
            save(onlineSignPicEntity);
        }
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.getById(onlineAgreementTemplateId);
        if (null == onlineAgreementTemplateEntity) {
            R.fail("协议模板id有误");
        }
        PDFUtil pdfUtil = new PDFUtil();
        try {
            Map map = pdfUtil.addPdf(onlineAgreementTemplateEntity.getAgreementTemplate(), onlineAgreementTemplateEntity.getTemplateCount(), signPic);
            FileInputStream fileInputStream = (FileInputStream) map.get("fileInputStream");
            File file = (File) map.get("htmlFile");
            String pdf = ossService.uploadSuffix(fileInputStream, ".pdf");
            fileInputStream.close();
            file.delete();
            OnlineAgreementNeedSignEntity onlineAgreementNeedSignEntity = onlineAgreementNeedSignService.getById(onlineAgreementNeedSignId);
            onlineAgreementNeedSignEntity.setSignState(SignState.SIGNING);
            onlineAgreementNeedSignService.saveOrUpdate(onlineAgreementNeedSignEntity);
            //稍后添加
            MakerEntity makerEntity = makerService.getById(ObjectId);
            AgreementEntity agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(onlineAgreementTemplateEntity.getAgreementType());
            agreementEntity.setSignDate(new Date());
            agreementEntity.setSignType(SignType.PLATFORMAGREEMENT);
            agreementEntity.setAgreementNo(UUID.randomUUID().toString());
            agreementEntity.setSequenceNo(UUID.randomUUID().toString());
            agreementEntity.setMakerId(ObjectId);
            agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateId);
            agreementEntity.setOnlineAggrementUrl(pdf);
            agreementEntity.setFirstSideSignPerson("地衣众包平台");
            agreementEntity.setSecondSideSignPerson(makerEntity.getName());
            agreementEntity.setUploadDatetime(new Date());
            agreementEntity.setUploadPerson(makerEntity.getName());
            agreementService.save(agreementEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("签名失败");
        }
        return R.success("保存成功");
    }

    @Override
    public OnlineSignPicEntity findObjectTypeAndObjectId(Long ObjectId, ObjectType objectType) {
        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectType, objectType)
                .eq(OnlineSignPicEntity::getObjectId, ObjectId);
        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);
        return onlineSignPicEntity;
    }
}
