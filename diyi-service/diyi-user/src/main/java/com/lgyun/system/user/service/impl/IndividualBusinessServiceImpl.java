package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.Ibstate;
import com.lgyun.common.enumeration.VerifyStatus;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IndividualBusinessAddDto;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.IndividualBusinessMapper;
import com.lgyun.system.user.service.IIndividualBusinessService;
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
public class IndividualBusinessServiceImpl extends ServiceImpl<IndividualBusinessMapper, IndividualBusinessEntity> implements IIndividualBusinessService {

    private final IMakerService iMakerService;

    @Override
    public R save(IndividualBusinessAddDto individualBusinessAddDto, BladeUser bladeUser) {

        //获取当前创客
        MakerEntity makerEntity = iMakerService.findByUserId(bladeUser.getUserId());
        //查看创客是否已经身份证实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getIdcardVerifyStatus()))) {
            return R.fail("请先进行身份证实名认证");
        }

        //查看创客是否已经刷脸实名认证
        if (!(VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus()))) {
            return R.fail("请先进行刷脸实名认证");
        }

        IndividualBusinessEntity individualBusinessEntity = new IndividualBusinessEntity();
        BeanUtils.copyProperties(individualBusinessAddDto, individualBusinessEntity);
        individualBusinessEntity.setIbstate(Ibstate.REGISTERING);
        individualBusinessEntity.setMakerId(makerEntity.getMakerId());
        individualBusinessEntity.setCreateTime(new Date());
        individualBusinessEntity.setUpdateTime(new Date());
        individualBusinessEntity.setIsDeleted(0);
        individualBusinessEntity.setStatus(1);

        save(individualBusinessEntity);

        return R.success("个体户新增成功");
    }

}
