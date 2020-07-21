package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
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

    List<AcceptPaysheetByEnterpriseListVO> getAcceptPaysheetsByEnterprise(Long enterpriseId ,Long makerId, IPage<AcceptPaysheetByEnterpriseListVO> page);

    AcceptPaysheetWorksheetVO getAcceptPaysheetWorksheet(Long makerId, Long acceptPaysheetId);

    List<EnterprisesByWorksheetListVO> getEnterprisesByWorksheet(Long makerId, IPage<EnterprisesByWorksheetListVO> page);

}

