package com.lgyun.common.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 树型节点类
 *
 * @author tzq
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeNode extends BaseNode {
    private static final long serialVersionUID = 1L;

    private String title;

}
