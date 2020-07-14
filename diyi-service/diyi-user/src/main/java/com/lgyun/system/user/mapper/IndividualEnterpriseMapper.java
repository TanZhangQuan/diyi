package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.IndividualEnterpriseListByMakerDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.vo.IndividualEnterpriseDetailVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Mapper
public interface IndividualEnterpriseMapper extends BaseMapper<IndividualEnterpriseEntity> {

    IndividualEnterpriseEntity findMakerId(Long makerId);

    List<IndividualEnterpriseListByMakerVO> listByMaker(IPage<IndividualEnterpriseListByMakerVO> page, IndividualEnterpriseListByMakerDto individualEnterpriseListByMakerDto);

    IndividualEnterpriseDetailVO findById(Long individualBusinessId);

}

