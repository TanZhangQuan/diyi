package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.AcceptSheetAndCsListDTO;
import com.lgyun.system.order.entity.AcceptPaysheetCsEntity;
import com.lgyun.system.order.vo.AcceptPaysheetAndCsListMakerVO;
import com.lgyun.system.order.vo.AcceptPaysheetCsDetailEnterpriseVO;
import com.lgyun.system.order.vo.AcceptPaysheetCsDetailMakerVO;
import com.lgyun.system.order.vo.AcceptPaysheetCsListEnterpriseVO;
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
    AcceptPaysheetCsDetailMakerVO queryCrowdAcceptPaysheetDetailMaker(Long makerId, Long acceptPaysheetId);

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
     * 查询众包交付支付验收单详情
     *
     * @param acceptPaysheetCsId
     * @return
     */
    AcceptPaysheetCsDetailEnterpriseVO queryCrowdAcceptPaysheetDetailEnterprise(Long acceptPaysheetCsId);

}

