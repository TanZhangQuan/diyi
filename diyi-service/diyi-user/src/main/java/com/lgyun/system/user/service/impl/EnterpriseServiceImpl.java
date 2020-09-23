package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.*;
import com.lgyun.system.user.dto.enterprise.AddOrUpdateEnterpriseContactDto;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.EnterpriseIdNameListVO;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.admin.*;
import com.lgyun.system.user.vo.enterprise.EnterpriseResponse;
import com.lgyun.system.user.wrapper.EnterpriseWrapper;
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
    private IEnterpriseServiceProviderService enterpriseProviderService;
    private IAgreementService agreementService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IUserService userService;

    @Override
    public Integer findCountByEnterpriseName(String enterpriseName, Long enterpriseId) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName, enterpriseName)
                .ne(enterpriseId != null, EnterpriseEntity::getId, enterpriseId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer findCountBySocialCreditNo(String socialCreditNo, Long enterpriseId) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getSocialCreditNo, socialCreditNo)
                .ne(enterpriseId != null, EnterpriseEntity::getId, enterpriseId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public MakerEnterpriseRelationVO getEnterpriseName(String enterpriseName) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName, enterpriseName);
        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);
        return EnterpriseWrapper.build().makerEnterpriseRelationVO(enterpriseEntity);
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId) {
        MakerEnterpriseEntity enterpriseIdAndMakerIdLian = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, RelationshipType.RELEVANCE);
        MakerEnterpriseEntity enterpriseIdAndMakerIdZhu = makerEnterpriseService.getEnterpriseIdAndMakerIdAndRelationshipType(enterpriseId, makerId, RelationshipType.ATTENTION);
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getId, enterpriseId);

        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);

        MakerEnterpriseRelationVO makerEnterpriseRelationVO = EnterpriseWrapper.build().makerEnterpriseRelationVO(enterpriseEntity);
        if (makerEnterpriseRelationVO == null) {
            return R.fail("商户不存在");
        }

        if (null == enterpriseIdAndMakerIdLian && null != enterpriseIdAndMakerIdZhu) {
            //TODO
            makerEnterpriseRelationVO.setContact1Phone("138********");
            makerEnterpriseRelationVO.setBizLicenceUrl("*");
            makerEnterpriseRelationVO.setLegalPersonName("***");
            makerEnterpriseRelationVO.setLegalPersonIdCard("*********");
            makerEnterpriseRelationVO.setSocialCreditNo("*******");
            makerEnterpriseRelationVO.setContact1Position(null);
            makerEnterpriseRelationVO.setShopUserName("*****");
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.ATTENTION);
            return R.data(makerEnterpriseRelationVO);
        } else if (null != enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu) {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        } else if (null == enterpriseIdAndMakerIdLian && null == enterpriseIdAndMakerIdZhu) {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.NORELATION);
            return R.data(makerEnterpriseRelationVO);
        } else {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        }
    }

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> getServiceProviders(Query query, Long enterpriseId) {
        return enterpriseProviderService.getServiceProviderByEnterpriseId(Condition.getPage(query.setDescs("create_time")), enterpriseId, null);
    }

    /**
     * 查询商户基本信息
     *
     * @param enterpriseId
     * @return
     */
    @Override
    public R<EnterpriseResponse> getBasicEnterpriseResponse(Long enterpriseId) {
        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        EnterpriseResponse enterpriseResponse = new EnterpriseResponse();
        BeanUtils.copyProperties(enterpriseEntity, enterpriseResponse);
        return R.data(enterpriseResponse);
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
    public R<IPage<QueryEnterpriseListPaymentVO>> queryEnterpriseListPayment(QueryEnterpriseListPaymentDTO queryEnterpriseListPaymentDTO, IPage<QueryEnterpriseListPaymentVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseListPayment(queryEnterpriseListPaymentDTO, page)));
    }

    @Override
    public R<IPage<QueryServiceProviderListPaymentVO>> queryServiceProviderListPayment(QueryServiceProviderListPaymentDTO queryServiceProviderListPaymentDTO, IPage<QueryServiceProviderListPaymentVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderListPayment(queryServiceProviderListPaymentDTO, page)));
    }

    @Override
    public R<IPage<EnterpriseIdNameListVO>> queryEnterpriseListNaturalPersonMaker(String enterpriseName, IPage<EnterpriseIdNameListVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseListNaturalPersonMaker(enterpriseName, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createEnterprise(AddEnterpriseDTO addEnterpriseDTO, AdminEntity adminEntity) {

        //判断商户联系人是否相同
        if (addEnterpriseDTO.getContact1Phone().equals(addEnterpriseDTO.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }

        //判断商户名称是否已存在
        Integer countByEnterpriseName = findCountByEnterpriseName(addEnterpriseDTO.getEnterpriseName(), null);
        if (countByEnterpriseName != null) {
            return R.fail("名称已存在");
        }

        //判断社会信用代码是否已存在
        Integer countBySocialCreditNo = findCountBySocialCreditNo(addEnterpriseDTO.getSocialCreditNo(), null);
        if (countBySocialCreditNo != null) {
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

        //上传加盟合同
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.ENTERPRISEJOINAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setPaperAgreementUrl(addEnterpriseDTO.getJoinContract());
        agreementEntity.setFirstSideSignPerson(adminEntity.getName());
        agreementEntity.setEnterpriseId(enterpriseEntity.getId());
        agreementEntity.setSecondSideSignPerson(enterpriseEntity.getContact1Name());
        agreementService.save(agreementEntity);

        //上传商户承诺函
        agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setPaperAgreementUrl(addEnterpriseDTO.getCommitmentLetter());
        agreementEntity.setFirstSideSignPerson(adminEntity.getName());
        agreementEntity.setEnterpriseId(enterpriseEntity.getId());
        agreementEntity.setSecondSideSignPerson(enterpriseEntity.getContact1Name());
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
        Integer countByEnterpriseName = findCountByEnterpriseName(updateEnterpriseDTO.getEnterpriseName(), enterpriseEntity.getId());
        if (countByEnterpriseName > 0) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        Integer countBySocialCreditNo = findCountBySocialCreditNo(updateEnterpriseDTO.getSocialCreditNo(), enterpriseEntity.getId());
        if (countBySocialCreditNo > 0) {
            return R.fail("统一社会信用代码已存在");
        }

        //根据联系人生成商户员工
        AddOrUpdateEnterpriseContactDto addOrUpdateEnterpriseContactDto = new AddOrUpdateEnterpriseContactDto();
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
    public R<IPage<QueryEnterpriseListEnterpriseVO>> queryEnterpriseListEnterprise(QueryEnterpriseListDTO queryEnterpriseListDTO, IPage<QueryEnterpriseListEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseListEnterprise(queryEnterpriseListDTO, page)));
    }

    @Override
    public R<QueryEnterpriseDetailEnterpriseVO> queryEnterpriseDetailEnterprise(Long enterpriseId) {
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
    public R<IPage<QueryCooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long enterpriseId, IPage<QueryCooperationServiceProviderListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCooperationServiceProviderList(enterpriseId, page)));
    }

    @Override
    public R<EnterpriseIdNameListVO> queryEnterpriseIdAndName(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseIdAndName(enterpriseId));
    }

}
