package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddOrUpdateAgentMainContactDTO;
import com.lgyun.system.user.entity.AgentPersonEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.mapper.AgentPersonMapper;
import com.lgyun.system.user.service.IAgentPersonService;
import com.lgyun.system.user.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 渠道商人员表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentPersonServiceImpl extends BaseServiceImpl<AgentPersonMapper, AgentPersonEntity> implements IAgentPersonService {

    private IUserService userService;

    @Override
    public Integer findCountByPhoneNumber(String phoneNumber) {
        QueryWrapper<AgentPersonEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentPersonEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public AgentPersonEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<AgentPersonEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentPersonEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrUpdateEnterpriseContact(AddOrUpdateAgentMainContactDTO addOrUpdateAgentMainContactDTO, Long agentMainId) {
        //判断渠道商联系人是否相同
        if (addOrUpdateAgentMainContactDTO.getContact1Phone().equals(addOrUpdateAgentMainContactDTO.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }

        //判断渠道商联系人1是否已存在
        AgentPersonEntity oldAgentPersonEntity1 = findByPhoneNumber(addOrUpdateAgentMainContactDTO.getContact1Phone());
        if (oldAgentPersonEntity1 != null && !(oldAgentPersonEntity1.getAgentMainId().equals(addOrUpdateAgentMainContactDTO.getAgentMainId()))) {
            return R.fail("联系人1电话/手机：" + addOrUpdateAgentMainContactDTO.getContact1Phone() + "已存在");
        }

        //判断渠道商联系人2是否已存在
        AgentPersonEntity oldAgentPersonEntity2 = findByPhoneNumber(addOrUpdateAgentMainContactDTO.getContact2Phone());
        if (oldAgentPersonEntity2 != null && !(oldAgentPersonEntity2.getAgentMainId().equals(addOrUpdateAgentMainContactDTO.getAgentMainId()))) {
            return R.fail("联系人2电话/手机：" + addOrUpdateAgentMainContactDTO.getContact2Phone() + "已存在");
        }

        User user;
        //处理联系人1
        if (oldAgentPersonEntity1 != null) {
            //修改联系人1
            oldAgentPersonEntity1.setWorkerName(addOrUpdateAgentMainContactDTO.getContact1Name());
            oldAgentPersonEntity1.setPositionName(addOrUpdateAgentMainContactDTO.getContact1Position());
            updateById(oldAgentPersonEntity1);
        } else {
            //新建联系人1
            user = new User();
            user.setUserType(UserType.AGENTMAIN);
            user.setAccount(addOrUpdateAgentMainContactDTO.getContact1Phone());
            user.setPhone(addOrUpdateAgentMainContactDTO.getContact1Phone());
            userService.save(user);

            oldAgentPersonEntity1 = new AgentPersonEntity();
            oldAgentPersonEntity1.setAgentMainId(addOrUpdateAgentMainContactDTO.getAgentMainId());
            oldAgentPersonEntity1.setWorkerId(user.getId());
            oldAgentPersonEntity1.setWorkerName(addOrUpdateAgentMainContactDTO.getContact1Name());
            oldAgentPersonEntity1.setPositionName(addOrUpdateAgentMainContactDTO.getContact1Position());
            oldAgentPersonEntity1.setPhoneNumber(addOrUpdateAgentMainContactDTO.getContact1Phone());
            oldAgentPersonEntity1.setUpLevelId(agentMainId);
            oldAgentPersonEntity1.setEmployeeUserName(addOrUpdateAgentMainContactDTO.getContact1Phone());
            oldAgentPersonEntity1.setEmployeePwd(DigestUtil.encrypt("123456"));
            oldAgentPersonEntity1.setAdminPower(true);
            save(oldAgentPersonEntity1);
        }
        //处理联系人2
        if (oldAgentPersonEntity2 != null) {
            //修改联系人2
            oldAgentPersonEntity2.setWorkerName(addOrUpdateAgentMainContactDTO.getContact2Name());
            oldAgentPersonEntity2.setPositionName(addOrUpdateAgentMainContactDTO.getContact2Position());
            updateById(oldAgentPersonEntity2);
        } else {
            //新建联系人2
            user = new User();
            user.setUserType(UserType.AGENTMAIN);
            user.setAccount(addOrUpdateAgentMainContactDTO.getContact2Phone());
            user.setPhone(addOrUpdateAgentMainContactDTO.getContact2Phone());
            userService.save(user);

            oldAgentPersonEntity2 = new AgentPersonEntity();
            oldAgentPersonEntity2.setAgentMainId(addOrUpdateAgentMainContactDTO.getAgentMainId());
            oldAgentPersonEntity2.setWorkerId(user.getId());
            oldAgentPersonEntity2.setWorkerName(addOrUpdateAgentMainContactDTO.getContact2Name());
            oldAgentPersonEntity2.setPositionName(addOrUpdateAgentMainContactDTO.getContact2Position());
            oldAgentPersonEntity2.setPhoneNumber(addOrUpdateAgentMainContactDTO.getContact2Phone());
            oldAgentPersonEntity2.setUpLevelId(agentMainId);
            oldAgentPersonEntity2.setEmployeeUserName(addOrUpdateAgentMainContactDTO.getContact2Phone());
            oldAgentPersonEntity2.setEmployeePwd(DigestUtil.encrypt("123456"));
            oldAgentPersonEntity2.setAdminPower(true);
            save(oldAgentPersonEntity2);
        }

        return R.success("创建成功");
    }
}
