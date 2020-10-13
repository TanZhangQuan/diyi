package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailProviderVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceSerProVO;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.EnterprisesIdNameListVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.MakerEnterpriseWebVO;
import com.lgyun.system.user.vo.RelMakerListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface MakerEnterpriseMapper extends BaseMapper<MakerEnterpriseEntity> {

    /**
     * 查询合作商户
     *
     * @param makerId
     * @param relationshipType
     * @param page
     * @return
     */
    List<MakerEnterpriseRelationVO> selectMakerEnterprisePage(Long makerId, RelationshipType relationshipType, IPage<MakerEnterpriseRelationVO> page);

    /**
     * 根据条件查询所有创客
     *
     * @param enterpriseId
     * @param relationshipType
     * @param certificationState
     * @param keyword
     * @param page
     * @return
     */
    List<RelMakerListVO> getEnterpriseMakerList(Long enterpriseId, RelationshipType relationshipType, CertificationState certificationState, String keyword, IPage<RelMakerListVO> page);

    /**
     * 查询创客所有交付支付验收单的商户
     *
     * @param makerId
     * @param page
     * @return
     */
    List<EnterprisesIdNameListVO> getEnterprisesByWorksheet(Long makerId, IPage<EnterprisesIdNameListVO> page);

    /**
     * 根据创客ID查询关联商户
     *
     * @param makerId
     * @param page
     * @return
     */
    List<EnterprisesIdNameListVO> findEnterpriseIdNameByMakerId(Long makerId, IPage<EnterprisesIdNameListVO> page);

    /**
     * 查询当前服务商的自助开票
     *
     * @param serviceProviderId
     * @param keyword
     * @param page
     * @return
     */
    List<SelfHelpInvoiceSerProVO> getSelfHelpInvoiceByServiceProviderId(Long serviceProviderId, String keyword, IPage<SelfHelpInvoiceSerProVO> page);

    /**
     * 根据商户id查询关联的创客
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    List<MakerEnterpriseWebVO> selectEnterpriseMaker( Long enterpriseId,IPage<MakerEnterpriseWebVO> page);

    /**
     * 根据当前服务商和自助开票ID查询自助开票详情
     *
     * @param selfHelpvoiceId
     * @param page
     * @return
     */
    List<SelfHelpInvoiceDetailProviderVO> getSelfHelpInvoiceDetails(Long selfHelpvoiceId, IPage<SelfHelpInvoiceDetailProviderVO> page);
}

