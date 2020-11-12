package com.lgyun.system.controller;


import com.lgyun.common.api.R;
import com.lgyun.common.ctrl.BladeController;
import com.lgyun.system.dto.DictDTO;
import com.lgyun.system.service.IDictService;
import com.lgyun.system.vo.DictVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典", tags = "字典")
public class DictController extends BladeController {

    private IDictService dictService;

    @GetMapping("/query-dict-tree")
    @ApiOperation(value = "查询字典树", notes = "查询字典树")
    public R<List<DictVO>> tree(@ApiParam(value = "字典码") @NotBlank(message = "请输入字典码") @RequestParam(required = false) String code) {
        return dictService.queryDictTree(code);
    }

    @PostMapping("/add-or-update-dict")
    @ApiOperation(value = "新增或修改字典", notes = "新增或修改字典")
    public R addOrUpdateDict(@Valid @RequestBody DictDTO dictDTO) {
        return dictService.addOrUpdateDict(dictDTO);
    }

    @GetMapping("/query-dict-value")
    @ApiOperation(value = "查询字典名称", notes = "查询字典名称")
    public R queryDictValue(@ApiParam(value = "字典码") @NotBlank(message = "请输入字典码") @RequestParam(required = false) String code,
                            @ApiParam(value = "字典值") @NotBlank(message = "请输入字典值") @RequestParam(required = false) String dictKey) {

        String dictValue = dictService.queryDictValue(code, dictKey);
        return R.data(dictValue);
    }

}
