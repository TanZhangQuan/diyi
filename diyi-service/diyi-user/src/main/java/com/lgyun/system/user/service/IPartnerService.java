package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddPartnerDTO;
import com.lgyun.system.user.dto.QueryPartnerDTO;
import com.lgyun.system.user.dto.UpdatePartnerDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.vo.PartnerVO;

/**
 * 合伙人信息表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
public interface IPartnerService extends BaseService<PartnerEntity> {

    /**
     * 平台查询所有合伙人
     *
     * @param page
     * @param queryPartnerDTO
     * @return
     */
    R<IPage<PartnerVO>> getPartnerList(IPage<PartnerVO> page, QueryPartnerDTO queryPartnerDTO);

    /**
     * 修改合伙人状态
     *
     * @param partnerId
     * @return
     */
    R updateIllegal(Long partnerId, AccountState accountState, AdminEntity adminEntity);

    /**
     * 添加合伙人
     *
     * @param addPartnerDTO
     * @param adminEntity
     * @return
     */
    R addPartner(AddPartnerDTO addPartnerDTO, AdminEntity adminEntity);

    /**
     * 编辑合伙人信息
     *
     * @param updatePartnerDTO
     * @param adminEntity
     * @return
     */
    R updatePartner(UpdatePartnerDTO updatePartnerDTO, AdminEntity adminEntity);
}

