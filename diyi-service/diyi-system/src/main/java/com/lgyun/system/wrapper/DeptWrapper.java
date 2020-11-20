package com.lgyun.system.wrapper;

import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.common.node.INode;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.entity.Dept;
import com.lgyun.system.service.IDeptService;
import com.lgyun.system.vo.DeptVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO> {

    private static IDeptService deptService;

    static {
        deptService = SpringUtil.getBean(IDeptService.class);
    }

    public static DeptWrapper build() {
        return new DeptWrapper();
    }

    @Override
    public DeptVO entityVO(Dept dept) {
        DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
        if (Func.equals(dept.getParentId(), CommonConstant.TOP_PARENT_ID)) {
            deptVO.setParentName(CommonConstant.TOP_PARENT_NAME);
        } else {
            Dept parent = deptService.getById(dept.getParentId());
            deptVO.setParentName(parent.getDeptName());
        }
        return deptVO;
    }

    public List<INode> listNodeVO(List<Dept> list) {
        List<INode> collect = list.stream().map(dept -> BeanUtil.copy(dept, DeptVO.class)).collect(Collectors.toList());
        return ForestNodeMerger.merge(collect);
    }

}
