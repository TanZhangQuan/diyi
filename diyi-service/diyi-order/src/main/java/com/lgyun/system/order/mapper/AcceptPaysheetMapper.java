package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.AcceptPayListDTO;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.AcceptPayListVO;
import com.lgyun.system.order.vo.AcceptPaysheetAndCsList;
import com.lgyun.system.order.vo.AcceptPaysheetDetailVO;
import com.lgyun.system.order.vo.PayEnterpriseMakerDetailListVO;
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
    List<AcceptPaysheetAndCsList> queryTotalSubAcceptPaysheetList(Long enterpriseId, Long makerId, IPage<AcceptPaysheetAndCsList> page);

    /**
     * 查询总包+分包交付支付验收单详情
     *
     * @param makerId
     * @param acceptPaysheetId
     * @return
     */
    AcceptPaysheetDetailVO queryTotalSubAcceptPaysheetDetail(Long makerId, Long acceptPaysheetId);

    /**
     * 查询众包交付支付验收单
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    List<AcceptPaysheetAndCsList> queryCrowdAcceptPaysheetList(Long enterpriseId, Long makerId, IPage<AcceptPaysheetAndCsList> page);

    /**
     * 查询众包交付支付验收单详情
     *
     * @param makerId
     * @param acceptPaysheetId
     * @return
     */
    AcceptPaysheetDetailVO queryCrowdAcceptPaysheetDetail(Long makerId, Long acceptPaysheetId);

    /**
     * 查询当前商户所有总包交付支付验收单
     *
     * @param enterpriseId
     * @param acceptPayListDto
     * @param page
     * @return
     */
    List<AcceptPayListVO> getAcceptPaySheetsByEnterprise(Long enterpriseId, AcceptPayListDTO acceptPayListDto, IPage<AcceptPayListVO> page);

    /**
     * 根据总包总包交付支付验收单ID查询关联创客
     *
     * @param acceptPaysheetId
     * @param page
     * @return
     */
    List<PayEnterpriseMakerDetailListVO> getMakerList(Long acceptPaysheetId, IPage<PayEnterpriseMakerDetailListVO> page);
}

