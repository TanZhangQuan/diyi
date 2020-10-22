package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.admin.AddPartnerDTO;
import com.lgyun.system.user.dto.admin.QueryPartnerDTO;
import com.lgyun.system.user.dto.admin.UpdatePartnerDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.mapper.PartnerMapper;
import com.lgyun.system.user.service.IPartnerService;
import com.lgyun.system.user.vo.admin.PartnerVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 合伙人信息表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class PartnerServiceImpl extends BaseServiceImpl<PartnerMapper, PartnerEntity> implements IPartnerService {


    public Boolean updateAgentState(Long agentMainId, AccountState accountState, Long updateUser) {
        PartnerEntity partnerEntity = baseMapper.selectById(agentMainId);
        partnerEntity.setUpdateUser(updateUser);
        partnerEntity.setUpdateTime(new Date());
        if (AccountState.NORMAL.equals(accountState)) {
            partnerEntity.setPartnerState(AccountState.NORMAL);
        } else if (AccountState.FREEZE.equals(accountState)) {
            partnerEntity.setPartnerState(AccountState.FREEZE);
        } else {
            partnerEntity.setPartnerState(AccountState.ILLEGAL);
        }
        return updateById(partnerEntity);
    }

    @Override
    public R<IPage<PartnerVO>> getPartnerList(IPage<PartnerVO> page, QueryPartnerDTO queryPartnerDTO) {
        return R.data(page.setRecords(baseMapper.getPartnerList(queryPartnerDTO, page)));
    }

    @Override
    public R updateIllegal(Long agentMainId, AdminEntity adminEntity) {
        return R.status(updateAgentState(agentMainId, AccountState.ILLEGAL, adminEntity.getUserId()));
    }

    @Override
    public R updateFreeze(Long agentMainId, AdminEntity adminEntity) {
        return R.status(updateAgentState(agentMainId, AccountState.FREEZE, adminEntity.getUserId()));
    }

    @Override
    public R updateNormal(Long agentMainId, AdminEntity adminEntity) {
        return R.status(updateAgentState(agentMainId, AccountState.NORMAL, adminEntity.getUserId()));
    }

    @Override
    public R addPartner(AddPartnerDTO addPartnerDTO, AdminEntity adminEntity) {
        Integer count = baseMapper.selectCount(new QueryWrapper<PartnerEntity>().eq("id_card_no", addPartnerDTO.getIdCardNo()));
        if (count > 0) {
            R.fail("已经存在" + addPartnerDTO.getIdCardNo() + "身份证创建的合伙人了");
        }
        count = baseMapper.selectCount(new QueryWrapper<PartnerEntity>().eq("phone_number", addPartnerDTO.getPhoneNumber()).eq("phone_number2", addPartnerDTO.getPhoneNumber()));
        if (count > 0) {
            R.fail("已经存在" + addPartnerDTO.getPhoneNumber() + "手机号创建的合伙人了");
        }
        PartnerEntity partnerEntity = new PartnerEntity();
        BeanUtil.copy(addPartnerDTO, partnerEntity);
        partnerEntity.setLoginPwd(DigestUtil.encrypt(addPartnerDTO.getLoginPwd()));
        partnerEntity.setCreateUser(adminEntity.getUserId());
        partnerEntity.setCreateTime(new Date());
        partnerEntity.setPartnerState(AccountState.NORMAL);
        save(partnerEntity);
        return R.fail("合伙人创建成功");
    }

    @Override
    public R updatePartner(UpdatePartnerDTO updatePartnerDTO, AdminEntity adminEntity) {
        PartnerEntity partnerEntity = baseMapper.selectById(updatePartnerDTO.getPartnerId());
        if (partnerEntity == null) {
            R.fail("不存在该合伙人");
        }
        BeanUtil.copy(updatePartnerDTO,partnerEntity);
        partnerEntity.setCreateTime(new Date());
        partnerEntity.setCreateUser(adminEntity.getUserId());
        updateById(partnerEntity);
        return R.fail("合伙人修改成功");
    }


}
