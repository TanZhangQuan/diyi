/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : diyi

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-08-19 15:59:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `diyi_accept_paysheet`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_accept_paysheet`;
CREATE TABLE `diyi_accept_paysheet` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付清单ID',
  `service_time_start` datetime NOT NULL COMMENT '服务开始日期',
  `service_time_end` datetime NOT NULL COMMENT '服务结束日期',
  `accept_paysheet_type` varchar(50) NOT NULL COMMENT '交付支付验收单类型：清单式，单人单张',
  `maker_id` bigint(50) DEFAULT NULL COMMENT '创客ID',
  `upload_date_source` varchar(50) NOT NULL COMMENT '上传来源',
  `upload_date_person` varchar(50) NOT NULL COMMENT '上传人员',
  `accept_paysheet_url` varchar(100) NOT NULL COMMENT '验收单URL',
  `confirm_date` datetime DEFAULT NULL COMMENT '验收单验收日期',
  `confirm_person` varchar(50) NOT NULL DEFAULT '' COMMENT '验收人员',
  `confirm_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '验收说明',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`pay_enterprise_id`,`maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='总包交付支付验收单表';

-- ----------------------------
-- Records of diyi_accept_paysheet
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_accept_paysheet_cs`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_accept_paysheet_cs`;
CREATE TABLE `diyi_accept_paysheet_cs` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票Id',
  `accept_paysheet_type` varchar(50) NOT NULL COMMENT '交付支付验收单类型：清单式，单人单张',
  `self_help_invoice_detail_id` bigint(50) DEFAULT NULL COMMENT '自助开票明细Id',
  `service_time_start` datetime DEFAULT NULL COMMENT '服务开始日期',
  `service_time_end` datetime DEFAULT NULL COMMENT '服务结束日期',
  `upload_date_source` varchar(50) NOT NULL COMMENT '上传来源',
  `upload_date_person` varchar(50) NOT NULL COMMENT '上传人员',
  `accept_paysheet_url` varchar(100) NOT NULL COMMENT '验收单URL',
  `confirm_date` datetime DEFAULT NULL COMMENT '验收单验收日期',
  `confirm_person` varchar(50) NOT NULL DEFAULT '' COMMENT '验收人员',
  `confirm_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '验收说明',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='众包交付支付验收单表';

-- ----------------------------
-- Records of diyi_accept_paysheet_cs
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_address`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_address`;
CREATE TABLE `diyi_address` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `object_id` bigint(50) NOT NULL COMMENT '对象Id',
  `object_type` varchar(20) NOT NULL COMMENT '对象身份1、创客本人2、商户人员3、服务商人员4、相关局人员5、渠道商人员6、合伙人本人',
  `address_name` varchar(50) NOT NULL COMMENT '收件人',
  `address_phone` varchar(50) NOT NULL COMMENT '手机号码',
  `province` varchar(500) NOT NULL COMMENT '省',
  `city` varchar(500) NOT NULL COMMENT '市',
  `area` varchar(20) NOT NULL COMMENT '区',
  `detailed_address` varchar(20) NOT NULL COMMENT '详细地址',
  `is_default` bit(1) NOT NULL COMMENT '是否默认',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收件地址表';

-- ----------------------------
-- Records of diyi_address
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_agreement`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_agreement`;
CREATE TABLE `diyi_agreement` (
  `id` bigint(50) NOT NULL COMMENT '唯一性控制',
  `agreement_type` int(11) NOT NULL COMMENT '协议类别 1,创客加盟协议；2，商户加盟协议；3，服务商加盟协议；4，渠道商加盟协议；5、合伙人加盟协议；6、园区合作协议；7、税局合作协议；8、工商合作协议；9、创客授权书；10、商户-创客补充协议；11、服务商-商户补充协议；12、创客单独税务事项委托授权书；13、创客单独支付事项委托授权书；14、其他协议',
  `sign_type` varchar(50) NOT NULL COMMENT '1、纸质协议2、平台在线协议3、三方在线协议',
  `sign_state` int(11) NOT NULL DEFAULT '0' COMMENT '0签署中 1已完毕',
  `sign_date` datetime NOT NULL COMMENT '签署日期',
  `agreement_no` varchar(100) NOT NULL COMMENT '协议编号',
  `sequence_no` varchar(100) NOT NULL DEFAULT '' COMMENT '顺序号',
  `maker_id` bigint(50) DEFAULT NULL COMMENT '创客ID',
  `enterprise_id` bigint(50) DEFAULT NULL COMMENT '商户ID',
  `service_provider_id` bigint(50) DEFAULT NULL COMMENT '服务商ID',
  `rel_bureau_id` bigint(50) DEFAULT NULL COMMENT '相关局ID',
  `agent_id` bigint(50) DEFAULT NULL COMMENT '渠道商ID',
  `partner_id` bigint(50) DEFAULT NULL COMMENT '合伙人ID',
  `online_agreement_template_id` bigint(50) NOT NULL COMMENT '平台在线协议模板ID',
  `online_aggrement_url` varchar(100) NOT NULL COMMENT '在线协议URL',
  `paper_agreement_url` varchar(100) NOT NULL DEFAULT '' COMMENT '纸质协议URL',
  `third_online_agreement_url` varchar(100) NOT NULL DEFAULT '' COMMENT '三方在线协议URL',
  `paper_agreement_upload` varchar(50) DEFAULT NULL COMMENT '纸质协议上传状态',
  `first_side_sign_person` varchar(100) NOT NULL COMMENT '甲方签署人员',
  `second_side_sign_person` varchar(100) NOT NULL COMMENT '乙方签署人员',
  `third_side_sign_person` varchar(100) NOT NULL DEFAULT '' COMMENT '丙方签署人员',
  `fourth_side_sign_person` varchar(100) NOT NULL DEFAULT '' COMMENT '丁方签署人员',
  `upload_datetime` datetime NOT NULL COMMENT '上传日期',
  `upload_person` varchar(100) NOT NULL COMMENT '上传人员',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='综合合同表';

-- ----------------------------
-- Records of diyi_agreement
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_deliver_material`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_deliver_material`;
CREATE TABLE `diyi_deliver_material` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `paysheet_id` bigint(50) NOT NULL COMMENT '确认函ID',
  `material_title` varchar(50) NOT NULL COMMENT '交付材料标题',
  `material_memo` varchar(500) NOT NULL COMMENT '交付材料说明',
  `material_url` varchar(100) NOT NULL COMMENT '材料URL',
  `third_material_url` varchar(100) NOT NULL COMMENT '第三方URL',
  `upload_date_time` datetime NOT NULL COMMENT '上传日期',
  `upload_person` varchar(100) NOT NULL COMMENT '上传人员',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验收单交付材料表';

-- ----------------------------
-- Records of diyi_deliver_material
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise`;
CREATE TABLE `diyi_enterprise` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_state` varchar(50) NOT NULL COMMENT '商户账户状态',
  `invite_no` varchar(50) NOT NULL COMMENT '创客加入邀请码',
  `enterprise_name` varchar(50) NOT NULL COMMENT '商户名称',
  `legal_person` varchar(50) NOT NULL COMMENT '法人代表名称',
  `legal_person_card` varchar(50) NOT NULL COMMENT '法人身份证',
  `social_credit_no` varchar(100) NOT NULL COMMENT '统一社会信用代码',
  `biz_licence_url` varchar(100) NOT NULL COMMENT '营业执照正本',
  `biz_licence_copy_url` varchar(100) NOT NULL COMMENT '营业执照副本',
  `enterprise_url` varchar(100) NOT NULL COMMENT '企业网址',
  `working_address` varchar(100) NOT NULL COMMENT '办公地址(快递地址）',
  `working_rel_name` varchar(50) NOT NULL COMMENT '收发票/税票快递【到付】联系人姓名',
  `working_rel_phone` varchar(50) NOT NULL COMMENT '收发票/税票快递【到付】联系人手机号',
  `invoice_enterprise_name` varchar(50) NOT NULL COMMENT '开票资料-公司名称',
  `invoice_tax_no` varchar(50) NOT NULL COMMENT '开票资料-税号',
  `invoice_address` varchar(100) NOT NULL COMMENT '开票资料-地址',
  `invoice_tel_no` varchar(50) NOT NULL COMMENT '开票资料-电话',
  `invoice_bank_name` varchar(50) NOT NULL COMMENT '开票资料-开户银行',
  `invoice_account_name` varchar(50) NOT NULL COMMENT '开票资料-账户名',
  `invoice_account` varchar(50) NOT NULL COMMENT '开票资料-账号',
  `business_pattern` varchar(50) NOT NULL COMMENT '业务外包模式：自然人众包（3%普票），自然人总包+分包（6%专票），个体户众包（3%专票），个体户总包+分包（6%专票），个体户众包（3%普票）',
  `crowd_source_pay_path` varchar(50) NOT NULL COMMENT '众包支付通路：通联支付代发，招商银行代发，系统集成代发，平台代收代付，平台预存支付',
  `service_price` decimal(5,2) NOT NULL COMMENT '综合税费率（默认9%）',
  `contact1_name` varchar(50) NOT NULL COMMENT '联系人1姓名（一般为老板/财务负责人）',
  `contact1_position` varchar(50) NOT NULL COMMENT '联系人1职位',
  `contact1_phone` varchar(50) NOT NULL COMMENT '联系人1电话手机（必填）',
  `contact1_mail` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人1邮箱',
  `contact2_name` varchar(50) NOT NULL COMMENT '联系人2姓名',
  `contact2_position` varchar(50) NOT NULL COMMENT '联系人2职位',
  `contact2_phone` varchar(50) NOT NULL COMMENT '联系人2电话手机（必填）',
  `contact2_mail` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人2邮箱',
  `spec_demmand` varchar(500) NOT NULL DEFAULT '' COMMENT '特殊需求',
  `create_type` varchar(50) NOT NULL COMMENT '创建类型：平台创建，自注册',
  `saler_id` bigint(50) NOT NULL COMMENT '营销人员',
  `runner_id` bigint(50) NOT NULL COMMENT '运营人员',
  `industry_type` varchar(50) NOT NULL COMMENT '行业分类',
  `main_business_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '主营业务描述',
  `qyshop_address` varchar(100) NOT NULL COMMENT '企翼网商铺地址',
  `shop_id` bigint(50) NOT NULL COMMENT '商铺ID',
  `shop_user_name` varchar(50) NOT NULL COMMENT '商铺用户名',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`invite_no`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k3` (`enterprise_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户表';

-- ----------------------------
-- Records of diyi_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_enterprise_provider`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_provider`;
CREATE TABLE `diyi_enterprise_provider` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商id',
  `match_person` varchar(50) NOT NULL COMMENT '分配人员',
  `match_desc` varchar(500) NOT NULL COMMENT '分配说明',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`enterprise_id`,`service_provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户服务商关联表';

-- ----------------------------
-- Records of diyi_enterprise_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_enterprise_provider_invoice_catalogs`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_provider_invoice_catalogs`;
CREATE TABLE `diyi_enterprise_provider_invoice_catalogs` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `invoice_catalog_name` varchar(50) NOT NULL COMMENT '发票类目名称',
  `set_date` datetime DEFAULT NULL COMMENT '设置日期',
  `set_person` varchar(50) DEFAULT NULL COMMENT '设置人员',
  `set_type` varchar(50) NOT NULL DEFAULT '1' COMMENT '设置性质',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户-服务商开票类目表';

-- ----------------------------
-- Records of diyi_enterprise_provider_invoice_catalogs
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_enterprise_report`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_report`;
CREATE TABLE `diyi_enterprise_report` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `main_body_type` varchar(50) NOT NULL COMMENT '申报主体类别',
  `main_body_id` bigint(50) NOT NULL COMMENT '申报主体ID',
  `report_theme` varchar(50) NOT NULL COMMENT '申报主题',
  `report_year` int(5) NOT NULL COMMENT '申报年度',
  `report_quater` int(5) NOT NULL COMMENT '申报季度',
  `report_month` int(5) NOT NULL COMMENT '申报月度',
  `report_dead_date` datetime NOT NULL COMMENT '申报截止日期',
  `report_complete_date` datetime DEFAULT NULL COMMENT '申报完成日期',
  `report_result_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '申报结果说明',
  `report_result_files` varchar(500) NOT NULL DEFAULT '' COMMENT '申报结果文件资料',
  `report_person` varchar(50) NOT NULL COMMENT '申报人员',
  `report_state` varchar(50) NOT NULL COMMENT '申报状态',
  `report_guard_name` varchar(50) NOT NULL COMMENT '申报相关政府机关名称',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='年度申报管理表';

-- ----------------------------
-- Records of diyi_enterprise_report
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_enterprise_worker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_worker`;
CREATE TABLE `diyi_enterprise_worker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `user_id` bigint(50) NOT NULL COMMENT '管理者ID',
  `avatar` varchar(100) NOT NULL DEFAULT '' COMMENT '头像',
  `enterprise_worker_state` varchar(50) NOT NULL COMMENT '商户员工账户状态',
  `worker_name` varchar(50) NOT NULL COMMENT '姓名',
  `worker_sex` varchar(50) NOT NULL COMMENT '性别',
  `position_name` varchar(50) NOT NULL COMMENT '岗位性质',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `up_level_id` bigint(50) NOT NULL COMMENT '上级主管',
  `employee_user_name` varchar(50) NOT NULL COMMENT '用户名',
  `employee_pwd` varchar(100) NOT NULL COMMENT '密码',
  `admin_power` bit(1) NOT NULL COMMENT '管理员特性',
  `super_admin` bit(1) NOT NULL COMMENT '是否超级管理员',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`employee_user_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k3` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户员工表';

-- ----------------------------
-- Records of diyi_enterprise_worker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_individual_business`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_individual_business`;
CREATE TABLE `diyi_individual_business` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `service_provider_id` bigint(50) DEFAULT NULL COMMENT '服务商ID',
  `biz_type` varchar(50) NOT NULL COMMENT '个体户税种：小规模',
  `ibname` varchar(50) NOT NULL DEFAULT '' COMMENT '个体户名称',
  `ibtax_no` varchar(50) NOT NULL DEFAULT '' COMMENT '统一社会信用代码',
  `build_date_time` datetime DEFAULT NULL COMMENT '营业执照的注册日期',
  `biz_park` varchar(100) NOT NULL DEFAULT '' COMMENT '园区',
  `provinceand_county` varchar(100) NOT NULL DEFAULT '' COMMENT '所在省市县',
  `registered_money` decimal(12,2) NOT NULL COMMENT '注册资金',
  `business_address` varchar(100) NOT NULL DEFAULT '' COMMENT '经营场所',
  `main_industry` varchar(50) NOT NULL COMMENT '主要行业',
  `biz_scope` varchar(100) NOT NULL COMMENT '经营范围',
  `candidated_names` varchar(500) NOT NULL COMMENT '注册时候选名称',
  `net_business_address` varchar(100) NOT NULL DEFAULT '' COMMENT '网络经营场所',
  `business_licence_url` varchar(100) NOT NULL DEFAULT '' COMMENT '营业执照正本',
  `business_licence_copy_url` varchar(100) NOT NULL DEFAULT '' COMMENT '营业执照副本',
  `ibstate` varchar(50) NOT NULL COMMENT '个体户状态：注册中，税务登记中，运营中，已注销',
  `submit_date_time` datetime DEFAULT NULL COMMENT '提交日期',
  `registered_date` datetime DEFAULT NULL COMMENT '注册日期',
  `tax_register_date_time` datetime DEFAULT NULL COMMENT '税务登记日期',
  `logout_date_time` datetime DEFAULT NULL COMMENT '注销日期',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(50) NOT NULL COMMENT '联系人手机号',
  `service_rat` decimal(3,2) DEFAULT NULL COMMENT '服务费率',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='个体工商户信息表';

-- ----------------------------
-- Records of diyi_individual_business
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_individual_business_annual_fee`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_individual_business_annual_fee`;
CREATE TABLE `diyi_individual_business_annual_fee` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `annual_fee_year` int(5) NOT NULL COMMENT '年费年度',
  `individual_business_id` bigint(50) NOT NULL COMMENT '个体户ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `annual_fee_date_time` datetime NOT NULL COMMENT '年费缴纳日期',
  `annual_fee_amount` decimal(12,2) NOT NULL COMMENT '年费金额',
  `annual_fee_start` datetime NOT NULL COMMENT '年费起始日期',
  `annual_fee_end` datetime NOT NULL COMMENT '年费终止日期',
  `annual_fee_state` varchar(50) NOT NULL COMMENT '年费状态：待缴费，已缴费',
  `year_serial` int(3) NOT NULL COMMENT '第几年',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`individual_business_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='个体工商户年费信息表';

-- ----------------------------
-- Records of diyi_individual_business_annual_fee
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_individual_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_individual_enterprise`;
CREATE TABLE `diyi_individual_enterprise` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `service_provider_id` bigint(50) DEFAULT NULL COMMENT '服务商ID',
  `biz_type` varchar(50) NOT NULL COMMENT '个独税种：小规模，一般纳税人',
  `ibname` varchar(50) NOT NULL DEFAULT '' COMMENT '个体户名称',
  `ibtax_no` varchar(50) NOT NULL DEFAULT '' COMMENT '统一社会信用代码',
  `build_date_time` datetime DEFAULT NULL COMMENT '营业执照的注册日期',
  `biz_park` varchar(100) NOT NULL DEFAULT '' COMMENT '园区',
  `provinceand_county` varchar(100) NOT NULL DEFAULT '' COMMENT '所在省市县',
  `registered_money` decimal(12,2) NOT NULL COMMENT '注册资金',
  `business_address` varchar(100) NOT NULL DEFAULT '' COMMENT '经营场所',
  `main_industry` varchar(50) NOT NULL COMMENT '主要行业',
  `biz_scope` varchar(100) NOT NULL COMMENT '经营范围',
  `candidated_names` varchar(500) NOT NULL COMMENT '注册时候选名称',
  `net_business_address` varchar(100) NOT NULL DEFAULT '' COMMENT '网络经营场所',
  `business_licence_url` varchar(100) NOT NULL DEFAULT '' COMMENT '营业执照正本',
  `business_licence_copy_url` varchar(100) NOT NULL DEFAULT '' COMMENT '营业执照副本',
  `ibstate` varchar(50) NOT NULL COMMENT '个体户状态：注册中，税务登记中，运营中，已注销',
  `submit_date_time` datetime DEFAULT NULL COMMENT '提交日期',
  `registered_date` datetime DEFAULT NULL COMMENT '注册日期',
  `tax_register_date_time` datetime DEFAULT NULL COMMENT '税务登记日期',
  `logout_date_time` datetime DEFAULT NULL COMMENT '注销日期',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(50) NOT NULL COMMENT '联系人手机号',
  `service_rat` decimal(3,2) DEFAULT NULL COMMENT '服务费率',
  `investor_hand_commitment` varchar(100) NOT NULL DEFAULT '' COMMENT '手持承诺书照片',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='个独信息表';

-- ----------------------------
-- Records of diyi_individual_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_individual_enterprise_annual_fee`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_individual_enterprise_annual_fee`;
CREATE TABLE `diyi_individual_enterprise_annual_fee` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `annual_fee_year` int(5) NOT NULL COMMENT '年费年度',
  `individual_enterprise_id` bigint(50) NOT NULL COMMENT '个独ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `annual_fee_date_time` datetime NOT NULL COMMENT '年费缴纳日期',
  `annual_fee_amount` decimal(12,2) NOT NULL COMMENT '年费金额',
  `annual_fee_start` datetime NOT NULL COMMENT '年费起始日期',
  `annual_fee_end` datetime NOT NULL COMMENT '年费终止日期',
  `annual_fee_state` varchar(50) NOT NULL COMMENT '年费状态：待缴费，已缴费',
  `year_serial` int(3) NOT NULL COMMENT '第几年',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`individual_enterprise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='个独年费信息表';

-- ----------------------------
-- Records of diyi_individual_enterprise_annual_fee
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_invoice_application`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_invoice_application`;
CREATE TABLE `diyi_invoice_application` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `invoice_catalog_id` bigint(50) NOT NULL COMMENT '开票类目ID',
  `application_date` datetime NOT NULL COMMENT '申请日期',
  `application_person` varchar(50) NOT NULL COMMENT '申请人',
  `voice_total_amount` decimal(12,2) NOT NULL COMMENT '开票总额',
  `application_desc` varchar(100) DEFAULT NULL COMMENT '申请说明',
  `application_state` varchar(50) NOT NULL COMMENT '处理状态 1,申请中；2，已拒绝；3，已全额开具；4，已部分开具，5已取消',
  `application_handle_desc` varchar(1000) DEFAULT NULL COMMENT '处理说明',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开票申请表';

-- ----------------------------
-- Records of diyi_invoice_application
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_invoice_application_pay_list`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_invoice_application_pay_list`;
CREATE TABLE `diyi_invoice_application_pay_list` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `application_id` bigint(50) NOT NULL COMMENT '总包开票申请ID',
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付清单ID',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='总包开票申请关联的支付清单';

-- ----------------------------
-- Records of diyi_invoice_application_pay_list
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_maker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker`;
CREATE TABLE `diyi_maker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `user_id` bigint(50) NOT NULL COMMENT '管理者ID',
  `openid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信open_id',
  `session_key` varchar(50) NOT NULL DEFAULT '' COMMENT '微信session_key',
  `rel_date` datetime DEFAULT NULL COMMENT '微信关联日期',
  `due_date` datetime DEFAULT NULL COMMENT '到期日期',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `avatar` varchar(100) NOT NULL DEFAULT '' COMMENT '头像',
  `certification_state` varchar(50) NOT NULL COMMENT '认证状态',
  `empower_sign_state` varchar(50) NOT NULL COMMENT '授权协议状态',
  `join_sign_state` varchar(100) DEFAULT NULL COMMENT '加盟协议状态',
  `maker_state` varchar(50) NOT NULL COMMENT '账户状态',
  `politic_state` varchar(50) NOT NULL DEFAULT '' COMMENT '政治面貌',
  `nationality` varchar(50) NOT NULL DEFAULT '' COMMENT '民族',
  `levelofedu` varchar(50) NOT NULL DEFAULT '' COMMENT '文化程度',
  `email_address` varchar(100) NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `idcard_no` varchar(100) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `phone_number2` varchar(50) NOT NULL DEFAULT '' COMMENT '手机号码2',
  `login_pwd` varchar(100) NOT NULL COMMENT '登录密码',
  `bank_card_no` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡号',
  `bank_name` varchar(100) NOT NULL DEFAULT '' COMMENT '开户银行',
  `sub_bank_name` varchar(100) NOT NULL DEFAULT '' COMMENT '开户支行',
  `idcard_pic` varchar(100) NOT NULL DEFAULT '' COMMENT '身份证正面图',
  `idcard_pic_back` varchar(100) NOT NULL DEFAULT '' COMMENT '身份证反面图',
  `self_pic` varchar(100) NOT NULL DEFAULT '' COMMENT '正面自拍照',
  `idcar_copy` varchar(100) NOT NULL DEFAULT '' COMMENT '身份证复印件图',
  `idcard_back_hand` varchar(100) NOT NULL DEFAULT '' COMMENT '手持证件反面照',
  `idcard_hand` varchar(100) NOT NULL DEFAULT '' COMMENT '手持证件正面照',
  `idcard_verify_status` varchar(50) NOT NULL COMMENT '身份证验证状态：未验证，验证通过，验证未通过',
  `idcard_verify_date` datetime DEFAULT NULL COMMENT '身份证验证日期时间',
  `face_verify_status` varchar(50) NOT NULL COMMENT '人脸验证状态：未验证，验证通过，验证未通过',
  `face_verify_date` datetime DEFAULT NULL COMMENT '人脸验证日期时间',
  `bank_card_verify_status` varchar(50) NOT NULL COMMENT '银行卡验证状态：未验证，验证通过，验证未通过',
  `bank_card_verify_date` datetime DEFAULT NULL COMMENT '银行卡验证日期时间',
  `phone_number_verify_status` varchar(50) NOT NULL COMMENT '手机号码验证状态：未验证，验证通过，验证未通过',
  `phone_number_verify_date` datetime DEFAULT NULL COMMENT '手机号码验证日期时间',
  `pic_verify` varchar(100) NOT NULL DEFAULT '' COMMENT '验证图片',
  `idcard_verify_type` varchar(50) DEFAULT NULL COMMENT '身份证验证类型：系统验证，手工验证',
  `manual_verify_name` varchar(50) NOT NULL DEFAULT '' COMMENT '手工验证人',
  `manual_verify_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '验证描述',
  `run_address` varchar(100) NOT NULL DEFAULT '' COMMENT '线上经营场所',
  `house_address` varchar(100) NOT NULL DEFAULT '' COMMENT '线下经营地址',
  `living_address` varchar(100) NOT NULL DEFAULT '' COMMENT '住址',
  `self_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '自我描述',
  `shop_id` bigint(50) DEFAULT NULL COMMENT '商铺ID',
  `shop_url` varchar(100) NOT NULL DEFAULT '' COMMENT '商铺URL',
  `shop_user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '商铺用户名',
  `apply_short_video` varchar(100) NOT NULL DEFAULT '' COMMENT '声明短视频',
  `video_audit` varchar(50) NOT NULL COMMENT '短视频审核状态：未审核，审核通过，审核未通过',
  `video_audit_date` datetime DEFAULT NULL COMMENT '审核日期',
  `video_audit_person_id` bigint(50) DEFAULT NULL COMMENT '审核人员',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`user_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创客表';

-- ----------------------------
-- Records of diyi_maker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_maker_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker_enterprise`;
CREATE TABLE `diyi_maker_enterprise` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `position_id` bigint(50) DEFAULT NULL COMMENT '外包岗位ID',
  `rel_type` varchar(50) NOT NULL COMMENT '关联类型：创客主动关联，企业主动关联，平台关联',
  `relationship_type` varchar(50) NOT NULL COMMENT '创客商户关系',
  `rel_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '关联备注',
  `cooperate_status` varchar(50) DEFAULT NULL COMMENT '合作状态：合作中，停止合作；首次关联时默认为合作中',
  `first_cooperation` bit(1) DEFAULT NULL COMMENT '创客第一次合作',
  `cooperation_start_time` datetime NOT NULL COMMENT '合作开始日期',
  `cooperation_end_time` datetime DEFAULT NULL COMMENT '合作终止日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`enterprise_id`,`maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创客商户关联表';

-- ----------------------------
-- Records of diyi_maker_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_maker_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker_invoice`;
CREATE TABLE `diyi_maker_invoice` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_maker_id` bigint(50) NOT NULL COMMENT '创客支付ID',
  `voice_type_no` varchar(50) NOT NULL COMMENT '发票代码',
  `voice_serial_no` varchar(50) NOT NULL COMMENT '发票号码',
  `maker_voice_get_date_time` datetime NOT NULL COMMENT '发票开具日期',
  `voice_category` varchar(50) NOT NULL COMMENT '货物或应税劳务、服务名称',
  `total_amount` decimal(12,2) NOT NULL COMMENT '价税合计',
  `sales_amount` decimal(12,2) NOT NULL COMMENT '金额合计',
  `tax_amount` decimal(12,2) NOT NULL COMMENT '税额合计',
  `voice_person` varchar(50) NOT NULL DEFAULT '' COMMENT '开票人',
  `sale_company` varchar(50) NOT NULL DEFAULT '' COMMENT '销售方名称',
  `help_make_organation_name` varchar(50) NOT NULL COMMENT '代开机关名称',
  `help_make_company` varchar(50) NOT NULL COMMENT '代开企业名称',
  `help_make_tax_no` varchar(50) NOT NULL COMMENT '代开企业税号',
  `maker_voice_url` varchar(100) NOT NULL COMMENT '发票URL',
  `maker_voice_upload_date_time` datetime NOT NULL COMMENT '发票上传日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创客门征单开发票信息表';

-- ----------------------------
-- Records of diyi_maker_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_maker_tax_record`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker_tax_record`;
CREATE TABLE `diyi_maker_tax_record` (
  `id` bigint(50) NOT NULL COMMENT '唯一性控制',
  `pay_maker_id` bigint(50) NOT NULL COMMENT '创客支付ID',
  `voice_type_no` varchar(100) NOT NULL COMMENT '票证号码',
  `voice_serial_no` varchar(100) NOT NULL COMMENT '金库名称',
  `maker_tax_amount` decimal(5,2) NOT NULL COMMENT '总税金',
  `total_amount` decimal(5,2) NOT NULL COMMENT '填票人',
  `SalesAmount` decimal(5,2) NOT NULL COMMENT '金额',
  `tax_amount` varchar(100) NOT NULL COMMENT '纳税人名称',
  `voice_person` varchar(100) NOT NULL COMMENT '纳税人识别号',
  `sale_company` varchar(100) NOT NULL COMMENT '税务机关',
  `help_make_organation_name` int(11) NOT NULL COMMENT '证明类型',
  `maker_tax_url` varchar(300) NOT NULL COMMENT '税务机关',
  `maker_tax_get_datetime` datetime NOT NULL COMMENT '完税证明开具日期',
  `maker_tax_upload_datetime` datetime NOT NULL COMMENT '完税证明上传日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创客单张完税证明信息表';

-- ----------------------------
-- Records of diyi_maker_tax_record
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_maker_total_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker_total_invoice`;
CREATE TABLE `diyi_maker_total_invoice` (
  `id` bigint(50) NOT NULL,
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付清单ID',
  `invoice_type_no` varchar(100) NOT NULL COMMENT '发票代码',
  `invoice_serial_no` varchar(100) NOT NULL COMMENT '发票号码',
  `invoice_datetime` datetime NOT NULL COMMENT '开票日期',
  `invoice_category` varchar(100) NOT NULL COMMENT '货物或应税劳务、服务名称',
  `total_amount` decimal(5,2) NOT NULL,
  `sales_amount` decimal(5,2) NOT NULL COMMENT '金额合计',
  `tax_amount` decimal(5,2) NOT NULL COMMENT '税额合计',
  `invoice_person` varchar(5) NOT NULL COMMENT '开票人',
  `sale_company` varchar(100) NOT NULL COMMENT '销售方名称',
  `company_invoice_url` varchar(300) NOT NULL COMMENT '总包发票URL',
  `company_voice_upload_datetime` datetime NOT NULL COMMENT '发票上传日期',
  `maker_tax_url` varchar(300) NOT NULL COMMENT '总完税证明URL',
  `maker_tax_list_url` varchar(3000) NOT NULL COMMENT '清单式完税凭证URL',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='汇总代开发票表';

-- ----------------------------
-- Records of diyi_maker_total_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_online_agreement_need_sign`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_online_agreement_need_sign`;
CREATE TABLE `diyi_online_agreement_need_sign` (
  `id` bigint(50) NOT NULL COMMENT '唯一性控制',
  `online_agreement_template_id` bigint(50) NOT NULL COMMENT '平台在线协议模板ID',
  `object_type` int(1) NOT NULL COMMENT '对象身份1、创客本人2、商户人员3、服务商人员4、相关局人员5、渠道商人员6、合伙人本人',
  `sign_state` varchar(50) NOT NULL DEFAULT '0' COMMENT '0未签约，1已签约',
  `sign_power` varchar(50) NOT NULL COMMENT '签字对象性质 甲方；2，乙方；3，丙方；4，丁方',
  `object_id` bigint(50) NOT NULL COMMENT '对象ID 1、创客ID2、商户ID，具体签署时可能是某个用户3、服务商ID，具体签署时可能是某个用户4、相关局ID，具体签署时可能是某个用户5、渠道商ID，具体签署时可能是某个用户6、合伙人ID',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`online_agreement_template_id`,`sign_power`,`object_type`,`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台在线协议需要签署清单表';

-- ----------------------------
-- Records of diyi_online_agreement_need_sign
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_online_agreement_template`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_online_agreement_template`;
CREATE TABLE `diyi_online_agreement_template` (
  `id` bigint(50) NOT NULL COMMENT '唯一性控制',
  `agreement_type` varchar(50) NOT NULL COMMENT '协议类别',
  `template_state` varchar(50) NOT NULL COMMENT '模板状态',
  `template_sign_state` varchar(255) NOT NULL COMMENT '模板开启状态',
  `agreement_template` varchar(100) NOT NULL COMMENT '协议模板',
  `upload_person` varchar(100) NOT NULL COMMENT '上传人员',
  `upload_date` datetime NOT NULL COMMENT '上传日期',
  `change_state_person` varchar(100) NOT NULL COMMENT '变更状态人员',
  `shange_state_date` datetime NOT NULL COMMENT '变更日期',
  `bool_all_makers` bit(1) NOT NULL COMMENT '是否需要全部创客签署',
  `template_type` varchar(50) NOT NULL COMMENT '模板类型',
  `template_count` int(11) NOT NULL COMMENT '模板的页数',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`agreement_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台在线协议模板表';

-- ----------------------------
-- Records of diyi_online_agreement_template
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_online_sign_pic`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_online_sign_pic`;
CREATE TABLE `diyi_online_sign_pic` (
  `id` bigint(50) NOT NULL COMMENT '唯一性控制',
  `object_type` varchar(50) NOT NULL COMMENT '对象身份1、创客本人2、商户人员3、服务商人员4、相关局人员5、渠道商人员6、合伙人本人',
  `object_id` bigint(50) NOT NULL COMMENT '对象身份ID',
  `worker_sex` bigint(50) NOT NULL COMMENT '相关人员ID 创客/合伙人，就取创客/合伙人ID，其余取相关工作人员ID',
  `sign_pic` varchar(100) NOT NULL COMMENT '签字笔迹URL',
  `sign_datetime` datetime NOT NULL COMMENT '签署日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='在线签字图片表';

-- ----------------------------
-- Records of diyi_online_sign_pic
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_pay`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay`;
CREATE TABLE `diyi_pay` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `order_id` bigint(50) NOT NULL COMMENT '订单ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `maker_type` varchar(50) NOT NULL COMMENT '创客类别：自然人，个体户',
  `individual_business_name` varchar(50) NOT NULL COMMENT '个体户名称',
  `maker_neIncome` decimal(12,2) NOT NULL COMMENT '创客到手外包费',
  `maker_tax_and_fee` decimal(12,2) NOT NULL COMMENT '创客税费',
  `company_manage_fee` decimal(12,2) NOT NULL COMMENT '企业管理服务费',
  `audit_fee` decimal(12,2) NOT NULL COMMENT '身份验证费',
  `pay_fee` decimal(12,2) NOT NULL COMMENT '支付手续费',
  `pay_state` varchar(50) NOT NULL COMMENT '支付状态：待支付，企业已申请支付，企业已支付，平台已支付，已确认收款',
  `company_apply_date_time` datetime DEFAULT NULL COMMENT '发票上传日期',
  `company_pay_ok_date_time` datetime DEFAULT NULL COMMENT '发票上传日期',
  `platform_pay_ok_date_time` datetime DEFAULT NULL COMMENT '发票上传日期',
  `maker_confirm_date_time` datetime DEFAULT NULL COMMENT '发票上传日期',
  `maker_tax_state` varchar(50) NOT NULL COMMENT '创客完税证明开票状态: 未开，已开',
  `maker_voice_state` varchar(50) NOT NULL COMMENT '创客发票开票状态: 未开，已开',
  `pay_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '支付说明',
  `maker_voice_category` varchar(50) NOT NULL COMMENT '创客发票类目',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='未知表';

-- ----------------------------
-- Records of diyi_pay
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_paysheet`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_paysheet`;
CREATE TABLE `diyi_paysheet` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_id` bigint(50) NOT NULL COMMENT '支付ID',
  `order_id` bigint(50) NOT NULL COMMENT '订单ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `upload_date_time` datetime NOT NULL COMMENT '上传日期',
  `paysheet_upload_source` varchar(50) NOT NULL COMMENT '上传来源：平台，外包企业',
  `upload_date_person` varchar(50) NOT NULL COMMENT '上传人员',
  `paysheet_url` varchar(100) NOT NULL COMMENT '确认函URL',
  `paysheet_confirm_date_time` datetime NOT NULL COMMENT '确认函确认日期',
  `paysheet_confirm_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '确认函确认说明',
  `income_confirm_date` datetime NOT NULL COMMENT '收款确认日期',
  `income_confirm_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '收款确认说明',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='未知表';

-- ----------------------------
-- Records of diyi_paysheet
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_pay_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay_enterprise`;
CREATE TABLE `diyi_pay_enterprise` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `charge_list_url` varchar(100) NOT NULL COMMENT '支付清单URL',
  `worksheet_id` bigint(50) DEFAULT NULL COMMENT '工单ID',
  `pay_to_platform_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '支付总额=外包费总额+总身份验证费+总开票手续费',
  `sourcing_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '外包费总额',
  `service_rate` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '服务税费率',
  `total_tax_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总税费=外包费总额*服务税费率',
  `maker_num` int(10) DEFAULT NULL COMMENT '创客数',
  `identify_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总身份验证费',
  `service_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总支付手续费',
  `pay_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '支付说明',
  `pay_state` varchar(50) NOT NULL COMMENT '支付给平台状态：待支付，已支付，已确认收款',
  `pay_confirm_date_time` datetime DEFAULT NULL COMMENT '支付确认日期时间',
  `confirm_date_time` datetime DEFAULT NULL COMMENT '确认回款日期时间',
  `employee_id` bigint(50) DEFAULT NULL COMMENT '确认到款人员ID',
  `audit_state` varchar(20) NOT NULL COMMENT '1，编辑中；2，已提交；3，审核通过；4，已驳回。驳回的还可以再提交审核',
  `company_invoice_state` varchar(50) NOT NULL COMMENT '开票状态：未开，已开',
  `invoice_print_date` datetime DEFAULT NULL COMMENT '开票日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户支付清单表';

-- ----------------------------
-- Records of diyi_pay_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_pay_enterprise_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay_enterprise_receipt`;
CREATE TABLE `diyi_pay_enterprise_receipt` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付ID',
  `enterprise_pay_receipt_url` varchar(100) NOT NULL COMMENT '支付回单图片URL地址',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户支付回单表';

-- ----------------------------
-- Records of diyi_pay_enterprise_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_pay_maker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay_maker`;
CREATE TABLE `diyi_pay_maker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付清单ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `maker_type` varchar(50) NOT NULL COMMENT '创客身份，自然人，个体户，个独。',
  `Individual_business_name` varchar(100) NOT NULL COMMENT '个体户/个独名称',
  `total_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总费用 外包费总额+身份验证费+支付手续费	',
  `maker_ne_income` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '外包费总额',
  `service_rate` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '服务税费率',
  `maker_tax_and_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '创客税费:外包费总额*服务税费率',
  `maker_net_income` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '创客到手:外包费总额-创客税费',
  `audit_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '身份验证费',
  `pay_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '支付手续费',
  `pay_state` varchar(50) NOT NULL COMMENT '1：待支付；2:企业已申请支付；3：企业已支付；4：平台已支付；5：已确认收款',
  `company_apply_datetime` datetime NOT NULL COMMENT '企业申请支付日期时间',
  `company_pay_ok_datetime` datetime NOT NULL COMMENT '企业支付确认日期时间',
  `platform_pay_ok_datetime` datetime NOT NULL COMMENT '平台支付确认日期时间',
  `maker_confirm_datetime` datetime NOT NULL COMMENT '取交付支付确认函的确认到款日期时间',
  `maker_tax_state` varchar(50) NOT NULL COMMENT '完税证明开票状态:1:已开；0：未开',
  `maker_invoice_state` varchar(50) NOT NULL COMMENT '发票开票状态:1:已开；0：未开',
  `invoice_type` varchar(50) NOT NULL COMMENT '发票类别:1,汇总代开；2，门征单开',
  `pay_memo` varchar(500) DEFAULT NULL COMMENT '支付说明',
  `maker_invoice_category` varchar(1000) NOT NULL COMMENT '创客发票类目:默认取订单中的默认信息，可更改，根据具体业务开，如*现代服务*市场推广费		',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创客支付明细表';

-- ----------------------------
-- Records of diyi_pay_maker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_pay_maker_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay_maker_receipt`;
CREATE TABLE `diyi_pay_maker_receipt` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_maker_id` bigint(50) NOT NULL COMMENT '创客支付ID',
  `maker_pay_receipt_url` varchar(100) NOT NULL COMMENT '支付回单图片URL地址',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创客支付明细回单表';

-- ----------------------------
-- Records of diyi_pay_maker_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_pay_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay_receipt`;
CREATE TABLE `diyi_pay_receipt` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_id` bigint(50) NOT NULL COMMENT '创客支付ID',
  `maker_pay_receipt_url` varchar(100) NOT NULL COMMENT '支付回单',
  `upload_date_time` datetime NOT NULL COMMENT '上传日期时间',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='未知表';

-- ----------------------------
-- Records of diyi_pay_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_platform_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_platform_invoice`;
CREATE TABLE `diyi_platform_invoice` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `application_id` bigint(50) NOT NULL COMMENT '总包开票申请ID',
  `invoice_print_date` datetime NOT NULL COMMENT '开票日期',
  `invoice_total_amount` decimal(12,2) NOT NULL COMMENT '开票总额',
  `invoice_numbers` int(11) NOT NULL COMMENT '发票张数',
  `invoice_print_person` varchar(50) NOT NULL COMMENT '开票人',
  `express_sheet_no` varchar(50) NOT NULL COMMENT '快递单号',
  `express_company_name` varchar(50) DEFAULT NULL COMMENT '快递公司',
  `express_update_datetime` datetime DEFAULT NULL COMMENT '快递更新日期',
  `express_update_person` datetime DEFAULT NULL COMMENT '快递更新人员',
  `express_update_person_tel` varchar(50) NOT NULL COMMENT '快递更新人员电话',
  `express_update_personTel` varchar(50) NOT NULL COMMENT '快递更新人员电话',
  `invoice_desc` varchar(50) NOT NULL COMMENT '开票说明',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='总包发票信息表';

-- ----------------------------
-- Records of diyi_platform_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_platform_invoice_list`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_platform_invoice_list`;
CREATE TABLE `diyi_platform_invoice_list` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `invoice_print_id` bigint(50) NOT NULL COMMENT '开票ID',
  `invoice_type_no` varchar(100) NOT NULL COMMENT '发票代码',
  `invoice_serial_no` varchar(100) NOT NULL COMMENT '发票号码',
  `invoice_datetime` datetime NOT NULL COMMENT '开票日期',
  `invoice_category` varchar(400) NOT NULL COMMENT '货物或应税劳务、服务名称',
  `total_amount` decimal(12,2) NOT NULL COMMENT '价税合计',
  `sales_amount` decimal(12,2) NOT NULL COMMENT '金额合计',
  `tax_amount` decimal(12,2) NOT NULL COMMENT '税额合计',
  `invoice_person` varchar(100) NOT NULL COMMENT '开票人',
  `sale_company` varchar(100) NOT NULL COMMENT '销售方名称',
  `company_invoice_url` varchar(100) NOT NULL COMMENT '总包发票URL',
  `company_voice_upload_datetime` datetime NOT NULL COMMENT '发票上传日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记录服务商开具给商户的总包发票';

-- ----------------------------
-- Records of diyi_platform_invoice_list
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_platform_invoice_pay_list`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_platform_invoice_pay_list`;
CREATE TABLE `diyi_platform_invoice_pay_list` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `invoice_print_id` bigint(50) NOT NULL COMMENT '开票ID',
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付清单ID',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记录服务商开具给商户的总包发票关联的支付清单';

-- ----------------------------
-- Records of diyi_platform_invoice_pay_list
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_platform_voice`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_platform_voice`;
CREATE TABLE `diyi_platform_voice` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付ID',
  `order_id` bigint(50) NOT NULL COMMENT '订单ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `voice_type_no` varchar(50) NOT NULL COMMENT '发票代码',
  `voice_serial_no` varchar(50) NOT NULL COMMENT '发票号码',
  `voice_date_time` datetime NOT NULL COMMENT '开票日期',
  `voice_category` varchar(50) NOT NULL COMMENT '货物或应税劳务、服务名称',
  `total_amount` decimal(12,2) NOT NULL COMMENT '价税合计',
  `sales_amount` decimal(12,2) NOT NULL COMMENT '金额合计',
  `tax_amount` decimal(12,2) NOT NULL COMMENT '税额合计',
  `voice_person` varchar(50) NOT NULL COMMENT '开票人',
  `sale_company` varchar(50) NOT NULL COMMENT '销售方名称',
  `companyInvoice_url` varchar(100) NOT NULL COMMENT '平台给企业开票URL地址',
  `company_voice_upload_date_time` datetime DEFAULT NULL COMMENT '发票上传日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='未知表';

-- ----------------------------
-- Records of diyi_platform_voice
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_position`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_position`;
CREATE TABLE `diyi_position` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `position_name` varchar(50) NOT NULL COMMENT '外包岗位名称',
  `position_desc` varchar(500) NOT NULL COMMENT '外包岗位描述',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`position_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外包岗位表';

-- ----------------------------
-- Records of diyi_position
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice`;
CREATE TABLE `diyi_self_help_invoice` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `online_pay_id` bigint(50) DEFAULT NULL COMMENT '在线支付ID',
  `apply_maker_id` bigint(50) DEFAULT NULL COMMENT '申请创客ID',
  `apply_enterprise_id` bigint(50) DEFAULT NULL COMMENT '申请商户ID',
  `apply_channel_id` bigint(50) DEFAULT NULL COMMENT '申请渠道ID',
  `apply_partner_id` bigint(50) DEFAULT NULL COMMENT '申请合伙人ID',
  `apply_management_id` bigint(50) DEFAULT NULL COMMENT '平台用户ID',
  `original_self_help_id` bigint(50) DEFAULT NULL COMMENT '原自助开票ID',
  `invoice_people_type` varchar(50) NOT NULL COMMENT '开票人身份类别',
  `list_file` varchar(100) NOT NULL COMMENT '开票清单文件',
  `charge_money_num` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总价税合计额',
  `service_rate` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '服务税费率',
  `service_and_tax_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总服务税费',
  `service_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总服务费',
  `service_tax` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总税',
  `service_invoice_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总开票手续费',
  `idendity_confirm_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总身份验证费',
  `address_id` bigint(50) NOT NULL COMMENT '收件地址Id',
  `confirm_price_person` varchar(100) NOT NULL DEFAULT '' COMMENT '核价人员',
  `confirm_price_datetime` datetime DEFAULT NULL COMMENT '核价时间',
  `current_state` varchar(50) NOT NULL COMMENT '当前状态：0,未申请，1，申请编辑中，2，审核中；3，已通过开票中；4，已驳回；5，已开票结束',
  `express_sheet_no` varchar(100) NOT NULL DEFAULT '' COMMENT '快递单号',
  `express_company_name` varchar(100) NOT NULL DEFAULT '' COMMENT '快递公司名称',
  `express_update_datetime` datetime DEFAULT NULL COMMENT '快递更新日期',
  `express_update_person` varchar(50) NOT NULL DEFAULT '' COMMENT '快递更新人员',
  `express_update_person_tel` varchar(50) NOT NULL DEFAULT '' COMMENT '快递更新人员电话',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票主表';

-- ----------------------------
-- Records of diyi_self_help_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_account`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_account`;
CREATE TABLE `diyi_self_help_invoice_account` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `account_name` varchar(50) NOT NULL COMMENT '账户名称',
  `account_no` varchar(50) NOT NULL COMMENT '银行账号',
  `account_bank` varchar(50) NOT NULL COMMENT '开户银行',
  `basic_account_bank` varchar(50) NOT NULL COMMENT '基本存款账号',
  `is_default` bit(1) NOT NULL COMMENT '是否默认',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票收款账号表';

-- ----------------------------
-- Records of diyi_self_help_invoice_account
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_apply`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_apply`;
CREATE TABLE `diyi_self_help_invoice_apply` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票主表id',
  `apply_date` datetime NOT NULL COMMENT '申请日期',
  `apply_state` varchar(100) NOT NULL COMMENT '申请状态',
  `apply_desc` varchar(500) NOT NULL COMMENT '申请说明',
  `audit_desc` varchar(500) NOT NULL COMMENT '审核说明',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票申请表';

-- ----------------------------
-- Records of diyi_self_help_invoice_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_detail`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_detail`;
CREATE TABLE `diyi_self_help_invoice_detail` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票Id',
  `invoice_people_name` varchar(100) NOT NULL COMMENT '开票人姓名',
  `invoice_people_type` varchar(50) NOT NULL COMMENT '开票人身份类别',
  `invoice_print_state` varchar(50) NOT NULL COMMENT '开票状态',
  `maker_id` bigint(50) DEFAULT NULL COMMENT '创客ID',
  `none_maker_invoice_person_id` bigint(50) DEFAULT NULL COMMENT '非创客开票人ID',
  `all_kind_enterprise_id` bigint(50) DEFAULT NULL COMMENT '各类企业ID',
  `invoice_type` varchar(100) NOT NULL COMMENT '开票类目',
  `charge_money_num` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '价税合计额',
  `flow_contract_url` varchar(500) NOT NULL COMMENT '流水回单URL',
  `business_contract_url` varchar(100) NOT NULL COMMENT '业务合同URL',
  `account_balance_url` varchar(100) NOT NULL COMMENT '账户余额url',
  `deliver_sheet_url` varchar(300) DEFAULT NULL COMMENT '交付支付验收单URL',
  `service_invoice_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '开票手续费',
  `idendity_confirm_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '身份验证费',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票明细表';

-- ----------------------------
-- Records of diyi_self_help_invoice_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_express`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_express`;
CREATE TABLE `diyi_self_help_invoice_express` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '唯一性控制',
  `address_id` bigint(50) NOT NULL COMMENT '地址id',
  `express_no` varchar(100) NOT NULL COMMENT '快递单号',
  `express_file_url` varchar(100) NOT NULL COMMENT '快递回单或二维码',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票快递单号表';

-- ----------------------------
-- Records of diyi_self_help_invoice_express
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_fee`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_fee`;
CREATE TABLE `diyi_self_help_invoice_fee` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票Id',
  `putin_date` datetime NOT NULL COMMENT '提交日期',
  `give_price_date` datetime NOT NULL COMMENT '核价日期',
  `total_tax_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总税费',
  `basic_tax_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '基础税费',
  `basic_tax_fee_rate` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '基础税费率',
  `invoice_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '开票手续费',
  `identify_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '身份验证费',
  `pay_desc` varchar(500) NOT NULL COMMENT '支付说明',
  `pay_certificate` varchar(500) NOT NULL COMMENT '支付回单',
  `pay_type` varchar(50) NOT NULL COMMENT '支付方式 1，微信；2，支付宝，3，银行转账；4，现金',
  `hand_pay_account_id` bigint(50) NOT NULL COMMENT '自助开票收款账号ID',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票收费表';

-- ----------------------------
-- Records of diyi_self_help_invoice_fee
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_person`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_person`;
CREATE TABLE `diyi_self_help_invoice_person` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `id_card_no` varchar(50) NOT NULL COMMENT '身份证号码',
  `id_card_name` varchar(50) NOT NULL COMMENT '身份证姓名',
  `id_card_pic` varchar(500) NOT NULL COMMENT '身份证正面图',
  `id_card_pic_back` varchar(500) NOT NULL COMMENT '身份证反面图',
  `phone_number` varchar(20) NOT NULL COMMENT '手机号码',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票非创客开票人表';

-- ----------------------------
-- Records of diyi_self_help_invoice_person
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider`;
CREATE TABLE `diyi_service_provider` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_state` varchar(50) NOT NULL COMMENT '服务商账户状态',
  `service_provider_name` varchar(50) NOT NULL COMMENT '服务商名称',
  `legal_person` varchar(50) NOT NULL COMMENT '法人代表名称',
  `legal_person_card` varchar(50) NOT NULL COMMENT '法人身份证',
  `social_credit_no` varchar(100) NOT NULL COMMENT '统一社会信用代码',
  `biz_licence_url` varchar(100) NOT NULL COMMENT '营业执照正本',
  `biz_licence_copy_url` varchar(100) NOT NULL COMMENT '营业执照副本',
  `service_provider_url` varchar(100) NOT NULL COMMENT '企业网址',
  `working_address` varchar(100) NOT NULL COMMENT '办公地址(快递地址）',
  `working_rel_name` varchar(50) NOT NULL COMMENT '收发票/税票快递【到付】联系人姓名',
  `working_rel_phone` varchar(50) NOT NULL COMMENT '收发票/税票快递【到付】联系人手机号',
  `invoice_enterprise_name` varchar(50) NOT NULL COMMENT '开票资料-公司名称',
  `invoice_tax_no` varchar(50) NOT NULL COMMENT '开票资料-税号',
  `invoice_address` varchar(100) NOT NULL COMMENT '开票资料-地址',
  `invoice_tel_no` varchar(50) NOT NULL COMMENT '开票资料-电话',
  `invoice_bank_name` varchar(50) NOT NULL COMMENT '开票资料-开户银行',
  `invoice_account_name` varchar(50) NOT NULL COMMENT '开票资料-账户名',
  `invoice_account` varchar(50) NOT NULL COMMENT '开票资料-账号',
  `business_pattern` varchar(50) NOT NULL COMMENT '业务外包产品范围：自然人众包，自然人纵波+分包，个体户众包，个体户总包+分包，个独，有限公司',
  `crowd_source_pay_path` varchar(50) NOT NULL COMMENT '众包支付通路：连连支付，银行支付',
  `service_price` decimal(5,2) NOT NULL COMMENT '综合税费率（默认9%）',
  `contact1_name` varchar(50) NOT NULL COMMENT '联系人1姓名（一般为老板/财务负责人）',
  `contact1_position` varchar(50) NOT NULL COMMENT '联系人1职位',
  `contact1_phone` varchar(50) NOT NULL COMMENT '联系人1电话手机（必填）',
  `contact1_mail` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人1邮箱',
  `contact2_name` varchar(50) NOT NULL COMMENT '联系人2姓名',
  `contact2_position` varchar(50) NOT NULL COMMENT '联系人2职位',
  `contact2_phone` varchar(50) NOT NULL COMMENT '联系人2电话手机（必填）',
  `contact2_mail` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人2邮箱',
  `spec_demmand` varchar(500) NOT NULL DEFAULT '' COMMENT '特殊需求',
  `create_type` varchar(50) NOT NULL COMMENT '创建类型：平台创建，自注册',
  `saler_id` bigint(50) NOT NULL COMMENT '营销人员',
  `runner_id` bigint(50) NOT NULL COMMENT '运营人员',
  `industry_type` varchar(50) NOT NULL COMMENT '行业分类',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`service_provider_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商的基本信息表';

-- ----------------------------
-- Records of diyi_service_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider_maker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider_maker`;
CREATE TABLE `diyi_service_provider_maker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `rel_type` varchar(50) NOT NULL COMMENT '关联类型：总包+分包支付关联；众包代开票关联',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`service_provider_id`,`enterprise_id`,`maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商创客关联表';

-- ----------------------------
-- Records of diyi_service_provider_maker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider_worker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider_worker`;
CREATE TABLE `diyi_service_provider_worker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `user_id` bigint(50) NOT NULL COMMENT '管理者ID',
  `avatar` varchar(100) NOT NULL DEFAULT '' COMMENT '头像',
  `service_provider_worker_state` varchar(50) NOT NULL COMMENT '服务商员工账户状态',
  `worker_name` varchar(50) NOT NULL COMMENT '姓名',
  `worker_sex` varchar(50) NOT NULL COMMENT '性别',
  `position_name` varchar(50) NOT NULL COMMENT '岗位性质',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `up_level_id` bigint(50) NOT NULL COMMENT '上级主管',
  `employee_user_name` varchar(50) NOT NULL COMMENT '用户名',
  `employee_pwd` varchar(100) NOT NULL COMMENT '密码',
  `admin_power` bit(1) NOT NULL COMMENT '管理员特性',
  `super_admin` bit(1) NOT NULL COMMENT '是否超级管理员',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`employee_user_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k3` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商员工表';

-- ----------------------------
-- Records of diyi_service_provider_worker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_setup`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_setup`;
CREATE TABLE `diyi_setup` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `allow_first_edit` bit(1) NOT NULL COMMENT '允许第一次关联企业更新创客信息',
  `allow_all_edit` bit(1) NOT NULL COMMENT '允许所有关联企业更新创客信息',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='特殊配置表';

-- ----------------------------
-- Records of diyi_setup
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_worksheet`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_worksheet`;
CREATE TABLE `diyi_worksheet` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `worksheet_no` varchar(50) NOT NULL COMMENT '工单编号',
  `worksheet_name` varchar(50) NOT NULL COMMENT '工单名称',
  `upPerson_num` int(10) DEFAULT '0' COMMENT '上线人数',
  `work_days` int(10) DEFAULT NULL COMMENT '工作天数',
  `worksheet_fee_low` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '最低费用',
  `worksheet_fee_high` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '最高费用',
  `worksheet_type` varchar(50) NOT NULL COMMENT '类型，总包+分包，众包/众采',
  `worksheet_mode` varchar(50) NOT NULL COMMENT '模式，派单、抢单、混合（默认：混合型）',
  `maker_type` varchar(50) NOT NULL COMMENT '创客身份，自然人，个体户，个独。如果是个体户/个独，则抢单或派单时需要指定相关个体户/个独，如果只有一个则不用指定。',
  `worksheet_state` varchar(50) NOT NULL COMMENT '工单状态：\r\na) 发布中，发布代抢单或代派单的工单\r\nb) 已关单，已经抢单或者派单完毕（人数不做控制依据）\r\nc) 验收中，有个人创客提交了工单等待验收或部分验收完毕\r\nd) 已完毕，所有个人创客都验收完毕了\r\ne) 已作废，验收中工单都可以作废，已完毕的不能作废',
  `destroy_datetime` datetime DEFAULT NULL COMMENT '作废时间',
  `destroy_person` varchar(50) NOT NULL DEFAULT '' COMMENT '作废人员',
  `destroy_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '作废说明',
  `close_worksheet_date` datetime DEFAULT NULL COMMENT '关单时间',
  `close_desc` varchar(50) DEFAULT NULL COMMENT '1，手动关单；2，自动关单',
  `close_person` varchar(500) NOT NULL DEFAULT '' COMMENT '关单人员',
  `finish_date` datetime DEFAULT NULL COMMENT '完毕日期',
  `worksheet_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '工单说明',
  `worksheet_desc_files` varchar(500) NOT NULL DEFAULT '' COMMENT '工单说明图文',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`worksheet_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单表';

-- ----------------------------
-- Records of diyi_worksheet
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_worksheet_attention`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_worksheet_attention`;
CREATE TABLE `diyi_worksheet_attention` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `worksheet_attention_no` bigint(50) NOT NULL COMMENT '工单关注编号',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`worksheet_attention_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='未知表';

-- ----------------------------
-- Records of diyi_worksheet_attention
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_worksheet_maker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_worksheet_maker`;
CREATE TABLE `diyi_worksheet_maker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `worksheet_id` bigint(50) NOT NULL COMMENT '工单ID',
  `maker_name` varchar(50) NOT NULL COMMENT '创客姓名',
  `get_type` varchar(50) NOT NULL COMMENT '获得方式：1,抢单获得；2，派单获得',
  `get_order_date` datetime NOT NULL COMMENT '抢单/派单日期',
  `worksheet_maker_state` varchar(50) NOT NULL COMMENT '工单创客的状态：1待提交，2待验证，3验证通过，4验证失败',
  `achievement_desc` varchar(1000) NOT NULL DEFAULT '' COMMENT '工作成果说明',
  `achievement_files` varchar(1000) NOT NULL DEFAULT '' COMMENT '工作成果附件',
  `achievement_date` datetime DEFAULT NULL COMMENT '提交工作成果日期',
  `check_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '验收金额',
  `check_person` varchar(500) NOT NULL DEFAULT '' COMMENT '验收人员',
  `check_date` datetime DEFAULT NULL COMMENT '验收时间',
  `arrange_person` varchar(50) NOT NULL DEFAULT '' COMMENT '派单人员',
  `arrange_date` datetime DEFAULT NULL COMMENT '派单日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`maker_id`,`worksheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单创客关联表';

-- ----------------------------
-- Records of diyi_worksheet_maker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_work_achievement`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_work_achievement`;
CREATE TABLE `diyi_work_achievement` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `worksheet_id` bigint(50) NOT NULL COMMENT '工单id',
  `work_explain` varchar(500) NOT NULL COMMENT '工作成果说明',
  `work_url` varchar(100) NOT NULL COMMENT '工作结果url',
  `check_money_num` decimal(12,2) NOT NULL COMMENT '验收金额',
  `check_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '验收说明',
  `check_person` varchar(100) NOT NULL COMMENT '验收人员',
  `chece_datetime` datetime NOT NULL COMMENT '验收日期时间',
  `work_achievement_state` varchar(50) NOT NULL COMMENT '工作成果状态 1：待验收，2验收通过，3验收不通过',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='众包工作验收成果表';

-- ----------------------------
-- Records of diyi_work_achievement
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_client`
-- ----------------------------
DROP TABLE IF EXISTS `sys_client`;
CREATE TABLE `sys_client` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `client_id` varchar(50) NOT NULL COMMENT '客户端id',
  `client_secret` varchar(50) NOT NULL COMMENT '客户端密钥',
  `resource_ids` varchar(100) NOT NULL DEFAULT '' COMMENT '资源集合',
  `scope` varchar(50) NOT NULL COMMENT '授权范围',
  `authorized_grant_types` varchar(100) NOT NULL COMMENT '授权类型',
  `web_server_redirect_uri` varchar(100) NOT NULL DEFAULT '' COMMENT '回调地址',
  `authorities` varchar(100) NOT NULL DEFAULT '' COMMENT '权限',
  `access_token_validity` int(10) NOT NULL COMMENT '令牌过期秒数',
  `refresh_token_validity` int(10) NOT NULL COMMENT '刷新令牌过期秒数',
  `additional_information` varchar(500) NOT NULL DEFAULT '' COMMENT '附加说明',
  `autoapprove` varchar(100) NOT NULL DEFAULT '' COMMENT '自动授权',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端表';

-- ----------------------------
-- Records of sys_client
-- ----------------------------
INSERT INTO `sys_client` VALUES ('1123598811738675201', 'sword', 'sword_secret', '', 'all', 'refresh_token,password,authorization_code', 'http://localhost:8888', '', '604800', '604800', '', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_client` VALUES ('1123598811738675202', 'saber', 'saber_secret', '', 'all', 'refresh_token,password,authorization_code', 'http://localhost:8080', '', '604800', '604800', '', '', null, '2019-03-24 10:42:29', null, '2019-03-24 10:42:32', '1', '0');
INSERT INTO `sys_client` VALUES ('1123598811738675203', 'app', 'app_secret', '', 'all', 'refresh_token,password,authorization_code', 'http://localhost:8000', '', '604800', '604800', '', '', null, '2019-03-24 10:42:29', null, '2019-03-24 10:42:32', '1', '0');

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  `parent_id` bigint(50) DEFAULT '0' COMMENT '父主键',
  `dept_name` varchar(50) NOT NULL DEFAULT '' COMMENT '部门名',
  `full_name` varchar(50) NOT NULL DEFAULT '' COMMENT '部门全称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1123598813738675201', '000000', '0', '刀锋科技', '江苏刀锋科技有限公司', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dept` VALUES ('1123598813738675202', '000000', '1123598813738675201', '常州刀锋', '常州刀锋科技有限公司', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dept` VALUES ('1123598813738675203', '000000', '1123598813738675201', '苏州刀锋', '苏州刀锋科技有限公司', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `parent_id` bigint(50) DEFAULT '0' COMMENT '父主键',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '字典码',
  `dict_key` int(2) DEFAULT NULL COMMENT '字典值',
  `dict_value` varchar(255) NOT NULL DEFAULT '' COMMENT '字典名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '字典备注',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1123598814738675201', '0', 'sex', '-1', '性别', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675202', '1123598814738675201', 'sex', '1', '男', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675203', '1123598814738675201', 'sex', '2', '女', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675204', '0', 'notice', '-1', '通知类型', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675205', '1123598814738675204', 'notice', '1', '发布通知', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675206', '1123598814738675204', 'notice', '2', '批转通知', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675207', '1123598814738675204', 'notice', '3', '转发通知', '3', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675208', '1123598814738675204', 'notice', '4', '指示通知', '4', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675209', '1123598814738675204', 'notice', '5', '任免通知', '5', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675210', '1123598814738675204', 'notice', '6', '事务通知', '6', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675211', '0', 'menu_category', '-1', '菜单类型', '3', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675212', '1123598814738675211', 'menu_category', '1', '菜单', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675213', '1123598814738675211', 'menu_category', '2', '按钮', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675214', '0', 'button_func', '-1', '按钮功能', '4', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675215', '1123598814738675214', 'button_func', '1', '工具栏', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675216', '1123598814738675214', 'button_func', '2', '操作栏', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675217', '1123598814738675214', 'button_func', '3', '工具操作栏', '3', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675218', '0', 'yes_no', '-1', '是否', '5', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675219', '1123598814738675218', 'yes_no', '1', '否', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738675220', '1123598814738675218', 'yes_no', '2', '是', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738777220', '0', 'post_category', '-1', '岗位类型', '12', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738777221', '1123598814738777220', 'post_category', '1', '高层', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738777222', '1123598814738777220', 'post_category', '2', '中层', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738777223', '1123598814738777220', 'post_category', '3', '基层', '3', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_dict` VALUES ('1123598814738777224', '1123598814738777220', 'post_category', '4', '其他', '4', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `parent_id` bigint(50) DEFAULT '0' COMMENT '父级菜单',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单编号',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `alias` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单别名',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '请求地址',
  `source` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单资源',
  `sort` int(3) DEFAULT NULL COMMENT '排序',
  `category` int(2) DEFAULT NULL COMMENT '菜单类型',
  `action` int(2) DEFAULT '0' COMMENT '操作按钮类型',
  `is_open` int(2) DEFAULT '1' COMMENT '是否打开新页面',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1123598815738675201', '0', 'desk', '工作台', 'menu', '/desk', 'iconfont iconicon_airplay', '1', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675202', '1123598815738675201', 'notice', '通知公告', 'menu', '/desk/notice', 'iconfont iconicon_sms', '1', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675203', '0', 'system', '系统管理', 'menu', '/system', 'iconfont iconicon_setting', '2', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675204', '1123598815738675203', 'user', '用户管理', 'menu', '/system/user', 'iconfont iconicon_principal', '1', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675205', '1123598815738675203', 'dept', '部门管理', 'menu', '/system/dept', 'iconfont iconicon_group', '2', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675206', '1123598815738675203', 'dict', '字典管理', 'menu', '/system/dict', 'iconfont iconicon_addresslist', '3', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675207', '1123598815738675203', 'menu', '菜单管理', 'menu', '/system/menu', 'iconfont iconicon_subordinate', '4', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675208', '1123598815738675203', 'role', '角色管理', 'menu', '/system/role', 'iconfont iconicon_boss', '5', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675209', '1123598815738675203', 'param', '参数管理', 'menu', '/system/param', 'iconfont iconicon_community_line', '6', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675210', '0', 'monitor', '系统监控', 'menu', '/monitor', 'iconfont icon-yanzhengma', '3', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675211', '1123598815738675210', 'doc', '接口文档', 'menu', 'http://localhost/doc.html', 'iconfont iconicon_study', '1', '1', '0', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675212', '1123598815738675210', 'admin', '服务治理', 'menu', 'http://localhost:7002', 'iconfont icon-canshu', '2', '1', '0', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675213', '1123598815738675210', 'log', '日志管理', 'menu', '/monitor/log', 'iconfont iconicon_doc', '3', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675214', '1123598815738675213', 'log_usual', '通用日志', 'menu', '/monitor/log/usual', '', '1', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675215', '1123598815738675213', 'log_api', '接口日志', 'menu', '/monitor/log/api', '', '2', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675216', '1123598815738675213', 'log_error', '错误日志', 'menu', '/monitor/log/error', '', '3', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675217', '0', 'tool', '研发工具', 'menu', '/tool', 'iconfont icon-wxbgongju', '4', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675218', '1123598815738675217', 'code', '代码生成', 'menu', '/tool/code', 'iconfont iconicon_savememo', '1', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675219', '1123598815738675202', 'notice_add', '新增', 'add', '/desk/notice/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675220', '1123598815738675202', 'notice_edit', '修改', 'edit', '/desk/notice/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675221', '1123598815738675202', 'notice_delete', '删除', 'delete', '/api/blade-desk/notice/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675222', '1123598815738675202', 'notice_view', '查看', 'view', '/desk/notice/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675223', '1123598815738675204', 'user_add', '新增', 'add', '/system/user/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675224', '1123598815738675204', 'user_edit', '修改', 'edit', '/system/user/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675225', '1123598815738675204', 'user_delete', '删除', 'delete', '/api/blade-user/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675226', '1123598815738675204', 'user_role', '角色配置', 'role', '', 'user-add', '4', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675227', '1123598815738675204', 'user_reset', '密码重置', 'reset-password', '/api/blade-user/reset-password', 'retweet', '5', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675228', '1123598815738675204', 'user_view', '查看', 'view', '/system/user/view', 'file-text', '6', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675229', '1123598815738675205', 'dept_add', '新增', 'add', '/system/dept/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675230', '1123598815738675205', 'dept_edit', '修改', 'edit', '/system/dept/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675231', '1123598815738675205', 'dept_delete', '删除', 'delete', '/api/blade-system/dept/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675232', '1123598815738675205', 'dept_view', '查看', 'view', '/system/dept/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675233', '1123598815738675206', 'dict_add', '新增', 'add', '/system/dict/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675234', '1123598815738675206', 'dict_edit', '修改', 'edit', '/system/dict/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675235', '1123598815738675206', 'dict_delete', '删除', 'delete', '/api/blade-system/dict/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675236', '1123598815738675206', 'dict_view', '查看', 'view', '/system/dict/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675237', '1123598815738675207', 'menu_add', '新增', 'add', '/system/menu/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675238', '1123598815738675207', 'menu_edit', '修改', 'edit', '/system/menu/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675239', '1123598815738675207', 'menu_delete', '删除', 'delete', '/api/blade-system/menu/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675240', '1123598815738675207', 'menu_view', '查看', 'view', '/system/menu/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675241', '1123598815738675208', 'role_add', '新增', 'add', '/system/role/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675242', '1123598815738675208', 'role_edit', '修改', 'edit', '/system/role/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675243', '1123598815738675208', 'role_delete', '删除', 'delete', '/api/blade-system/role/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675244', '1123598815738675208', 'role_view', '查看', 'view', '/system/role/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675245', '1123598815738675209', 'param_add', '新增', 'add', '/system/param/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675246', '1123598815738675209', 'param_edit', '修改', 'edit', '/system/param/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675247', '1123598815738675209', 'param_delete', '删除', 'delete', '/api/blade-system/param/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675248', '1123598815738675209', 'param_view', '查看', 'view', '/system/param/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675249', '1123598815738675214', 'log_usual_view', '查看', 'view', '/monitor/log/usual/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675250', '1123598815738675215', 'log_api_view', '查看', 'view', '/monitor/log/api/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675251', '1123598815738675216', 'log_error_view', '查看', 'view', '/monitor/log/error/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675252', '1123598815738675218', 'code_add', '新增', 'add', '/tool/code/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675253', '1123598815738675218', 'code_edit', '修改', 'edit', '/tool/code/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675254', '1123598815738675218', 'code_delete', '删除', 'delete', '/api/blade-system/code/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675255', '1123598815738675218', 'code_view', '查看', 'view', '/tool/code/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675256', '1123598815738675203', 'tenant', '租户管理', 'menu', '/system/tenant', 'iconfont icon-quanxian', '7', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675257', '1123598815738675256', 'tenant_add', '新增', 'add', '/system/tenant/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675258', '1123598815738675256', 'tenant_edit', '修改', 'edit', '/system/tenant/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675259', '1123598815738675256', 'tenant_delete', '删除', 'delete', '/api/blade-system/tenant/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675260', '1123598815738675256', 'tenant_view', '查看', 'view', '/system/tenant/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675261', '1123598815738675203', 'client', '应用管理', 'menu', '/system/client', 'iconfont iconicon_mobilephone', '8', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675262', '1123598815738675261', 'client_add', '新增', 'add', '/system/client/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675263', '1123598815738675261', 'client_edit', '修改', 'edit', '/system/client/edit', 'form', '2', '2', '2', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675264', '1123598815738675261', 'client_delete', '删除', 'delete', '/api/blade-system/client/remove', 'delete', '3', '2', '3', '3', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675265', '1123598815738675261', 'client_view', '查看', 'view', '/system/client/view', 'file-text', '4', '2', '2', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675266', '1123598815738675217', 'datasource', '数据源管理', 'menu', '/tool/datasource', 'iconfont icon-caidanguanli', '2', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675267', '1123598815738675266', 'datasource_add', '新增', 'add', '/tool/datasource/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675268', '1123598815738675266', 'datasource_edit', '修改', 'edit', '/tool/datasource/edit', 'form', '2', '2', '2', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675269', '1123598815738675266', 'datasource_delete', '删除', 'delete', '/api/blade-develop/datasource/remove', 'delete', '3', '2', '3', '3', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675270', '1123598815738675266', 'datasource_view', '查看', 'view', '/tool/datasource/view', 'file-text', '4', '2', '2', '2', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1164733389668962251', '1123598815738675203', 'post', '岗位管理', 'menu', '/system/post', 'iconfont iconicon_message', '2', '1', '0', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1164733389668962252', '1164733389668962251', 'post_add', '新增', 'add', '/system/post/add', 'plus', '1', '2', '1', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1164733389668962253', '1164733389668962251', 'post_edit', '修改', 'edit', '/system/post/edit', 'form', '2', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1164733389668962254', '1164733389668962251', 'post_delete', '删除', 'delete', '/api/blade-system/post/remove', 'delete', '3', '2', '3', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_menu` VALUES ('1164733389668962255', '1164733389668962251', 'post_view', '查看', 'view', '/system/post/view', 'file-text', '4', '2', '2', '1', '', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');

-- ----------------------------
-- Table structure for `sys_param`
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `param_name` varchar(255) NOT NULL DEFAULT '' COMMENT '参数名',
  `param_key` varchar(255) NOT NULL DEFAULT '' COMMENT '参数键',
  `param_value` varchar(255) NOT NULL DEFAULT '' COMMENT '参数值',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数表';

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES ('1123598819738675201', '是否开启注册功能', 'account.registerUser', 'true', '开启注册', null, '2018-12-28 12:19:01', null, '2018-12-28 12:19:01', '1', '0');
INSERT INTO `sys_param` VALUES ('1123598819738675202', '账号初始密码', 'account.initPassword', '123456', '初始密码', null, '2018-12-28 12:19:01', null, '2018-12-28 12:19:01', '1', '0');

-- ----------------------------
-- Table structure for `sys_post`
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  `category` int(11) DEFAULT NULL COMMENT '岗位类型',
  `post_code` varchar(12) NOT NULL DEFAULT '' COMMENT '岗位编号',
  `post_name` varchar(50) NOT NULL DEFAULT '' COMMENT '岗位名称',
  `sort` int(3) DEFAULT NULL COMMENT '岗位排序',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '岗位描述',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES ('1123598817738675201', '000000', '1', 'ceo', '首席执行官', '1', '总经理', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_post` VALUES ('1123598817738675202', '000000', '1', 'coo', '首席运营官', '2', '常务总经理', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_post` VALUES ('1123598817738675203', '000000', '1', 'cfo', '首席财务官', '3', '财务总经理', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_post` VALUES ('1123598817738675204', '000000', '1', 'cto', '首席技术官', '4', '技术总监', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_post` VALUES ('1123598817738675205', '000000', '1', 'cio', '首席信息官', '5', '信息总监', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_post` VALUES ('1123598817738675206', '000000', '2', 'pm', '技术经理', '6', '研发和产品是永远的朋友', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_post` VALUES ('1123598817738675207', '000000', '2', 'hrm', '人力经理', '7', '人力资源部门工作管理者', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_post` VALUES ('1123598817738675208', '000000', '3', 'staff', '普通员工', '8', '普通员工', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  `parent_id` bigint(50) DEFAULT '0' COMMENT '父主键',
  `role_name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `role_alias` varchar(255) NOT NULL DEFAULT '' COMMENT '角色别名',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1123598816738675201', '000000', '0', '超级管理员', '1', 'administrator', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role` VALUES ('1123598816738675202', '000000', '0', '用户', '2', 'user', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `menu_id` bigint(50) DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(50) DEFAULT NULL COMMENT '角色id',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1123598817738675201', '1123598815738675201', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675202', '1123598815738675202', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675203', '1123598815738675203', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675204', '1123598815738675204', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675205', '1123598815738675205', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675206', '1123598815738675206', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675207', '1123598815738675207', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675208', '1123598815738675208', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675209', '1123598815738675209', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675210', '1123598815738675210', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675211', '1123598815738675211', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675212', '1123598815738675212', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675213', '1123598815738675213', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675214', '1123598815738675214', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675215', '1123598815738675215', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675216', '1123598815738675216', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675217', '1123598815738675217', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675218', '1123598815738675218', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675219', '1123598815738675219', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675220', '1123598815738675220', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675221', '1123598815738675221', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675222', '1123598815738675222', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675223', '1123598815738675223', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675224', '1123598815738675224', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675225', '1123598815738675225', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675226', '1123598815738675226', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675227', '1123598815738675227', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675228', '1123598815738675228', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675229', '1123598815738675229', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675230', '1123598815738675230', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675231', '1123598815738675231', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675232', '1123598815738675232', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675233', '1123598815738675233', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675234', '1123598815738675234', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675235', '1123598815738675235', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675236', '1123598815738675236', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675237', '1123598815738675237', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675238', '1123598815738675238', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675239', '1123598815738675239', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675240', '1123598815738675240', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675241', '1123598815738675241', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675242', '1123598815738675242', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675243', '1123598815738675243', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675244', '1123598815738675244', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675245', '1123598815738675245', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675246', '1123598815738675246', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675247', '1123598815738675247', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675248', '1123598815738675248', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675249', '1123598815738675249', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675250', '1123598815738675250', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675251', '1123598815738675251', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675252', '1123598815738675252', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675253', '1123598815738675253', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675254', '1123598815738675254', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675255', '1123598815738675255', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675256', '1123598815738675256', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675257', '1123598815738675257', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675258', '1123598815738675258', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675259', '1123598815738675259', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675260', '1123598815738675260', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675261', '1123598815738675261', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675262', '1123598815738675262', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675263', '1123598815738675263', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675264', '1123598815738675264', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675265', '1123598815738675265', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675266', '1123598815738675266', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675267', '1123598815738675267', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675268', '1123598815738675268', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675269', '1123598815738675269', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1123598817738675270', '1123598815738675270', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1161272893875225001', '1164733389668962251', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1161272893875225002', '1164733389668962252', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1161272893875225003', '1164733389668962253', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1161272893875225004', '1164733389668962254', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1161272893875225005', '1164733389668962255', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('1161272893875225006', '1164733389668962256', '1123598816738675201', null, '2019-03-24 10:40:55', null, '2019-03-24 10:40:59', '1', '0');

-- ----------------------------
-- Table structure for `sys_tenant`
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) NOT NULL COMMENT '租户ID',
  `tenant_name` varchar(50) NOT NULL COMMENT '租户名称',
  `linkman` varchar(20) NOT NULL DEFAULT '' COMMENT '联系人',
  `contact_number` varchar(20) NOT NULL DEFAULT '' COMMENT '联系电话',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '联系地址',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES ('1123598820738675201', '000000', '管理组', 'admin', '666666', '管理组', null, '2019-01-01 00:00:39', null, '2019-01-01 00:00:39', '1', '0');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
  `user_type` varchar(50) NOT NULL COMMENT '用户类型',
  `code` varchar(12) NOT NULL DEFAULT '' COMMENT '用户编号',
  `account` varchar(50) NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '昵称',
  `real_name` varchar(10) NOT NULL DEFAULT '' COMMENT '真名',
  `avatar` varchar(100) NOT NULL DEFAULT '' COMMENT '头像',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `phone` varchar(50) NOT NULL DEFAULT '' COMMENT '手机',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` smallint(6) DEFAULT NULL COMMENT '性别',
  `role_id` varchar(50) NOT NULL DEFAULT '' COMMENT '角色id',
  `dept_id` varchar(50) NOT NULL DEFAULT '' COMMENT '部门id',
  `post_id` varchar(50) NOT NULL DEFAULT '' COMMENT '岗位id',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`user_type`,`phone`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`user_type`,`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1123598821738675201', '000000', 'ADMIN', '', 'admin', '90b9aa7e25f80cf4f64e990b78a9fc5ebd6cecad', '管理员', '管理员', '', 'admin@bladex.vip', '22233322', '2018-08-08 00:00:00', '1', '1123598816738675201', '1123598813738675201', '1123598817738675201', null, '2018-08-08 00:00:00', null, '2018-08-08 00:00:00', '1', '0');

DROP TABLE IF EXISTS `diyi_self_help_invoice_sp_detail`;
CREATE TABLE `diyi_self_help_invoice_sp_detail` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_apply_provider_id` bigint(50) NOT NULL COMMENT '服务商自助开票明细Id',
  `self_help_invoice_detail_id` bigint(50) NOT NULL COMMENT '自助开票明细Id',
  `invoice_scan_pictures` varchar(1000) NOT NULL COMMENT ' 发票扫描件（可多张）',
  `tax_scan_pictures` varchar(1000) NOT NULL COMMENT ' 税票扫描件（可多张）',
  `invoice_operate_person` varchar(50) NOT NULL COMMENT '发票处理人员',
  `update_datetime` datetime NOT NULL COMMENT '更新日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商开票明细：是从自助开票明细中选择过来的，信息是一致的';


DROP TABLE IF EXISTS `diyi_self_help_invoice_sp`;
CREATE TABLE `diyi_self_help_invoice_sp` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票Id',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `operate_person` varchar(50) NOT NULL COMMENT ' 提交人员',
  `apply_date` datetime NOT NULL COMMENT '申请日期 可以多次申请，每次独立数据',
  `apply_state` varchar(50) NOT NULL COMMENT ' 申请状态 1，已提交开票中；2，已撤回；3，已开票结束。。。这个是管理端运营老师负责，提交和撤回。',
  `apply_desc` varchar(500) NOT NULL COMMENT '申请说明',
  `audit_desc` varchar(500) NOT NULL COMMENT '结果说明',
  `charge_money_num` decimal(12,2) NOT NULL COMMENT '价税合计额',
  `value_money_num` decimal(12,2) NOT NULL COMMENT '总开票金额合计',
  `service_rate` decimal(12,2) NOT NULL COMMENT '服务税费率',
  `service_and_tax_money` decimal(12,2) NOT NULL COMMENT '总服务税费',
  `service_invoice_fee` decimal(12,2) NOT NULL COMMENT '总开票手续费',
  `idendity_confirm_fee` decimal(12,2) NOT NULL COMMENT '总身份验证费',
  `pay_total_num` decimal(12,2) NOT NULL COMMENT '支付总额',
  `address_id` bigint(50) NOT NULL COMMENT '收件地址Id',
  `address_type` varchar(50) NOT NULL COMMENT '收件地址性质 1，快递给管理中心；2，直接快递给客户',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[0-非正常 1-正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否已删除[0-未删除 1-已删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票-服务商：记录自助开票主表的提交给不同服务商的';
