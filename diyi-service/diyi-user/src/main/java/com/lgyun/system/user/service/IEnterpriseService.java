package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.vo.TransactionMonthVO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterpriseStatisticalVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.enterprise.EnterpriseResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商户信息 Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public interface IEnterpriseService extends BaseService<EnterpriseEntity> {

    /**
     * 通过商户名字查询
     *
     * @param enterpriseName
     * @return
     */
    MakerEnterpriseRelationVO getEnterpriseName(String enterpriseName);

    /**
     * 通过商户id查询
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId);

    /**
     * 获取商户首页统计数据
     *
     * @param enterpriseId
     * @return
     */
    R<EnterpriseStatisticalVO> statistical(Long enterpriseId);

    /**
     * 获取商户合作服务商
     *
     * @param query
     * @param enterpriseId
     * @return
     */
    R<IPage<ServiceProviderIdNameListVO>> getServiceProviders(Query query, Long enterpriseId);

    /**
     * 获取商户交易金额
     *
     * @param enterpriseId
     * @return
     */
    R<TransactionMonthVO> queryEnterprisePayMoney(Long enterpriseId);

    /**
     * 获取商户基本信息
     *
     * @param bladeUser
     * @return
     */
    EnterpriseResponse getBasicEnterpriseResponse(BladeUser bladeUser);

    /**
     * 上传商户营业执照
     *
     * @param bladeUser
     * @param file
     * @return
     */
    void uploadEnterpriseLicence(BladeUser bladeUser, MultipartFile file) throws Exception;
}

