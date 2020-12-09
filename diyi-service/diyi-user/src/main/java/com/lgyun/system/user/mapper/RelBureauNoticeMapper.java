package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.RelBureauNoticeFileListDTO;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.lgyun.system.user.vo.RelBureauNoticeDetailVO;
import com.lgyun.system.user.vo.RelBureauNoticeListVO;
import com.lgyun.system.user.vo.RelBureauNoticeUpdateDetailVO;
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
     * 查询相关局通知列表
     *
     * @param relBureauId
     * @param boolrelBureau
     * @param relBureauNoticeFileListDTO
     * @param page
     * @return
     */
    List<RelBureauNoticeListVO> queryRelBureauNoticeList(Long relBureauId, Boolean boolrelBureau, RelBureauNoticeFileListDTO relBureauNoticeFileListDTO, IPage<RelBureauNoticeListVO> page);

    /**
     * 查询相关局通知详情
     *
     * @param relBureauNoticeId
     * @return
     */
    RelBureauNoticeDetailVO queryRelBureauNoticeDetail(Long relBureauNoticeId);

    /**
     * 查询相关局通知编辑详情
     *
     * @param relBureauNoticeId
     * @return
     */
    RelBureauNoticeUpdateDetailVO queryRelBureauNoticeUpdateDetail(Long relBureauNoticeId);

}

