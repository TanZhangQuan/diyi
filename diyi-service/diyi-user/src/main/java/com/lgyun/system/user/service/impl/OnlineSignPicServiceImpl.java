package com.lgyun.system.user.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.OnlineSignPicEntity;
import com.lgyun.system.user.mapper.OnlineSignPicMapper;
import com.lgyun.system.user.service.IOnlineSignPicService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;

import java.util.Date;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 15:59:14
 */
@Slf4j
@Service
@AllArgsConstructor
public class OnlineSignPicServiceImpl extends BaseServiceImpl<OnlineSignPicMapper, OnlineSignPicEntity> implements IOnlineSignPicService {

    @Override
    public R saveOnlineSignPic(Long ObjectID,Integer ObjectType,String signPic) {
        OnlineSignPicEntity onlineSignPicEntity = new OnlineSignPicEntity();
        onlineSignPicEntity.setObjectType(ObjectType);
        onlineSignPicEntity.setObjectId(ObjectID);
        onlineSignPicEntity.setSignPic(signPic);
        onlineSignPicEntity.setSignDatetime(new Date());
        onlineSignPicEntity.setWorkerSex(ObjectID);
        save(onlineSignPicEntity);
        return R.success("保存成功");
    }
}
