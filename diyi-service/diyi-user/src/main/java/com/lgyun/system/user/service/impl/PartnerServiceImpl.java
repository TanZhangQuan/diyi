package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddPartnerDTO;
import com.lgyun.system.user.dto.QueryPartnerDTO;
import com.lgyun.system.user.dto.UpdatePartnerDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.mapper.PartnerMapper;
import com.lgyun.system.user.service.IPartnerService;
import com.lgyun.system.user.vo.PartnerVO;
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


    @Override
    public R<IPage<PartnerVO>> getPartnerList(IPage<PartnerVO> page, QueryPartnerDTO queryPartnerDTO) {
        if (queryPartnerDTO.getBeginDate() != null && queryPartnerDTO.getEndDate() != null) {
            if (queryPartnerDTO.getBeginDate().after(queryPartnerDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间！");
            }
        }
        return R.data(page.setRecords(baseMapper.getPartnerList(queryPartnerDTO, page)));
    }

    @Override
    public R updateIllegal(Long partnerId, AccountState accountState,AdminEntity adminEntity) {
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

        Integer count = baseMapper.selectCount(new QueryWrapper<PartnerEntity>().eq("id_card_no", addPartnerDTO.getIdCardNo()));
        if (count > 0) {
            R.fail("你的身份证" + addPartnerDTO.getIdCardNo() + "已经创建合伙人了！");
        }

        count = baseMapper.selectCount(new QueryWrapper<PartnerEntity>().eq("phone_number", addPartnerDTO.getPhoneNumber()));
        if (count > 0) {
            R.fail("你的手机号" + addPartnerDTO.getPhoneNumber() + "已经创建合伙人了！");
        }

        PartnerEntity partnerEntity = new PartnerEntity();
        BeanUtil.copy(addPartnerDTO, partnerEntity);
        partnerEntity.setLoginPwd(DigestUtil.encrypt(addPartnerDTO.getLoginPwd()));
        partnerEntity.setCreateUser(adminEntity.getUserId());
        partnerEntity.setCreateTime(new Date());
        partnerEntity.setPartnerState(AccountState.NORMAL);
        save(partnerEntity);
        return R.fail("合伙人创建成功！");
    }

    @Override
    public R updatePartner(UpdatePartnerDTO updatePartnerDTO, AdminEntity adminEntity) {
        PartnerEntity partnerEntity = baseMapper.selectById(updatePartnerDTO.getPartnerId());
        if (partnerEntity == null) {
            R.fail("不存在该合伙人！");
        }
        BeanUtil.copy(updatePartnerDTO, partnerEntity);
        partnerEntity.setCreateTime(new Date());
        partnerEntity.setCreateUser(adminEntity.getUserId());
        updateById(partnerEntity);
        return R.fail("合伙人修改成功！");
    }


}
