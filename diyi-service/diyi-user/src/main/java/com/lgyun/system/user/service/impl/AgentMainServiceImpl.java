package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CreateType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.user.dto.AgentMainListDTO;
import com.lgyun.system.user.dto.ContactsInfoDTO;
import com.lgyun.system.user.dto.CreateAgentMainDTO;
import com.lgyun.system.user.dto.UpdateAgentMainDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.mapper.AgentMainMapper;
import com.lgyun.system.user.service.IAgentMainService;
import com.lgyun.system.user.service.IAgentMainWorkerService;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 渠道商信息表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentMainServiceImpl extends BaseServiceImpl<AgentMainMapper, AgentMainEntity> implements IAgentMainService {

    private IOrderClient orderClient;
    private IAgentMainWorkerService agentMainWorkerService;

    @Override
    public int queryCountById(Long agentMainId) {
        QueryWrapper<AgentMainEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainEntity::getId, agentMainId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<IPage<AgentMainListAdminVO>> getAgentMainListAdmin(AgentMainListDTO agentMainListDTO, IPage<AgentMainListAdminVO> page) {
        if (agentMainListDTO.getBeginDate() != null && agentMainListDTO.getEndDate() != null) {
            if (agentMainListDTO.getBeginDate().after(agentMainListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getAgentMainListAdmin(agentMainListDTO, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateAgentMainState(Long agentMainId, AccountState agentMainState) {

        AgentMainEntity agentMainEntity = getById(agentMainId);
        if (agentMainEntity == null) {
            return R.fail("渠道商不存在");
        }

        if (!(agentMainEntity.getAgentMainState().equals(agentMainState))) {
            agentMainEntity.setAgentMainState(agentMainState);
            updateById(agentMainEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createAgentMain(CreateAgentMainDTO createAgentMainDTO, AdminEntity adminEntity) {
        //判断渠道商名称是否已存在
        int agentMainNum = count(Wrappers.<AgentMainEntity>query().lambda().eq(AgentMainEntity::getAgentMainName, createAgentMainDTO.getAgentMainName()));
        if (agentMainNum > 0) {
            return R.fail("渠道商名称已存在");
        }

        //判断社会信用代码是否已存在
        agentMainNum = count(Wrappers.<AgentMainEntity>query().lambda().eq(AgentMainEntity::getSocialCreditNo, createAgentMainDTO.getSocialCreditNo()));
        if (agentMainNum > 0) {
            return R.fail("渠道商统一社会信用代码已存在");
        }

        int agentMainWorkerNum = agentMainWorkerService.count(Wrappers.<AgentMainWorkerEntity>query().lambda().eq(AgentMainWorkerEntity::getEmployeeUserName, createAgentMainDTO.getEmployeeUserName()));
        if (agentMainWorkerNum > 0) {
            return R.fail("已存在相同用户名的管理员");
        }

        agentMainWorkerNum = agentMainWorkerService.count(Wrappers.<AgentMainWorkerEntity>query().lambda().eq(AgentMainWorkerEntity::getPhoneNumber, createAgentMainDTO.getPhoneNumber()));
        if (agentMainWorkerNum > 0) {
            return R.fail("已存在相同手机号的管理员");
        }

        AgentMainEntity AgentMainEntity = new AgentMainEntity();
        AgentMainEntity.setCreateType(CreateType.PLATFORMCREATE);
        BeanUtil.copy(createAgentMainDTO, AgentMainEntity);
        save(AgentMainEntity);

        //保存收件地址
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setObjectId(AgentMainEntity.getId());
        addressEntity.setObjectType(ObjectType.AGENTMAINPEOPLE);
        addressEntity.setBoolDefault(true);
        BeanUtils.copyProperties(createAgentMainDTO, addressEntity);
        orderClient.createAddress(addressEntity);

        //密码加密
        createAgentMainDTO.setEmployeePwd(DigestUtil.encrypt(createAgentMainDTO.getEmployeePwd()));

        AgentMainWorkerEntity agentMainWorkerEntity = new AgentMainWorkerEntity();
        agentMainWorkerEntity.setPositionName(PositionName.MANAGEMENT);
        agentMainWorkerEntity.setSuperAdmin(true);
        agentMainWorkerEntity.setAdminPower(true);
        BeanUtil.copy(createAgentMainDTO, agentMainWorkerEntity);
        agentMainWorkerEntity.setAgentMainId(AgentMainEntity.getId());
        agentMainWorkerService.save(agentMainWorkerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateAgentMain(UpdateAgentMainDTO updateAgentMainDTO, AdminEntity adminEntity) {

        AgentMainEntity agentMainEntity = getById(updateAgentMainDTO.getAgentMainId());
        if (agentMainEntity == null) {
            return R.fail("渠道商不存在");
        }

        //查询渠道商管理员
        AgentMainWorkerEntity agentMainWorkerEntity = agentMainWorkerService.getOne(Wrappers.<AgentMainWorkerEntity>query().lambda()
                .eq(AgentMainWorkerEntity::getAgentMainId, agentMainEntity.getId())
                .isNull(AgentMainWorkerEntity::getUpLevelId));

        if (agentMainWorkerEntity == null) {
            return R.fail("渠道商管理员不存在");
        }

        if (StringUtils.isNotBlank(updateAgentMainDTO.getEmployeePwd())) {
            if (updateAgentMainDTO.getEmployeePwd().length() < 6 || updateAgentMainDTO.getEmployeePwd().length() > 18) {
                return R.fail("请输入长度为6-18位的密码");
            }

            updateAgentMainDTO.setEmployeePwd(DigestUtil.encrypt(updateAgentMainDTO.getEmployeePwd()));
        } else {
            updateAgentMainDTO.setEmployeePwd(agentMainWorkerEntity.getEmployeePwd());
        }

        //判断渠道商名称是否已存在
        int agentMainNum = count(Wrappers.<AgentMainEntity>query().lambda()
                .eq(AgentMainEntity::getAgentMainName, updateAgentMainDTO.getAgentMainName())
                .ne(AgentMainEntity::getId, agentMainEntity.getId()));
        if (agentMainNum > 0) {
            return R.fail("渠道商名称已存在");
        }

        //判断社会信用代码是否已存在
        agentMainNum = count(Wrappers.<AgentMainEntity>query().lambda()
                .eq(AgentMainEntity::getSocialCreditNo, updateAgentMainDTO.getSocialCreditNo())
                .ne(AgentMainEntity::getId, agentMainEntity.getId()));
        if (agentMainNum > 0) {
            return R.fail("渠道商统一社会信用代码已存在");
        }

        int agentMainNumWorkerNum = agentMainWorkerService.count(Wrappers.<AgentMainWorkerEntity>query().lambda()
                .eq(AgentMainWorkerEntity::getEmployeeUserName, updateAgentMainDTO.getEmployeeUserName())
                .ne(AgentMainWorkerEntity::getId, agentMainWorkerEntity.getId()));
        if (agentMainNumWorkerNum > 0) {
            return R.fail("已存在相同用户名的管理员");
        }

        agentMainNumWorkerNum = agentMainWorkerService.count(Wrappers.<AgentMainWorkerEntity>query().lambda()
                .eq(AgentMainWorkerEntity::getPhoneNumber, updateAgentMainDTO.getPhoneNumber())
                .ne(AgentMainWorkerEntity::getId, agentMainWorkerEntity.getId()));
        if (agentMainNumWorkerNum > 0) {
            return R.fail("已存在相同手机号的管理员");
        }

        //编辑渠道商
        BeanUtil.copy(updateAgentMainDTO, agentMainEntity);
        updateById(agentMainEntity);

        //编辑渠道商员工
        BeanUtil.copy(updateAgentMainDTO, agentMainWorkerEntity);
        agentMainWorkerService.updateById(agentMainWorkerEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<AgentMainUpdateDetailVO> queryAgentMainUpdateDetail(Long agentMainId) {
        return R.data(baseMapper.queryAgentMainUpdateDetail(agentMainId));
    }

    @Override
    public R<AgentMainInfoVO> queryEnterpriseInfo(Long agentMainId) {
        return R.data(baseMapper.queryEnterpriseInfo(agentMainId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateEnterpriseUrl(Long agentMainId, String enterpriseUrl) {

        AgentMainEntity agentMainEntity = getById(agentMainId);
        if (agentMainEntity == null) {
            return R.fail("渠道商不存在");
        }

        agentMainEntity.setEnterpriseUrl(enterpriseUrl);
        updateById(agentMainEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<ContactInfoVO> queryContact(Long agentMainId) {
        return R.data(baseMapper.queryContact(agentMainId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateContact(Long agentMainId, ContactsInfoDTO contactsInfoDTO) {
        AgentMainEntity agentMainEntity = getById(agentMainId);
        if (agentMainEntity == null) {
            return R.fail("渠道商不存在");
        }

        BeanUtil.copyProperties(contactsInfoDTO, agentMainEntity);
        updateById(agentMainEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<InvoiceVO> queryeInvoice(Long agentMainId) {
        return R.data(baseMapper.queryeInvoice(agentMainId));
    }

}
