package com.lgyun.system.vo;

import com.lgyun.system.entity.Post;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位表 视图实体类
 *
 * @author liangfeihu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostVO extends Post {
    private static final long serialVersionUID = 1L;

    /**
     * 岗位分类名
     */
    private String categoryName;

}
