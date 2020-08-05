package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.dto.AcceptPayListDto;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.AcceptPayListVO;
import com.lgyun.system.order.vo.AcceptPaysheetByEnterpriseListVO;
import com.lgyun.system.order.vo.AcceptPaysheetWorksheetVO;
import com.lgyun.system.user.vo.EnterprisesByWorksheetListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
@Mapper
public interface AcceptPaysheetMapper extends BaseMapper<AcceptPaysheetEntity> {

    /**
     * 查询创客对应某商户的所有交付支付验收单
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    List<AcceptPaysheetByEnterpriseListVO> getAcceptPaysheetsByEnterprise(Long enterpriseId ,Long makerId, IPage<AcceptPaysheetByEnterpriseListVO> page);

    /**
     * 根据ID查询交付支付验收单
     *
     * @param makerId
     * @param acceptPaysheetId
     * @return
     */
    AcceptPaysheetWorksheetVO getAcceptPaysheetWorksheet(Long makerId, Long acceptPaysheetId);

    /**
     * 查询创客所有交付支付验收单的商户
     *
     * @param makerId
     * @param page
     * @return
     */
    List<EnterprisesByWorksheetListVO> getEnterprisesByWorksheet(Long makerId, IPage<EnterprisesByWorksheetListVO> page);

    /**
     * 查询当前商户所有总包交付支付验收单
     *
     * @param enterpriseId
     * @param acceptPayListDto
     * @param page
     * @return
     */
    List<AcceptPayListVO> getByDtoEnterprise(Long enterpriseId, AcceptPayListDto acceptPayListDto, IPage<AcceptPayListVO> page);
}

