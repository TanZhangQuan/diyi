package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.admin.QueryPartnerDTO;
import com.lgyun.system.user.entity.PartnerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.admin.PartnerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合伙人信息表 Mapper
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Mapper
public interface PartnerMapper extends BaseMapper<PartnerEntity> {

    /**
     * 平台--查询所有合伙人
     * @param queryPartnerDTO
     * @param page
     * @return
     */
    List<PartnerVO> getPartnerList(QueryPartnerDTO queryPartnerDTO, IPage<PartnerVO> page);
}

