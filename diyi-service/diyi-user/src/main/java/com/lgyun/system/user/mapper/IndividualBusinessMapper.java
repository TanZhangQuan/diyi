package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.IndividualBusinessListByMakerDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.vo.IndividualBusinessDetailVO;
import com.lgyun.system.user.vo.IndividualBusinessListByMakerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Mapper
public interface IndividualBusinessMapper extends BaseMapper<IndividualBusinessEntity> {

    //通过创客id查询个体户
    List<IndividualBusinessEntity> findMakerId(Long makerId);

    List<IndividualBusinessListByMakerVO> listByMaker(IPage<IndividualBusinessListByMakerVO> page, IndividualBusinessListByMakerDto individualBusinessListByMakerDto);

    IndividualBusinessDetailVO findById(Long individualBusinessId);
}

