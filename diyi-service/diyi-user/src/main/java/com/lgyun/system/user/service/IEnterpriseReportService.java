package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BodyType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.EnterpriseReportEntity;
import com.lgyun.system.user.vo.EnterpriseReportsVO;

/**
 * 年度申报管理表 Service 接口
 *
 * @author tzq
 * @since 2020-08-12 14:47:56
 */
public interface IEnterpriseReportService extends BaseService<EnterpriseReportEntity> {

    /**
     * 根据申报主体类别，申报主体ID查询年审信息
     *
     * @param query
     * @param mainBodyType
     * @param mainBodyId
     * @return
     */
    R<IPage<EnterpriseReportsVO>> findByBodyTypeAndBodyId(Query query, BodyType mainBodyType, Long mainBodyId);

    /**
     * 根据申报主体类别，申报主体ID查询申报结果文件资料
     *
     * @param mainBodyType
     * @param mainBodyId
     * @return
     */
    String findReportResultFiles(BodyType mainBodyType, Long mainBodyId);
}

