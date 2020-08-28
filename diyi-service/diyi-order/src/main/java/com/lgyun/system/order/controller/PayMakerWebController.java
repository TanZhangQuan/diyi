package com.lgyun.system.order.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.EnterprisePayState;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.order.dto.PayMakerUploadDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.excel.PayEnterpriseImportListener;
import com.lgyun.system.order.service.IPayEnterpriseService;
import com.lgyun.system.order.service.IPayMakerService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * 创客支付明细表控制器
 *
 * @author liangfeihu
 * @since 2020-08-27 11:13:13
 */
@Slf4j
@RestController
@RequestMapping("/web/pay_maker")
@Validated
@AllArgsConstructor
@Api(value = "创客支付明细表相关接口(管理端)", tags = "创客支付明细表相关接口(管理端)")
public class PayMakerWebController {

	private IPayEnterpriseService payEnterpriseService;
	private IPayMakerService payMakerService;
	private IUserClient iUserClient;


	@PostMapping("/upload")
	@ApiOperation(value = "上传分包支付清单", notes = "上传分包支付清单")
	public R upload(@Valid @RequestBody PayMakerUploadDto payMakerUploadDto, BladeUser bladeUser) {

		log.info("上传分包支付清单");
		try {
			//获取当前服务商员工
			R<ServiceProviderWorkerEntity> result = iUserClient.currentServiceProviderWorker(bladeUser);
			if (!(result.isSuccess())) {
				return result;
			}
			ServiceProviderWorkerEntity serviceProviderWorkerEntity = result.getData();

			PayEnterpriseEntity payEnterpriseEntity = payEnterpriseService.getById(payMakerUploadDto.getPayEnterpriseId());
			if (payEnterpriseEntity == null) {
				return R.fail("总包支付清单不存在");
			}

			if (!(serviceProviderWorkerEntity.getServiceProviderId().equals(payEnterpriseEntity.getServiceProviderId()))) {
				return R.fail("总包支付清单不属于当前服务商");
			}

			if (!(PayEnterpriseAuditState.APPROVED.equals(payEnterpriseEntity.getAuditState()))) {
				return R.fail("总包支付清单未审核");
			}

			if (!(EnterprisePayState.CONFIRMPAY.equals(payEnterpriseEntity.getPayState()))) {
				return R.fail("总包支付清单未确认支付");
			}

			String path = payEnterpriseEntity.getChargeListUrl();
			String type = path.substring(path.lastIndexOf(".") + 1);
			//根据文件后缀（xls/xlsx）进行判断
			InputStream input = new URL(path).openStream();
			if (!("xls".equals(type)) && !("xlsx".equals(type))) {
				return R.fail("支付清单文件类型有误");
			}

			PayEnterpriseImportListener payEnterpriseImportListener = new PayEnterpriseImportListener(payMakerService, payEnterpriseEntity, payMakerUploadDto.getPayReceiptUrl());
			InputStream inputStream = new BufferedInputStream(input);
			ExcelReaderBuilder builder = EasyExcel.read(inputStream, PayEnterpriseExcel.class, payEnterpriseImportListener);
			builder.doReadAll();

			return R.success("上传成功");
		} catch (Exception e) {
			log.error("上传分包支付清单异常", e);
		}

		return R.fail("上传失败");
	}

}
