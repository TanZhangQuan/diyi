package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.BureauType;
import com.lgyun.system.user.dto.QueryRelBureauListDTO;
import com.lgyun.system.user.entity.RelBureauEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.RelBureauVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 税务局管理表 Mapper
 *
 * @author liangfeihu
 * @since 2020-10-20 18:47:56
 */
@Mapper
public interface RelBureauMapper extends BaseMapper<RelBureauEntity> {

    List<RelBureauVO> QueryRelBureau(@Param("query") QueryRelBureauListDTO queryRelBureauListDTO, IPage<RelBureauVO> page, @Param("bureauType") BureauType bureauType);
}

