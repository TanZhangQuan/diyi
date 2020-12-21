package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddMakerDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddOrUpdateDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseUpdateServiceProviderDTO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.*;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
public interface IIndividualBusinessService extends BaseService<IndividualBusinessEntity> {

    /**
     * 新增个体户
     *
     * @param individualBusinessEnterpriseAddMakerDto
     * @param makerEntity
     * @return
     */
    R<String> createIndividualBusinessMaker(IndividualBusinessEnterpriseAddMakerDTO individualBusinessEnterpriseAddMakerDto, MakerEntity makerEntity);

    /**
     * 通过创客id查询个体户
     *
     * @param makerId
     * @return
     */
    int queryIndividualBusinessNumByMakerId(Long makerId);

    /**
     * 查询当前创客的所有个体户
     *
     * @param page
     * @param makerId
     * @param ibstate
     * @return
     */
    R<IPage<IndividualBusinessEnterpriseListMakerVO>> queryIndividualBusinessListMaker(Long partnerId, Ibstate ibstate, Long makerId, IPage<IndividualBusinessEnterpriseListMakerVO> page);

    /**
     * 根据ID查询个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    R<IndividualBusinessEnterpriseDetailMakerVO> queryIndividualBusinessDetailMaker(Long individualBusinessId);

    /**
     * 添加/编辑个体户
     *
     * @param individualBusinessEnterpriseAddOrUpdateDto
     * @param enterpriseId
     * @return
     */
    R<String> addOrUpdateIndividualBusiness(IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, Long enterpriseId);

    /**
     * 查询当前商户的所有关联创客的个体户
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderId
     * @param individualBusinessEnterpriseListDto
     * @return
     */
    R<IPage<IndividualBusinessEnterpriseListVO>> queryIndividualBusinessList(Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto, IPage<IndividualBusinessEnterpriseListVO> page);

    /**
     * 查询个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    R<IndividualBusinessEnterpriseDetailVO> queryIndividualBusinessDetail(Long individualBusinessId);

    /**
     * 查询编辑个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    R<IndividualBusinessEnterpriseUpdateDetailVO> queryUpdateIndividualBusinessDetail(Long individualBusinessId);

    /**
     * 查询编辑个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    R<IndividualBusinessEnterpriseUpdateDetailServiceProviderVO> queryUpdateIndividualBusinessDetailServiceProvider(Long individualBusinessId);

    /**
     * 根据创客ID, 统一社会信用代码查询个体户
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    IndividualBusinessEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo);

    /**
     * 根据统一社会信用代码查询个体户
     *
     * @param ibtaxNo
     * @return
     */
    IndividualBusinessEntity queryIndividualBusinessByIbtaxNo(String ibtaxNo);

    /**
     * 根据个体户ID查询个体户
     *
     * @param individualBusinessId
     * @return
     */
    int queryCountById(Long individualBusinessId);

    /**
     * 注销个体户
     *
     * @param serviceProviderId
     * @param individualBusinessId
     * @return
     */
    R<String> cancelIndividualBusiness(Long serviceProviderId, Long individualBusinessId);

    /**
     * 修改个体户
     *
     * @param individualBusinessEnterpriseUpdateServiceProviderDTO
     * @param serviceProviderId
     * @return
     */
    R<String> updateIndividualBusinessServiceProvider(IndividualBusinessEnterpriseUpdateServiceProviderDTO individualBusinessEnterpriseUpdateServiceProviderDTO, Long serviceProviderId);

    /**
     * 匹配服务商
     *
     * @param serviceProviderId
     * @param individualBusinessId
     * @return
     */
    R<String> mateServiceProvider(Long serviceProviderId, Long individualBusinessId);

}

