package com.lgyun.system.user.controller.admin;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.feign.IUserClient;
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
 * 平台合同管理
 * @author jun.
 * @date 2020/9/9.
 * @time 10:17.
 */
@Slf4j
@RestController
@RequestMapping("/admin/agreement")
@Validated
@AllArgsConstructor
@Api(value = "(平台)合同相关相关接口", tags = "(平台)合同相关相关接口")
public class AgreementAdminController {
    private IAgreementService iAgreementService;

    private IUserClient iUserClient;

    private IMakerService makerService;

    /**
     * 查询平台自然人创客的合同
     */
    @GetMapping("/find_maker_agreement")
    @ApiOperation(value = "查询平台自然人创客的合同", notes = "查询平台自然人创客的合同")
    public R findAdminMakerAgreement(BladeUser bladeUser, Query query,
                                @RequestParam(required = false) String makerName){
        log.info("查询平台自然人创客的合同");
        try {
            return makerService.getMakerName(query.getCurrent(),query.getSize(),makerName);
        }catch (Exception e){
            log.error("查询平台自然人创客的合同异常", e);
        }
        return R.fail("查询平台自然人创客的合同失败");
    }

    /**
     * 平台通过创客id查询加盟合同或者授权协议
     */
    @GetMapping("/find_admin_maker_id_agreement")
    @ApiOperation(value = "平台通过创客id查询加盟合同或者授权协议", notes = "平台通过创客id查询加盟合同或者授权协议")
    public R findAdminMakerIdAgreement(BladeUser bladeUser, Long makerId,AgreementType agreementType){
        log.info("平台通过创客id查询加盟合同或者授权协议");
        try {
            return iAgreementService.findAdminMakerId(makerId,agreementType);
        }catch (Exception e){
            log.error("平台通过创客id查询加盟合同或者授权协议异常", e);
        }
        return R.fail("平台通过创客id查询加盟合同或者授权协议失败");
    }


    /**
     * 平台通过创客id查询合作商户的合同
     */
    @GetMapping("/find_maker_id_enterprise_agreement")
    @ApiOperation(value = "平台通过创客id查询合作商户的合同", notes = "平台通过创客id查询合作商户的合同")
    public R findAdMaEnterAgreement(BladeUser bladeUser, Long makerId,Query query,String enterpriseName){
        log.info("平台通过创客id查询合作商户的合同");
        try {
            return iAgreementService.findAdMaEnterAgreement(makerId,enterpriseName,Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.error("平台通过创客id查询合作商户的合同异常", e);
        }
        return R.fail("平台通过创客id查询合作商户的合同失败");
    }

    /**
     * 平台通过创客id查询授权视频
     */
    @GetMapping("/find_maker_id_apply_short_video")
    @ApiOperation(value = "平台通过创客id查询授权视频", notes = "平台通过创客id查询授权视频")
    public R findMakerIdApplyShortVideo(BladeUser bladeUser, Long makerId){
        log.info("平台通过创客id查询授权视频");
        try {
            return R.data(makerService.getById(makerId));
        }catch (Exception e){
            log.error("平台通过创客id查询授权视频异常", e);
        }
        return R.fail("平台通过创客id查询授权视频失败");
    }

    /**
     * 平台端添加合同
     */
    @PostMapping("/save_admin_agreement")
    @ApiOperation(value = "平台端添加合同", notes = "平台端添加合同")
    public R saveAdminAgreement(BladeUser bladeUser,
                                @ApiParam(value = "合同id,编辑的时候才传")@RequestParam(required = false) Long agreementId,
                                @ApiParam(value = "名字，商户就是商户名字，创客就是创客名字") String name,
                                @ApiParam(value = "对象id") Long objectId,
                                @ApiParam(value = "对象类型") ObjectType objectType,
                                @ApiParam(value = "1：代表合同，2授权") Integer contractType,
                                @ApiParam(value = "合同类别") AgreementType agreementType,
                                @ApiParam(value = "合同类别") String paperAgreementUrl){
        log.info("平台端添加合同");
        try {
            return iAgreementService.saveAdminAgreement(agreementId,name,objectId,objectType,contractType,agreementType,paperAgreementUrl);
        }catch (Exception e){
            log.error("平台端添加合同异常", e);
        }
        return R.fail("平台端添加合同失败");
    }

    /**
     * 平台端上传创客授权视频
     */
    @PostMapping("/save_admin_maker_video")
    @ApiOperation(value = "平台端添加合同", notes = "平台端添加合同")
    public R saveAdminMakerVideo(BladeUser bladeUser,
                                 @ApiParam(value = "创客id")@RequestParam(required = false) Long makerId,
                                @ApiParam(value = "创客视频Url")@RequestParam(required = false) String videoUrl){
        log.info("平台端上传创客授权视频");
        try {
            return makerService.saveAdminMakerVideo(makerId,videoUrl);
        }catch (Exception e){
            log.error("平台端上传创客授权视频异常", e);
        }
        return R.fail("平台端上传创客授权视频失败");
    }

    /**
     * 平台根据商户id查询商户加盟合同或授权协议
     */
    @GetMapping("/find_maker_id_agreement")
    @ApiOperation(value = "平台根据商户id查询商户加盟合同或授权协议", notes = "平台根据商户id查询商户加盟合同或授权协议")
    public R findAdminEnterpriseId(BladeUser bladeUser, Long enterpriseId,AgreementType agreementType){
        log.info("平台根据商户id查询商户加盟合同或授权协议");
        try {
            return iAgreementService.findAdminEnterpriseId(enterpriseId,agreementType);
        }catch (Exception e){
            log.error("平台根据商户id查询商户加盟合同或授权协议异常", e);
        }
        return R.fail("平台根据商户id查询商户加盟合同或授权协议失败");
    }

    /**
     * 平台根据商户id查询合作服务商的合同
     */
    @GetMapping("/find_enter_id_service_agreement")
    @ApiOperation(value = "平台根据商户id查询合作服务商的合同", notes = "平台根据商户id查询合作服务商的合同")
    public R findEnterIdServiceAgreement(BladeUser bladeUser, Long enterpriseId,Query query,String serviceProviderName){
        log.info("平台根据商户id查询合作服务商的合同");
        try {
            return iAgreementService.findEnterIdServiceAgreement(enterpriseId,serviceProviderName,Condition.getPage(query.setDescs("create_time")));
        }catch (Exception e){
            log.error("平台根据商户id查询合作服务商的合同异常", e);
        }
        return R.fail("平台根据商户id查询合作服务商的合同失败");
    }


    /**
     * 平台根据服务商id查询服务商加盟合同或授权协议
     */
    @GetMapping("/find_admin_ser_id_agreement")
    @ApiOperation(value = "平台根据服务商id查询服务商加盟合同或授权协议", notes = "平台根据服务商id查询服务商加盟合同或授权协议")
    public R findAdminSerIdAgreement(BladeUser bladeUser, Long serviceProviderId,AgreementType agreementType){
        log.info("平台根据服务商id查询服务商加盟合同或授权协议");
        try {
            return iAgreementService.findAdminSerIdAgreement(serviceProviderId,agreementType);
        }catch (Exception e){
            log.error("平台根据服务商id查询服务商加盟合同或授权协议异常", e);
        }
        return R.fail("平台根据服务商id查询服务商加盟合同或授权协议失败");
    }
}
