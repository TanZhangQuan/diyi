package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauNoticeDTO;
import com.lgyun.system.user.dto.RelBureauNoticeFileListDTO;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.lgyun.system.user.vo.RelBureauNoticeDetailVO;
import com.lgyun.system.user.vo.RelBureauNoticeFileListUnReadNumVO;
import com.lgyun.system.user.vo.RelBureauNoticeListVO;
import com.lgyun.system.user.vo.RelBureauNoticeUpdateDetailVO;

/**
 * 相关局通知管理表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauNoticeService extends BaseService<RelBureauNoticeEntity> {

    /**
     * 添加或编辑相关局通知
     *
     * @param relBureauId
     * @param addOrUpdateRelBureauNoticeDTO
     * @return
     */
    R<String> addOrUpdateRelBureauNotice(Long relBureauId, AddOrUpdateRelBureauNoticeDTO addOrUpdateRelBureauNoticeDTO);

    /**
     * 查询相关局通知列表
     *
     * @param relBureauId
     * @param boolrelBureau
     * @param relBureauNoticeFileListDTO
     * @param page
     * @return
     */
    R<IPage<RelBureauNoticeListVO>> queryRelBureauNoticeList(Long relBureauId, Boolean boolrelBureau, RelBureauNoticeFileListDTO relBureauNoticeFileListDTO, IPage<RelBureauNoticeListVO> page);

    /**
     * 查询服务商相关局通知列表
     *
     * @param serviceProviderId
     * @param serviceProviderWorkerId
     * @param query
     * @return
     */
    R<RelBureauNoticeFileListUnReadNumVO> queryRelBureauNoticeListServiceProvider(Long serviceProviderId, Long serviceProviderWorkerId, Query query);

    /**
     * 查询相关局通知详情
     *
     * @param relBureauNoticeId
     * @return
     */
    R<RelBureauNoticeDetailVO> queryRelBureauNoticeDetail(Long relBureauNoticeId);

    /**
     * 查询相关局通知编辑详情
     *
     * @param relBureauNoticeId
     * @return
     */
    R<RelBureauNoticeUpdateDetailVO> queryRelBureauNoticeUpdateDetail(Long relBureauNoticeId);

    /**
     * 删除相关局通知
     *
     * @param relBureauId
     * @param relBureauNoticeId
     * @return
     */
    R<String> deleteRelBureauNotice(Long relBureauId, Long relBureauNoticeId);

    /**
     * 修改相关局通知状态
     *
     * @param relBureauId
     * @param relBureauNoticeId
     * @param relBureauNoticeFileState
     * @return
     */
    R<String> updateRelBureauNoticeState(Long relBureauId, Long relBureauNoticeId, RelBureauNoticeFileState relBureauNoticeFileState);

}

