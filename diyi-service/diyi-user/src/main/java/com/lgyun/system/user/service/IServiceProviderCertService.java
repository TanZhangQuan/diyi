package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddOrUpdateServiceProviderCertDTO;
import com.lgyun.system.user.entity.ServiceProviderCertEntity;
import com.lgyun.system.user.vo.ServiceProviderCertListVO;

/**
 * 服务商资格信息表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-09-17 10:58:41
 */
public interface IServiceProviderCertService extends BaseService<ServiceProviderCertEntity> {

    /**
     * 查询服务商资格信息
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    R<IPage<ServiceProviderCertListVO>> queryServiceProviderCertList(Long serviceProviderId, IPage<ServiceProviderCertListVO> page);

    /**
     * 添加或修改服务商资格信息
     *
     * @param addOrUpdateServiceProviderCertDTO
     * @return
     */
    R<String> addOrUpdateServiceProviderCert(AddOrUpdateServiceProviderCertDTO addOrUpdateServiceProviderCertDTO);
}

