package com.lgyun.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.ctrl.BladeController;
import com.lgyun.common.node.INode;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.system.entity.Dept;
import com.lgyun.system.service.IDeptService;
import com.lgyun.system.vo.DeptVO;
import com.lgyun.system.wrapper.DeptWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Api(value = "部门", tags = "部门")
public class DeptController extends BladeController {

    private IDeptService deptService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "传入dept")
    public R<DeptVO> detail(Dept dept) {
        Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
        return R.data(DeptWrapper.build().entityVO(detail));
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "列表", notes = "传入dept")
    public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dept, BladeUser bladeUser) {
        QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
        List<Dept> list = deptService.list((!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, bladeUser.getTenantId()) : queryWrapper);
        return R.data(DeptWrapper.build().listNodeVO(list));
    }

    /**
     * 获取部门树形结构
     *
     * @return
     */
    @GetMapping("/tree")
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<DeptVO>> tree(String tenantId, BladeUser bladeUser) {
        List<DeptVO> tree = deptService.tree(Func.toStr(tenantId, bladeUser.getTenantId()));
        return R.data(tree);
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperation(value = "新增或修改", notes = "传入dept")
    public R submit(@Valid @RequestBody Dept dept, BladeUser user) {
        if (Func.isEmpty(dept.getId())) {
            dept.setTenantId(user.getTenantId());
        }
        return R.status(deptService.saveOrUpdate(dept));
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(deptService.removeByIds(Func.toLongList(ids)));
    }

}
