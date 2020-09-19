package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.BeanServiceUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.dto.serviceProvider.AddOrUpdateServiceProviderContactDto;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.mapper.ServiceProviderWorkerMapper;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.ServiceProviderWorkerVO;
import com.lgyun.system.user.vo.admin.QueryServiceProviderWorkerListVO;
import com.lgyun.system.vo.GrantRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 服务商员工表 Service 实现
 *
 * @author tzq
 * @since 2020-08-13 17:05:17
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderWorkerServiceImpl extends BaseServiceImpl<ServiceProviderWorkerMapper, ServiceProviderWorkerEntity> implements IServiceProviderWorkerService {

    private IUserService userService;
    private IServiceProviderService iServiceProviderService;
    private RedisUtil redisUtil;
    private final ISysClient sysClient;

    @Override
    public ServiceProviderWorkerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public ServiceProviderWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getEmployeeUserName, employeeUserName)
                .eq(ServiceProviderWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("账号未登陆");
        }

        User user = userService.getById(bladeUser.getUserId());
        if (user == null){
            return R.fail("用户不存在");
        }

        if (!(UserType.SERVICEPROVIDER.equals(user.getUserType()))) {
            return R.fail("用户类型有误");
        }

        ServiceProviderWorkerEntity serviceProviderWorkerEntity = findByUserId(bladeUser.getUserId());
        if (serviceProviderWorkerEntity == null) {
            return R.fail("账号未注册");
        }

        ServiceProviderEntity serviceProviderEntity = iServiceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
            return R.fail("服务商状态非正常，请联系客服");
        }

        if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(serviceProviderWorkerEntity);
    }

    @Override
    public ServiceProviderWorkerEntity findByUserId(Long userId) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<String> updatePassword(UpdatePasswordDto updatePasswordDto) {

        ServiceProviderWorkerEntity serviceProviderWorkerEntity = findByPhoneNumber(updatePasswordDto.getPhoneNumber());
        if (serviceProviderWorkerEntity == null) {
            return R.fail("手机号未注册");
        }

        //查询缓存短信验证码
        String redisCode = (String) redisUtil.get(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, updatePasswordDto.getSmsCode())) {
            return R.fail("短信验证码不正确");
        }

        serviceProviderWorkerEntity.setEmployeePwd(DigestUtil.encrypt(updatePasswordDto.getNewPassword()));
        save(serviceProviderWorkerEntity);

        //删除缓存短信验证码
        redisUtil.del(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());

        return R.success("修改密码成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrUpdateServiceProviderContact(AddOrUpdateServiceProviderContactDto addOrUpdateServiceProviderContactDto, Long serviceProviderWorkerId) {

        //判断服务商联系人是否相同
        if (addOrUpdateServiceProviderContactDto.getContact1Phone().equals(addOrUpdateServiceProviderContactDto.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }

        //判断服务商联系人1是否已存在
        ServiceProviderWorkerEntity oldServiceProviderWorkerEntity1 = findByPhoneNumber(addOrUpdateServiceProviderContactDto.getContact1Phone());
        User user1 = null;
        if (oldServiceProviderWorkerEntity1 != null) {

            if (!(oldServiceProviderWorkerEntity1.getServiceProviderId().equals(addOrUpdateServiceProviderContactDto.getServiceProviderId()))){
                return R.fail("联系人1电话/手机：" + addOrUpdateServiceProviderContactDto.getContact1Phone() + "已存在");
            }

            user1 = userService.findByPhone(addOrUpdateServiceProviderContactDto.getContact1Phone(), UserType.SERVICEPROVIDER);
            if (user1 == null) {
                return R.fail("联系人1系统数据有误");
            }
        }

        //判断服务商联系人2是否已存在
        ServiceProviderWorkerEntity oldServiceProviderWorkerEntity2 = findByPhoneNumber(addOrUpdateServiceProviderContactDto.getContact2Phone());
        User user2 = null;
        if (oldServiceProviderWorkerEntity2 != null) {

            if (!(oldServiceProviderWorkerEntity2.getServiceProviderId().equals(addOrUpdateServiceProviderContactDto.getServiceProviderId()))){
                return R.fail("联系人2电话/手机：" + addOrUpdateServiceProviderContactDto.getContact2Phone() + "已存在");
            }

            user2 = userService.findByPhone(addOrUpdateServiceProviderContactDto.getContact2Phone(), UserType.SERVICEPROVIDER);
            if (user2 == null) {
                return R.fail("联系人2系统数据有误");
            }
        }

        //处理联系人1
        if (oldServiceProviderWorkerEntity1 != null) {
            //修改联系人2
            user1.setName(addOrUpdateServiceProviderContactDto.getContact1Name());
            user1.setRealName(addOrUpdateServiceProviderContactDto.getContact1Name());
            user1.setEmail(addOrUpdateServiceProviderContactDto.getContact1Mail());
            userService.updateById(user1);

            oldServiceProviderWorkerEntity1.setWorkerName(addOrUpdateServiceProviderContactDto.getContact1Name());
            oldServiceProviderWorkerEntity1.setPositionName(addOrUpdateServiceProviderContactDto.getContact1Position());
            updateById(oldServiceProviderWorkerEntity1);
        } else {
            //新建联系人1
            user1 = new User();
            user1.setUserType(UserType.SERVICEPROVIDER);
            user1.setAccount(addOrUpdateServiceProviderContactDto.getContact1Phone());
            user1.setPassword(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
            user1.setName(addOrUpdateServiceProviderContactDto.getContact1Name());
            user1.setRealName(addOrUpdateServiceProviderContactDto.getContact1Name());
            user1.setEmail(addOrUpdateServiceProviderContactDto.getContact1Mail());
            user1.setPhone(addOrUpdateServiceProviderContactDto.getContact1Phone());
            userService.save(user1);

            oldServiceProviderWorkerEntity1 = new ServiceProviderWorkerEntity();
            oldServiceProviderWorkerEntity1.setServiceProviderId(addOrUpdateServiceProviderContactDto.getServiceProviderId());
            oldServiceProviderWorkerEntity1.setUserId(user1.getId());
            oldServiceProviderWorkerEntity1.setWorkerName(addOrUpdateServiceProviderContactDto.getContact1Name());
            oldServiceProviderWorkerEntity1.setPositionName(addOrUpdateServiceProviderContactDto.getContact1Position());
            oldServiceProviderWorkerEntity1.setPhoneNumber(addOrUpdateServiceProviderContactDto.getContact1Phone());
            oldServiceProviderWorkerEntity1.setUpLevelId(serviceProviderWorkerId);
            oldServiceProviderWorkerEntity1.setEmployeeUserName(addOrUpdateServiceProviderContactDto.getContact1Phone());
            oldServiceProviderWorkerEntity1.setEmployeePwd(DigestUtil.encrypt("123456"));
            oldServiceProviderWorkerEntity1.setAdminPower(true);
            save(oldServiceProviderWorkerEntity1);
        }

        //处理联系人2
        if (oldServiceProviderWorkerEntity2 != null) {
            //修改联系人2
            user2.setName(addOrUpdateServiceProviderContactDto.getContact2Name());
            user2.setRealName(addOrUpdateServiceProviderContactDto.getContact2Name());
            user2.setEmail(addOrUpdateServiceProviderContactDto.getContact2Mail());
            userService.updateById(user2);

            oldServiceProviderWorkerEntity2.setWorkerName(addOrUpdateServiceProviderContactDto.getContact2Name());
            oldServiceProviderWorkerEntity2.setPositionName(addOrUpdateServiceProviderContactDto.getContact2Position());
            updateById(oldServiceProviderWorkerEntity2);
        } else {
            //新建联系人2
            user2 = new User();
            user2.setUserType(UserType.SERVICEPROVIDER);
            user2.setAccount(addOrUpdateServiceProviderContactDto.getContact2Phone());
            user2.setPassword(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
            user2.setName(addOrUpdateServiceProviderContactDto.getContact2Name());
            user2.setRealName(addOrUpdateServiceProviderContactDto.getContact2Name());
            user2.setEmail(addOrUpdateServiceProviderContactDto.getContact2Mail());
            user2.setPhone(addOrUpdateServiceProviderContactDto.getContact2Phone());
            userService.save(user2);

            oldServiceProviderWorkerEntity2 = new ServiceProviderWorkerEntity();
            oldServiceProviderWorkerEntity2.setServiceProviderId(addOrUpdateServiceProviderContactDto.getServiceProviderId());
            oldServiceProviderWorkerEntity2.setUserId(user2.getId());
            oldServiceProviderWorkerEntity2.setWorkerName(addOrUpdateServiceProviderContactDto.getContact2Name());
            oldServiceProviderWorkerEntity2.setPositionName(addOrUpdateServiceProviderContactDto.getContact2Position());
            oldServiceProviderWorkerEntity2.setPhoneNumber(addOrUpdateServiceProviderContactDto.getContact2Phone());
            oldServiceProviderWorkerEntity2.setUpLevelId(serviceProviderWorkerId);
            oldServiceProviderWorkerEntity2.setEmployeeUserName(addOrUpdateServiceProviderContactDto.getContact2Phone());
            oldServiceProviderWorkerEntity2.setEmployeePwd(DigestUtil.encrypt("123456"));
            oldServiceProviderWorkerEntity2.setAdminPower(true);
            save(oldServiceProviderWorkerEntity2);
        }

        return R.success("创建成功");
    }

    @Override
    public R<List<QueryServiceProviderWorkerListVO>> queryServiceProviderWorkerList(Long serviceProviderId, PositionName positionName) {
        return R.data(baseMapper.queryServiceProviderWorkerList(serviceProviderId, positionName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveServiceProviderAccount(ServiceProviderWorkerVO request, ServiceProviderWorkerEntity workerEntity, BladeUser bladeUser) {
        User userLogin = userService.getById(bladeUser.getUserId());
        if (request.getId() != null) {
            // 更新账号
            ServiceProviderWorkerEntity entity = this.getById(request.getId());
            if (entity == null) {
                return R.fail("没有此账号");
            }

            BeanUtils.copyProperties(request, entity, BeanServiceUtil.getNullPropertyNames(request));
            if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                String collect = request.getMenuNameList().stream().collect(Collectors.joining(", "));
                entity.setMenus(collect);
            }
            if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                String encrypt = DigestUtil.encrypt(request.getEmployeePwd());
                entity.setEmployeePwd(encrypt);
                userLogin.setPassword(encrypt);
                userLogin.setAccount(entity.getPhoneNumber());
            }

            userService.updateById(userLogin);
            this.updateById(entity);
        } else {
            // 新增账号
            ServiceProviderWorkerEntity workerServiceByPhoneNumber = this.findByPhoneNumber(request.getPhoneNumber());
            if (workerServiceByPhoneNumber != null) {
                return R.fail("该手机号已经注册过");
            }

            UserInfo userInfo = userService.userInfoByPhone(request.getPhoneNumber(), UserType.SERVICEPROVIDER);
            if (userInfo != null) {
                return R.fail("该手机号已经注册过");
            }
            //新建管理员
            User user = new User();
            user.setUserType(UserType.SERVICEPROVIDER);
            user.setAccount(request.getPhoneNumber());
            if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                user.setPassword(DigestUtil.encrypt(request.getEmployeePwd()));
            }
            user.setPhone(request.getPhoneNumber());
            user.setName(request.getEmployeeUserName());
            user.setRealName(request.getWorkerName());
            userService.save(user);

            ServiceProviderWorkerEntity entity = new ServiceProviderWorkerEntity();
            BeanUtils.copyProperties(request, entity, BeanServiceUtil.getNullPropertyNames(request));
            if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                String collect = request.getMenuNameList().stream().collect(Collectors.joining(", "));
                entity.setMenus(collect);
            }
            entity.setUserId(user.getId());
            entity.setServiceProviderId(workerEntity.getServiceProviderId());
            entity.setUpLevelId(workerEntity.getId());
            if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                entity.setEmployeePwd(DigestUtil.encrypt(request.getEmployeePwd()));
            }

            this.save(entity);
            request.setId(entity.getId());
        }
        if (request.getMenuIds() != null && request.getMenuIds().size() > 0) {
            GrantRequest grantRequest = new GrantRequest();
            grantRequest.setAccountId(request.getId());
            List<String> menuIds = request.getMenuIds();
            List<Long> menuList = new ArrayList<>();
            menuIds.stream().forEach(menu -> {
                menuList.add(Long.valueOf(menu));
            });
            grantRequest.setMenuIds(menuList);

            grantRequest.setUserId(bladeUser.getUserId());

            R grant = sysClient.grantFeign(grantRequest);
            if (!grant.isSuccess()) {
                return R.fail("新增、更新服务商账号失败");
            }
        }

        return R.success("OK");
    }

}
