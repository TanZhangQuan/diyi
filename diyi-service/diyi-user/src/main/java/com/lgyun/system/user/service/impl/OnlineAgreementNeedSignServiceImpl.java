package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.SignPower;
import com.lgyun.common.enumeration.TemplateType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import com.lgyun.system.user.mapper.OnlineAgreementNeedSignMapper;
import com.lgyun.system.user.service.IOnlineAgreementNeedSignService;
import com.lgyun.system.user.vo.OnlineAgreementNeedSignVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Slf4j
@Service
@AllArgsConstructor
public class OnlineAgreementNeedSignServiceImpl extends BaseServiceImpl<OnlineAgreementNeedSignMapper, OnlineAgreementNeedSignEntity> implements IOnlineAgreementNeedSignService {

    @Override
    public R<String> OnlineAgreementNeedSignAdd(Long onlineAgreementTemplateId, ObjectType objectType, SignPower signPower, Long objectId) {

        OnlineAgreementNeedSignEntity oldOnlineAgreementNeedSignEntity = findByonlineAgreementTemplateIdAndobjectTypeAndobjectId(onlineAgreementTemplateId, objectType, signPower, objectId);
        if (oldOnlineAgreementNeedSignEntity != null) {
            return R.fail("需要签署的授权协议已存在");
        }

        OnlineAgreementNeedSignEntity onlineAgreementNeedSignEntity = new OnlineAgreementNeedSignEntity();
        onlineAgreementNeedSignEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateId);
        onlineAgreementNeedSignEntity.setObjectType(objectType);
        onlineAgreementNeedSignEntity.setSignPower(signPower);
        onlineAgreementNeedSignEntity.setObjectId(objectId);
        save(onlineAgreementNeedSignEntity);

        return R.success("添加成功");
    }

    @Override
    public OnlineAgreementNeedSignEntity findByonlineAgreementTemplateIdAndobjectTypeAndobjectId(Long onlineAgreementTemplateId, ObjectType objectType, SignPower signPower, Long objectId) {
        QueryWrapper<OnlineAgreementNeedSignEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineAgreementNeedSignEntity::getOnlineAgreementTemplateId, onlineAgreementTemplateId)
                .eq(OnlineAgreementNeedSignEntity::getObjectType, objectType)
                .eq(OnlineAgreementNeedSignEntity::getSignPower, signPower)
                .eq(OnlineAgreementNeedSignEntity::getObjectId, objectId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<List<OnlineAgreementNeedSignVO>> getOnlineAgreementNeedSign(Long makerId, TemplateType templateType) {
        List<OnlineAgreementNeedSignVO> onlineAgreementNeedSign = baseMapper.getOnlineAgreementNeedSign(makerId, templateType);
        return R.data(onlineAgreementNeedSign);
    }
}
