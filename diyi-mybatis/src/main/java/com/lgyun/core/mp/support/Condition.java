package com.lgyun.core.mp.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.constant.TokenConstant;
import com.lgyun.common.support.Kv;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页工具
 *
 * @author liangfeihu
 * @since 2020/6/6 00:43
 */
public class Condition {

    /**
     * 转化成mybatis plus中的Page
     *
     * @param query 查询条件
     * @return IPage
     */
    public static <T> IPage<T> getPage(Query query) {
        Page<T> page = new Page<>(Func.toInt(query.getCurrent(), 1), Func.toInt(query.getSize(), 10));
        List<OrderItem> list = new ArrayList<>();
        String str = SqlKeyword.filter(query.getAscs());
        list.add(OrderItem.asc(str));

        String descStr = SqlKeyword.filter(query.getDescs());
        list.add(OrderItem.desc(descStr));
//        page.setOrders(list);
        return page;
    }

    /**
     * 查询mybatis plus中的QueryWrapper
     *
     * @param entity 实体
     * @param <T>    类型
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
        return new QueryWrapper<>(entity);
    }

    /**
     * 查询mybatis plus中的QueryWrapper
     *
     * @param query 查询条件
     * @param clazz 实体类
     * @param <T>   类型
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
        Kv exclude = Kv.init().set(TokenConstant.HEADER, TokenConstant.HEADER)
                .set("current", "current").set("size", "size").set("ascs", "ascs").set("descs", "descs");
        return getQueryWrapper(query, exclude, clazz);
    }

    /**
     * 查询mybatis plus中的QueryWrapper
     *
     * @param query   查询条件
     * @param exclude 排除的查询条件
     * @param clazz   实体类
     * @param <T>     类型
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Map<String, Object> exclude, Class<T> clazz) {
        exclude.forEach((k, v) -> query.remove(k));
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.setEntity(BeanUtil.newInstance(clazz));
        SqlKeyword.buildCondition(query, qw);
        return qw;
    }

}
