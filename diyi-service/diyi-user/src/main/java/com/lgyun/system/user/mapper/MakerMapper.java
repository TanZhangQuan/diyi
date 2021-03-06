package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.system.user.dto.MakerListIndividualDTO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.*;
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
    BaseInfoVO queryMakerInfo(Long makerId);

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
     * @param makerId
     * @return
     */
    MakerDetailWebVO queryMakerDetail(Long makerId);

    /**
     * 查询商户关联的创客
     *
     * @param enterpriseId
     * @param partnerId
     * @param makerListIndividualDTO
     * @param page
     * @return
     */
    List<MakerListIndividualVO> queryMakerListIndividual(Long enterpriseId, Long partnerId, MakerListIndividualDTO makerListIndividualDTO, IPage<MakerListIndividualVO> page);

    /**
     * 根据条件查询所有创客
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param relBureauId
     * @param relationshipType
     * @param certificationState
     * @param keyword
     * @param page
     * @return
     */
    List<MakerListWebVO> queryMakerList(Long enterpriseId, Long serviceProviderId, Long relBureauId, RelationshipType relationshipType, CertificationState certificationState, String keyword, IPage<MakerListWebVO> page);

    /**
     * 工单查询所有创客
     *
     * @param enterpriseId
     * @param makerName
     * @param page
     * @return
     */
    List<MakerWorkSheetListVO> queryWorkMakerList(Long enterpriseId, String makerName, IPage<MakerWorkSheetListVO> page);

    /**
     * 查询所有创客
     *
     * @param keyWord
     * @param page
     * @return
     */
    List<MakerSelectListVO> queryMakerSelectList(String keyWord, IPage<MakerSelectListVO> page);

    /**
     * 待审核列表
     */
    List<MakerToExamineListVO> queryMakerToExamineList(String makerName, IPage<MakerToExamineListVO> page);
}

