package com.lgyun.system.user.controller.maker;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.enumeration.TemplateType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.*;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/maker/agreement")
@Validated
@AllArgsConstructor
@Api(value = "创客端---合同管理模块相关接口", tags = "创客端---合同管理模块相关接口")
public class AgreementMakerController {

    private IMakerService makerService;
    private IAgreementService agreementService;
    private IOnlineSignPicService onlineSignPicService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private IServiceProviderMakerService serviceProviderMakerService;

    @GetMapping("/query-maker-agreement")
    @ApiOperation(value = "根据创客查询合同", notes = "根据创客查询合同")
    public R queryMakerAgreement(Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return agreementService.makerIdFind(makerEntity.getId(), onlineAgreementTemplateId, onlineAgreementNeedSignId);
    }

    @GetMapping("/query-enterprise-agreement")
    @ApiOperation(value = "根据商户查询合同", notes = "根据商户查询合同")
    public R queryEnterpriseAgreement(Long enterpriseId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        return R.data(agreementService.findByEnterpriseId(enterpriseId, makerEntity.getId()));
    }

    @GetMapping("/query-online-agreement-url")
    @ApiOperation(value = "查看在线协议URL", notes = "查看在线协议URL")
    public R queryOnlineAgreementUrl(Long agreementId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }

        return agreementService.queryOnlineAgreementUrl(agreementId);
    }

    @GetMapping("/query-online-agreement-need-sign")
    @ApiOperation(value = "查询创客需要签署的授权协议和合同", notes = "查询创客需要签署的授权协议和合同")
    public R queryOnlineAgreementNeedSign(@ApiParam(value = "签署文件模板类型") @NotNull(message = "请选择签署文件模板类型") @RequestParam(required = false) TemplateType templateType, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return onlineAgreementNeedSignService.getOnlineAgreementNeedSign(ObjectType.MAKERPEOPLE, makerEntity.getId(), templateType);
    }

    @GetMapping("/query-cooperation-enterprise-list")
    @ApiOperation(value = "查询合作商户", notes = "查询合作商户")
    public R queryCooperationEnterpriseList(Query query, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerEnterpriseService.selectMakerEnterprisePage(makerEntity.getId(), RelationshipType.RELEVANCE, Condition.getPage(query.setDescs("me.create_time")));
    }

    @PostMapping("/upload-maker-video")
    @ApiOperation(value = "上传创客视频", notes = "上传创客视频")
    public R uploadMakerVideo(@ApiParam(value = "创客视频") @NotBlank(message = "请上传创客视频") @RequestParam(required = false) String applyShortVideo, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return makerService.uploadMakerVideo(makerEntity, applyShortVideo);
    }

    @PostMapping("/query-maker-video")
    @ApiOperation(value = "获取创客视频", notes = "获取创客视频")
    public R queryMakerVideo(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return R.data(makerEntity);
    }

    /**
     * 下载创客视频文档
     */
    @PostMapping("/download-document")
    @ApiOperation(value = "下载创客视频文档", notes = "下载创客视频文档")
    public R downloadDocument(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        return makerService.downloadDocument(makerEntity.getId());
    }

    @PostMapping("/save-online-agreement-need-sign")
    @ApiOperation(value = "确认签字", notes = "确认签字")
    public R saveOnlineAgreementNeedSign(@ApiParam(value = "签名图片", required = true) @NotBlank(message = "请上传签名图片") @URL(message = "请输入正确的链接") @RequestParam(required = false) String signPic,
                                         Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return onlineSignPicService.confirmationSignature(makerEntity.getId(), ObjectType.MAKERPEOPLE, signPic, onlineAgreementTemplateId, onlineAgreementNeedSignId);
    }

    @PostMapping("/save-online-sign-pic")
    @ApiOperation(value = "保存签名照片", notes = "保存签名照片")
    public R saveOnlineSignPic(@ApiParam(value = "签名图片", required = true) @NotBlank(message = "请上传签名图片") @URL(message = "请输入正确的链接") @RequestParam(required = false) String signPic
                                         , BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return onlineSignPicService.saveOnlineSignPic(makerEntity.getId(), ObjectType.MAKERPEOPLE, signPic);
    }


    @GetMapping("/whether-autograph")
    @ApiOperation(value = "查看创客是否存在签名", notes = "查看创客是否存在签名")
    public R whetherAutograph(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        return onlineSignPicService.whetherAutograph(ObjectType.MAKERPEOPLE,makerEntity.getId());
    }

    @GetMapping("/query-contract-template")
    @ApiOperation(value = "查询合同模板", notes = "查询合同模板")
    public R queryContractTemplate(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        return agreementService.queryContractTemplate();
    }

    @PostMapping("/one-click-authorization")
    @ApiOperation(value = "一键授权", notes = "一键授权")
    public R oneClickAuthorization(BladeUser bladeUser,@ApiParam(value = "签名图片", required = true) @NotBlank(message = "请上传签名图片") @URL(message = "请输入正确的链接") @RequestParam(required = false) String signPic) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        return onlineSignPicService.oneClickAuthorization(makerEntity.getId(),signPic);
    }

    @PostMapping("/relieve-one-click-authorization")
    @ApiOperation(value = "解除一键授权", notes = "解除一键授权")
    public R relieveOneClickAuthorization(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        return onlineSignPicService.relieveOneClickAuthorization(makerEntity.getId());
    }

    @GetMapping("/query-cooperation-service-provider-list")
    @ApiOperation(value = "查询合作服务商", notes = "查询合作服务商")
    public R queryCooperationServiceProviderList(BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return serviceProviderMakerService.queryCooperationServiceProviderList(makerEntity.getId());
    }

    @GetMapping("/query-service-provider-agreement")
    @ApiOperation(value = "根据服务商查询合同", notes = "根据服务商查询合同")
    public R queryServiceProviderAgreement(Long serviceProviderId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();
        return R.data(agreementService.findByserviceProviderId(serviceProviderId, makerEntity.getId()));
    }
}
