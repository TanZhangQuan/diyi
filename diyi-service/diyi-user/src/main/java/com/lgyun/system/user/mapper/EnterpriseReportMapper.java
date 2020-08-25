package com.lgyun.system.user.mapper;

import com.lgyun.common.enumeration.BodyType;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 年度申报管理表 Mapper
 *
 * @author liangfeihu
 * @since 2020-08-12 14:47:56
 */
@Mapper
public interface EnterpriseReportMapper extends BaseMapper<EnterpriseReportEntity> {

    /**
     * 根据申报主体类别，申报主体ID查询申报结果文件资料
     *
     * @param mainBodyType
     * @param mainBodyId
     * @return
     */
    String findReportResultFiles(BodyType mainBodyType, Long mainBodyId);
}

