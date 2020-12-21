package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
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
public interface IndividualBusinessMapper extends BaseMapper<IndividualBusinessEntity> {

    /**
     * 查询当前创客的所有个体户
     *
     * @param makerId
     * @param partnerId
     * @param ibstate
     * @param page
     * @return
     */
    List<IndividualBusinessEnterpriseListMakerVO> queryIndividualBusinessListMaker(Long partnerId, Ibstate ibstate, Long makerId, IPage<IndividualBusinessEnterpriseListMakerVO> page);

    /**
     * 查询个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    IndividualBusinessEnterpriseDetailMakerVO queryIndividualBusinessDetailMaker(Long individualBusinessId);

    /**
     * 查询所有个体户
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param individualBusinessEnterpriseListDto
     * @param page
     * @return
     */
    List<IndividualBusinessEnterpriseListVO> queryIndividualBusinessList(Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, IPage<IndividualBusinessEnterpriseListVO> page);

    /**
     * 查询个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    IndividualBusinessEnterpriseDetailVO queryIndividualBusinessDetail(Long individualBusinessId);

    /**
     * 查询编辑个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    IndividualBusinessEnterpriseUpdateDetailVO queryUpdateIndividualBusinessDetail(Long individualBusinessId);

    /**
     * 查询编辑个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    IndividualBusinessEnterpriseUpdateDetailServiceProviderVO queryUpdateIndividualBusinessDetailServiceProvider(Long individualBusinessId);
}

