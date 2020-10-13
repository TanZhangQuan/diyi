package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IMakerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 平台端---合同管理模块相关接口
 *
 * @author tzq
 * @date 2020/9/9.
 * @time 10:17.
 */
@RestController
@RequestMapping("/admin/agreement")
@Validated
@AllArgsConstructor
@Api(value = "平台端---合同管理模块相关接口", tags = "平台端---合同管理模块相关接口")
public class AgreementAdminController {

    private IAgreementService iAgreementService;
    private IMakerService makerService;
    private IAdminService adminService;

    @GetMapping("/find_maker_agreement")
    @ApiOperation(value = "查询平台自然人创客的合同", notes = "查询平台自然人创客的合同")
    public R findAdminMakerAgreement(@ApiParam(value = "创客名称") @RequestParam(required = false) String makerName, Query query) {
        return makerService.getMakerName(query.getCurrent(), query.getSize(), makerName);
    }

    @GetMapping("/find_admin_maker_id_agreement")
    @ApiOperation(value = "平台通过创客id查询加盟合同或者授权协议", notes = "平台通过创客id查询加盟合同或者授权协议")
    public R findAdminMakerIdAgreement(Long makerId, AgreementType agreementType) {
        return iAgreementService.findAdminMakerId(makerId, agreementType);
    }

    @GetMapping("/find_maker_id_enterprise_agreement")
    @ApiOperation(value = "平台通过创客id查询合作商户的合同", notes = "平台通过创客id查询合作商户的合同")
    public R findAdMaEnterAgreement(Long makerId, Query query, String enterpriseName) {
        return iAgreementService.findAdMaEnterAgreement(makerId, enterpriseName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/find_maker_id_apply_short_video")
    @ApiOperation(value = "平台通过创客id查询授权视频", notes = "平台通过创客id查询授权视频")
    public R findMakerIdApplyShortVideo(Long makerId) {
        return R.data(makerService.getById(makerId));
    }

    @PostMapping("/save_admin_agreement")
    @ApiOperation(value = "平台端添加合同", notes = "平台端添加合同")
    public R saveAdminAgreement(@ApiParam(value = "合同id,编辑的时候才传") @RequestParam(required = false) Long agreementId,
                                @ApiParam(value = "名字，商户就是商户名字，创客就是创客名字") String name,
                                @ApiParam(value = "对象id") Long objectId,
                                @ApiParam(value = "对象类型") ObjectType objectType,
                                @ApiParam(value = "1：代表合同，2授权") Integer contractType,
                                @ApiParam(value = "合同类别") AgreementType agreementType,
                                @ApiParam(value = "合同类别") String paperAgreementUrl) {

        return iAgreementService.saveAdminAgreement(agreementId, name, objectId, objectType, contractType, agreementType, paperAgreementUrl);
    }

    @PostMapping("/save_admin_maker_video")
    @ApiOperation(value = "平台端添加合同", notes = "平台端添加合同")
    public R saveAdminMakerVideo(@ApiParam(value = "创客id") @RequestParam(required = false) Long makerId,
                                 @ApiParam(value = "创客视频Url") @RequestParam(required = false) String videoUrl) {

        return makerService.saveAdminMakerVideo(makerId, videoUrl);
    }

    @GetMapping("/find_maker_id_agreement")
    @ApiOperation(value = "平台根据商户id查询商户加盟合同或授权协议", notes = "平台根据商户id查询商户加盟合同或授权协议")
    public R findAdminEnterpriseId(Long enterpriseId, AgreementType agreementType) {
        return iAgreementService.findAdminEnterpriseId(enterpriseId, agreementType);
    }

    @GetMapping("/find_enter_id_service_agreement")
    @ApiOperation(value = "平台根据商户id查询合作服务商的合同", notes = "平台根据商户id查询合作服务商的合同")
    public R findEnterIdServiceAgreement(Long enterpriseId, Query query, String serviceProviderName) {
        return iAgreementService.findEnterIdServiceAgreement(enterpriseId, serviceProviderName, Condition.getPage(query.setDescs("create_time")));
    }

    @GetMapping("/find_admin_ser_id_agreement")
    @ApiOperation(value = "平台根据服务商id查询服务商加盟合同或授权协议", notes = "平台根据服务商id查询服务商加盟合同或授权协议")
    public R findAdminSerIdAgreement(Long serviceProviderId, AgreementType agreementType) {
        return iAgreementService.findAdminSerIdAgreement(serviceProviderId, agreementType);
    }
}
