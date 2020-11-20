package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddPartnerDTO;
import com.lgyun.system.user.dto.QueryPartnerDTO;
import com.lgyun.system.user.dto.UpdatePartnerDTO;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.PartnerMapper;
import com.lgyun.system.user.service.IOnlineAgreementNeedSignService;
import com.lgyun.system.user.service.IOnlineAgreementTemplateService;
import com.lgyun.system.user.service.IPartnerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.PartnerVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * 合伙人信息表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class PartnerServiceImpl extends BaseServiceImpl<PartnerMapper, PartnerEntity> implements IPartnerService {

    private IUserService userService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private IOnlineAgreementTemplateService onlineAgreementTemplateService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PartnerEntity partnerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd) {
        return partnerSave(openid, sessionKey, purePhoneNumber, loginPwd, "", "", "", "", "", null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PartnerEntity partnerSave(String purePhoneNumber, String loginPwd, String name, String idcardNo, String bankCardNo, String bankName, String subBankName, Long introducePartnerId) {
        return partnerSave("", "", purePhoneNumber, loginPwd, name, idcardNo, bankCardNo, bankName, subBankName, introducePartnerId);
    }

    @Transactional(rollbackFor = Exception.class)
    public PartnerEntity partnerSave(String openid, String sessionKey, String purePhoneNumber, String loginPwd, String name,
                                     String idcardNo, String bankCardNo, String bankName, String subBankName, Long introducePartnerId) {

        PartnerEntity partnerEntity;
        PartnerEntity partnerEntityPhoneNumber = findByPhoneNumber(purePhoneNumber);
        PartnerEntity partnerEntityIdcardNo = findByIdcardNo(idcardNo);
        if (partnerEntityPhoneNumber == null && partnerEntityIdcardNo == null) {
            //新建管理员
            User user = new User();
            user.setUserType(UserType.PARTNER);
            user.setAccount(purePhoneNumber);
            user.setPhone(purePhoneNumber);
            userService.save(user);

            //新建合伙人
            partnerEntity = new PartnerEntity();
            //微信登陆新建合伙人
            if (StringUtils.isNotBlank(openid) && StringUtils.isNotBlank(sessionKey)) {
                partnerEntity.setOpenid(openid);
                partnerEntity.setSessionKey(sessionKey);
                partnerEntity.setRelDate(new Date());
            }

            partnerEntity.setIntroducePartnerId(introducePartnerId);
            partnerEntity.setUserId(user.getId());
            partnerEntity.setPhoneNumber(purePhoneNumber);
            if (StringUtil.isNotBlank(loginPwd)) {
                partnerEntity.setLoginPwd(loginPwd);
            } else {
                partnerEntity.setLoginPwd(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
            }
            partnerEntity.setName(name);
            partnerEntity.setIdcardNo(idcardNo);
            partnerEntity.setBankCardNo(bankCardNo);
            partnerEntity.setBankName(bankName);
            partnerEntity.setSubBankName(subBankName);
            save(partnerEntity);

            //新建合伙人添加加盟合同需要签署的模板
            OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = onlineAgreementTemplateService.findTemplateType(AgreementType.PARTNERJOINAGREEMENT, 0);
            if (onlineAgreementTemplateEntity != null) {
                onlineAgreementNeedSignService.OnlineAgreementNeedSignAdd(onlineAgreementTemplateEntity.getId(), ObjectType.PARTNERSHIPPEOPLE, SignPower.PARTYB, partnerEntity.getId());
            }

        } else if (partnerEntityPhoneNumber != null && partnerEntityIdcardNo == null) {
            if (!(VerifyStatus.VERIFYPASS.equals(partnerEntityPhoneNumber.getIdcardVerifyStatus()))) {
                partnerEntityPhoneNumber.setName(name);
                partnerEntityPhoneNumber.setIdcardNo(idcardNo);
                partnerEntityPhoneNumber.setBankCardNo(bankCardNo);
                partnerEntityPhoneNumber.setBankName(bankName);
                partnerEntityPhoneNumber.setSubBankName(subBankName);
                updateById(partnerEntityPhoneNumber);
            } else {
                if (!(VerifyStatus.VERIFYPASS.equals(partnerEntityPhoneNumber.getBankCardVerifyStatus()))) {
                    partnerEntityPhoneNumber.setBankCardNo(bankCardNo);
                    partnerEntityPhoneNumber.setBankName(bankName);
                    partnerEntityPhoneNumber.setSubBankName(subBankName);
                    updateById(partnerEntityPhoneNumber);
                }
            }

            partnerEntity = partnerEntityPhoneNumber;
        } else {

            if (!(VerifyStatus.VERIFYPASS.equals(partnerEntityIdcardNo.getIdcardVerifyStatus()))) {
                partnerEntityIdcardNo.setName(name);
                partnerEntityIdcardNo.setIdcardNo(idcardNo);
                partnerEntityIdcardNo.setBankCardNo(bankCardNo);
                partnerEntityIdcardNo.setBankName(bankName);
                partnerEntityIdcardNo.setSubBankName(subBankName);
                updateById(partnerEntityIdcardNo);
            } else {
                if (!(VerifyStatus.VERIFYPASS.equals(partnerEntityIdcardNo.getBankCardVerifyStatus()))) {
                    partnerEntityIdcardNo.setBankCardNo(bankCardNo);
                    partnerEntityIdcardNo.setBankName(bankName);
                    partnerEntityIdcardNo.setSubBankName(subBankName);
                    updateById(partnerEntityIdcardNo);
                }
            }

            partnerEntity = partnerEntityIdcardNo;
        }

        return partnerEntity;
    }

    @Override
    public PartnerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<PartnerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PartnerEntity findByIdcardNo(String idcardNo) {
        QueryWrapper<PartnerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerEntity::getIdcardNo, idcardNo);

        return baseMapper.selectOne(queryWrapper);
    }


    @Override
    public PartnerEntity findByPhoneNumberAndLoginPwd(String phoneNumber, String loginPwd) {
        QueryWrapper<PartnerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerEntity::getPhoneNumber, phoneNumber)
                .eq(PartnerEntity::getLoginPwd, loginPwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void partnerUpdate(PartnerEntity partnerEntity, String openid, String sessionKey) {
        //更新微信信息
        partnerEntity.setOpenid(openid);
        partnerEntity.setSessionKey(sessionKey);
        updateById(partnerEntity);
    }

    @Override
    public R<IPage<PartnerVO>> getPartnerList(IPage<PartnerVO> page, QueryPartnerDTO queryPartnerDTO) {
        if (queryPartnerDTO.getBeginDate() != null && queryPartnerDTO.getEndDate() != null) {
            if (queryPartnerDTO.getBeginDate().after(queryPartnerDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }
        return R.data(page.setRecords(baseMapper.getPartnerList(queryPartnerDTO, page)));
    }

    @Override
    public R updateIllegal(Long partnerId, AccountState accountState, AdminEntity adminEntity) {
        PartnerEntity partnerEntity = baseMapper.selectById(partnerId);
        partnerEntity.setUpdateUser(adminEntity.getUserId());
        partnerEntity.setUpdateTime(new Date());
        if (AccountState.NORMAL.equals(accountState)) {
            partnerEntity.setPartnerState(AccountState.NORMAL);
        } else if (AccountState.FREEZE.equals(accountState)) {
            partnerEntity.setPartnerState(AccountState.FREEZE);
        } else {
            partnerEntity.setPartnerState(AccountState.ILLEGAL);
        }
        this.updateById(partnerEntity);
        return null;
    }


    @Override
    public R addPartner(AddPartnerDTO addPartnerDTO, AdminEntity adminEntity) {

        partnerSave(addPartnerDTO.getPhoneNumber(), DigestUtil.encrypt(addPartnerDTO.getLoginPwd()), addPartnerDTO.getName(), addPartnerDTO.getIdcardNo(), addPartnerDTO.getBankCardNo(),
                addPartnerDTO.getBankName(), addPartnerDTO.getSubBankName(), addPartnerDTO.getIntroducePartnerId());

        return R.fail("合伙人创建成功");
    }

    @Override
    public R updatePartner(UpdatePartnerDTO updatePartnerDTO, AdminEntity adminEntity) {
        PartnerEntity partnerEntity = baseMapper.selectById(updatePartnerDTO.getPartnerId());
        if (partnerEntity == null) {
            R.fail("不存在该合伙人");
        }
        BeanUtil.copy(updatePartnerDTO, partnerEntity);
        partnerEntity.setCreateTime(new Date());
        partnerEntity.setCreateUser(adminEntity.getUserId());
        updateById(partnerEntity);
        return R.fail("合伙人修改成功");
    }


}
