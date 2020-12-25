package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.ServiceProviderRuleEntity;
import com.lgyun.system.user.mapper.ServiceProviderRulesMapper;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.service.IServiceProviderRulesService;
import com.lgyun.system.user.vo.ServiceProviderRuleVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * 服务商-业务合规要求表 Service 实现
 *
 * @author tzq
 * @since 2020-12-01 10:55:04
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderRulesServiceImpl extends BaseServiceImpl<ServiceProviderRulesMapper, ServiceProviderRuleEntity> implements IServiceProviderRulesService {

    private IMakerService makerService;
    private IAgreementService agreementService;
    private IEnterpriseService enterpriseService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrUpdateServiceProviderRule(Long serviceProviderId, Set<MakerRule> makerRuleSet, Set<EnterpriseRule> enterpriseRuleSet) {

        ServiceProviderRuleEntity serviceProviderRuleEntity = queryByServiceProvider(serviceProviderId);
        if (serviceProviderRuleEntity == null) {
            serviceProviderRuleEntity = new ServiceProviderRuleEntity();
        }

        serviceProviderRuleEntity.setServiceProviderId(serviceProviderId);

        //处理创客业务规则
        StringBuffer makerRuleBuffer = new StringBuffer();
        if (makerRuleSet != null && !(makerRuleSet.isEmpty())) {
            for (int i = 0; i < makerRuleSet.size(); i++) {
                MakerRule makerRule = (MakerRule) makerRuleSet.toArray()[i];
                if (i == 0) {
                    makerRuleBuffer.append(makerRule.getValue());
                } else {
                    makerRuleBuffer.append(",").append(makerRule.getValue());
                }
            }
        }
        serviceProviderRuleEntity.setMakerRules(String.valueOf(makerRuleBuffer));

        //处理商户业务规则
        StringBuffer enterpriseRuleBuffer = new StringBuffer();
        if (enterpriseRuleSet != null && !(enterpriseRuleSet.isEmpty())) {
            for (int i = 0; i < enterpriseRuleSet.size(); i++) {
                EnterpriseRule enterpriseRule = (EnterpriseRule) enterpriseRuleSet.toArray()[i];
                if (i == 0) {
                    enterpriseRuleBuffer.append(enterpriseRule.getValue());
                } else {
                    enterpriseRuleBuffer.append(",").append(enterpriseRule.getValue());
                }
            }
        }
        serviceProviderRuleEntity.setEnterpriseRules(String.valueOf(enterpriseRuleBuffer));

        saveOrUpdate(serviceProviderRuleEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public ServiceProviderRuleEntity queryByServiceProvider(Long serviceProviderId) {
        QueryWrapper<ServiceProviderRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderRuleEntity::getServiceProviderId, serviceProviderId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<ServiceProviderRuleVO> queryServiceProviderRule(Long serviceProviderId) {

        //查询服务商-创客业务规则
        Set<MakerRule> makerRuleSet = queryMakerRuleByServiceProvider(serviceProviderId);
        //查询服务商-商户业务规则
        Set<EnterpriseRule> enterpriseRuleSet = queryEnterpriseRuleByServiceProvider(serviceProviderId);

        ServiceProviderRuleVO serviceProviderRuleVO = new ServiceProviderRuleVO();
        serviceProviderRuleVO.setMakerRuleSet(makerRuleSet);
        serviceProviderRuleVO.setEnterpriseRuleSet(enterpriseRuleSet);

        return R.data(serviceProviderRuleVO);
    }

    @Override
    public Set<MakerRule> queryMakerRuleByServiceProvider(Long serviceProviderId) {
        Set<MakerRule> makerRuleSet = new HashSet<>();
        String makerRules = baseMapper.queryMakerRuleByServiceProvider(serviceProviderId);
        if (StringUtils.isNotBlank(makerRules)) {
            //解析服务商-创客规则
            String[] split = makerRules.split(",");
            for (String string : split) {
                if (StringUtils.isNotBlank(string)) {
                    makerRuleSet.add(Enum.valueOf(MakerRule.class, string));
                }
            }
        }

        return makerRuleSet;
    }

    @Override
    public Set<EnterpriseRule> queryEnterpriseRuleByServiceProvider(Long serviceProviderId) {

        Set<EnterpriseRule> enterpriseRuleSet = new HashSet<>();
        String enterpriseRules = baseMapper.queryEnterpriseRuleByServiceProvider(serviceProviderId);
        if (StringUtils.isNotBlank(enterpriseRules)) {
            //解析服务商-创客规则
            String[] split = enterpriseRules.split(",");
            for (String string : split) {
                if (StringUtils.isNotBlank(string)) {
                    enterpriseRuleSet.add(Enum.valueOf(EnterpriseRule.class, string));
                }
            }
        }

        return enterpriseRuleSet;
    }

    @Override
    public R<String> dealMakerRule(Long serviceProviderId, Long makerId) {
        MakerEntity makerEntity = makerService.getById(makerId);
        Set<MakerRule> makerRuleSet = queryMakerRuleByServiceProvider(serviceProviderId);
        for (MakerRule makerRule : makerRuleSet) {

            switch (makerRule) {

                case IDCARDVERIFY:

                    if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
                        return R.fail("未身份证验证");
                    }
                    break;

                case EMPOWERVIDEO:

                    if (!(VideoAudit.AUDITPASS.equals(makerEntity.getVideoAudit()))) {
                        return R.fail("无审核通过的授权视频");
                    }
                    break;

                case FACEVERIFY:

                    if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
                        return R.fail("未活体认证");
                    }
                    break;

                case BANKCARDVERIFY:

                    if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getBankCardVerifyStatus()))) {
                        return R.fail("未银行卡验证");
                    }
                    break;

                case PHONENUMBERVERIFY:

                    if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getPhoneNumberVerifyStatus()))) {
                        return R.fail("未手机号验证");
                    }
                    break;

                case EMPOWERSIGN:

                    int empowersignNum = agreementService.queryValidAgreementNum(null, null, ObjectType.MAKERPEOPLE, makerId, AgreementType.MAKERPOWERATTORNEY);
                    if (empowersignNum <= 0) {
                        return R.fail("未有有效的创客授权书");
                    }
                    break;

                default:
                    break;
            }
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<String> dealEnterpriseRule(Long serviceProviderId, Long enterpriseId) {
        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseId);
        Set<EnterpriseRule> enterpriseRuleSet = queryEnterpriseRuleByServiceProvider(serviceProviderId);
        for (EnterpriseRule enterpriseRule : enterpriseRuleSet) {

            switch (enterpriseRule) {

                case BIZLICENCE:

                    if (StringUtils.isBlank(enterpriseEntity.getBizLicenceUrl())) {
                        return R.fail("商户未上传营业执照");
                    }
                    break;

                case COMMITMENTLETTER:

                    int empowersignNum = agreementService.queryValidAgreementNum(null, null, ObjectType.ENTERPRISEPEOPLE, enterpriseId, AgreementType.ENTERPRISEPROMISE);
                    if (empowersignNum <= 0) {
                        return R.fail("商户未有有效的商户业务真实性承诺函");
                    }
                    break;

                default:
                    break;
            }
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
