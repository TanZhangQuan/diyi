package com.lgyun.system.wrapper;

import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.common.node.INode;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.service.IDictService;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.vo.DictVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 */
public class DictWrapper extends BaseEntityWrapper<Dict, DictVO> {

    private static IDictService dictService;

    static {
        dictService = SpringUtil.getBean(IDictService.class);
    }

    public static DictWrapper build() {
        return new DictWrapper();
    }

    @Override
    public DictVO entityVO(Dict dict) {
        DictVO dictVO = BeanUtil.copy(dict, DictVO.class);
        if (Func.equals(dict.getParentId(), CommonConstant.TOP_PARENT_ID)) {
            dictVO.setDictValue(CommonConstant.TOP_PARENT_NAME);
        } else {
            Dict parent = dictService.getById(dict.getParentId());
            dictVO.setDictValue(parent.getDictValue());
        }
        return dictVO;
    }

    public List<INode> listNodeVO(List<Dict> list) {
        List<INode> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictVO.class)).collect(Collectors.toList());
        return ForestNodeMerger.merge(collect);
    }

}
