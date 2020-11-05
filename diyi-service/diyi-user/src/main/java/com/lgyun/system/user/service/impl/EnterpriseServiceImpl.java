package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddOrUpdateEnterpriseDTO;
import com.lgyun.system.user.dto.QueryEnterpriseListDTO;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
    public R<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName) {
        return R.data(baseMapper.getEnterpriseName(enterpriseName));
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId) {
        int relevanceNum = makerEnterpriseService.queryMakerEnterpriseNum(enterpriseId, makerId, RelationshipType.RELEVANCE);
        int attentionNum = makerEnterpriseService.queryMakerEnterpriseNum(enterpriseId, makerId, RelationshipType.ATTENTION);
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
            makerEnterpriseRelationVO.setLegalPersonIdcard("*********");
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
            throw new CustomException("上传文件内容为空");
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
    public R<String> createOrUpdateEnterprise(AddOrUpdateEnterpriseDTO addOrUpdateEnterpriseDTO, AdminEntity adminEntity) {

        if (addOrUpdateEnterpriseDTO.getEnterpriseId() == null) {

            if (StringUtils.isBlank(addOrUpdateEnterpriseDTO.getEmployeePwd())) {
                return R.fail("请输入密码");
            } else {

                if (addOrUpdateEnterpriseDTO.getEmployeePwd().length() <= 6 || addOrUpdateEnterpriseDTO.getEmployeePwd().length() >= 18) {
                    return R.fail("请输入长度为6-18位的密码");
                }

                addOrUpdateEnterpriseDTO.setEmployeePwd(DigestUtil.encrypt(addOrUpdateEnterpriseDTO.getEmployeePwd()));
            }

            //判断商户名称是否已存在
            int enterpriseNum = count(Wrappers.<EnterpriseEntity>query().lambda().eq(EnterpriseEntity::getEnterpriseName, addOrUpdateEnterpriseDTO.getEnterpriseName()));
            if (enterpriseNum > 0) {
                return R.fail("商户名称已存在");
            }

            //判断社会信用代码是否已存在
            enterpriseNum = count(Wrappers.<EnterpriseEntity>query().lambda().eq(EnterpriseEntity::getSocialCreditNo, addOrUpdateEnterpriseDTO.getSocialCreditNo()));
            if (enterpriseNum > 0) {
                return R.fail("商户统一社会信用代码已存在");
            }

            int serviceProviderWorkerNum = enterpriseWorkerService.count(Wrappers.<EnterpriseWorkerEntity>query().lambda().eq(EnterpriseWorkerEntity::getEmployeeUserName, addOrUpdateEnterpriseDTO.getEmployeeUserName()));
            if (serviceProviderWorkerNum > 0) {
                return R.fail("已存在相同用户名的管理员");
            }

            serviceProviderWorkerNum = enterpriseWorkerService.count(Wrappers.<EnterpriseWorkerEntity>query().lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, addOrUpdateEnterpriseDTO.getPhoneNumber()));
            if (serviceProviderWorkerNum > 0) {
                return R.fail("已存在相同手机号的管理员");
            }

            EnterpriseEntity enterpriseEntity = new EnterpriseEntity();
            enterpriseEntity.setRunnerId(adminEntity.getId());
            enterpriseEntity.setSalerId(adminEntity.getId());
            enterpriseEntity.setCreateType(CreateType.PLATFORMCREATE);
            enterpriseEntity.setInviteNo(addOrUpdateEnterpriseDTO.getPhoneNumber());
            BeanUtil.copy(addOrUpdateEnterpriseDTO, enterpriseEntity);
            save(enterpriseEntity);

            //新建联系人员工
            User user = new User();
            user.setUserType(UserType.ENTERPRISE);
            user.setAccount(addOrUpdateEnterpriseDTO.getEmployeeUserName());
            user.setPhone(addOrUpdateEnterpriseDTO.getPhoneNumber());
            userService.save(user);

            EnterpriseWorkerEntity enterpriseWorkerEntity = new EnterpriseWorkerEntity();
            enterpriseWorkerEntity.setUserId(user.getId());
            enterpriseWorkerEntity.setPositionName(PositionName.MANAGEMENT);
            enterpriseWorkerEntity.setAdminPower(true);
            BeanUtil.copy(addOrUpdateEnterpriseDTO, enterpriseWorkerEntity);
            enterpriseWorkerEntity.setEnterpriseId(enterpriseEntity.getId());
            enterpriseWorkerService.save(enterpriseWorkerEntity);

            //上传商户加盟合同
            AgreementEntity agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.ENTERPRISEJOINAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setPaperAgreementUrl(addOrUpdateEnterpriseDTO.getJoinContract());
            agreementEntity.setFirstSideSignPerson("地衣众包平台");
            agreementEntity.setEnterpriseId(enterpriseEntity.getId());
            agreementEntity.setSecondSideSignPerson(enterpriseEntity.getEnterpriseName());
            agreementService.save(agreementEntity);

            //上传商户承诺函
            String[] split = addOrUpdateEnterpriseDTO.getCommitmentLetters().split(",");
            for (int i = 0; i < split.length; i++) {
                if (StringUtils.isNotBlank(split[i])) {
                    agreementEntity = new AgreementEntity();
                    agreementEntity.setAgreementType(AgreementType.ENTERPRISEPROMISE);
                    agreementEntity.setSignType(SignType.PAPERAGREEMENT);
                    agreementEntity.setSignState(SignState.SIGNED);
                    agreementEntity.setAuditState(AuditState.APPROVED);
                    agreementEntity.setPaperAgreementUrl(split[i]);
                    agreementEntity.setFirstSideSignPerson("地衣众包平台");
                    agreementEntity.setEnterpriseId(enterpriseEntity.getId());
                    agreementEntity.setSecondSideSignPerson(enterpriseEntity.getEnterpriseName());
                    agreementService.save(agreementEntity);
                }
            }

            return R.success("新建商户成功");

        } else {

            EnterpriseEntity enterpriseEntity = getById(addOrUpdateEnterpriseDTO.getEnterpriseId());
            if (enterpriseEntity == null) {
                return R.fail("商户不存在");
            }

            //查询商户管理员
            EnterpriseWorkerEntity enterpriseWorkerEntity = enterpriseWorkerService.getOne(Wrappers.<EnterpriseWorkerEntity>query().lambda()
                    .eq(EnterpriseWorkerEntity::getEnterpriseId, enterpriseEntity.getId())
                    .isNull(EnterpriseWorkerEntity::getUpLevelId));

            if (StringUtils.isNotBlank(addOrUpdateEnterpriseDTO.getEmployeePwd())) {
                if (addOrUpdateEnterpriseDTO.getEmployeePwd().length() <= 6 || addOrUpdateEnterpriseDTO.getEmployeePwd().length() >= 18) {
                    return R.fail("请输入长度为6-18位的密码");
                }

                addOrUpdateEnterpriseDTO.setEmployeePwd(DigestUtil.encrypt(addOrUpdateEnterpriseDTO.getEmployeePwd()));
            } else {
                addOrUpdateEnterpriseDTO.setEmployeePwd(enterpriseWorkerEntity.getEmployeePwd());
            }

            //判断商户名称是否已存在
            int enterpriseNum = count(Wrappers.<EnterpriseEntity>query().lambda()
                    .eq(EnterpriseEntity::getEnterpriseName, addOrUpdateEnterpriseDTO.getEnterpriseName())
                    .ne(EnterpriseEntity::getId, enterpriseEntity.getId()));
            if (enterpriseNum > 0) {
                return R.fail("商户名称已存在");
            }

            //判断社会信用代码是否已存在
            enterpriseNum = count(Wrappers.<EnterpriseEntity>query().lambda()
                    .eq(EnterpriseEntity::getSocialCreditNo, addOrUpdateEnterpriseDTO.getSocialCreditNo())
                    .ne(EnterpriseEntity::getId, enterpriseEntity.getId()));
            if (enterpriseNum > 0) {
                return R.fail("商户统一社会信用代码已存在");
            }

            int enterpriseWorkerNum = enterpriseWorkerService.count(Wrappers.<EnterpriseWorkerEntity>query().lambda()
                    .eq(EnterpriseWorkerEntity::getEmployeeUserName, addOrUpdateEnterpriseDTO.getEmployeeUserName())
                    .ne(EnterpriseWorkerEntity::getId, enterpriseWorkerEntity.getId()));
            if (enterpriseWorkerNum > 0) {
                return R.fail("已存在相同用户名的管理员");
            }

            enterpriseWorkerNum = enterpriseWorkerService.count(Wrappers.<EnterpriseWorkerEntity>query().lambda()
                    .eq(EnterpriseWorkerEntity::getPhoneNumber, addOrUpdateEnterpriseDTO.getPhoneNumber())
                    .ne(EnterpriseWorkerEntity::getId, enterpriseWorkerEntity.getId()));
            if (enterpriseWorkerNum > 0) {
                return R.fail("已存在相同手机号的管理员");
            }

            //编辑商户员工
            BeanUtil.copy(addOrUpdateEnterpriseDTO, enterpriseWorkerEntity);
            enterpriseWorkerService.updateById(enterpriseWorkerEntity);

            //上传加盟合同
            AgreementEntity agreementEntity = agreementService.getOne(Wrappers.<AgreementEntity>query().lambda()
                    .eq(AgreementEntity::getEnterpriseId, enterpriseEntity.getId())
                    .eq(AgreementEntity::getAgreementType, AgreementType.ENTERPRISEJOINAGREEMENT)
                    .eq(AgreementEntity::getSignState, SignState.SIGNED)
                    .eq(AgreementEntity::getAuditState, AuditState.APPROVED));

            agreementEntity.setPaperAgreementUrl(addOrUpdateEnterpriseDTO.getJoinContract());
            agreementService.updateById(agreementEntity);

            //删除已上传的商户承诺函
            agreementService.deleteByEnterprise(enterpriseEntity.getId(), AgreementType.ENTERPRISEPROMISE);

            //上传商户承诺函
            String[] split = addOrUpdateEnterpriseDTO.getCommitmentLetters().split(",");
            for (int i = 0; i < split.length; i++) {
                if (StringUtils.isNotBlank(split[i])) {
                    agreementEntity = new AgreementEntity();
                    agreementEntity.setAgreementType(AgreementType.ENTERPRISEPROMISE);
                    agreementEntity.setSignType(SignType.PAPERAGREEMENT);
                    agreementEntity.setSignState(SignState.SIGNED);
                    agreementEntity.setAuditState(AuditState.APPROVED);
                    agreementEntity.setPaperAgreementUrl(split[i]);
                    agreementEntity.setFirstSideSignPerson("地衣众包平台");
                    agreementEntity.setEnterpriseId(enterpriseEntity.getId());
                    agreementEntity.setSecondSideSignPerson(enterpriseEntity.getEnterpriseName());
                    agreementService.save(agreementEntity);
                }
            }

            BeanUtil.copy(addOrUpdateEnterpriseDTO, enterpriseEntity);
            updateById(enterpriseEntity);

            return R.success("编辑商户成功");

        }

    }

    @Override
    public R<IPage<EnterpriseListEnterpriseVO>> queryEnterpriseListEnterprise(QueryEnterpriseListDTO queryEnterpriseListDTO, IPage<EnterpriseListEnterpriseVO> page) {

        if (queryEnterpriseListDTO.getBeginDate() != null && queryEnterpriseListDTO.getEndDate() != null) {
            if (queryEnterpriseListDTO.getBeginDate().after(queryEnterpriseListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryEnterpriseListEnterprise(queryEnterpriseListDTO, page)));
    }

    @Override
    public R<EnterpriseUpdateDetailVO> queryEnterpriseUpdateDetail(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseUpdateDetail(enterpriseId));
    }

    @Override
    public R<String> updateEnterpriseState(Long enterpriseId, AccountState enterpriseState) {

        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        if (!(enterpriseEntity.getEnterpriseState().equals(enterpriseState))) {
            enterpriseEntity.setEnterpriseState(enterpriseState);
            updateById(enterpriseEntity);
        }

        return R.success("操作成功");
    }

    @Override
    public R getEnterpriseAll(Long enterpriseId,String enterpriseName,IPage<EnterpriseEntity> page) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(enterpriseName) && null != enterpriseId){
            queryWrapper.lambda().eq(EnterpriseEntity::getId,enterpriseId)
                    .like(EnterpriseEntity::getEnterpriseName,enterpriseName);
        }
        if(null != enterpriseId && StringUtils.isEmpty(enterpriseName)){
            queryWrapper.lambda().eq(EnterpriseEntity::getId,enterpriseId);
        }
        if(StringUtils.isNotEmpty(enterpriseName) && null == enterpriseId){
            queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName,enterpriseName);
        }
        IPage<EnterpriseEntity> enterpriseEntityIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(enterpriseEntityIPage);
    }

}
