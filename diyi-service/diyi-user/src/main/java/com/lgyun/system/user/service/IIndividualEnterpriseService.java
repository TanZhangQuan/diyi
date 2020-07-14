package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.IndividualEnterpriseAddDto;
import com.lgyun.system.user.dto.IndividualEnterpriseListByMakerDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.vo.IndividualEnterpriseDetailVO;
import com.lgyun.system.user.vo.IndividualEnterpriseListByMakerVO;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public interface IIndividualEnterpriseService extends BaseService<IndividualEnterpriseEntity> {

    //新增个独
    R save(IndividualEnterpriseAddDto individualEnterpriseAddDto, BladeUser bladeUser);

    //通过创客id查询个独
    IndividualEnterpriseEntity findMakerId(Long makerId);

    //查询当前创客的所有个独
    R<IPage<IndividualEnterpriseListByMakerVO>> listByMaker(IPage<IndividualEnterpriseListByMakerVO> page, IndividualEnterpriseListByMakerDto individualEnterpriseListByMakerDto);

    //根据ID查询个独详情
    R<IndividualEnterpriseDetailVO> findById(Long individualEnterpriseId);
}

