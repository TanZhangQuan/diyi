package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.MakerListIndividualDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.EnterpriseMakerDetailVO;
import com.lgyun.system.user.vo.MakerDetailVO;
import com.lgyun.system.user.vo.MakerInfoVO;
import com.lgyun.system.user.vo.MakerListIndividualVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@Mapper
public interface MakerMapper extends BaseMapper<MakerEntity> {

    /**
     * 查询当前创客基本信息
     *
     * @param makerId
     * @return
     */
    MakerInfoVO queryMakerInfo(Long makerId);

    /**
     * 查询当前创客详情
     *
     * @param makerId
     * @return
     */
    MakerDetailVO queryCurrentMakerDetail(Long makerId);

    /**
     * 根据创客ID查询创客详情
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    EnterpriseMakerDetailVO getMakerDetailById(Long enterpriseId, Long makerId);

    /**
     * 查询商户关联的创客
     *
     * @param enterpriseId
     * @param makerListIndividualDTO
     * @param page
     * @return
     */
    List<MakerListIndividualVO> queryMakerListIndividual(Long enterpriseId, MakerListIndividualDTO makerListIndividualDTO, IPage<MakerListIndividualVO> page);
}

