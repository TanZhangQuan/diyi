package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddPartnerDTO;
import com.lgyun.system.user.dto.PartnerListDTO;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.mapper.PartnerMapper;
import com.lgyun.system.user.service.IOnlineAgreementNeedSignService;
import com.lgyun.system.user.service.IOnlineAgreementTemplateService;
import com.lgyun.system.user.service.IPartnerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.PartnerInfoVO;
import com.lgyun.system.user.vo.PartnerListVO;
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
    public R<PartnerEntity> currentPartner(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        PartnerEntity partnerEntity = findByUserId(bladeUser.getUserId());
        if (partnerEntity == null) {
            return R.fail("合伙人不存在");
        }

        if (!(AccountState.NORMAL.equals(partnerEntity.getPartnerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(partnerEntity);
    }

    @Override
    public PartnerEntity findByUserId(Long userId) {
        QueryWrapper<PartnerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

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
                onlineAgreementNeedSignService.OnlineAgreementNeedSignAdd(onlineAgreementTemplateEntity.getId(), ObjectType.PARTNERPEOPLE, SignPower.PARTYB, partnerEntity.getId());
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
    public R<IPage<PartnerListVO>> queryPartnerList(PartnerListDTO partnerListDTO, IPage<PartnerListVO> page) {

        if (partnerListDTO.getBeginDate() != null && partnerListDTO.getEndDate() != null) {
            if (partnerListDTO.getBeginDate().after(partnerListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryPartnerList(partnerListDTO, page)));
    }

    @Override
    public R updatePartnerState(Long partnerId, AccountState partnerState) {

        PartnerEntity partnerEntity = getById(partnerId);
        if (partnerEntity == null) {
            return R.fail("合伙人不存在");
        }

        if (!(partnerEntity.getPartnerState().equals(partnerState))) {
            partnerEntity.setPartnerState(partnerState);
            updateById(partnerEntity);
        }

        return R.success("操作成功");
    }

    @Override
    public R createPartner(AddPartnerDTO addPartnerDTO) {

        Long introducePartnerId = null;
        if (StringUtils.isNotBlank(addPartnerDTO.getIntroducePartnerPhone())) {
            PartnerEntity partnerEntity = findByPhoneNumber(addPartnerDTO.getIntroducePartnerPhone());
            if (partnerEntity == null) {
                return R.fail("介绍合伙人不存在");
            }

            introducePartnerId = partnerEntity.getId();
        }

        partnerSave(addPartnerDTO.getPhoneNumber(), DigestUtil.encrypt(addPartnerDTO.getLoginPwd()), addPartnerDTO.getName(), addPartnerDTO.getIdcardNo(), addPartnerDTO.getBankCardNo(),
                addPartnerDTO.getBankName(), addPartnerDTO.getSubBankName(), introducePartnerId);

        return R.success("创建合伙人成功");
    }

    @Override
    public R<PartnerInfoVO> queryPartnerInfo(Long partnerId) {
        return R.data(baseMapper.queryPartnerInfo(partnerId));
    }

    @Override
    public int queryCountById(Long partnerId) {
        QueryWrapper<PartnerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerEntity::getId, partnerId);
        return baseMapper.selectCount(queryWrapper);
    }

}
