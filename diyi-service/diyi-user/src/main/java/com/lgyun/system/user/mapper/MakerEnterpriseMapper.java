package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.MakerEnterpriseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Mapper
public interface MakerEnterpriseMapper extends BaseMapper<MakerEnterpriseEntity> {
    /**
     * 通过创客id查询
     */
    List<MakerEnterpriseEntity> getMakerId(Long makerId);


    /**
     * 自定义分页
     *
     * @param page
     * @return
     */
    List<MakerEnterpriseVO> selectMakerEnterprisePage(IPage page, Long makerId,Integer relationshipType);


    /**
     * 通过
     */
    MakerEnterpriseEntity selectCancelfollow(Long enterpriseId, Long markId);
}

