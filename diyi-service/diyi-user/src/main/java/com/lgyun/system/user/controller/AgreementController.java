package com.lgyun.system.user.controller;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Agreement 控制器
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@RestController
@RequestMapping("/agreement")
@Validated
@AllArgsConstructor
@Api(value = "平台合同的信息相关接口", tags = "平台合同的信息相关接口")
public class AgreementController {

    private IAgreementService agreementService;
    private IOnlineSignPicService onlineSignPicService;
    private IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IMakerService iMakerService;

    @GetMapping("/makerIdFind")
    @ApiOperation(value = "根据创客查询合同", notes = "根据创客查询合同")
    public R makerIdFind(BladeUser bladeUser, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {
        log.info("根据创客查询合同");
        try {
            //查询当前创客
            R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return agreementService.makerIdFind(makerEntity.getId(), onlineAgreementTemplateId, onlineAgreementNeedSignId);
        } catch (Exception e) {
            log.error("根据创客查询合同异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/makerIdCompanyFind")
    @ApiOperation(value = "根据商户查询合同", notes = "根据商户查询合同")
    public R makerIdCompanyFind(Long employeeId) {
        log.info("根据商户查询合同");
        try {
            List<AgreementEntity> agreementEntities = agreementService.findByEnterpriseId(employeeId);
            return R.data(agreementEntities);
        } catch (Exception e) {
            log.error("根据商户查询合同异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/getEmployeeAgreement")
    @ApiOperation(value = "查看商户合同", notes = "查看商户合同")
    public R getEmployeeIdAgreement(Long agreementId) {
        log.info("查看商户合同");
        try {
            AgreementEntity agreementEntity = agreementService.getById(agreementId);
            if (null == agreementEntity) {
                R.fail("合同不存在");
            }

            return R.data(agreementEntity.getOnlineAggrementUrl());
        } catch (Exception e) {
            log.error("查看商户合同异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/saveOnlineAgreementNeedSign")
    @ApiOperation(value = "确认签字", notes = "确认签字")
    public R saveOnlineAgreementNeedSign(BladeUser bladeUser, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {
        log.info("保存创客的签名");
        try {
            //查询当前创客
            R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return onlineSignPicService.saveOnlineSignPic(makerEntity.getId(), ObjectType.MAKERPEOPLE, signPic, onlineAgreementTemplateId, onlineAgreementNeedSignId);
        } catch (Exception e) {
            log.error("保存创客的签名异常", e);
        }
        return R.fail("保存签名失败");
    }

    @GetMapping("/getOnlineAgreementNeedSign")
    @ApiOperation(value = "查询创客需要签署的授权协议和合同", notes = "查询创客需要签署的授权协议和合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateType", value = "签署文件类型", paramType = "query", dataType = "string"),
    })
    public R getOnlineAgreementNeedSign(BladeUser bladeUser, TemplateType templateType) {
        log.info("查询创客需要签署的授权协议和合同");
        try {
            //查询当前创客
            R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return onlineAgreementNeedSignService.getOnlineAgreementNeedSign(makerEntity.getId(), templateType);
        } catch (Exception e) {
            log.error("查询创客需要签署的授权协议和合同异常", e);
        }
        return R.fail("查询失败");
    }

    @GetMapping("/selectMakerEnterprisePage")
    @ApiOperation(value = "查询合作商户", notes = "查询合作商户")
    public R selectMakerEnterprisePage(BladeUser bladeUser, Query query) {
        log.info("查询合作商户");
        try {
            //查询当前创客
            R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            IPage<MakerEnterpriseRelationVO> pages = makerEnterpriseService.selectMakerEnterprisePage(Condition.getPage(query.setDescs("create_time")), makerEntity.getId(), RelationshipType.RELEVANCE);
            return R.data(pages);
        } catch (Exception e) {
            log.error("查询合作商户异常", e);
        }
        return R.fail("查询失败");
    }

    @PostMapping("/uploadMakerVideo")
    @ApiOperation(value = "上传创客视频", notes = "上传创客视频")
    public R uploadMakerVideo(BladeUser bladeUser, String applyShortVideo) {
        log.info("上传创客视频");
        try {
            //查询当前创客
            R<MakerEntity> result = iMakerService.currentMaker(bladeUser);
            if (!(result.isSuccess())) {
                return result;
            }
            MakerEntity makerEntity = result.getData();

            return iMakerService.uploadMakerVideo(makerEntity, applyShortVideo);
        } catch (Exception e) {
            log.error("上传创客视频异常", e);
        }
        return R.fail("上传视频失败");
    }

}
