package com.lgyun.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.dto.DictDTO;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.mapper.DictMapper;
import com.lgyun.system.service.IDictService;
import com.lgyun.system.vo.DictVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author liangfeihu
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {


    @Override
    public R<String> addOrUpdateDict(DictDTO dictDTO) {

        //判判code和dictValue的记录是否存在
        int oldDictNum = count(Wrappers.<Dict>query().lambda().eq(Dict::getCode, dictDTO)
                .eq(Dict::getDictKey, dictDTO.getDictKey())
                .ne(dictDTO.getDictId() != null, Dict::getId, dictDTO.getDictId()));
        if (oldDictNum > 0) {
            return R.fail("已存在相同字典码，字典值的记录");
        }

        Dict dict;
        if (dictDTO.getDictId() == null) {

            dict = new Dict();
            BeanUtils.copyProperties(dictDTO, dict);
            save(dict);

            return R.success("添加字典成功");

        } else {

            dict = getById(dictDTO.getDictId());
            if (dict == null) {
                return R.fail("字典不存在");
            }

            BeanUtils.copyProperties(dictDTO, dict);
            updateById(dict);

            return R.success("编辑字典成功");
        }

    }

    @Override
    public R<List<DictVO>> queryDictTree(String code) {
        return R.data(ForestNodeMerger.merge(baseMapper.queryDictTree(code)));
    }

    @Override
    public String queryDictValue(String code, String dictKey) {
        return baseMapper.queryDictValue(code, dictKey);
    }

}
