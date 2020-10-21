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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创客端---合同管理模块相关接口
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@RestController
@RequestMapping("/maker/agreement")
@Validated
@AllArgsConstructor
@Api(value = "创客端---合同管理模块相关接口", tags = "创客端---合同管理模块相关接口")
public class AgreementMakerController {

    private IMakerService makerService;
    private IAgreementService agreementService;
    private IOnlineSignPicService onlineSignPicService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private IMakerEnterpriseService makerEnterpriseService;

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

        return R.data(agreementService.findByEnterpriseId(enterpriseId));
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

    @PostMapping("/save-online-agreement-need-sign")
    @ApiOperation(value = "确认签字", notes = "确认签字")
    public R saveOnlineAgreementNeedSign(String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return onlineSignPicService.saveOnlineSignPic(makerEntity.getId(), ObjectType.MAKERPEOPLE, signPic, onlineAgreementTemplateId, onlineAgreementNeedSignId);
    }

    @GetMapping("/query-online-agreement-need-sign")
    @ApiOperation(value = "查询创客需要签署的授权协议和合同", notes = "查询创客需要签署的授权协议和合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateType", value = "签署文件类型", paramType = "query", dataType = "string"),
    })
    public R queryOnlineAgreementNeedSign(TemplateType templateType, BladeUser bladeUser) {
        //查询当前创客
        R<MakerEntity> result = makerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return onlineAgreementNeedSignService.getOnlineAgreementNeedSign(makerEntity.getId(), templateType);
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

        return makerEnterpriseService.selectMakerEnterprisePage(Condition.getPage(query.setDescs("create_time")), makerEntity.getId(), RelationshipType.RELEVANCE);
    }

    @PostMapping("/upload-maker-video")
    @ApiOperation(value = "上传创客视频", notes = "上传创客视频")
    public R uploadMakerVideo(String applyShortVideo, BladeUser bladeUser) {
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
}
