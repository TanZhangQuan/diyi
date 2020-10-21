package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.admin.RelBureauNoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 相关局通知管理表 Mapper
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Mapper
public interface RelBureauNoticeMapper extends BaseMapper<RelBureauNoticeEntity> {
    List<RelBureauNoticeVO> queryTaxBureauNotice(@Param("bureauId") Long bureauId, IPage<RelBureauNoticeVO> page);
}

