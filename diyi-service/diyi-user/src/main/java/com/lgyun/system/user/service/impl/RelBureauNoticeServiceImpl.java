package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauNoticeDTO;
import com.lgyun.system.user.dto.RelBureauNoticeFileListDTO;
import com.lgyun.system.user.entity.RelBureauNoticeEntity;
import com.lgyun.system.user.entity.RelBureauNoticeReadEntity;
import com.lgyun.system.user.mapper.RelBureauNoticeMapper;
import com.lgyun.system.user.service.IRelBureauNoticeReadService;
import com.lgyun.system.user.service.IRelBureauNoticeService;
import com.lgyun.system.user.service.IRelBureauServiceProviderService;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 相关局通知管理表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauNoticeServiceImpl extends BaseServiceImpl<RelBureauNoticeMapper, RelBureauNoticeEntity> implements IRelBureauNoticeService {

    private IRelBureauNoticeReadService relBureauNoticeReadService;
    private IRelBureauServiceProviderService relBureauServiceProviderService;

    @Override
    public R<String> addOrUpdateRelBureauNotice(Long relBureauId, AddOrUpdateRelBureauNoticeDTO addOrUpdateRelBureauNoticeDTO) {

        RelBureauNoticeEntity relBureauNoticeEntity;
        if (addOrUpdateRelBureauNoticeDTO.getRelBureauNoticeId() == null) {

            relBureauNoticeEntity = new RelBureauNoticeEntity();
            relBureauNoticeEntity.setRelBureauId(relBureauId);
            relBureauNoticeEntity.setNoticeState(RelBureauNoticeFileState.EDITING);
            BeanUtils.copyProperties(addOrUpdateRelBureauNoticeDTO, relBureauNoticeEntity);
            save(relBureauNoticeEntity);

        } else {

            relBureauNoticeEntity = getById(addOrUpdateRelBureauNoticeDTO.getRelBureauNoticeId());
            if (relBureauNoticeEntity == null) {
                return R.fail("通知不存在");
            }

            if (!(RelBureauNoticeFileState.EDITING.equals(relBureauNoticeEntity.getNoticeState()))) {
                return R.fail("非编辑状态通知，不可编辑");
            }

            BeanUtils.copyProperties(addOrUpdateRelBureauNoticeDTO, relBureauNoticeEntity);
            updateById(relBureauNoticeEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<IPage<RelBureauNoticeListVO>> queryRelBureauNoticeList(Long relBureauId, Boolean boolrelBureau, RelBureauNoticeFileListDTO relBureauNoticeFileListDTO, IPage<RelBureauNoticeListVO> page) {

        if (relBureauNoticeFileListDTO.getBeginDate() != null && relBureauNoticeFileListDTO.getEndDate() != null) {
            if (relBureauNoticeFileListDTO.getBeginDate().after(relBureauNoticeFileListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryRelBureauNoticeList(relBureauId, boolrelBureau, relBureauNoticeFileListDTO, page)));
    }

    @Override
    public R<RelBureauNoticeFileListUnReadNumVO> queryRelBureauNoticeListServiceProvider(Long serviceProviderId, Long serviceProviderWorkerId, Query query) {


        RelBureauNoticeFileListUnReadNumVO relBureauNoticeFileListUnReadNumVO = new RelBureauNoticeFileListUnReadNumVO();
        //未读数
        int unreadNum = baseMapper.queryRelBureauNoticeUnreadNum(serviceProviderId, serviceProviderWorkerId);
        relBureauNoticeFileListUnReadNumVO.setUnReadNum(unreadNum);

        //相关局通知
        IPage<RelBureauNoticeFileListServiceProviderVO> page = Condition.getPage(query.setDescs("bool_read"));
        List<RelBureauNoticeFileListServiceProviderVO> relBureauFileList = baseMapper.queryRelBureauNoticeListServiceProvider(serviceProviderId, serviceProviderWorkerId, page);
        relBureauNoticeFileListUnReadNumVO.setRelBureauFileList(page.setRecords(relBureauFileList));

        return R.data(relBureauNoticeFileListUnReadNumVO);
    }

    @Override
    public R<RelBureauNoticeDetailVO> queryRelBureauNoticeDetail(Long relBureauNoticeId, Boolean boolRead, Long serviceProviderId, Long serviceProviderWorkerId) {

        //服务商员工查看详情添加已读数据
        if (boolRead != null && boolRead) {
            int relBureauFileServiceProviderCount = relBureauNoticeReadService.queryRelBureauNoticeServiceProviderCount(relBureauNoticeId, serviceProviderWorkerId);
            if (relBureauFileServiceProviderCount <= 0) {
                RelBureauNoticeReadEntity relBureauNoticeReadEntity = new RelBureauNoticeReadEntity();
                relBureauNoticeReadEntity.setRelBureauNoticeId(relBureauNoticeId);
                relBureauNoticeReadEntity.setServicerProviderId(serviceProviderId);
                relBureauNoticeReadEntity.setServicerProviderWorkerId(serviceProviderWorkerId);
                relBureauNoticeReadService.save(relBureauNoticeReadEntity);
            }
        }

        return R.data(baseMapper.queryRelBureauNoticeDetail(relBureauNoticeId));
    }

    @Override
    public R<RelBureauNoticeUpdateDetailVO> queryRelBureauNoticeUpdateDetail(Long relBureauNoticeId) {
        return R.data(baseMapper.queryRelBureauNoticeUpdateDetail(relBureauNoticeId));
    }

    @Override
    public R<String> deleteRelBureauNotice(Long relBureauId, Long relBureauNoticeId) {

        RelBureauNoticeEntity relBureauNoticeEntity = getById(relBureauNoticeId);
        if (relBureauNoticeEntity == null) {
            return R.fail("通知不存在");
        }

        if (!(relBureauNoticeEntity.getRelBureauId().equals(relBureauId))) {
            return R.fail("通知不属于相关局");
        }

        removeById(relBureauNoticeId);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<String> updateRelBureauNoticeState(Long relBureauId, Long relBureauNoticeId, RelBureauNoticeFileState relBureauNoticeFileState) {

        RelBureauNoticeEntity relBureauNoticeEntity = getById(relBureauNoticeId);
        if (relBureauNoticeEntity == null) {
            return R.fail("通知不存在");
        }

        if (!(relBureauNoticeEntity.getRelBureauId().equals(relBureauId))) {
            return R.fail("通知不属于相关局");
        }

        if (!(RelBureauNoticeFileState.PUBLISHED.equals(relBureauNoticeFileState)) && !(RelBureauNoticeFileState.CANCELLED.equals(relBureauNoticeFileState))) {
            return R.fail("只能选择发布或作废");
        }

        if (RelBureauNoticeFileState.EDITING.equals(relBureauNoticeEntity.getNoticeState()) && RelBureauNoticeFileState.CANCELLED.equals(relBureauNoticeFileState)) {
            return R.fail("编辑状态的通知不可作废");
        }

        if (RelBureauNoticeFileState.HAVEREAD.equals(relBureauNoticeEntity.getNoticeState()) && RelBureauNoticeFileState.PUBLISHED.equals(relBureauNoticeFileState)) {
            return R.fail("已阅读状态的通知不可重新发布");
        }

        switch (relBureauNoticeFileState) {

            case PUBLISHED:

                relBureauNoticeEntity.setPublishDatetime(new Date());
                //判断相关局是否全部服务商已阅读
                int relBureauServiceProviderNum = relBureauServiceProviderService.queryRelBureauServiceProviderNum(relBureauId);
                int serviceProviderReadNum = relBureauNoticeReadService.queryServiceProviderCount(relBureauNoticeId);
                if (serviceProviderReadNum >= relBureauServiceProviderNum) {
                    relBureauNoticeFileState = RelBureauNoticeFileState.HAVEREAD;
                }
                break;

            case CANCELLED:

                relBureauNoticeEntity.setCancelDatetime(new Date());
                break;

            default:
                return R.fail("状态有误");
        }

        relBureauNoticeEntity.setNoticeState(relBureauNoticeFileState);
        updateById(relBureauNoticeEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
