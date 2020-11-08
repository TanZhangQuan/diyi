package com.lgyun.core.mp.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.DateUtil;
import com.lgyun.common.tool.SecureUtil;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 * @author liangfeihu
 * @since 2020/6/6 00:41
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public boolean save(T entity) {
        BladeUser user = SecureUtil.getUser();
        if (user != null) {
            entity.setCreateUser(user.getUserId());
            entity.setUpdateUser(user.getUserId());
        }
        Date now = DateUtil.now();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        if (entity.getStatus() == null) {
            entity.setStatus(BladeConstant.DB_STATUS_NORMAL);
        }
        entity.setIsDeleted(BladeConstant.DB_NOT_DELETED);
        return super.save(entity);
    }

    @Override
    public boolean updateById(T entity) {
        BladeUser user = SecureUtil.getUser();
        if (user != null) {
            entity.setUpdateUser(user.getUserId());
        }
        entity.setUpdateTime(DateUtil.now());
        return super.updateById(entity);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        BladeUser user = SecureUtil.getUser();
        Date now = DateUtil.now();

        entityList.forEach(entity -> {
            if (user != null) {
                entity.setCreateUser(user.getUserId());
                entity.setUpdateUser(user.getUserId());
            }
            entity.setCreateTime(now);
            entity.setUpdateTime(now);
            if (entity.getStatus() == null) {
                entity.setStatus(BladeConstant.DB_STATUS_NORMAL);
            }
            entity.setIsDeleted(BladeConstant.DB_NOT_DELETED);
        });
        return super.saveBatch(entityList);
    }

    @Override
    public boolean deleteLogic(@NotEmpty List<Long> ids) {
        return super.removeByIds(ids);
    }

}
