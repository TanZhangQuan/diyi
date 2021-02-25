package com.lgyun.system.user.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class RelBureauNoticeFileListUnReadNumVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "相关局通知未读数")
    private int unReadNum;

    @ApiModelProperty(value = "相关局通知")
    private IPage<RelBureauNoticeFileListServiceProviderVO> relBureauFileList;

}
