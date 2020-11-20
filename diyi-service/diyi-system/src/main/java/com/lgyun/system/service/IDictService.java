package com.lgyun.system.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.dto.DictDTO;
import com.lgyun.system.entity.Dict;
import com.lgyun.system.vo.DictVO;

import java.util.List;

/**
 * 服务类
 *
 * @author tzq
 */
public interface IDictService extends BaseService<Dict> {

    /**
     * 新增或修改字典
     *
     * @param dictDTO
     * @return
     */
    R<String> addOrUpdateDict(DictDTO dictDTO);

    /**
     * 树形结构
     *
     * @return
     */
    R<List<DictVO>> queryDictTree(String code);

    /**
     * 查询字典名称
     *
     * @param code
     * @param dictKey
     * @return
     */
    String queryDictValue(String code, String dictKey);

}
