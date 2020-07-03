package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.system.user.dto.IndividualEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualEnterpriseMapper;
import com.lgyun.system.user.service.IIndividualEnterpriseService;
import com.lgyun.system.user.service.IMakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class IndividualEnterpriseServiceImpl extends ServiceImpl<IndividualEnterpriseMapper, IndividualEnterpriseEntity> implements IIndividualEnterpriseService {

    private final IMakerService iMakerService;

    @Override
    public R save(IndividualEnterpriseAddDto individualEnterpriseAddDto) {

        //TODO
        MakerEntity makerEntity = iMakerService.getById(1278969988057903106L);
        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行刷脸实名认证");
        }

        IndividualEnterpriseEntity individualEnterpriseEntity = new IndividualEnterpriseEntity();
        BeanUtils.copyProperties(individualEnterpriseAddDto, individualEnterpriseEntity);
        individualEnterpriseEntity.setIbstate(Ibstate.REGISTERING);
        individualEnterpriseEntity.setMakerId(makerEntity.getMakerId());
        individualEnterpriseEntity.setCreateTime(new Date());
        individualEnterpriseEntity.setUpdateTime(new Date());
        individualEnterpriseEntity.setIsDeleted(0);
        individualEnterpriseEntity.setStatus(1);

        save(individualEnterpriseEntity);

        return R.success("个独新增成功");
    }

}
