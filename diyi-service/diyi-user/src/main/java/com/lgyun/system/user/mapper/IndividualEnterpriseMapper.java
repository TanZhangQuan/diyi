package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
@Mapper
public interface IndividualEnterpriseMapper extends BaseMapper<IndividualEnterpriseEntity> {

    /**
     * 查询当前创客的所有个独
     *
     * @param makerId
     * @param ibstate
     * @param page
     * @return
     */
    List<IndividualBusinessEnterpriseListMakerVO> queryIndividualBusinessListMaker(Long makerId, Ibstate ibstate, IPage<IndividualBusinessEnterpriseListMakerVO> page);

    /**
     * 查询个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    IndividualBusinessEnterpriseDetailMakerVO queryIndividualEnterpriseDetailMaker(Long individualEnterpriseId);

    /**
     * 查询所有个独
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param individualBusinessEnterpriseListDto
     * @param page
     * @return
     */
    List<IndividualBusinessEnterpriseListVO> queryIndividualEnterpriseList(Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, IPage<IndividualBusinessEnterpriseListVO> page);

    /**
     * 查询个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    IndividualBusinessEnterpriseDetailVO queryIndividualEnterpriseDetail(Long individualEnterpriseId);

    /**
     * 查询编辑个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    IndividualBusinessEnterpriseUpdateDetailVO queryUpdateIndividualEnterpriseDetail(Long individualEnterpriseId);

    /**
     * 查询编辑个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    IndividualBusinessEnterpriseUpdateDetailServiceProviderVO queryUpdateIndividualEnterpriseDetailServiceProvider(Long individualEnterpriseId);
}

