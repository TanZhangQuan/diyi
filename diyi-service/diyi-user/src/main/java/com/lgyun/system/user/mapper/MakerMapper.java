package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.EnterpriseMakerDetailVO;
import com.lgyun.system.user.vo.maker.MakerDetailVO;
import com.lgyun.system.user.vo.maker.MakerInfoVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@Mapper
public interface MakerMapper extends BaseMapper<MakerEntity> {

    /**
     * 查询当前创客基本信息
     *
     * @param makerId
     * @return
     */
    MakerInfoVO queryMakerInfo(Long makerId);

    /**
     * 查询当前创客详情
     *
     * @param makerId
     * @return
     */
    MakerDetailVO queryCurrentMakerDetail(Long makerId);

    /**
     * 根据创客ID查询创客详情
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    EnterpriseMakerDetailVO getMakerDetailById(Long enterpriseId,Long makerId);

}

