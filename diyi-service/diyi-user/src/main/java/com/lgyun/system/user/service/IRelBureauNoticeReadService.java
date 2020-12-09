package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.RelBureauNoticeReadEntity;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;

/**
 * 相关局通知阅读管理表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauNoticeReadService extends BaseService<RelBureauNoticeReadEntity> {

    /**
     * 查询相关局通知已读服务商数
     *
     * @param relBureauNoticeId
     * @return
     */
    int queryServiceProviderCount(Long relBureauNoticeId);

    /**
     * 查询相关局通知已读服务商
     *
     * @param relBureauNoticeId
     * @param page
     * @return
     */
    R<IPage<ServiceProviderIdNameListVO>> queryReadServiceProviderList(Long relBureauNoticeId, IPage<ServiceProviderIdNameListVO> page);

}

