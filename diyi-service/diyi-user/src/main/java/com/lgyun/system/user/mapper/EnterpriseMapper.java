package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.WorksheetMakerState;
import com.lgyun.common.enumeration.WorksheetState;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterprisesByWorksheetListVO;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<EnterpriseEntity> {
    /**
     * 通过商户名字查询
     */
    List<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName);

    /**
     * 通过商户id查询
     */
    MakerEnterpriseRelationVO getEnterpriseId(Long enterpriseId,Integer difference);
    MakerEnterpriseRelationVO getEnterpriseId(Long enterpriseId);

    /**
     * 查询创客所有交付支付验收单的商户
     */
    List<EnterprisesByWorksheetListVO> getEnterprisesByWorksheet(WorksheetState worksheetState, WorksheetMakerState worksheetMakerState, Long makerId, IPage<EnterprisesByWorksheetListVO> page);

}

