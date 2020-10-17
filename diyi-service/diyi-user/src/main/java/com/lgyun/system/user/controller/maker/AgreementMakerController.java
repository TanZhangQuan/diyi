package com.lgyun.system.user.controller.maker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.RelationshipType;
import com.lgyun.common.enumeration.TemplateType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
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
 * Agreement 控制器
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@RestController
@RequestMapping("/agreement")
@Validated
@AllArgsConstructor
@Api(value = "平台合同的信息相关接口", tags = "平台合同的信息相关接口")
public class AgreementMakerController {

    private IAgreementService agreementService;
    private IOnlineSignPicService onlineSignPicService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IMakerService iMakerService;

    @GetMapping("/makerIdFind")
    @ApiOperation(value = "根据创客查询合同", notes = "根据创客查询合同")
    public R makerIdFind(BladeUser bladeUser, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return agreementService.makerIdFind(makerEntity.getId(), onlineAgreementTemplateId, onlineAgreementNeedSignId);
    }

    @GetMapping("/makerIdCompanyFind")
    @ApiOperation(value = "根据商户查询合同", notes = "根据商户查询合同")
    public R makerIdCompanyFind(Long employeeId) {
        return R.data(agreementService.findByEnterpriseId(employeeId));
    }

    @GetMapping("/getEmployeeAgreement")
    @ApiOperation(value = "查看商户合同", notes = "查看商户合同")
    public R getEmployeeIdAgreement(Long agreementId) {
        AgreementEntity agreementEntity = agreementService.getById(agreementId);
        if (null == agreementEntity) {
            R.fail("合同不存在");
        }

        return R.data(agreementEntity.getOnlineAggrementUrl());
    }

    @PostMapping("/saveOnlineAgreementNeedSign")
    @ApiOperation(value = "确认签字", notes = "确认签字")
    public R saveOnlineAgreementNeedSign(BladeUser bladeUser, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return onlineSignPicService.saveOnlineSignPic(makerEntity.getId(), ObjectType.MAKERPEOPLE, signPic, onlineAgreementTemplateId, onlineAgreementNeedSignId);
    }

    @GetMapping("/getOnlineAgreementNeedSign")
    @ApiOperation(value = "查询创客需要签署的授权协议和合同", notes = "查询创客需要签署的授权协议和合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateType", value = "签署文件类型", paramType = "query", dataType = "string"),
    })
    public R getOnlineAgreementNeedSign(BladeUser bladeUser, TemplateType templateType) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return onlineAgreementNeedSignService.getOnlineAgreementNeedSign(makerEntity.getId(), templateType);
    }

    @GetMapping("/selectMakerEnterprisePage")
    @ApiOperation(value = "查询合作商户", notes = "查询合作商户")
    public R selectMakerEnterprisePage(BladeUser bladeUser, Query query) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        IPage<MakerEnterpriseRelationVO> pages = makerEnterpriseService.selectMakerEnterprisePage(Condition.getPage(query.setDescs("create_time")), makerEntity.getId(), RelationshipType.RELEVANCE);

        return R.data(pages);
    }

    @PostMapping("/uploadMakerVideo")
    @ApiOperation(value = "上传创客视频", notes = "上传创客视频")
    public R uploadMakerVideo(BladeUser bladeUser, String applyShortVideo) {
        //查询当前创客
        R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
        if (!(result.isSuccess())) {
            return result;
        }
        MakerEntity makerEntity = result.getData();

        return iMakerService.uploadMakerVideo(makerEntity, applyShortVideo);
    }

}
