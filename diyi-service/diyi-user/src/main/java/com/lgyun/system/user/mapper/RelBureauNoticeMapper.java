package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.NoticeState;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.lgyun.system.user.vo.RelBureauNoticeListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 相关局通知管理表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Mapper
public interface RelBureauNoticeMapper extends BaseMapper<RelBureauNoticeEntity> {

    /**
     * 查询相关局通知
     *
     * @param relBureauId
     * @param noticeState
     * @param page
     * @return
     */
    List<RelBureauNoticeListVO> queryBureauNoticeList(Long relBureauId, NoticeState noticeState, IPage<RelBureauNoticeListVO> page);
}

