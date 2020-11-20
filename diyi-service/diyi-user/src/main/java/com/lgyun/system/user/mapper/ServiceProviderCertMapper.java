package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.ServiceProviderCertEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.ServiceProviderCertListVO;
import com.lgyun.system.user.vo.ServiceProviderCertUpdateDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 服务商资格信息表 Mapper
 *
 * @author tzq
 * @since 2020-09-17 10:58:41
 */
@Mapper
public interface ServiceProviderCertMapper extends BaseMapper<ServiceProviderCertEntity> {

    /**
     * 查询服务商资格信息
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    List<ServiceProviderCertListVO> queryServiceProviderCertList(Long serviceProviderId, IPage<ServiceProviderCertListVO> page);

    /**
     * 查询编辑服务商资格信息
     *
     * @param serviceProviderCertId
     * @return
     */
    ServiceProviderCertUpdateDetailVO queryServiceProviderCertUpdateDetail(Long serviceProviderCertId);
}

