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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public R<String> confirmationSignature(Long objectId, ObjectType objectType, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {

//        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectId, objectId).eq(OnlineSignPicEntity::getObjectType, objectType);
//        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);
//
//        if (null == onlineSignPicEntity) {
//            onlineSignPicEntity = new OnlineSignPicEntity();
//            onlineSignPicEntity.setObjectType(objectType);
//            onlineSignPicEntity.setObjectId(objectId);
//            onlineSignPicEntity.setSignPic(signPic);
//            onlineSignPicEntity.setAuditState(AuditState.EDITING);
//
//            save(onlineSignPicEntity);
//        }

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

    @Override
    public R saveOnlineSignPic(Long objectId, ObjectType objectType, String signPic) {
        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectId, objectId).eq(OnlineSignPicEntity::getObjectType, objectType);
        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);

        if (null == onlineSignPicEntity) {
            onlineSignPicEntity = new OnlineSignPicEntity();
            onlineSignPicEntity.setObjectType(objectType);
            onlineSignPicEntity.setObjectId(objectId);
            onlineSignPicEntity.setSignPic(signPic);
            onlineSignPicEntity.setAuditState(AuditState.EDITING);
            save(onlineSignPicEntity);
        }else if(AuditState.REJECTED.equals(onlineSignPicEntity.getAuditState())){
            onlineSignPicEntity.setSignPic(signPic);
            saveOrUpdate(onlineSignPicEntity);
        }else if(AuditState.EDITING.equals(onlineSignPicEntity.getAuditState())){
            onlineSignPicEntity.setSignPic(signPic);
            saveOrUpdate(onlineSignPicEntity);
        }
        return R.success("成功");
    }
    @Override
    public R whetherAutograph(ObjectType objectType,Long objectId) {
        MakerEntity makerEntity = makerService.getById(objectId);

        Map<String,Object> map = new HashMap<>();
        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectType,objectType)
                .eq(OnlineSignPicEntity::getObjectId,objectId);

        List<OnlineSignPicEntity> onlineSignPicEntities = baseMapper.selectList(queryWrapper);
        if(null == onlineSignPicEntities || onlineSignPicEntities.size() == 0){
            map.put("state",0);
            map.put("content","");
            return R.data(map);
        }
        OnlineSignPicEntity onlineSignPicEntity = onlineSignPicEntities.get(0);
        if((null == onlineSignPicEntity || StringUtils.isBlank(onlineSignPicEntity.getSignPic()))){
            map.put("state",0);
            map.put("content","");
            return R.data(map);
        }
        if(AuditState.EDITING.equals(onlineSignPicEntity.getAuditState())){
            map.put("state",1);
            map.put("content",onlineSignPicEntity.getSignPic());
            return R.data(map);
        }
        if(AuditState.REJECTED.equals(onlineSignPicEntity.getAuditState())){
            map.put("state",2);
            map.put("content",onlineSignPicEntity.getSignPic());
            map.put("rejectedExplanation",onlineSignPicEntity.getRejectedExplanation());
            return R.data(map);
        }
        if(AuditState.APPROVED.equals(onlineSignPicEntity.getAuditState()) && AuthorizationAudit.AUTHORIZED.equals(makerEntity.getAuthorizationAudit())){
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("state",3);
            map.put("content",onlineSignPicEntity.getSignPic());
            map.put("time",sdf2.format(onlineSignPicEntity.getCreateTime()));
            return R.data(map);
        }else{
            map.put("state",0);
            map.put("content",onlineSignPicEntity.getSignPic());
            return R.data(map);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> oneClickAuthorization(Long makerId,String signPic) {
        MakerEntity makerEntity = makerService.getById(makerId);
        if(null == makerEntity){
            return R.fail("用户不存在");
        }
        List<OnlineSignPicEntity> onlineSignPicEntities = baseMapper.selectList(new QueryWrapper<OnlineSignPicEntity>().lambda()
                .eq(OnlineSignPicEntity::getObjectType, ObjectType.MAKERPEOPLE)
                .eq(OnlineSignPicEntity::getObjectId, makerId));
        if(null == onlineSignPicEntities || onlineSignPicEntities.size() == 0){
            OnlineSignPicEntity onlineSignPicEntity = new OnlineSignPicEntity();
            onlineSignPicEntity.setSignPic(signPic);
            onlineSignPicEntity.setObjectType(ObjectType.MAKERPEOPLE);
            onlineSignPicEntity.setObjectId(makerId);
            onlineSignPicEntity.setAuditState(AuditState.EDITING);
            save(onlineSignPicEntity);
            return R.success("授权成功,请耐心等待审核");
        }else if(AuditState.EDITING.equals(onlineSignPicEntities.get(0).getAuditState())){
            return R.success("已经授权,请耐心等待审核");
        }else if(AuditState.REJECTED.equals(onlineSignPicEntities.get(0).getAuditState())){
            OnlineSignPicEntity onlineSignPicEntity = onlineSignPicEntities.get(0);
            onlineSignPicEntity.setSignPic(signPic);
            onlineSignPicEntity.setAuditState(AuditState.EDITING);
            saveOrUpdate(onlineSignPicEntity);
            return R.success("授权成功,请耐心等待审核");
        }else{
            return R.success("已经授权");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> relieveOneClickAuthorization(Long makerId) {
        MakerEntity makerEntity = makerService.getById(makerId);
        if(null == makerEntity){
            return R.fail("用户不存在");
        }
        List<OnlineSignPicEntity> onlineSignPicEntities = baseMapper.selectList(new QueryWrapper<OnlineSignPicEntity>().lambda()
                .eq(OnlineSignPicEntity::getObjectType, ObjectType.MAKERPEOPLE)
                .eq(OnlineSignPicEntity::getObjectId, makerId));
        if(null == onlineSignPicEntities || onlineSignPicEntities.size() == 0){
            return R.fail("没有授权信息");
        }
        OnlineSignPicEntity onlineSignPicEntity = onlineSignPicEntities.get(0);
        onlineSignPicEntity.setAuditState(AuditState.EDITING);
        saveOrUpdate(onlineSignPicEntity);
        makerEntity.setAuthorizationAudit(AuthorizationAudit.UNAUTHORIZED);
        makerService.saveOrUpdate(makerEntity);
        return R.success("解除授权成功");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R toExamineAuthorization(Long makerId, AuditState auditState, String rejectedExplanation) {
        MakerEntity makerEntity = makerService.getById(makerId);
        if(null == makerEntity){
            return R.fail("创客不能为空");
        }
        if(AuditState.EDITING.equals(auditState)){
            return R.fail("审核状态有误");
        }
        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectId,makerId).eq(OnlineSignPicEntity::getObjectType,ObjectType.MAKERPEOPLE);
        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);
        if(AuditState.APPROVED.equals(auditState)){
            onlineSignPicEntity.setAuditState(auditState);
            saveOrUpdate(onlineSignPicEntity);
            //加盟
            AgreementEntity adminMakerId = agreementService.findAdminMakerId1(makerId, AgreementType.MAKERJOINAGREEMENT);
            if(null == adminMakerId){
                OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.findTemplateType(AgreementType.MAKERJOINAGREEMENT, true);
                OnlineAgreementNeedSignEntity onlineAgreementNeedSignEntity = onlineAgreementNeedSignService.findByonlineAgreementTemplateIdAndobjectTypeAndobjectId(onlineAgreementTemplateEntity.getId(), ObjectType.MAKERPEOPLE, SignPower.PARTYB, makerId);
                confirmationSignature(makerId, ObjectType.MAKERPEOPLE, onlineSignPicEntity.getSignPic(), onlineAgreementTemplateEntity.getId(), onlineAgreementNeedSignEntity.getId());
            }
            //授权
            AgreementEntity adminMakerId1 = agreementService.findAdminMakerId1(makerId, AgreementType.MAKERPOWERATTORNEY);
            if(null == adminMakerId1){
                OnlineAgreementTemplateEntity onlineAgreementTemplateEntity1 = onlineAgreementTemplateService.findTemplateType(AgreementType.MAKERPOWERATTORNEY, true);
                OnlineAgreementNeedSignEntity onlineAgreementNeedSignEntity1 = onlineAgreementNeedSignService.findByonlineAgreementTemplateIdAndobjectTypeAndobjectId(onlineAgreementTemplateEntity1.getId(), ObjectType.MAKERPEOPLE, SignPower.PARTYB, makerId);
                confirmationSignature(makerId, ObjectType.MAKERPEOPLE, onlineSignPicEntity.getSignPic(), onlineAgreementTemplateEntity1.getId(), onlineAgreementNeedSignEntity1.getId());
            }
            makerEntity.setAuthorizationAudit(AuthorizationAudit.AUTHORIZED);
            makerEntity.setCertificationState(CertificationState.CERTIFIED);
            makerEntity.setCertificationDate(new Date());
            makerService.saveOrUpdate(makerEntity);
            return R.success("审核成功");
        }else{
            if(StringUtils.isBlank(rejectedExplanation)){
                return R.fail("驳回内容不能为空！！");
            }
            onlineSignPicEntity.setAuditState(auditState);
            onlineSignPicEntity.setRejectedExplanation(rejectedExplanation);
            saveOrUpdate(onlineSignPicEntity);
            return R.success("驳回成功");
        }

    }

    @Override
    public Integer saveMerchantMakerSupplement(Long enterpriseId, Long makerId) {
        AgreementEntity byEnterpriseAndMakerSuppl = agreementService.findByEnterpriseAndMakerSuppl(makerId, enterpriseId);
        if(byEnterpriseAndMakerSuppl != null){
            return 3;
        }
        PDFUtil pdfUtil = new PDFUtil();
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.findEntSerTemplateType(enterpriseId, ObjectType.ENTERPRISEPEOPLE, AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT);
        if(null == onlineAgreementTemplateEntity){
            return 0;
        }
        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectId,makerId).eq(OnlineSignPicEntity::getObjectType,ObjectType.MAKERPEOPLE);
        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);
        if(!AuditState.APPROVED.equals(onlineSignPicEntity.getAuditState()) || StringUtils.isBlank(onlineSignPicEntity.getSignPic())){
            return 1;
        }
        String pdf = "";
        try{
            Map map = pdfUtil.addPdf(onlineAgreementTemplateEntity.getAgreementTemplate(), 1, onlineSignPicEntity.getSignPic(), 100, 100);
            FileInputStream fileInputStream = (FileInputStream) map.get("fileInputStream");
            File file = (File) map.get("htmlFile");
            pdf = ossService.uploadSuffix(fileInputStream, ".pdf");
            fileInputStream.close();
            file.delete();
        }catch (Exception e){
            return 2;
        }
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(onlineAgreementTemplateEntity.getAgreementType());
        agreementEntity.setSignType(SignType.PLATFORMAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setValidState(ValidState.VALIDING);
        agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
        agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
        agreementEntity.setAgreementUrl(pdf);
        agreementEntity.setPartyA(ObjectType.ENTERPRISEPEOPLE);
        agreementEntity.setPartyAId(enterpriseId);
        agreementEntity.setPartyB(ObjectType.MAKERPEOPLE);
        agreementEntity.setPartyBId(makerId);
        agreementService.save(agreementEntity);
        return 3;
    }

    @Override
    public Integer saveServiceProviderMakerSupplement(Long serviceProviderId, Long makerId) {
        AgreementEntity byEnterpriseAndMakerSuppl = agreementService.findByServiceProviderAndMakerSuppl(makerId, serviceProviderId);
        if(byEnterpriseAndMakerSuppl != null){
            return 3;
        }
        PDFUtil pdfUtil = new PDFUtil();
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.findEntSerTemplateType(serviceProviderId, ObjectType.SERVICEPEOPLE, AgreementType.SERMAKSUPPLEMENTARYAGREEMENT);
        if(null == onlineAgreementTemplateEntity){
            return 0;
        }
        QueryWrapper<OnlineSignPicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineSignPicEntity::getObjectId,makerId).eq(OnlineSignPicEntity::getObjectType,ObjectType.MAKERPEOPLE);
        OnlineSignPicEntity onlineSignPicEntity = baseMapper.selectOne(queryWrapper);
        if(!AuditState.APPROVED.equals(onlineSignPicEntity.getAuditState()) || StringUtils.isBlank(onlineSignPicEntity.getSignPic())){
            return 1;
        }
        String pdf = "";
        try{
            Map map = pdfUtil.addPdf(onlineAgreementTemplateEntity.getAgreementTemplate(), 1, onlineSignPicEntity.getSignPic(), 100, 100);
            FileInputStream fileInputStream = (FileInputStream) map.get("fileInputStream");
            File file = (File) map.get("htmlFile");
            pdf = ossService.uploadSuffix(fileInputStream, ".pdf");
            fileInputStream.close();
            file.delete();
        }catch (Exception e){
            return 2;
        }
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(onlineAgreementTemplateEntity.getAgreementType());
        agreementEntity.setSignType(SignType.PLATFORMAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setValidState(ValidState.VALIDING);
        agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
        agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
        agreementEntity.setAgreementUrl(pdf);
        agreementEntity.setPartyA(ObjectType.SERVICEPEOPLE);
        agreementEntity.setPartyAId(serviceProviderId);
        agreementEntity.setPartyB(ObjectType.MAKERPEOPLE);
        agreementEntity.setPartyBId(makerId);
        agreementService.save(agreementEntity);
        return 3;
    }
}
