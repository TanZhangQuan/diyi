package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.vo.DictVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author liangfeihu
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 查询字典树
     *
     * @return
     */
    List<DictVO> queryDictTree(String code);


    /**
     * 查询字典名称
     *
     * @param code    字典编号
     * @param dictKey 字典序号
     * @return
     */
    String queryDictValue(String code, String dictKey);

}
