package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.EnterpriseListDTO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<EnterpriseEntity> {

    /**
     * 通过商户名字查询
     *
     * @param enterpriseName
     * @return
     */
    MakerEnterpriseRelationVO getEnterpriseName(String enterpriseName);

    /**
     * 根据商户ID查询商户详情
     *
     * @param enterpriseId
     * @return
     */
    EnterprisesDetailVO getEnterpriseDetailById(Long enterpriseId);

    /**
     * 支付管理模块查询所有商户
     *
     * @param enterpriseName
     * @param page
     * @return
     */
    List<EnterpriseListPaymentVO> queryEnterpriseListPayment(String enterpriseName, IPage<EnterpriseListPaymentVO> page);

    /**
     * 查询所有商户
     *
     * @param enterpriseListDTO
     * @param page
     * @return
     */
    List<EnterpriseListAdminVO> queryEnterpriseList(Long agentMainId, Long relBureauId, EnterpriseListDTO enterpriseListDTO, IPage<EnterpriseListAdminVO> page);

    /**
     * 查询编辑商户详情
     *
     * @param enterpriseId
     * @return
     */
    EnterpriseUpdateDetailVO queryEnterpriseUpdateDetail(Long enterpriseId);

    /**
     * 查询商户基本信息
     *
     * @param enterpriseId
     * @return
     */
    EnterpriseInfoVO queryEnterpriseInfo(Long enterpriseId);

    /**
     * 查询商户的联系人
     *
     * @param enterpriseId
     * @return
     */
    ContactInfoVO queryContact(Long enterpriseId);

    /**
     * 查询商户的开票信息
     *
     * @param enterpriseId
     * @return
     */
    InvoiceVO queryeInvoice(Long enterpriseId);

    /**
     * 查询商户编号和名称
     *
     * @param serviceProviderId
     * @param partnerId
     * @param enterpriseName
     * @param page
     * @return
     */
    List<EnterpriseIdNameListVO> queryEnterpriseIdAndNameList(Long serviceProviderId, Long partnerId, String enterpriseName, IPage<EnterpriseIdNameListVO> page);

    /**
     * 查询商户详情
     *
     * @param enterpriseId
     * @return
     */
    EnterprisesDetailAgentMainVO queryEnterpriseDetailAgentMain(Long enterpriseId);

    /**
     * 查询商户详情
     *
     * @param enterpriseId
     * @return
     */
    EnterprisesDetailPartnerVO queryEnterpriseDetailPartner(Long enterpriseId);
}

