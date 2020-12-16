package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.RelBureauListDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.lgyun.system.user.vo.RelBureauDetailVO;
import com.lgyun.system.user.vo.RelBureauInfoVO;
import com.lgyun.system.user.vo.RelBureauListVO;
import com.lgyun.system.user.vo.RelBureauUpdateDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 相关局管理表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Mapper
public interface RelBureauMapper extends BaseMapper<RelBureauEntity> {

    /**
     * 查询相关局
     *
     * @param relBureauListDTO
     * @param page
     * @return
     */
    List<RelBureauListVO> queryRelBureauList(RelBureauListDTO relBureauListDTO, IPage<RelBureauListVO> page);

    /**
     * 查询相关局编辑详情
     *
     * @param relBureauId
     * @return
     */
    RelBureauUpdateDetailVO queryRelBureauUpdateDetail(Long relBureauId);

    /**
     * 查询相关局详情
     *
     * @param relBureauId
     * @return
     */
    RelBureauDetailVO queryRelBureauDetail(Long relBureauId);

    /**
     * 查询相关局基础信息
     *
     * @param relBureauId
     * @return
     */
    RelBureauInfoVO queryRelBureauInfo(Long relBureauId);
}

