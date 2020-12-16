package com.lgyun.system.user.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;

@Data
public class RelBureauNoticeFileListUnReadNumVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局通知未读数
     */
    private int unReadNum;

    /**
     * 相关局通知
     */
    private IPage<RelBureauNoticeFileListServiceProviderVO> relBureauFileList;

}
