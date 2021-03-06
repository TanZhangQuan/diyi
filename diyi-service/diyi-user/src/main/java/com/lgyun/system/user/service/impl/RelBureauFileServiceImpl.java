package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauFileDTO;
import com.lgyun.system.user.dto.RelBureauNoticeFileListDTO;
import com.lgyun.system.user.entity.RelBureauFileEntity;
import com.lgyun.system.user.entity.RelBureauFileReadEntity;
import com.lgyun.system.user.mapper.RelBureauFileMapper;
import com.lgyun.system.user.service.IRelBureauFileReadService;
import com.lgyun.system.user.service.IRelBureauFileService;
import com.lgyun.system.user.service.IRelBureauServiceProviderService;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 相关局监管文件：相关局监管文件管理表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauFileServiceImpl extends BaseServiceImpl<RelBureauFileMapper, RelBureauFileEntity> implements IRelBureauFileService {

    private IRelBureauFileReadService relBureauFileReadService;
    private IRelBureauServiceProviderService relBureauServiceProviderService;

    @Override
    public R<String> addOrUpdateRelBureauFile(Long relBureauId, AddOrUpdateRelBureauFileDTO addOrUpdateRelBureauFileDTO) {

        RelBureauFileEntity relBureauFileEntity;
        if (addOrUpdateRelBureauFileDTO.getRelBureauFileId() == null) {

            relBureauFileEntity = new RelBureauFileEntity();
            relBureauFileEntity.setRelBureauId(relBureauId);
            relBureauFileEntity.setFileState(RelBureauNoticeFileState.EDITING);
            BeanUtils.copyProperties(addOrUpdateRelBureauFileDTO, relBureauFileEntity);
            save(relBureauFileEntity);

        } else {

            relBureauFileEntity = getById(addOrUpdateRelBureauFileDTO.getRelBureauFileId());
            if (relBureauFileEntity == null) {
                return R.fail("监督文件不存在");
            }

            if (!(RelBureauNoticeFileState.EDITING.equals(relBureauFileEntity.getFileState()))) {
                return R.fail("非编辑状态监督文件，不可编辑");
            }

            BeanUtils.copyProperties(addOrUpdateRelBureauFileDTO, relBureauFileEntity);
            updateById(relBureauFileEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<RelBureauFileUpdateDetailVO> queryRelBureauFileUpdateDetail(Long relBureauFileId) {
        return R.data(baseMapper.queryRelBureauFileUpdateDetail(relBureauFileId));
    }

    @Override
    public R<IPage<RelBureauFileListVO>> queryRelBureauFileList(Long relBureauId, Boolean boolrelBureau, RelBureauNoticeFileListDTO relBureauNoticeFileListDTO, IPage<RelBureauFileListVO> page) {

        if (relBureauNoticeFileListDTO.getBeginDate() != null && relBureauNoticeFileListDTO.getEndDate() != null) {
            if (relBureauNoticeFileListDTO.getBeginDate().after(relBureauNoticeFileListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryRelBureauFileList(relBureauId, boolrelBureau, relBureauNoticeFileListDTO, page)));
    }

    @Override
    public R queryRelBureauFileListServiceProvider(Long serviceProviderId, Long serviceProviderWorkerId, Query query) {


        RelBureauNoticeFileListUnReadNumVO relBureauNoticeFileListUnReadNumVO = new RelBureauNoticeFileListUnReadNumVO();
        //未读数
        int unreadNum = baseMapper.queryRelBureauFileUnreadNum(serviceProviderId, serviceProviderWorkerId);
        relBureauNoticeFileListUnReadNumVO.setUnReadNum(unreadNum);

        //相关局通知
        IPage<RelBureauNoticeFileListServiceProviderVO> page = Condition.getPage(query.setDescs("bool_read"));
        List<RelBureauNoticeFileListServiceProviderVO> relBureauFileList = baseMapper.queryRelBureauFileListServiceProvider(serviceProviderId, serviceProviderWorkerId, page);
        relBureauNoticeFileListUnReadNumVO.setRelBureauFileList(page.setRecords(relBureauFileList));

        return R.data(relBureauNoticeFileListUnReadNumVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<RelBureauFileDetailVO> queryRelBureauFileDetail(Long relBureauFileId, Boolean boolRead, Long serviceProviderId, Long serviceProviderWorkerId) {

        //服务商员工查看详情添加已读数据
        if (boolRead != null && boolRead) {
            int relBureauFileServiceProviderCount = relBureauFileReadService.queryRelBureauFileServiceProviderCount(relBureauFileId, serviceProviderWorkerId);
            if (relBureauFileServiceProviderCount <= 0){
                RelBureauFileReadEntity relBureauFileReadEntity = new RelBureauFileReadEntity();
                relBureauFileReadEntity.setRelBureauFileId(relBureauFileId);
                relBureauFileReadEntity.setServicerProviderId(serviceProviderId);
                relBureauFileReadEntity.setServicerProviderWorkerId(serviceProviderWorkerId);
                relBureauFileReadService.save(relBureauFileReadEntity);
            }
        }

        return R.data(baseMapper.queryRelBureauFileDetail(relBureauFileId));
    }

    @Override
    public R<String> deleteRelBureauFile(Long relBureauId, Long relBureauFileId) {

        RelBureauFileEntity relBureauFileEntity = getById(relBureauFileId);
        if (relBureauFileEntity == null) {
            return R.fail("监督文件不存在");
        }

        if (!(relBureauFileEntity.getRelBureauId().equals(relBureauId))) {
            return R.fail("监督文件不属于相关局");
        }

        removeById(relBureauFileId);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<String> updateRelBureauFileState(Long relBureauId, Long relBureauFileId, RelBureauNoticeFileState relBureauNoticeFileState) {

        RelBureauFileEntity relBureauFileEntity = getById(relBureauFileId);
        if (relBureauFileEntity == null) {
            return R.fail("监督文件不存在");
        }

        if (!(relBureauFileEntity.getRelBureauId().equals(relBureauId))) {
            return R.fail("监督文件不属于相关局");
        }

        if (!(RelBureauNoticeFileState.PUBLISHED.equals(relBureauNoticeFileState)) && !(RelBureauNoticeFileState.CANCELLED.equals(relBureauNoticeFileState))) {
            return R.fail("只能选择发布或作废");
        }

        if (RelBureauNoticeFileState.EDITING.equals(relBureauFileEntity.getFileState()) && RelBureauNoticeFileState.CANCELLED.equals(relBureauNoticeFileState)) {
            return R.fail("编辑状态的监督文件不可作废");
        }

        if (RelBureauNoticeFileState.HAVEREAD.equals(relBureauFileEntity.getFileState()) && RelBureauNoticeFileState.PUBLISHED.equals(relBureauNoticeFileState)) {
            return R.fail("已阅读状态的监督文件不可重新发布");
        }

        switch (relBureauNoticeFileState) {

            case PUBLISHED:

                relBureauFileEntity.setPublishDatetime(new Date());
                //判断相关局是否全部服务商已阅读
                int relBureauServiceProviderNum = relBureauServiceProviderService.queryRelBureauServiceProviderNum(relBureauId);
                int serviceProviderReadNum = relBureauFileReadService.queryServiceProviderCount(relBureauFileId);
                if (serviceProviderReadNum >= relBureauServiceProviderNum) {
                    relBureauNoticeFileState = RelBureauNoticeFileState.HAVEREAD;
                }
                break;

            case CANCELLED:

                relBureauFileEntity.setCancelDatetime(new Date());
                break;

            default:
                return R.fail("状态有误");
        }

        relBureauFileEntity.setFileState(relBureauNoticeFileState);
        updateById(relBureauFileEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
