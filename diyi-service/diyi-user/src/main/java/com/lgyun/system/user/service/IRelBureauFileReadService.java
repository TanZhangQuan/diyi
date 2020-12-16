package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.RelBureauFileReadEntity;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;

/**
 * 相关局监管文件阅读管理表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauFileReadService extends BaseService<RelBureauFileReadEntity> {

    /**
     * 相关局监管文件已读服务商数
     *
     * @param relBureauFileId
     * @return
     */
    int queryServiceProviderCount(Long relBureauFileId);

    /**
     * 相关局监管文件-服务商员工数
     *
     * @param relBureauFileId
     * @param serviceProviderWorkerId
     * @return
     */
    int queryRelBureauFileServiceProviderCount(Long relBureauFileId, Long serviceProviderWorkerId);

    /**
     * 查询相关局通知已读服务商
     *
     * @param relBureauFileId
     * @param page
     * @return
     */
    R<IPage<ServiceProviderIdNameListVO>> queryReadServiceProviderList(Long relBureauFileId, IPage<ServiceProviderIdNameListVO> page);

}

