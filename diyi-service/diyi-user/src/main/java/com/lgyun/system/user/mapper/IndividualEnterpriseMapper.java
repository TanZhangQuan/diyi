package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.EnterpriseIndividualEnterpriseDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.vo.EnterpriseIndividualEnterpriseVO;
import com.lgyun.system.user.vo.IndividualEnterpriseDetailVO;
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

    IndividualEnterpriseDetailVO findById(Long individualEnterpriseId);

    List<EnterpriseIndividualEnterpriseVO> getByDtoEnterprise(Long enterpriseId, EnterpriseIndividualEnterpriseDto enterpriseIndividualEnterpriseDto, IPage<EnterpriseIndividualEnterpriseVO> page);

    EnterpriseIndividualEnterpriseVO findByIdEnterprise(Long individualEnterpriseId);
}

