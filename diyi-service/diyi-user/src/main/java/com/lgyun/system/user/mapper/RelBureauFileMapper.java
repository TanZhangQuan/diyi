package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.RelBureauNoticeFileListDTO;
import com.lgyun.system.user.entity.RelBureauFileEntity;
import com.lgyun.system.user.vo.RelBureauFileDetailVO;
import com.lgyun.system.user.vo.RelBureauFileListVO;
import com.lgyun.system.user.vo.RelBureauFileUpdateDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 相关局监管文件：相关局监管文件管理表 Mapper
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Mapper
public interface RelBureauFileMapper extends BaseMapper<RelBureauFileEntity> {

    /**
     * 查询相关局监督文件编辑详情
     *
     * @param relBureauFileId
     * @return
     */
    RelBureauFileUpdateDetailVO queryRelBureauFileUpdateDetail(Long relBureauFileId);

    /**
     * 查询相关局监督文件列表
     *
     * @param relBureauId
     * @param boolrelBureau
     * @param relBureauNoticeFileListDTO
     * @param page
     * @return
     */
    List<RelBureauFileListVO> queryRelBureauFileList(Long relBureauId, Boolean boolrelBureau, RelBureauNoticeFileListDTO relBureauNoticeFileListDTO, IPage<RelBureauFileListVO> page);

    /**
     * 查询相关局监督文件详情
     *
     * @param relBureauFileId
     * @return
     */
    RelBureauFileDetailVO queryRelBureauFileDetail(Long relBureauFileId);

}

