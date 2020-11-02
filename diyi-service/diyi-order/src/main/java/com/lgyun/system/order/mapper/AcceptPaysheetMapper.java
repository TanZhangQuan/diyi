package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
@Mapper
public interface AcceptPaysheetMapper extends BaseMapper<AcceptPaysheetEntity> {

    /**
     * 查询总包+分包交付支付验收单
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    List<AcceptPaysheetAndCsListMakerVO> queryTotalSubAcceptPaysheetListMaker(Long enterpriseId, Long makerId, IPage<AcceptPaysheetAndCsListMakerVO> page);

    /**
     * 查询总包+分包交付支付验收单详情
     *
     * @param makerId
     * @param acceptPaysheetId
     * @return
     */
    AcceptPaysheetDetailMakerVO queryTotalSubAcceptPaysheetDetailMaker(Long makerId, Long acceptPaysheetId);

    /**
     * 查询当前商户所有总包交付支付验收单
     *
     * @param enterpriseId
     * @param acceptSheetAndCsListDto
     * @param page
     * @return
     */
    List<AcceptPaysheetListEnterpriseVO> queryTotalSubAcceptPaysheetListEnterprise(Long enterpriseId, AcceptSheetAndCsListDTO acceptSheetAndCsListDto, IPage<AcceptPaysheetListEnterpriseVO> page);

    /**
     * 查询当前商户所有总包+分包交付支付验收单详情
     *
     * @param acceptPaysheetId
     * @return
     */
    AcceptPaysheetDetailEnterpriseVO queryTotalSubAcceptPaysheetDetailEnterprise(Long acceptPaysheetId);

    /**
     * 根据总包总包交付支付验收单ID查询关联创客
     *
     * @param acceptPaysheetId
     * @param page
     * @return
     */
    List<PayEnterpriseMakerDetailListVO> getMakerList(Long acceptPaysheetId, IPage<PayEnterpriseMakerDetailListVO> page);

    /**
     * 查询总包支付清单的交付支付验收单关联记录ID
     *
     * @param payEnterpriseId
     */
    List<Long> queryAcceptPaysheetIdList(Long payEnterpriseId);

    /**
     * 删除交付支付验收单
     *
     * @param payEnterpriseId
     */
    void deleteAcceptPaysheet(Long payEnterpriseId);
}

