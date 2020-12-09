package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddOrUpdateRelBureauFileDTO;
import com.lgyun.system.user.dto.RelBureauNoticeFileListDTO;
import com.lgyun.system.user.entity.RelBureauFileEntity;
import com.lgyun.system.user.vo.RelBureauFileDetailVO;
import com.lgyun.system.user.vo.RelBureauFileListVO;
import com.lgyun.system.user.vo.RelBureauFileUpdateDetailVO;

/**
 * 相关局监管文件：相关局监管文件管理表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauFileService extends BaseService<RelBureauFileEntity> {

    /**
     * 添加或编辑相关局监督文件
     *
     * @param relBureauId
     * @param addOrUpdateRelBureauFileDTO
     * @return
     */
    R<String> addOrUpdateRelBureauFile(Long relBureauId, AddOrUpdateRelBureauFileDTO addOrUpdateRelBureauFileDTO);

    /**
     * 查询相关局监督文件编辑详情
     *
     * @param relBureauFileId
     * @return
     */
    R<RelBureauFileUpdateDetailVO> queryRelBureauFileUpdateDetail(Long relBureauFileId);

    /**
     * 查询相关局监督文件列表
     *
     * @param relBureauId
     * @param boolrelBureau
     * @param relBureauNoticeFileListDTO
     * @param page
     * @return
     */
    R<IPage<RelBureauFileListVO>> queryRelBureauFileList(Long relBureauId, boolean boolrelBureau, RelBureauNoticeFileListDTO relBureauNoticeFileListDTO, IPage<RelBureauFileListVO> page);

    /**
     * 查询相关局监督文件详情
     *
     * @param relBureauFileId
     * @return
     */
    R<RelBureauFileDetailVO> queryRelBureauFileDetail(Long relBureauFileId);

    /**
     * 删除相关局监督文件
     *
     * @param relBureauId
     * @param relBureauFileId
     * @return
     */
    R<String> deleteRelBureauFile(Long relBureauId, Long relBureauFileId);

    /**
     * 修改相关局监督文件状态
     *
     * @param id
     * @param relBureauFileId
     * @param relBureauNoticeFileState
     * @return
     */
    R<String> updateRelBureauFileState(Long id, Long relBureauFileId, RelBureauNoticeFileState relBureauNoticeFileState);
}

