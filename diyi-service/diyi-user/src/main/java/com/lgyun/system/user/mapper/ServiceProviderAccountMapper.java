package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.ServiceProviderAccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.ServiceProviderAccountListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 服务商收款账户信息表 Mapper
 *
 * @author tzq
 * @since 2020-11-08 20:57:37
 */
@Mapper
public interface ServiceProviderAccountMapper extends BaseMapper<ServiceProviderAccountEntity> {

    /**
     * 查询服务商收款账户信息
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    List<ServiceProviderAccountListVO> queryServiceProviderAccountList(Long serviceProviderId, IPage<ServiceProviderAccountListVO> page);

    /**
     * 查询编辑服务商收款账户信息
     *
     * @param serviceProviderAccounttId
     * @return
     */
    ServiceProviderAccountListVO queryServiceProviderAccountUpdateDetail(Long serviceProviderAccounttId);
}

