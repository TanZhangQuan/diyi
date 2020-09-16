package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.user.vo.admin.QueryEnterpriseWorkerEnterpriseVO;
import com.lgyun.system.user.vo.enterprise.EnterpriseContactRequest;

import java.util.List;

/**
 * 商户员工(联系人) Service 接口
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
public interface IEnterpriseWorkerService extends BaseService<EnterpriseWorkerEntity> {

    /**
     * 根据手机号码查询商户员工
     *
     * @param phoneNumber
     * @return
     */
    EnterpriseWorkerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据用户名密码查询商户员工
     *
     * @param
     * @return
     */
    EnterpriseWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd);

    /**
     * 查询当前商户员工
     *
     * @param bladeUser
     * @return
     */
    R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser);

    /**
     * 根据userId查找商户员工
     *
     * @param userId
     * @return
     */
    EnterpriseWorkerEntity findByUserId(Long userId);

    /**
     * 修改密码
     *
     * @param updatePasswordDto
     * @return
     */
    R<String> updatePassword(UpdatePasswordDto updatePasswordDto);

    /**
     * 新增商户联系人
     *
     * @param request
     * @param enterpriseId
     * @param enterpriseWorkerId
     * @return
     */
    R<String> addNewEnterpriseWorker(EnterpriseContactRequest request, Long enterpriseId, Long enterpriseWorkerId);

    /**
     * 新增或更新商户账号信息，含认证授权
     *
     * @param request
     * @param enterpriseWorkerEntity
     * @param bladeUser
     * @return
     */
    R<String> saveEnterpriseAccount(EnterpriseWorkerVO request, EnterpriseWorkerEntity enterpriseWorkerEntity, BladeUser bladeUser);

    /**
     * 商户管理模块查询商户员工
     *
     * @param enterpriseId
     * @param positionName
     * @return
     */
    R<List<QueryEnterpriseWorkerEnterpriseVO>> queryEnterpriseWorkerEnterprise(Long enterpriseId, PositionName positionName);

}


