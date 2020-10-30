package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.*;
import com.lgyun.system.user.dto.AddOrUpdateEnterpriseContactDTO;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import com.lgyun.system.user.vo.EnterpriseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseMapper, EnterpriseEntity> implements IEnterpriseService {

    private AliyunOssService ossService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IAgreementService agreementService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IUserService userService;

    @Override
    public int queryCountById(Long id) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getId, id);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int queryCountByEnterpriseName(String enterpriseName, Long enterpriseId) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName, enterpriseName)
                .ne(enterpriseId != null, EnterpriseEntity::getId, enterpriseId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int queryCountBySocialCreditNo(String socialCreditNo, Long enterpriseId) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getSocialCreditNo, socialCreditNo)
                .ne(enterpriseId != null, EnterpriseEntity::getId, enterpriseId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName) {
        return R.data(baseMapper.getEnterpriseName(enterpriseName));
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId) {
        int relevanceNum = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, RelationshipType.RELEVANCE);
        int attentionNum = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, RelationshipType.ATTENTION);
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getId, enterpriseId);

        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);
        if (null == enterpriseEntity) {
            return R.fail("商户不存在");
        }

        MakerEnterpriseRelationVO makerEnterpriseRelationVO = BeanUtil.copy(enterpriseEntity, MakerEnterpriseRelationVO.class);

        if ((0 == relevanceNum && 0 < attentionNum) || (0 == relevanceNum && 0 == attentionNum)) {
            makerEnterpriseRelationVO.setContact1Phone(makerEnterpriseRelationVO.getContact1Phone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            makerEnterpriseRelationVO.setBizLicenceUrl("*");
            makerEnterpriseRelationVO.setLegalPersonName("***");
            makerEnterpriseRelationVO.setLegalPersonIdCard("*********");
            makerEnterpriseRelationVO.setSocialCreditNo("*******");
            makerEnterpriseRelationVO.setContact1Position(null);
            makerEnterpriseRelationVO.setShopUserName("*****");
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.ATTENTION);
            return R.data(makerEnterpriseRelationVO);
        } else if (0 < relevanceNum && 0 == attentionNum) {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        } else {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        }
    }

    /**
     * 查询商户基本信息
     *
     * @param enterpriseId
     * @return
     */
    @Override
    public R<EnterpriseVO> getBasicEnterpriseResponse(Long enterpriseId) {
        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        EnterpriseVO enterpriseVO = new EnterpriseVO();
        BeanUtils.copyProperties(enterpriseEntity, enterpriseVO);
        return R.data(enterpriseVO);
    }

    /**
     * 上传商户营业执照
     *
     * @param enterpriseId
     * @param file
     * @return
     */
    @Override
    public void uploadEnterpriseLicence(Long enterpriseId, MultipartFile file) throws Exception {

        //判断文件内容是否为空
        if (file.isEmpty()) {
            throw new CustomException("上传文件不能为空");
        }
        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 上传文件中
        String url = ossService.uploadSuffix(file.getBytes(), suffix);

        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        log.info("[uploadEnterpriseLicence]enterpriseId={} url={}", enterpriseEntity.getId(), url);
        // 更新营业执照
        enterpriseEntity.setBizLicenceUrl(url);
        this.save(enterpriseEntity);
    }

    @Override
    public R<EnterprisesDetailVO> getEnterpriseDetailById(Long enterpriseId) {
        return R.data(baseMapper.getEnterpriseDetailById(enterpriseId));
    }

    @Override
    public R<IPage<EnterpriseListPaymentVO>> queryEnterpriseListPayment(String enterpriseName, IPage<EnterpriseListPaymentVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseListPayment(enterpriseName, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createEnterprise(AddEnterpriseDTO addEnterpriseDTO, AdminEntity adminEntity) {

        //判断商户联系人是否相同
        if (addEnterpriseDTO.getContact1Phone().equals(addEnterpriseDTO.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }

        //判断商户名称是否已存在
        int countByEnterpriseName = queryCountByEnterpriseName(addEnterpriseDTO.getEnterpriseName(), null);
        if (countByEnterpriseName > 0) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        int countBySocialCreditNo = queryCountBySocialCreditNo(addEnterpriseDTO.getSocialCreditNo(), null);
        if (countBySocialCreditNo > 0) {
            return R.fail("统一社会信用代码已存在");
        }

        //判断商户联系人1是否已存在
        Integer countByPhoneNumber1 = enterpriseWorkerService.findCountByPhoneNumber(addEnterpriseDTO.getContact1Phone());
        if (countByPhoneNumber1 > 0) {
            return R.fail("联系人1电话/手机：" + addEnterpriseDTO.getContact1Phone() + "已存在");
        }

        //判断商户联系人2是否已存在
        Integer countByPhoneNumber2 = enterpriseWorkerService.findCountByPhoneNumber(addEnterpriseDTO.getContact2Phone());
        if (countByPhoneNumber2 > 0) {
            return R.fail("联系人2电话/手机：" + addEnterpriseDTO.getContact2Phone() + "已存在");
        }

        EnterpriseEntity enterpriseEntity = new EnterpriseEntity();
        BeanUtil.copy(addEnterpriseDTO, enterpriseEntity);
        enterpriseEntity.setCreateType(CreateType.PLATFORMCREATE);
        save(enterpriseEntity);

        //新建联系人员工1
        User user = new User();
        user.setUserType(UserType.ENTERPRISE);
        user.setAccount(addEnterpriseDTO.getContact1Phone());
        user.setPhone(addEnterpriseDTO.getContact1Phone());
        userService.save(user);

        EnterpriseWorkerEntity enterpriseWorkerEntity = new EnterpriseWorkerEntity();
        enterpriseWorkerEntity.setEnterpriseId(enterpriseEntity.getId());
        enterpriseWorkerEntity.setUserId(user.getId());
        enterpriseWorkerEntity.setWorkerName(addEnterpriseDTO.getContact1Name());
        enterpriseWorkerEntity.setPositionName(addEnterpriseDTO.getContact1Position());
        enterpriseWorkerEntity.setPhoneNumber(addEnterpriseDTO.getContact1Phone());
        enterpriseWorkerEntity.setEmployeeUserName(addEnterpriseDTO.getContact1Phone());
        enterpriseWorkerEntity.setEmployeePwd(DigestUtil.encrypt("123456"));
        enterpriseWorkerEntity.setAdminPower(true);
        enterpriseWorkerService.save(enterpriseWorkerEntity);

        //新建联系人员工2
        user = new User();
        user.setUserType(UserType.ENTERPRISE);
        user.setAccount(addEnterpriseDTO.getContact2Phone());
        user.setPhone(addEnterpriseDTO.getContact2Phone());
        userService.save(user);

        enterpriseWorkerEntity = new EnterpriseWorkerEntity();
        enterpriseWorkerEntity.setEnterpriseId(enterpriseEntity.getId());
        enterpriseWorkerEntity.setUserId(user.getId());
        enterpriseWorkerEntity.setWorkerName(addEnterpriseDTO.getContact2Name());
        enterpriseWorkerEntity.setPositionName(addEnterpriseDTO.getContact2Position());
        enterpriseWorkerEntity.setPhoneNumber(addEnterpriseDTO.getContact2Phone());
        enterpriseWorkerEntity.setEmployeeUserName(addEnterpriseDTO.getContact2Phone());
        enterpriseWorkerEntity.setEmployeePwd(DigestUtil.encrypt("123456"));
        enterpriseWorkerEntity.setAdminPower(true);
        enterpriseWorkerService.save(enterpriseWorkerEntity);

        //上传商户加盟合同
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.ENTERPRISEJOINAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setPaperAgreementUrl(addEnterpriseDTO.getJoinContract());
        agreementEntity.setFirstSideSignPerson("地衣众包平台");
        agreementEntity.setEnterpriseId(enterpriseEntity.getId());
        agreementEntity.setSecondSideSignPerson(enterpriseEntity.getEnterpriseName());
        agreementService.save(agreementEntity);

        //上传商户承诺函
        agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.ENTERPRISEPROMISE);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setPaperAgreementUrl(addEnterpriseDTO.getCommitmentLetter());
        agreementEntity.setFirstSideSignPerson("地衣众包平台");
        agreementEntity.setEnterpriseId(enterpriseEntity.getId());
        agreementEntity.setSecondSideSignPerson(enterpriseEntity.getEnterpriseName());
        agreementService.save(agreementEntity);

        return R.success("添加商户成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateEnterprise(UpdateEnterpriseDTO updateEnterpriseDTO, AdminEntity adminEntity) {

        EnterpriseEntity enterpriseEntity = getById(updateEnterpriseDTO.getEnterpriseId());
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        //判断商户名称是否已存在
        Integer countByEnterpriseName = queryCountByEnterpriseName(updateEnterpriseDTO.getEnterpriseName(), enterpriseEntity.getId());
        if (countByEnterpriseName > 0) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        Integer countBySocialCreditNo = queryCountBySocialCreditNo(updateEnterpriseDTO.getSocialCreditNo(), enterpriseEntity.getId());
        if (countBySocialCreditNo > 0) {
            return R.fail("统一社会信用代码已存在");
        }

        //根据联系人生成商户员工
        AddOrUpdateEnterpriseContactDTO addOrUpdateEnterpriseContactDto = new AddOrUpdateEnterpriseContactDTO();
        addOrUpdateEnterpriseContactDto.setEnterpriseId(enterpriseEntity.getId());
        addOrUpdateEnterpriseContactDto.setContact1Name(updateEnterpriseDTO.getContact1Name());
        addOrUpdateEnterpriseContactDto.setContact1Position(updateEnterpriseDTO.getContact1Position());
        addOrUpdateEnterpriseContactDto.setContact1Phone(updateEnterpriseDTO.getContact1Phone());
        addOrUpdateEnterpriseContactDto.setContact1Mail(updateEnterpriseDTO.getContact1Mail());
        addOrUpdateEnterpriseContactDto.setContact2Name(updateEnterpriseDTO.getContact2Name());
        addOrUpdateEnterpriseContactDto.setContact2Position(updateEnterpriseDTO.getContact2Position());
        addOrUpdateEnterpriseContactDto.setContact2Phone(updateEnterpriseDTO.getContact2Phone());
        addOrUpdateEnterpriseContactDto.setContact2Mail(updateEnterpriseDTO.getContact2Mail());
        R<String> result = enterpriseWorkerService.addOrUpdateEnterpriseContact(addOrUpdateEnterpriseContactDto, null);
        if (!(result.isSuccess())) {
            return result;
        }

        //上传或修改加盟合同
        AgreementEntity agreementEntity = agreementService.findSuccessAgreement(enterpriseEntity.getId(), null, AgreementType.ENTERPRISEJOINAGREEMENT, null, SignState.SIGNED);
        if (agreementEntity != null) {
            agreementEntity.setPaperAgreementUrl(updateEnterpriseDTO.getJoinContract());
            agreementService.updateById(agreementEntity);
        } else {
            agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.ENTERPRISEJOINAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setPaperAgreementUrl(updateEnterpriseDTO.getJoinContract());
            agreementEntity.setFirstSideSignPerson(adminEntity.getName());
            agreementEntity.setEnterpriseId(enterpriseEntity.getId());
            agreementEntity.setSecondSideSignPerson(enterpriseEntity.getContact1Name());
            agreementService.save(agreementEntity);
        }

        //上传或修改商户承诺函
        agreementEntity = agreementService.findSuccessAgreement(enterpriseEntity.getId(), null, AgreementType.OTHERAGREEMENT, AuditState.APPROVED, null);
        if (agreementEntity != null) {
            agreementEntity.setPaperAgreementUrl(updateEnterpriseDTO.getCommitmentLetter());
            agreementService.updateById(agreementEntity);
        } else {
            agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setPaperAgreementUrl(updateEnterpriseDTO.getCommitmentLetter());
            agreementEntity.setFirstSideSignPerson(adminEntity.getName());
            agreementEntity.setEnterpriseId(enterpriseEntity.getId());
            agreementEntity.setSecondSideSignPerson(enterpriseEntity.getContact1Name());
            agreementService.save(agreementEntity);
        }

        BeanUtil.copy(updateEnterpriseDTO, enterpriseEntity);
        updateById(enterpriseEntity);

        return R.success("编辑商户成功");
    }

    @Override
    public R<IPage<EnterpriseListEnterpriseVO>> queryEnterpriseListEnterprise(QueryEnterpriseListDTO queryEnterpriseListDTO, IPage<EnterpriseListEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseListEnterprise(queryEnterpriseListDTO, page)));
    }

    @Override
    public R<EnterpriseDetailEnterpriseVO> queryEnterpriseDetailEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseDetailEnterprise(enterpriseId));
    }

    @Override
    public R<String> updateEnterpriseState(Long enterpriseId, AccountState enterpriseState) {

        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        if (!(enterpriseState.equals(enterpriseEntity.getEnterpriseState()))) {
            enterpriseEntity.setEnterpriseState(enterpriseState);
            save(enterpriseEntity);
        }

        return R.fail("更改商户状态成功");
    }

    @Override
    public R<IPage<CooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long enterpriseId, IPage<CooperationServiceProviderListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCooperationServiceProviderList(enterpriseId, page)));
    }

    @Override
    public R getEnterpriseAll(IPage<EnterpriseEntity> page) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        IPage<EnterpriseEntity> enterpriseEntityIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(enterpriseEntityIPage);
    }

}
