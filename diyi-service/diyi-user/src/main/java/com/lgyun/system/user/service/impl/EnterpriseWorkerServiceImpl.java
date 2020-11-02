package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.BeanServiceUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.dto.GrantDTO;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.mapper.EnterpriseWorkerMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.user.vo.EnterpriseWorkerDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseWorkerServiceImpl extends BaseServiceImpl<EnterpriseWorkerMapper, EnterpriseWorkerEntity> implements IEnterpriseWorkerService {

    private final IUserService userService;
    private final ISysClient sysClient;

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        EnterpriseWorkerEntity enterpriseWorkerEntity = findByUserId(bladeUser.getUserId());
        if (enterpriseWorkerEntity == null) {
            return R.fail("商户员工不存在");
        }

        if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
            return R.fail("商户状态非正常，请联系客服");
        }

        return R.data(enterpriseWorkerEntity);
    }

    @Override
    public R<EnterpriseWorkerDetailVO> queryEnterpriseWorkerDetail(Long enterpriseWorkerId) {
        return R.data(baseMapper.queryEnterpriseWorkerDetail(enterpriseWorkerId));
    }

    @Override
    public EnterpriseWorkerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public EnterpriseWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getEmployeeUserName, employeeUserName)
                .eq(EnterpriseWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer findCountByPhoneNumber(String phoneNumber) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public EnterpriseWorkerEntity findByUserId(Long userId) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveEnterpriseAccount(EnterpriseWorkerVO request, EnterpriseWorkerEntity enterpriseWorkerEntity, BladeUser bladeUser) {
        User userLogin = userService.getById(bladeUser.getUserId());
        if (request.getId() != null) {
            // 更新账号
            EnterpriseWorkerEntity entity = this.getById(request.getId());
            if (entity == null) {
                return R.fail("没有此账号");
            }

            BeanUtils.copyProperties(request, entity, BeanServiceUtil.getNullPropertyNames(request));
            if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                String collect = String.join(", ", request.getMenuNameList());
                entity.setMenus(collect);
            }
            if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                String encrypt = DigestUtil.encrypt(request.getEmployeePwd());
                entity.setEmployeePwd(encrypt);
                userLogin.setAccount(entity.getPhoneNumber());
            }

            userService.updateById(userLogin);
            this.updateById(entity);
        } else {
            // 新增账号
            Integer countByPhoneNumber = this.findCountByPhoneNumber(request.getPhoneNumber());
            if (countByPhoneNumber > 0) {
                return R.fail("该手机号已经注册过");
            }

            UserInfo userInfo = userService.queryUserInfoByPhone(request.getPhoneNumber(), UserType.ENTERPRISE);
            if (userInfo != null) {
                return R.fail("该手机号已经注册过");
            }
            //新建管理员
            User user = new User();
            user.setUserType(UserType.ENTERPRISE);
            user.setAccount(request.getPhoneNumber());
            user.setPhone(request.getPhoneNumber());
            userService.save(user);

            EnterpriseWorkerEntity entity = new EnterpriseWorkerEntity();
            BeanUtils.copyProperties(request, entity, BeanServiceUtil.getNullPropertyNames(request));
            if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                String collect = String.join(", ", request.getMenuNameList());
                entity.setMenus(collect);
            }
            entity.setUserId(user.getId());
            entity.setEnterpriseId(enterpriseWorkerEntity.getEnterpriseId());
            entity.setUpLevelId(enterpriseWorkerEntity.getId());
            if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                entity.setEmployeePwd(DigestUtil.encrypt(request.getEmployeePwd()));
            }

            this.save(entity);
            request.setId(entity.getId());
        }
        if (request.getMenuIds() != null && request.getMenuIds().size() > 0) {
            GrantDTO grantDTO = new GrantDTO();
            grantDTO.setAccountId(request.getId());
            List<String> menuIds = request.getMenuIds();
            List<Long> menuList = new ArrayList<>();
            menuIds.forEach(menu -> menuList.add(Long.valueOf(menu)));
            grantDTO.setMenuIds(menuList);

            grantDTO.setUserId(bladeUser.getUserId());

            R grant = sysClient.grantFeign(grantDTO);
            if (!grant.isSuccess()) {
                return R.fail("新增、更新商户账号失败");
            }
        }

        return R.success("OK");
    }

}
