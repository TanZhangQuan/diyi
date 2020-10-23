package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDTO;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.enterprise.IndividualEnterpriseDetailEnterpriseVO;
import com.lgyun.system.user.vo.maker.IndividualEnterpriseDetailMakerVO;
import com.lgyun.system.user.vo.maker.IndividualEnterpriseListVO;

import java.util.List;

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
     * @param individualBusinessEnterpriseAddDto
     * @param makerEntity
     * @return
     */
    R<String> save(IndividualBusinessEnterpriseAddDTO individualBusinessEnterpriseAddDto, MakerEntity makerEntity);

    /**
     * 通过创客id查询个独
     *
     * @param makerId
     * @return
     */
    List<IndividualEnterpriseEntity> findMakerId(Long makerId);

    /**
     * 查询当前创客的所有个独
     *
     * @param page
     * @param makerId
     * @param ibstate
     * @return
     */
    R<IPage<IndividualEnterpriseListVO>> listByMaker(IPage<IndividualEnterpriseListVO> page, Long makerId, Ibstate ibstate);

    /**
     * 根据ID查询个独详情
     *
     * @param individualEnterpriseId
     * @return
     */
    R<IndividualEnterpriseDetailMakerVO> findById(Long individualEnterpriseId);

    /**
     * 查询当前商户的所有关联创客的个独
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderId
     * @param individualBusinessEnterpriseDto
     * @return
     */
    R<IPage<IndividualEnterpriseDetailEnterpriseVO>> getIndividualEnterpriseList(IPage<IndividualEnterpriseDetailEnterpriseVO> page, Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto);

    /**
     * 创建个独
     *
     * @param individualBusinessEnterpriseWebAddDto
     * @param enterpriseId
     * @return
     */
    R<String> createIndividualEnterprise(IndividualBusinessEnterpriseWebAddDTO individualBusinessEnterpriseWebAddDto, Long enterpriseId);

    /**
     * 根据创客ID, 统一社会信用代码查询个独
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    IndividualEnterpriseEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo);


    /**
     * 统一社会信用代码查询个独
     *
     * @param ibtaxNo
     * @return
     */
    IndividualEnterpriseEntity findByIbtaxNo(String ibtaxNo);

    /**
     * 修改个独状态
     *
     * @param serviceProviderId
     * @param individualEnterpriseId
     * @param ibstate
     * @return
     */
    R<String> updateIbstate(Long serviceProviderId, Long individualEnterpriseId, Ibstate ibstate);

}

