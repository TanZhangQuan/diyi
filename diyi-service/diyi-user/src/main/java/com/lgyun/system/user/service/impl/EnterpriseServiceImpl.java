package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.*;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.EnterpriseIdNameListVO;
import com.lgyun.system.user.vo.EnterprisesDetailVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.admin.*;
import com.lgyun.system.user.vo.enterprise.EnterpriseContactRequest;
import com.lgyun.system.user.vo.enterprise.EnterpriseResponse;
import com.lgyun.system.user.wrapper.EnterpriseWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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
    public EnterpriseEntity findByEnterpriseName(String enterpriseName) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName, enterpriseName);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public EnterpriseEntity findBySocialCreditNo(String socialCreditNo) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getSocialCreditNo, socialCreditNo);
        return baseMapper.selectOne(queryWrapper);
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
            makerEnterpriseRelationVO.setContact1Position("********");
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
            throw new RuntimeException("上传文件不能为空");
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
    public R<IPage<QueryEnterpriseListNaturalPersonMaker>> queryEnterpriseListNaturalPersonMaker(String enterpriseName, IPage<QueryEnterpriseListNaturalPersonMaker> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseListNaturalPersonMaker(enterpriseName, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createEnterprise(AddEnterpriseDTO addEnterpriseDTO) {

        //判断商户联系人是否已存在
        if (addEnterpriseDTO.getContact1Phone().equals(addEnterpriseDTO.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }

        //判断商户名称是否已存在
        EnterpriseEntity oldEnterpriseEntity = findByEnterpriseName(addEnterpriseDTO.getEnterpriseName());
        if (oldEnterpriseEntity != null) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        oldEnterpriseEntity = findBySocialCreditNo(addEnterpriseDTO.getSocialCreditNo());
        if (oldEnterpriseEntity != null) {
            return R.fail("统一社会信用代码已存在");
        }

        //判断商户联系人1是否已存在
        EnterpriseWorkerEntity oldEnterpriseWorkerEntity = enterpriseWorkerService.findByPhoneNumber(addEnterpriseDTO.getContact1Phone());
        if (oldEnterpriseWorkerEntity != null) {
            return R.fail("联系人1电话/手机：" + addEnterpriseDTO.getContact1Phone() + "已存在");
        }

        //判断商户联系人2是否已存在
        oldEnterpriseWorkerEntity = enterpriseWorkerService.findByPhoneNumber(addEnterpriseDTO.getContact2Phone());
        if (oldEnterpriseWorkerEntity != null) {
            return R.fail("联系人2电话/手机：" + addEnterpriseDTO.getContact2Phone() + "已存在");
        }

        EnterpriseEntity enterpriseEntity = new EnterpriseEntity();
        BeanUtil.copy(addEnterpriseDTO, enterpriseEntity);
        //生成创客邀请码
        enterpriseEntity.setInviteNo(String.valueOf(UUID.randomUUID()));
        //商户创建模式
        enterpriseEntity.setCreateType(CreateType.PLATFORMCREATE);
        save(enterpriseEntity);

        //根据联系人1生成商户员工
        EnterpriseContactRequest request = new EnterpriseContactRequest();
        request.setWorkerName(addEnterpriseDTO.getContact1Name());
        request.setPhoneNumber(addEnterpriseDTO.getContact1Phone());
        request.setEmployeeUserName(addEnterpriseDTO.getContact1Phone());
        request.setPositionName(EnterprisePositionName.MANAGEMENT);
        request.setEmail(addEnterpriseDTO.getContact1Mail());
        request.setAdminPower(true);
        R<String> result = enterpriseWorkerService.addNewEnterpriseWorker(request, enterpriseEntity.getId(), null);
        if (!(result.isSuccess())) {
            log.error(result.getMsg());
            throw new RuntimeException("添加联系人1为商户员工失败");
        }

        //根据联系人2生成商户员工
        request = new EnterpriseContactRequest();
        request.setWorkerName(addEnterpriseDTO.getContact2Name());
        request.setPhoneNumber(addEnterpriseDTO.getContact2Phone());
        request.setEmployeeUserName(addEnterpriseDTO.getContact2Phone());
        request.setPositionName(EnterprisePositionName.MANAGEMENT);
        request.setEmail(addEnterpriseDTO.getContact2Mail());
        request.setAdminPower(true);
        result = enterpriseWorkerService.addNewEnterpriseWorker(request, enterpriseEntity.getId(), null);
        if (!(result.isSuccess())) {
            log.error(result.getMsg());
            throw new RuntimeException("添加联系人2为商户员工失败");
        }

        //上传加盟合同
        agreementService.uploadAgreementByAdmin(ObjectType.ENTERPRISEPEOPLE, enterpriseEntity.getId(),
                AgreementType.ENTERPRISEJOINAGREEMENT, addEnterpriseDTO.getJoinContract());

        //上传商户承诺函
        agreementService.uploadAgreementByAdmin(ObjectType.ENTERPRISEPEOPLE, enterpriseEntity.getId(),
                AgreementType.OTHERAGREEMENT, addEnterpriseDTO.getCommitmentLetter());

        return R.success("添加商户成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateEnterprise(UpdateEnterpriseDTO updateEnterpriseDTO) {

        //判断商户联系人是否已存在
        if (updateEnterpriseDTO.getContact1Phone().equals(updateEnterpriseDTO.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }

        EnterpriseEntity enterpriseEntity = getById(updateEnterpriseDTO.getEnterpriseId());
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        //判断商户名称是否已存在
        EnterpriseEntity oldEnterpriseEntity = findByEnterpriseName(updateEnterpriseDTO.getEnterpriseName());
        if (oldEnterpriseEntity != null && !(oldEnterpriseEntity.getId().equals(enterpriseEntity.getId()))) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        oldEnterpriseEntity = findBySocialCreditNo(updateEnterpriseDTO.getSocialCreditNo());
        if (oldEnterpriseEntity != null && !(oldEnterpriseEntity.getId().equals(enterpriseEntity.getId()))) {
            return R.fail("统一社会信用代码已存在");
        }

        //判断商户联系人1是否已存在
        EnterpriseWorkerEntity oldEnterpriseWorkerEntity1 = enterpriseWorkerService.findByPhoneNumber(updateEnterpriseDTO.getContact1Phone());
        if (oldEnterpriseWorkerEntity1 != null && !(oldEnterpriseWorkerEntity1.getEnterpriseId().equals(enterpriseEntity.getId()))) {
            return R.fail("联系人1电话/手机：" + updateEnterpriseDTO.getContact1Phone() + "已存在");
        }

        //判断商户联系人2是否已存在
        EnterpriseWorkerEntity oldEnterpriseWorkerEntity2 = enterpriseWorkerService.findByPhoneNumber(updateEnterpriseDTO.getContact2Phone());
        if (oldEnterpriseWorkerEntity2 != null && !(oldEnterpriseWorkerEntity2.getEnterpriseId().equals(enterpriseEntity.getId()))) {
            return R.fail("联系人2电话/手机：" + updateEnterpriseDTO.getContact2Phone() + "已存在");
        }

        BeanUtil.copy(updateEnterpriseDTO, enterpriseEntity);
        updateById(enterpriseEntity);

        //根据联系人1生成商户员工
        if (oldEnterpriseWorkerEntity1 == null) {
            EnterpriseContactRequest request = new EnterpriseContactRequest();
            request.setWorkerName(updateEnterpriseDTO.getContact1Name());
            request.setPhoneNumber(updateEnterpriseDTO.getContact1Phone());
            request.setEmployeeUserName(updateEnterpriseDTO.getContact1Phone());
            request.setPositionName(EnterprisePositionName.MANAGEMENT);
            request.setEmail(updateEnterpriseDTO.getContact1Mail());
            request.setAdminPower(true);
            R<String> result = enterpriseWorkerService.addNewEnterpriseWorker(request, enterpriseEntity.getId(), null);
            if (!(result.isSuccess())) {
                log.error(result.getMsg());
                throw new RuntimeException("添加联系人1为商户员工失败");
            }
        }

        //根据联系人2生成商户员工
        if (oldEnterpriseWorkerEntity2 == null) {
            EnterpriseContactRequest request = new EnterpriseContactRequest();
            request.setWorkerName(updateEnterpriseDTO.getContact2Name());
            request.setPhoneNumber(updateEnterpriseDTO.getContact2Phone());
            request.setEmployeeUserName(updateEnterpriseDTO.getContact2Phone());
            request.setPositionName(EnterprisePositionName.MANAGEMENT);
            request.setEmail(updateEnterpriseDTO.getContact2Mail());
            request.setAdminPower(true);
            R<String> result = enterpriseWorkerService.addNewEnterpriseWorker(request, enterpriseEntity.getId(), null);
            if (!(result.isSuccess())) {
                log.error(result.getMsg());
                throw new RuntimeException("添加联系人2为商户员工失败");
            }
        }

        AgreementEntity joinContract = agreementService.findSuccessAgreement(enterpriseEntity.getId(), AgreementType.ENTERPRISEJOINAGREEMENT, null, SignState.SIGNED);
        if (joinContract != null) {
            joinContract.setPaperAgreementUrl(updateEnterpriseDTO.getJoinContract());
            agreementService.updateById(joinContract);
        } else {
            //上传加盟合同
            agreementService.uploadAgreementByAdmin(ObjectType.ENTERPRISEPEOPLE, enterpriseEntity.getId(),
                    AgreementType.ENTERPRISEJOINAGREEMENT, updateEnterpriseDTO.getJoinContract());
        }

        AgreementEntity commitmentLetter = agreementService.findSuccessAgreement(enterpriseEntity.getId(), AgreementType.OTHERAGREEMENT, AuditState.APPROVED, null);
        if (commitmentLetter != null) {
            commitmentLetter.setPaperAgreementUrl(updateEnterpriseDTO.getCommitmentLetter());
            agreementService.updateById(commitmentLetter);
        } else {
            //上传商户承诺函
            agreementService.uploadAgreementByAdmin(ObjectType.ENTERPRISEPEOPLE, enterpriseEntity.getId(),
                    AgreementType.OTHERAGREEMENT, updateEnterpriseDTO.getCommitmentLetter());
        }

        return R.success("编辑商户成功");
    }

    @Override
    public R<IPage<QueryEnterpriseListEnterpriseVO>> queryEnterpriseListEnterprise(QueryEnterpriseListEnterpriseDTO queryEnterpriseListEnterpriseDTO, IPage<QueryEnterpriseListEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseListEnterprise(queryEnterpriseListEnterpriseDTO, page)));
    }

    @Override
    public R<QueryEnterpriseDetailEnterpriseVO> queryEnterpriseDetailEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseDetailEnterprise(enterpriseId));
    }

    @Override
    public R<String> updateEnterpriseState(Long enterpriseId, AccountState accountState) {

        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        enterpriseEntity.setEnterpriseState(accountState);
        save(enterpriseEntity);

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
