package com.lgyun.core.mp.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.tool.DateUtil;
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
 * @author tzq
 * @since 2020/6/6 00:41
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public boolean save(T entity) {
        Date now = DateUtil.now();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        entity.setBoolDeleted(false);
        return super.save(entity);
    }

    @Override
    public boolean updateById(T entity) {
        entity.setUpdateTime(DateUtil.now());
        return super.updateById(entity);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        Date now = DateUtil.now();

        entityList.forEach(entity -> {
            entity.setCreateTime(now);
            entity.setUpdateTime(now);
            entity.setBoolDeleted(false);
        });
        return super.saveBatch(entityList);
    }

    @Override
    public boolean deleteLogic(@NotEmpty List<Long> ids) {
        return super.removeByIds(ids);
    }

}
