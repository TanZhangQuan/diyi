package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.entity.AcceptPaysheetCsEntity;
import com.lgyun.system.order.vo.enterprise.AcceptPaysheetCsDetailEnterpriseVO;
import com.lgyun.system.order.vo.enterprise.AcceptPaysheetCsListEnterpriseVO;
import com.lgyun.system.order.vo.enterprise.AcceptPaysheetCsSingleListEnterpriseVO;
import com.lgyun.system.order.vo.maker.AcceptPaysheetAndCsListMakerVO;
import com.lgyun.system.order.vo.maker.AcceptPaysheetDetailMakerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 众包交付支付验收单表 Mapper
 *
 * @author liangfeihu
 * @since 2020-08-05 10:43:29
 */
@Mapper
public interface AcceptPaysheetCsMapper extends BaseMapper<AcceptPaysheetCsEntity> {

    /**
     * 查询众包交付支付验收单
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    List<AcceptPaysheetAndCsListMakerVO> queryCrowdAcceptPaysheetListMaker(Long enterpriseId, Long makerId, IPage<AcceptPaysheetAndCsListMakerVO> page);

    /**
     * 查询众包交付支付验收单详情
     *
     * @param makerId
     * @param acceptPaysheetId
     * @return
     */
    AcceptPaysheetDetailMakerVO queryCrowdAcceptPaysheetDetailMaker(Long makerId, Long acceptPaysheetId);

    /**
     * 查询当前商户所有众包交付支付验收单
     *
     * @param enterpriseId
     * @param acceptSheetAndCsListDto
     * @param page
     * @return
     */
    List<AcceptPaysheetCsListEnterpriseVO> queryCrowdAcceptPaysheetListEnterprise(Long enterpriseId, AcceptSheetAndCsListDTO acceptSheetAndCsListDto, IPage<AcceptPaysheetCsListEnterpriseVO> page);

    /**
     * 查询众包交付支付验收单明细
     *
     * @param acceptPaysheetCsId
     * @return
     */
    AcceptPaysheetCsDetailEnterpriseVO queryCrowdAcceptPaysheetDetailEnterprise(Long acceptPaysheetCsId);

    /**
     * 查询单人单张的众包交付支付验收单
     *
     * @param selfHelpInvoiceId
     * @param selfHelpInvoiceDetailId
     * @param page
     * @return
     */
    List<AcceptPaysheetCsSingleListEnterpriseVO> queryCrowdAcceptPaysheetSingleList(Long selfHelpInvoiceId, Long selfHelpInvoiceDetailId, IPage<AcceptPaysheetCsSingleListEnterpriseVO> page);


}

