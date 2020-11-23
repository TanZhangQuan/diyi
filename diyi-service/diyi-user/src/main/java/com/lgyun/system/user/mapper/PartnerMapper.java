package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.PartnerListDTO;
import com.lgyun.system.user.entity.PartnerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.PartnerInfoVO;
import com.lgyun.system.user.vo.PartnerListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合伙人信息表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Mapper
public interface PartnerMapper extends BaseMapper<PartnerEntity> {

    /**
     * 平台--查询所有合伙人
     *
     * @param partnerListDTO
     * @param page
     * @return
     */
    List<PartnerListVO> queryPartnerList(PartnerListDTO partnerListDTO, IPage<PartnerListVO> page);

    /**
     * 查询合伙人基本信息
     *
     * @param partnerId
     * @return
     */
    PartnerInfoVO queryPartnerInfo(Long partnerId);
}

