package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddMakerDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddOrUpdateDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseListDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseUpdateServiceProviderDTO;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.*;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
public interface IIndividualEnterpriseService extends BaseService<IndividualEnterpriseEntity> {

    /**
     * 新增个独
     *
     * @param individualBusinessEnterpriseAddMakerDto
     * @param makerEntity
     * @return
     */
    R<String> createIndividualEnterpriseMaker(IndividualBusinessEnterpriseAddMakerDTO individualBusinessEnterpriseAddMakerDto, MakerEntity makerEntity);

    /**
     * 通过创客id查询个独
     *
     * @param makerId
     * @return
     */
    int queryIndividualEnterpriseNumByMakerId(Long makerId);

    /**
     * 查询当前创客的所有个独
     *
     * @param page
     * @param makerId
     * @param ibstate
     * @return
     */
    R<IPage<IndividualBusinessEnterpriseListMakerVO>> queryIndividualBusinessListMaker(Long makerId, Ibstate ibstate, IPage<IndividualBusinessEnterpriseListMakerVO> page);

    /**
     * 根据ID查询个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    R<IndividualBusinessEnterpriseDetailMakerVO> queryIndividualEnterpriseDetailMaker(Long individualEnterpriseId);

    /**
     * 添加/编辑个独
     *
     * @param individualBusinessEnterpriseAddOrUpdateDto
     * @param enterpriseId
     * @return
     */
    R<String> addOrUpdateIndividualEnterprise(IndividualBusinessEnterpriseAddOrUpdateDTO individualBusinessEnterpriseAddOrUpdateDto, Long enterpriseId);

    /**
     * 查询当前商户的所有关联创客的个独
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderId
     * @param individualBusinessEnterpriseListDto
     * @return
     */
    R<IPage<IndividualBusinessEnterpriseListVO>> queryIndividualEnterpriseList(IPage<IndividualBusinessEnterpriseListVO> page, Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseListDTO individualBusinessEnterpriseListDto);

    /**
     * 查询个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    R<IndividualBusinessEnterpriseDetailVO> queryIndividualEnterpriseDetail(Long individualEnterpriseId);

    /**
     * 查询编辑个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    R<IndividualBusinessEnterpriseUpdateDetailVO> queryUpdateIndividualEnterpriseDetail(Long individualEnterpriseId);

    /**
     * 查询编辑个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    R<IndividualBusinessEnterpriseUpdateDetailServiceProviderVO> queryUpdateIndividualEnterpriseDetailServiceProvider(Long individualEnterpriseId);

    /**
     * 根据创客ID, 统一社会信用代码查询个独
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    IndividualEnterpriseEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo);

    /**
     * 根据个独名称查询个独
     *
     * @param ibname
     * @return
     */
    IndividualEnterpriseEntity queryIndividualEnterpriseByIbname(String ibname);

    /**
     * 根据统一社会信用代码查询个独
     *
     * @param ibtaxNo
     * @return
     */
    IndividualEnterpriseEntity queryIndividualEnterpriseByIbtaxNo(String ibtaxNo);

    /**
     * 注销个独
     *
     * @param serviceProviderId
     * @param individualEnterpriseId
     * @return
     */
    R<String> cancelIndividualEnterprise(Long serviceProviderId, Long individualEnterpriseId);

    /**
     * 修改个独
     *
     * @param individualBusinessEnterpriseUpdateServiceProviderDTO
     * @param serviceProviderId
     * @return
     */
    R<String> updateIndividualEnterpriseServiceProvider(IndividualBusinessEnterpriseUpdateServiceProviderDTO individualBusinessEnterpriseUpdateServiceProviderDTO, Long serviceProviderId);

    /**
     * 匹配服务商
     *
     * @param serviceProviderId
     * @param individualEnterpriseId
     * @return
     */
    R<String> mateServiceProvider(Long serviceProviderId, Long individualEnterpriseId);

}

