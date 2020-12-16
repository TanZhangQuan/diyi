package com.lgyun.system.user.mapper;

import com.lgyun.system.user.entity.RelBureauNoticeReadEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 相关局通知阅读管理表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Mapper
public interface RelBureauNoticeReadMapper extends BaseMapper<RelBureauNoticeReadEntity> {

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
     * @return
     */
    List<ServiceProviderIdNameListVO> queryReadServiceProviderList(Long relBureauNoticeId);

}

