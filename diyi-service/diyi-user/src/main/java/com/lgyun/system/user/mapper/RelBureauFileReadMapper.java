package com.lgyun.system.user.mapper;

import com.lgyun.system.user.entity.RelBureauFileReadEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 相关局监管文件阅读管理表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Mapper
public interface RelBureauFileReadMapper extends BaseMapper<RelBureauFileReadEntity> {

    /**
     * 相关局监管文件已读服务商数
     *
     * @param relBureauFilesId
     * @return
     */
    int queryServiceProviderCount(Long relBureauFilesId);

    /**
     * 查询相关局通知已读服务商
     *
     * @param relBureauFileId
     * @return
     */
    List<ServiceProviderIdNameListVO> queryReadServiceProviderList(Long relBureauFileId);

}

