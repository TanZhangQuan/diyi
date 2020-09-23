package com.lgyun.system.user.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.common.api.R;
import com.lgyun.common.tool.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.wrapper.RegularDeclareWrapper;
import com.lgyun.system.user.entity.RegularDeclareEntity;
import com.lgyun.system.user.service.IRegularDeclareService;

/**
 * 申报表控制器
 *
 * @author liangfeihu
 * @since 2020-09-22 15:46:31
 */
@Slf4j
@RestController
@RequestMapping("/regulardeclare")
@Validated
@AllArgsConstructor
@Api(value = "申报表相关接口", tags = "申报表相关接口")
public class RegularDeclareAdminController {

	private IRegularDeclareService regularDeclareService;
}
