package com.lgyun.system.user.mapper;

import com.lgyun.system.user.entity.AdminEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.AdminDetailVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 平台管理员信息表 Mapper
 *
 * @author liangfeihu
 * @since 2020-09-19 15:02:12
 */
@Mapper
public interface AdminMapper extends BaseMapper<AdminEntity> {

    /**
     * 查询当前管理员详情
     *
     * @param adminId
     * @return
     */
    AdminDetailVO queryAdminDetail(Long adminId);
}

