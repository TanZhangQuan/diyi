package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;

import java.util.List;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public interface IMakerEnterpriseService extends BaseService<MakerEnterpriseEntity> {

    /**
     * 通过创客id查询
     */
    List<MakerEnterpriseEntity> getMakerId(Long makerId);


    /**
     * 查询关联商户和关注商户
     *
     * @param page
     * @return
     */
    IPage<MakerEnterpriseRelationVO> selectMakerEnterprisePage(IPage page, Long makerId, Integer relationshipType);


    /**
     * 添加和取消关注 relationshipType = 1,取消 2添加
     */
    R addOrCancelfollow(Long enterpriseId,Long makerId,Integer attribute);


}

