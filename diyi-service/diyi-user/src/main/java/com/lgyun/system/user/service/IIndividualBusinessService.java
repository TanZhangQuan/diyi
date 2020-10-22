package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseAddDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseDTO;
import com.lgyun.system.user.dto.IndividualBusinessEnterpriseWebAddDTO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.EnterpriseReportsVO;
import com.lgyun.system.user.vo.enterprise.IndividualBusinessDetailEnterpriseVO;
import com.lgyun.system.user.vo.maker.IndividualBusinessDetailMakerVO;
import com.lgyun.system.user.vo.maker.IndividualBusinessListVO;

import java.util.List;

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
     * @param individualBusinessEnterpriseAddDto
     * @param makerEntity
     * @return
     */
    R<String> save(IndividualBusinessEnterpriseAddDTO individualBusinessEnterpriseAddDto, MakerEntity makerEntity);

    /**
     * 通过创客id查询个体户
     *
     * @param makerId
     * @return
     */
    List<IndividualBusinessEntity> findMakerId(Long makerId);

    /**
     * 查询当前创客的所有个体户
     *
     * @param page
     * @param makerId
     * @param ibstate
     * @return
     */
    R<IPage<IndividualBusinessListVO>> listByMaker(IPage<IndividualBusinessListVO> page, Long makerId, Ibstate ibstate);

    /**
     * 根据ID查询个体户详情
     *
     * @param individualBusinessId
     * @return
     */
    R<IndividualBusinessDetailMakerVO> findById(Long individualBusinessId);

    /**
     * 查询当前商户的所有关联创客的个体户
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderId
     * @param individualBusinessEnterpriseDto
     * @return
     */
    R<IPage<IndividualBusinessDetailEnterpriseVO>> getIndividualBusinessList(IPage<IndividualBusinessDetailEnterpriseVO> page, Long enterpriseId, Long serviceProviderId, IndividualBusinessEnterpriseDTO individualBusinessEnterpriseDto);

    /**
     * 创建个体户
     *
     * @param individualBusinessEnterpriseWebAddDto
     * @param enterpriseId
     * @return
     */
    R<String> createIndividualBusiness(IndividualBusinessEnterpriseWebAddDTO individualBusinessEnterpriseWebAddDto, Long enterpriseId);

    /**
     * 根据创客ID, 统一社会信用代码查询个体户
     *
     * @param makerId
     * @param ibtaxNo
     * @return
     */
    IndividualBusinessEntity findByMakerIdAndIbtaxNo(Long makerId, String ibtaxNo);

    /**
     * 统一社会信用代码查询个体户
     *
     * @param ibtaxNo
     * @return
     */
    IndividualBusinessEntity findByIbtaxNo(String ibtaxNo);

    /**
     * 查询个体户年审信息
     *
     * @param query
     * @param individualBusinessId
     * @return
     */
    R<IPage<EnterpriseReportsVO>> queryEnterpriseReports(Query query, Long individualBusinessId);

    /**
     * 修改个体户状态
     *
     * @param serviceProviderId
     * @param individualBusinessId
     * @param ibstate
     * @return
     */
    R<String> updateIbstate(Long serviceProviderId, Long individualBusinessId, Ibstate ibstate);
}

