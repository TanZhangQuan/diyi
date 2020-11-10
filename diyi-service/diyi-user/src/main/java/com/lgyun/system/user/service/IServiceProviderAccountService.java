package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddOrUpdateServiceProviderAccountDTO;
import com.lgyun.system.user.entity.ServiceProviderAccountEntity;
import com.lgyun.system.user.vo.ServiceProviderAccountListVO;

/**
 * 服务商收款账户信息表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-11-08 20:57:37
 */
public interface IServiceProviderAccountService extends BaseService<ServiceProviderAccountEntity> {

    /**
     * 查询服务商收款账户信息
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    R<IPage<ServiceProviderAccountListVO>> queryServiceProviderAccountList(Long serviceProviderId, IPage<ServiceProviderAccountListVO> page);

    /**
     * 添加或修改服务商收款账户信息
     *
     * @param serviceProviderId
     * @param addOrUpdateServiceProviderAccountDTO
     * @return
     */
    R<String> addOrUpdateServiceProviderAccount(Long serviceProviderId, AddOrUpdateServiceProviderAccountDTO addOrUpdateServiceProviderAccountDTO);

    /**
     * 查询编辑服务商收款账户信息
     *
     * @param serviceProviderAccounttId
     * @return
     */
    R<ServiceProviderAccountListVO> queryServiceProviderAccountUpdateDetail(Long serviceProviderAccounttId);
}

