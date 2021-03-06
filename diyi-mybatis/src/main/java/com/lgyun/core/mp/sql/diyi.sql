/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : diyi

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-12-07 19:45:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `diyi_accept_paysheet`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_accept_paysheet`;
CREATE TABLE `diyi_accept_paysheet` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付清单ID',
  `accept_paysheet_type` varchar(50) NOT NULL COMMENT '交付支付验收单类型',
  `pay_maker_id` bigint(50) DEFAULT NULL COMMENT '分包支付明细ID',
  `service_time_start` datetime NOT NULL COMMENT '服务开始日期',
  `service_time_end` datetime NOT NULL COMMENT '服务结束日期',
  `accept_paysheet_url` varchar(500) NOT NULL COMMENT '交付支付验收单',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
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
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票ID',
  `accept_paysheet_cs_type` varchar(50) NOT NULL COMMENT '交付支付验收单类型',
  `self_help_invoice_detail_id` bigint(50) DEFAULT NULL COMMENT '自助开票明细ID',
  `service_time_start` datetime NOT NULL COMMENT '服务开始日期',
  `service_time_end` datetime NOT NULL COMMENT '服务结束日期',
  `accept_paysheet_cs_url` varchar(500) NOT NULL COMMENT '交付支付验收单',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='众包/众采交付支付验收单表';

-- ----------------------------
-- Records of diyi_accept_paysheet_cs
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_accept_paysheet_cs_list`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_accept_paysheet_cs_list`;
CREATE TABLE `diyi_accept_paysheet_cs_list` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `accept_paysheet_cs_id` bigint(50) NOT NULL COMMENT '总包交付支付验收单ID',
  `self_help_invoice_detail_id` bigint(50) NOT NULL COMMENT '自主开票明细ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`accept_paysheet_cs_id`,`self_help_invoice_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='总包交付支付验收单清单表';

-- ----------------------------
-- Records of diyi_accept_paysheet_cs_list
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_accept_paysheet_list`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_accept_paysheet_list`;
CREATE TABLE `diyi_accept_paysheet_list` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `accept_paysheet_id` bigint(50) NOT NULL COMMENT '总包交付支付验收单ID',
  `pay_maker_id` bigint(50) NOT NULL COMMENT '分包支付明细ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`accept_paysheet_id`,`pay_maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='众包交付支付验收单清单表';

-- ----------------------------
-- Records of diyi_accept_paysheet_list
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_address`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_address`;
CREATE TABLE `diyi_address` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `object_id` bigint(50) NOT NULL COMMENT '对象ID',
  `object_type` varchar(50) NOT NULL COMMENT '对象身份',
  `address_name` varchar(50) NOT NULL COMMENT '收件人姓名',
  `address_phone` varchar(50) NOT NULL COMMENT '收件人手机号码',
  `province` varchar(50) NOT NULL COMMENT '省',
  `city` varchar(50) NOT NULL COMMENT '市',
  `area` varchar(50) NOT NULL COMMENT '区',
  `detailed_address` varchar(100) NOT NULL COMMENT '详细地址',
  `bool_default` bit(1) NOT NULL COMMENT '是否默认',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收件地址表';

-- ----------------------------
-- Records of diyi_address
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_admin`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_admin`;
CREATE TABLE `diyi_admin` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `admin_state` varchar(50) NOT NULL COMMENT '管理员账户状态',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `worker_sex` varchar(50) NOT NULL DEFAULT '' COMMENT '性别',
  `position_name` varchar(50) NOT NULL COMMENT '岗位性质',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `up_level_id` bigint(50) DEFAULT NULL COMMENT '上级主管',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `login_pwd` varchar(100) NOT NULL COMMENT '密码',
  `role_id` bigint(50) DEFAULT NULL COMMENT '角色ID',
  `super_admin` bit(1) NOT NULL COMMENT '管理员权限',
  `admin_power` bit(1) NOT NULL COMMENT '管理员特性',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台管理员信息表';

-- ----------------------------
-- Records of diyi_admin
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_admin_center_material`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_admin_center_material`;
CREATE TABLE `diyi_admin_center_material` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `material_name` varchar(50) NOT NULL COMMENT '业务资料名称',
  `material_belong` varchar(50) NOT NULL COMMENT '文档归属',
  `material_type` varchar(50) NOT NULL COMMENT '文档属性',
  `open_atribute` varchar(50) NOT NULL COMMENT '公开属性',
  `material_url` varchar(500) NOT NULL COMMENT '文件',
  `material_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '文件概述',
  `material_state` varchar(50) NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='综合业务资料表';

-- ----------------------------
-- Records of diyi_admin_center_material
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_agent_main`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_agent_main`;
CREATE TABLE `diyi_agent_main` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `agent_main_state` varchar(50) NOT NULL COMMENT '渠道商账户状态',
  `agent_main_name` varchar(50) NOT NULL COMMENT '渠道商名称',
  `legal_person_name` varchar(50) NOT NULL COMMENT '法人',
  `legal_person_idcard` varchar(50) NOT NULL DEFAULT '' COMMENT '法人身份证',
  `social_credit_no` varchar(50) NOT NULL COMMENT '统一社会信用代码',
  `biz_licence_url` varchar(500) NOT NULL COMMENT '营业执照正本',
  `biz_licence_copy_url` varchar(500) NOT NULL DEFAULT '' COMMENT '营业执照副本',
  `enterprise_url` varchar(500) NOT NULL DEFAULT '' COMMENT '企业网址',
  `working_address` varchar(100) NOT NULL DEFAULT '' COMMENT '办公地址(快递地址）',
  `working_rel_name` varchar(50) NOT NULL DEFAULT '' COMMENT '收发票/税票快递【到付】联系人姓名',
  `working_rel_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '收发票/税票快递【到付】联系人手机号',
  `invoice_enterprise_name` varchar(50) NOT NULL COMMENT '开票资料-公司名称',
  `invoice_tax_no` varchar(50) NOT NULL COMMENT '开票资料-税号',
  `invoice_address_phone` varchar(100) NOT NULL COMMENT '开票资料-地址和电话',
  `invoice_bank_name_account` varchar(100) NOT NULL COMMENT '开票资料-开户银行和账号',
  `co_product_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '合作产品说明',
  `contact1_name` varchar(50) NOT NULL COMMENT '联系人1姓名',
  `contact1_position` varchar(50) NOT NULL COMMENT '联系人1职位',
  `contact1_phone` varchar(50) NOT NULL COMMENT '联系人1电话手机（必填）',
  `contact1_mail` varchar(50) NOT NULL COMMENT '联系人1邮箱',
  `contact2_name` varchar(50) NOT NULL COMMENT '联系人2姓名',
  `contact2_position` varchar(50) NOT NULL COMMENT '联系人2职位',
  `contact2_phone` varchar(50) NOT NULL COMMENT '联系人2电话手机（必填）',
  `contact2_mail` varchar(50) NOT NULL COMMENT '联系人2邮箱',
  `spec_demmand` varchar(50) NOT NULL DEFAULT '' COMMENT '特殊需求',
  `create_type` varchar(50) NOT NULL COMMENT '创建方式',
  `saler_id` bigint(50) DEFAULT NULL COMMENT '营销人员',
  `runner_id` bigint(50) DEFAULT NULL COMMENT '运营人员',
  `industry_type` varchar(50) NOT NULL DEFAULT '' COMMENT '行业分类',
  `main_business_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '主营业务描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`agent_main_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`social_credit_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道商信息表';

-- ----------------------------
-- Records of diyi_agent_main
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_agent_main_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_agent_main_enterprise`;
CREATE TABLE `diyi_agent_main_enterprise` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `agent_main_id` bigint(50) NOT NULL COMMENT '渠道商ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `cooperate_type` varchar(50) NOT NULL COMMENT '合作关系',
  `cooperate_status` varchar(50) NOT NULL COMMENT '合作状态',
  `match_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '分配说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`agent_main_id`,`enterprise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道商户表';

-- ----------------------------
-- Records of diyi_agent_main_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_agent_main_service_provider`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_agent_main_service_provider`;
CREATE TABLE `diyi_agent_main_service_provider` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `agent_main_id` bigint(50) NOT NULL COMMENT '渠道商ID',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `cooperate_status` varchar(50) NOT NULL COMMENT '合作状态',
  `match_desc` varchar(100) DEFAULT NULL COMMENT '分配说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`agent_main_id`,`service_provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道商-服务商表';

-- ----------------------------
-- Records of diyi_agent_main_service_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_agent_main_worker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_agent_main_worker`;
CREATE TABLE `diyi_agent_main_worker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `agent_main_id` bigint(50) NOT NULL COMMENT '渠道商ID',
  `role_id` bigint(50) DEFAULT NULL COMMENT '角色ID',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `agent_main_worker_state` varchar(50) NOT NULL COMMENT '商户员工账户状态',
  `worker_name` varchar(50) NOT NULL COMMENT '姓名',
  `worker_sex` varchar(50) NOT NULL DEFAULT '' COMMENT '性别',
  `position_name` varchar(50) NOT NULL COMMENT '岗位性质',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `up_level_id` bigint(50) DEFAULT NULL COMMENT '上级主管',
  `employee_user_name` varchar(50) NOT NULL COMMENT '用户名',
  `employee_pwd` varchar(100) NOT NULL COMMENT '密码',
  `super_admin` bit(1) NOT NULL COMMENT '管理员权限',
  `admin_power` bit(1) NOT NULL COMMENT '管理员特性',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`employee_user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道商员工表';

-- ----------------------------
-- Records of diyi_agent_main_worker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_agreement`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_agreement`;
CREATE TABLE `diyi_agreement` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `agreement_type` varchar(50) NOT NULL COMMENT '合同协议类别',
  `sign_type` varchar(50) NOT NULL COMMENT '签署类型',
  `sign_state` varchar(50) NOT NULL COMMENT '签署状态',
  `audit_state` varchar(50) NOT NULL COMMENT '审核状态',
  `valid_state` varchar(50) NOT NULL COMMENT '合同有效性',
  `end_datetime` datetime DEFAULT NULL COMMENT '截止日期',
  `agreement_no` varchar(100) NOT NULL COMMENT '合同协议编号',
  `party_a` varchar(50) DEFAULT NULL COMMENT '甲方身份',
  `party_a_id` bigint(50) DEFAULT NULL COMMENT '甲方ID',
  `party_b` varchar(50) NOT NULL COMMENT '乙方身份',
  `party_b_id` bigint(50) NOT NULL COMMENT '乙方ID',
  `online_agreement_template_id` bigint(50) DEFAULT NULL COMMENT '平台在线协议模板ID',
  `agreement_url` varchar(500) NOT NULL COMMENT '协议合同',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`agreement_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='综合合同表';

-- ----------------------------
-- Records of diyi_agreement
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_client`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_client`;
CREATE TABLE `diyi_client` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `client_id` varchar(50) NOT NULL COMMENT '客户端ID',
  `client_secret` varchar(50) NOT NULL COMMENT '客户端密钥',
  `access_token_validity` int(10) NOT NULL COMMENT '令牌过期秒数',
  `refresh_token_validity` int(10) NOT NULL COMMENT '刷新令牌过期秒数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端表';

-- ----------------------------
-- Records of diyi_client
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_dict`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_dict`;
CREATE TABLE `diyi_dict` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `parent_id` bigint(50) DEFAULT NULL COMMENT '父主键',
  `code` varchar(50) NOT NULL COMMENT '字典码',
  `dict_key` varchar(50) NOT NULL COMMENT '字典值',
  `dict_value` varchar(50) NOT NULL COMMENT '字典名称',
  `sort` int(5) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(1000) NOT NULL DEFAULT '' COMMENT '字典备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`code`,`dict_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Records of diyi_dict
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
  `legal_person_name` varchar(50) NOT NULL COMMENT '法人代表名称',
  `legal_person_mobile` varchar(50) NOT NULL DEFAULT '' COMMENT '法人手机号',
  `legal_person_idcard` varchar(50) NOT NULL DEFAULT '' COMMENT '法人身份证',
  `social_credit_no` varchar(50) NOT NULL COMMENT '统一社会信用代码',
  `biz_licence_url` varchar(500) NOT NULL COMMENT '营业执照正本',
  `biz_licence_copy_url` varchar(500) NOT NULL DEFAULT '' COMMENT '营业执照副本',
  `enterprise_url` varchar(500) NOT NULL DEFAULT '' COMMENT '商户网址',
  `working_address` varchar(100) NOT NULL DEFAULT '' COMMENT '办公地址(快递地址）',
  `working_rel_name` varchar(50) NOT NULL DEFAULT '' COMMENT '收发票/税票快递【到付】联系人姓名',
  `working_rel_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '收发票/税票快递【到付】联系人手机号',
  `invoice_enterprise_name` varchar(50) NOT NULL COMMENT '开票资料-公司名称',
  `invoice_tax_no` varchar(50) NOT NULL COMMENT '开票资料-税号（统一社会信用代码）',
  `invoice_address_phone` varchar(100) NOT NULL COMMENT '开票资料-地址和电话',
  `invoice_bank_name_account` varchar(100) NOT NULL COMMENT '开票资料-开户银行和账号',
  `contact1_name` varchar(50) NOT NULL COMMENT '联系人1姓名（一般为老板/财务负责人）',
  `contact1_position` varchar(50) NOT NULL COMMENT '联系人1职位',
  `contact1_phone` varchar(50) NOT NULL COMMENT '联系人1电话手机（必填）',
  `contact1_mail` varchar(50) NOT NULL COMMENT '联系人1邮箱',
  `contact2_name` varchar(50) NOT NULL COMMENT '联系人2姓名',
  `contact2_position` varchar(50) NOT NULL COMMENT '联系人2职位',
  `contact2_phone` varchar(50) NOT NULL COMMENT '联系人2电话手机（必填）',
  `contact2_mail` varchar(50) NOT NULL COMMENT '联系人2邮箱',
  `spec_demmand` varchar(100) NOT NULL DEFAULT '' COMMENT '特殊需求',
  `create_type` varchar(50) NOT NULL COMMENT '创建类型：平台创建，自注册',
  `saler_id` bigint(50) DEFAULT NULL COMMENT '营销人员',
  `runner_id` bigint(50) DEFAULT NULL COMMENT '运营人员',
  `industry_type` varchar(50) NOT NULL DEFAULT '' COMMENT '行业分类',
  `main_business_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '主营业务描述',
  `qyshop_address` varchar(500) NOT NULL DEFAULT '' COMMENT '企翼网商铺地址',
  `shop_id` bigint(50) DEFAULT NULL COMMENT '商铺ID',
  `shop_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '商铺用户名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`invite_no`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`enterprise_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k3` (`social_credit_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户信息表';

-- ----------------------------
-- Records of diyi_enterprise
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
  `report_year` int(5) DEFAULT NULL COMMENT '申报年度',
  `report_quater` int(5) DEFAULT NULL COMMENT '申报季度',
  `report_month` int(5) DEFAULT NULL COMMENT '申报月度',
  `report_dead_date` datetime DEFAULT NULL COMMENT '申报截止日期',
  `report_complete_date` datetime DEFAULT NULL COMMENT '申报完成日期',
  `report_result_desc` varchar(100) DEFAULT '' COMMENT '申报结果说明',
  `report_result_files` varchar(500) DEFAULT '' COMMENT '申报结果文件资料',
  `report_person` varchar(50) DEFAULT NULL COMMENT '申报人员',
  `report_state` varchar(50) DEFAULT NULL COMMENT '申报状态',
  `report_guard_name` varchar(50) DEFAULT NULL COMMENT '申报相关政府机关名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='年度申报管理表';

-- ----------------------------
-- Records of diyi_enterprise_report
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_enterprise_service_provider`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_service_provider`;
CREATE TABLE `diyi_enterprise_service_provider` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `match_desc` varchar(100) DEFAULT NULL COMMENT '分配说明',
  `cooperate_status` varchar(50) NOT NULL COMMENT '合作状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`enterprise_id`,`service_provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户-服务商关联表';

-- ----------------------------
-- Records of diyi_enterprise_service_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_enterprise_service_provider_invoice_catalogs`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_service_provider_invoice_catalogs`;
CREATE TABLE `diyi_enterprise_service_provider_invoice_catalogs` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `invoice_catalog_name` varchar(50) NOT NULL COMMENT '发票类目名称',
  `set_type` varchar(50) NOT NULL COMMENT '设置性质',
  `invoice_demand` varchar(50) NOT NULL COMMENT '开票诉求',
  `invoice_demand_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '开票诉求备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户-服务商开票类目表';

-- ----------------------------
-- Records of diyi_enterprise_service_provider_invoice_catalogs
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_enterprise_worker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_worker`;
CREATE TABLE `diyi_enterprise_worker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `role_id` bigint(50) DEFAULT NULL COMMENT '角色ID',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `enterprise_worker_state` varchar(50) NOT NULL COMMENT '商户员工账户状态',
  `worker_name` varchar(50) NOT NULL COMMENT '姓名',
  `worker_sex` varchar(50) NOT NULL DEFAULT '' COMMENT '性别',
  `position_name` varchar(50) NOT NULL COMMENT '岗位性质',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `up_level_id` bigint(50) DEFAULT NULL COMMENT '上级主管',
  `employee_user_name` varchar(50) NOT NULL COMMENT '用户名',
  `employee_pwd` varchar(100) NOT NULL COMMENT '密码',
  `super_admin` bit(1) NOT NULL COMMENT '管理员权限',
  `admin_power` bit(1) NOT NULL COMMENT '管理员特性',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`employee_user_name`)
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
  `biz_type` varchar(50) NOT NULL COMMENT '个体户税种',
  `ibname` varchar(50) NOT NULL DEFAULT '' COMMENT '个体户名称',
  `ibtax_no` varchar(50) NOT NULL DEFAULT '' COMMENT '统一社会信用代码',
  `build_date_time` datetime DEFAULT NULL COMMENT '营业执照的注册日期',
  `biz_park` varchar(100) NOT NULL DEFAULT '' COMMENT '园区',
  `province` varchar(50) NOT NULL DEFAULT '' COMMENT '所在省',
  `city` varchar(50) NOT NULL DEFAULT '' COMMENT '所在市',
  `area` varchar(100) NOT NULL DEFAULT '' COMMENT '所在县',
  `registered_money` decimal(12,2) NOT NULL COMMENT '注册资金',
  `business_address` varchar(100) NOT NULL DEFAULT '' COMMENT '经营场所',
  `main_industry` varchar(50) NOT NULL COMMENT '主要行业',
  `biz_scope` varchar(500) NOT NULL COMMENT '经营范围',
  `candidated_names` varchar(500) NOT NULL COMMENT '注册时候选名称',
  `net_business_address` varchar(500) NOT NULL DEFAULT '' COMMENT '网络经营场所',
  `business_licence_url` varchar(500) NOT NULL DEFAULT '' COMMENT '营业执照正本',
  `business_licence_copy_url` varchar(500) NOT NULL DEFAULT '' COMMENT '营业执照副本',
  `ibstate` varchar(50) NOT NULL COMMENT '个体户状态',
  `submit_date_time` datetime DEFAULT NULL COMMENT '提交日期',
  `registered_date` datetime DEFAULT NULL COMMENT '注册日期',
  `tax_register_date_time` datetime DEFAULT NULL COMMENT '税务登记日期',
  `logout_date_time` datetime DEFAULT NULL COMMENT '注销日期',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(50) NOT NULL COMMENT '联系人手机号',
  `service_rate` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '服务费率',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `individual_business_id` bigint(50) NOT NULL COMMENT '个体户ID',
  `annual_fee_year` int(4) NOT NULL COMMENT '年费年度',
  `annual_fee_date_time` datetime NOT NULL COMMENT '年费缴纳日期',
  `annual_fee_amount` decimal(12,2) NOT NULL COMMENT '年费金额',
  `annual_fee_start` datetime NOT NULL COMMENT '年费起始日期',
  `annual_fee_end` datetime NOT NULL COMMENT '年费终止日期',
  `annual_fee_state` varchar(50) NOT NULL COMMENT '年费缴费状态',
  `year_serial` int(3) NOT NULL COMMENT '第几年',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `biz_type` varchar(50) NOT NULL COMMENT '个独税种',
  `ibname` varchar(50) NOT NULL DEFAULT '' COMMENT '个独名称',
  `ibtax_no` varchar(50) NOT NULL DEFAULT '' COMMENT '统一社会信用代码',
  `build_date_time` datetime DEFAULT NULL COMMENT '营业执照的注册日期',
  `biz_park` varchar(100) NOT NULL DEFAULT '' COMMENT '园区',
  `province` varchar(50) NOT NULL DEFAULT '' COMMENT '所在省',
  `city` varchar(50) NOT NULL DEFAULT '' COMMENT '所在市',
  `area` varchar(100) NOT NULL DEFAULT '' COMMENT '所在县',
  `registered_money` decimal(12,2) NOT NULL COMMENT '注册资金',
  `business_address` varchar(100) NOT NULL DEFAULT '' COMMENT '经营场所',
  `main_industry` varchar(50) NOT NULL COMMENT '主要行业',
  `biz_scope` varchar(500) NOT NULL COMMENT '经营范围',
  `candidated_names` varchar(500) NOT NULL COMMENT '注册时候选名称',
  `net_business_address` varchar(500) NOT NULL DEFAULT '' COMMENT '网络经营场所',
  `business_licence_url` varchar(500) NOT NULL DEFAULT '' COMMENT '营业执照正本',
  `business_licence_copy_url` varchar(500) NOT NULL DEFAULT '' COMMENT '营业执照副本',
  `ibstate` varchar(50) NOT NULL COMMENT '个独状态',
  `submit_date_time` datetime DEFAULT NULL COMMENT '提交日期',
  `registered_date` datetime DEFAULT NULL COMMENT '注册日期',
  `tax_register_date_time` datetime DEFAULT NULL COMMENT '税务登记日期',
  `logout_date_time` datetime DEFAULT NULL COMMENT '注销日期',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(50) NOT NULL COMMENT '联系人手机号',
  `service_rate` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '服务费率',
  `investor_hand_commitment` varchar(500) NOT NULL DEFAULT '' COMMENT '手持承诺书照片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `individual_enterprise_id` bigint(50) NOT NULL COMMENT '个独ID',
  `annual_fee_year` int(5) NOT NULL COMMENT '年费年度',
  `annual_fee_date_time` datetime NOT NULL COMMENT '年费缴纳日期',
  `annual_fee_amount` decimal(12,2) NOT NULL COMMENT '年费金额',
  `annual_fee_start` datetime NOT NULL COMMENT '年费起始日期',
  `annual_fee_end` datetime NOT NULL COMMENT '年费终止日期',
  `annual_fee_state` varchar(50) NOT NULL COMMENT '年费缴费状态',
  `year_serial` int(3) NOT NULL COMMENT '第几年',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `voice_total_amount` decimal(12,2) NOT NULL COMMENT '开票总额',
  `application_desc` varchar(100) DEFAULT NULL COMMENT '申请说明',
  `application_state` varchar(50) NOT NULL COMMENT '处理状态',
  `application_handle_desc` varchar(100) DEFAULT NULL COMMENT '处理说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `openid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信open_id',
  `session_key` varchar(50) NOT NULL DEFAULT '' COMMENT '微信session_key',
  `rel_date` datetime DEFAULT NULL COMMENT '微信关联日期',
  `due_date` datetime DEFAULT NULL COMMENT '到期日期',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `certification_state` varchar(50) NOT NULL COMMENT '认证状态',
  `certification_date` datetime DEFAULT NULL COMMENT '认证时间',
  `maker_state` varchar(50) NOT NULL COMMENT '账户状态',
  `politic_state` varchar(50) NOT NULL DEFAULT '' COMMENT '政治面貌',
  `nationality` varchar(50) NOT NULL DEFAULT '' COMMENT '民族',
  `levelofedu` varchar(50) NOT NULL DEFAULT '' COMMENT '文化程度',
  `email_address` varchar(100) NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `idcard_no` varchar(100) DEFAULT NULL COMMENT '身份证号码',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `login_pwd` varchar(100) NOT NULL COMMENT '登录密码',
  `bank_card_no` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡号',
  `bank_name` varchar(100) NOT NULL DEFAULT '' COMMENT '开户银行',
  `sub_bank_name` varchar(100) NOT NULL DEFAULT '' COMMENT '开户支行',
  `idcard_pic` varchar(500) NOT NULL DEFAULT '' COMMENT '身份证正面图',
  `idcard_pic_back` varchar(500) NOT NULL DEFAULT '' COMMENT '身份证反面图',
  `self_pic` varchar(500) NOT NULL DEFAULT '' COMMENT '正面自拍照',
  `idcard_copy` varchar(500) NOT NULL DEFAULT '' COMMENT '身份证复印件图',
  `idcard_back_hand` varchar(500) NOT NULL DEFAULT '' COMMENT '手持证件反面照',
  `idcard_hand` varchar(500) NOT NULL DEFAULT '' COMMENT '手持证件正面照',
  `idcard_verify_status` varchar(50) NOT NULL COMMENT '身份证验证状态',
  `idcard_verify_date` datetime DEFAULT NULL COMMENT '身份证验证日期时间',
  `face_verify_status` varchar(50) NOT NULL COMMENT '活体验证状态',
  `face_verify_date` datetime DEFAULT NULL COMMENT '活体验证日期时间',
  `bank_card_verify_status` varchar(50) NOT NULL COMMENT '银行卡验证状态',
  `bank_card_verify_date` datetime DEFAULT NULL COMMENT '银行卡验证日期时间',
  `phone_number_verify_status` varchar(50) NOT NULL COMMENT '手机号码验证状态',
  `phone_number_verify_date` datetime DEFAULT NULL COMMENT '手机号码验证日期时间',
  `pic_verify` varchar(500) NOT NULL DEFAULT '' COMMENT '验证图片',
  `idcard_verify_type` varchar(50) DEFAULT NULL COMMENT '身份证验证类型',
  `manual_verify_id` varchar(50) DEFAULT NULL COMMENT '手工验证人',
  `manual_verify_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '验证描述',
  `run_address` varchar(100) NOT NULL DEFAULT '' COMMENT '线上经营场所',
  `house_address` varchar(100) NOT NULL DEFAULT '' COMMENT '线下经营地址',
  `living_address` varchar(100) NOT NULL DEFAULT '' COMMENT '住址',
  `self_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '自我描述',
  `shop_id` bigint(50) DEFAULT NULL COMMENT '商铺ID',
  `shop_url` varchar(500) NOT NULL DEFAULT '' COMMENT '商铺',
  `shop_user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '商铺用户名',
  `apply_short_video` varchar(500) NOT NULL DEFAULT '' COMMENT '声明短视频',
  `video_audit` varchar(50) NOT NULL COMMENT '短视频审核状态',
  `video_audit_date` datetime DEFAULT NULL COMMENT '审核日期',
  `video_audit_person_id` bigint(50) DEFAULT NULL COMMENT '审核人员',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`idcard_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创客信息表';

-- ----------------------------
-- Records of diyi_maker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_maker_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker_enterprise`;
CREATE TABLE `diyi_maker_enterprise` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `position_id` bigint(50) DEFAULT NULL COMMENT '外包岗位ID',
  `rel_type` varchar(50) NOT NULL COMMENT '关联类型：创客主动关联，商户主动关联，平台关联',
  `relationship_type` varchar(50) NOT NULL COMMENT '创客商户关系',
  `rel_memo` varchar(100) NOT NULL DEFAULT '' COMMENT '关联备注',
  `cooperate_status` varchar(50) NOT NULL COMMENT '合作状态',
  `first_cooperation` bit(1) DEFAULT NULL COMMENT '创客第一次合作',
  `cooperation_start_time` datetime NOT NULL COMMENT '合作开始日期',
  `cooperation_end_time` datetime DEFAULT NULL COMMENT '合作终止日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`enterprise_id`,`maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='创客-商户关联表';

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
  `voice_type_no` varchar(50) DEFAULT NULL COMMENT '发票代码',
  `voice_serial_no` varchar(50) DEFAULT NULL COMMENT '发票号码',
  `maker_voice_get_date_time` datetime DEFAULT NULL COMMENT '发票开具日期',
  `voice_category` varchar(50) DEFAULT NULL COMMENT '货物或应税劳务、服务名称',
  `total_amount` decimal(12,2) DEFAULT NULL COMMENT '价税合计',
  `sales_amount` decimal(12,2) DEFAULT NULL COMMENT '金额合计',
  `tax_amount` decimal(12,2) DEFAULT NULL COMMENT '税额合计',
  `voice_person` varchar(50) DEFAULT '' COMMENT '开票人',
  `sale_company` varchar(50) DEFAULT '' COMMENT '销售方名称',
  `help_make_organation_name` varchar(50) DEFAULT NULL COMMENT '代开机关名称',
  `help_make_company` varchar(50) DEFAULT NULL COMMENT '代开商户名称',
  `help_make_tax_no` varchar(50) DEFAULT NULL COMMENT '代开商户税号',
  `maker_voice_url` varchar(500) NOT NULL COMMENT '发票',
  `maker_voice_upload_date_time` datetime DEFAULT NULL COMMENT '发票上传日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_maker_id` bigint(50) NOT NULL COMMENT '创客支付ID',
  `voice_type_no` varchar(100) DEFAULT NULL COMMENT '票证代码',
  `voice_serial_no` varchar(100) DEFAULT NULL COMMENT '票证号码',
  `maker_tax_amount` decimal(5,2) DEFAULT NULL COMMENT '总税金',
  `total_amount` decimal(5,2) DEFAULT NULL COMMENT '价税合计',
  `sales_amount` decimal(5,2) DEFAULT NULL COMMENT '金额合计',
  `tax_amount` decimal(5,2) DEFAULT NULL COMMENT '税额合计',
  `voice_person` varchar(50) DEFAULT NULL COMMENT '开票人',
  `sale_company` varchar(100) DEFAULT NULL COMMENT '销售机关',
  `help_make_organation_name` varchar(100) DEFAULT NULL COMMENT '代开机关名称',
  `maker_tax_url` varchar(300) DEFAULT NULL COMMENT '完税证明',
  `maker_tax_get_datetime` datetime NOT NULL COMMENT '完税证明开具日期',
  `maker_tax_upload_datetime` datetime DEFAULT NULL COMMENT '完税证明上传日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `id` bigint(50) NOT NULL COMMENT '主键',
  `pay_enterprise_id` bigint(50) NOT NULL COMMENT '支付清单ID',
  `invoice_type_no` varchar(100) NOT NULL DEFAULT '' COMMENT '发票代码',
  `invoice_serial_no` varchar(100) NOT NULL DEFAULT '' COMMENT '发票号码',
  `invoice_datetime` datetime DEFAULT NULL COMMENT '开票日期',
  `invoice_category` varchar(100) NOT NULL DEFAULT '' COMMENT '货物或应税劳务、服务名称',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总金额',
  `sales_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '金额合计',
  `tax_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '税额合计',
  `invoice_person` varchar(5) NOT NULL DEFAULT '' COMMENT '开票人',
  `sale_company` varchar(100) NOT NULL DEFAULT '' COMMENT '销售方名称',
  `company_invoice_url` varchar(500) NOT NULL COMMENT '总包发票',
  `company_voice_upload_datetime` datetime DEFAULT NULL COMMENT '发票上传日期',
  `maker_tax_url` varchar(500) NOT NULL COMMENT '总完税证明',
  `maker_tax_list_url` varchar(500) DEFAULT NULL COMMENT '清单式完税凭证',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='汇总代开发票表';

-- ----------------------------
-- Records of diyi_maker_total_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_menu`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_menu`;
CREATE TABLE `diyi_menu` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `parent_id` bigint(50) DEFAULT NULL COMMENT '父级菜单',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `path` varchar(500) NOT NULL COMMENT '请求地址',
  `category` varchar(50) NOT NULL COMMENT '菜单类型',
  `menu_type` varchar(50) DEFAULT NULL COMMENT '菜单用户类型',
  `sort` int(5) DEFAULT '0' COMMENT '排序',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Records of diyi_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_online_agreement_need_sign`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_online_agreement_need_sign`;
CREATE TABLE `diyi_online_agreement_need_sign` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `online_agreement_template_id` bigint(50) NOT NULL COMMENT '平台在线协议模板ID',
  `object_type` varchar(50) NOT NULL COMMENT '对象身份',
  `sign_state` varchar(50) NOT NULL COMMENT '签约状态',
  `sign_power` varchar(50) NOT NULL COMMENT '签字对象性质',
  `object_id` bigint(50) NOT NULL COMMENT '对象ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台在线协议需要签署清单表';

-- ----------------------------
-- Records of diyi_online_agreement_need_sign
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_online_agreement_template`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_online_agreement_template`;
CREATE TABLE `diyi_online_agreement_template` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `agreement_type` varchar(50) NOT NULL COMMENT '协议类别',
  `template_state` varchar(50) NOT NULL COMMENT '模板状态',
  `agreement_template` varchar(500) NOT NULL COMMENT '协议模板',
  `bool_all_sign` bit(1) NOT NULL COMMENT '是否需要全部签署',
  `template_type` varchar(50) NOT NULL COMMENT '模板类型',
  `template_count` int(10) NOT NULL COMMENT '模板的页数',
  `x_coordinate` float DEFAULT '0' COMMENT 'x坐标',
  `y_coordinate` float DEFAULT '0' COMMENT 'y坐标',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台在线协议模板表';

-- ----------------------------
-- Records of diyi_online_agreement_template
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_online_sign_pic`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_online_sign_pic`;
CREATE TABLE `diyi_online_sign_pic` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `object_type` varchar(50) NOT NULL COMMENT '对象身份',
  `object_id` bigint(50) NOT NULL COMMENT '对象ID',
  `sign_pic` varchar(500) NOT NULL COMMENT '签字笔迹',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`object_type`,`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='在线签字图片表';

-- ----------------------------
-- Records of diyi_online_sign_pic
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_partner`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_partner`;
CREATE TABLE `diyi_partner` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `introduce_partner_id` bigint(50) DEFAULT NULL COMMENT '介绍合伙人ID',
  `openid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信open_id',
  `session_key` varchar(50) NOT NULL DEFAULT '' COMMENT '微信session_key',
  `rel_date` datetime DEFAULT NULL COMMENT '微信关联日期',
  `due_date` datetime DEFAULT NULL COMMENT '到期日期',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `certification_state` varchar(50) NOT NULL COMMENT '认证状态',
  `certification_date` datetime DEFAULT NULL COMMENT '认证时间',
  `partner_state` varchar(50) NOT NULL COMMENT '账户状态',
  `politic_state` varchar(50) NOT NULL DEFAULT '' COMMENT '政治面貌',
  `nationality` varchar(50) NOT NULL DEFAULT '' COMMENT '民族',
  `levelofedu` varchar(50) NOT NULL DEFAULT '' COMMENT '文化程度',
  `email_address` varchar(100) NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `idcard_no` varchar(100) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `login_pwd` varchar(100) NOT NULL COMMENT '登录密码',
  `bank_card_no` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡号',
  `bank_name` varchar(100) NOT NULL DEFAULT '' COMMENT '开户银行',
  `sub_bank_name` varchar(100) NOT NULL DEFAULT '' COMMENT '开户支行',
  `idcard_pic` varchar(500) NOT NULL DEFAULT '' COMMENT '身份证正面图',
  `idcard_pic_back` varchar(500) NOT NULL DEFAULT '' COMMENT '身份证反面图',
  `self_pic` varchar(500) NOT NULL DEFAULT '' COMMENT '正面自拍照',
  `idcard_copy` varchar(500) NOT NULL DEFAULT '' COMMENT '身份证复印件图',
  `idcard_back_hand` varchar(500) NOT NULL DEFAULT '' COMMENT '手持证件反面照',
  `idcard_hand` varchar(500) NOT NULL DEFAULT '' COMMENT '手持证件正面照',
  `idcard_verify_status` varchar(50) NOT NULL COMMENT '身份证验证状态：未验证，验证通过，验证未通过',
  `idcard_verify_date` datetime DEFAULT NULL COMMENT '身份证验证日期时间',
  `face_verify_status` varchar(50) NOT NULL COMMENT '活体验证状态：未验证，验证通过，验证未通过',
  `face_verify_date` datetime DEFAULT NULL COMMENT '活体验证日期时间',
  `bank_card_verify_status` varchar(50) NOT NULL COMMENT '银行卡验证状态：未验证，验证通过，验证未通过',
  `bank_card_verify_date` datetime DEFAULT NULL COMMENT '银行卡验证日期时间',
  `phone_number_verify_status` varchar(50) NOT NULL COMMENT '手机号码验证状态：未验证，验证通过，验证未通过',
  `phone_number_verify_date` datetime DEFAULT NULL COMMENT '手机号码验证日期时间',
  `pic_verify` varchar(500) NOT NULL DEFAULT '' COMMENT '验证图片',
  `idcard_verify_type` varchar(50) DEFAULT NULL COMMENT '身份证验证类型：系统验证，手工验证',
  `manual_verify_id` varchar(50) DEFAULT NULL COMMENT '手工验证人',
  `manual_verify_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '验证描述',
  `run_address` varchar(100) NOT NULL DEFAULT '' COMMENT '线上经营场所',
  `house_address` varchar(100) NOT NULL DEFAULT '' COMMENT '线下经营地址',
  `living_address` varchar(100) NOT NULL DEFAULT '' COMMENT '住址',
  `self_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '自我描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合伙人信息表';

-- ----------------------------
-- Records of diyi_partner
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_partner_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_partner_enterprise`;
CREATE TABLE `diyi_partner_enterprise` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `partner_id` bigint(50) NOT NULL COMMENT '合伙人ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `cooperate_type` varchar(50) NOT NULL COMMENT '合作关系',
  `cooperate_status` varchar(50) NOT NULL COMMENT '合作状态',
  `operate_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '分配说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`enterprise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合伙人-商户关联表';

-- ----------------------------
-- Records of diyi_partner_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_pay_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay_enterprise`;
CREATE TABLE `diyi_pay_enterprise` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `charge_list_url` varchar(500) NOT NULL COMMENT '支付清单URL',
  `maker_type` varchar(50) NOT NULL COMMENT '创客身份',
  `maker_invoice_type` varchar(50) DEFAULT NULL COMMENT '创客发票开票类别',
  `worksheet_id` bigint(50) DEFAULT NULL COMMENT '工单ID',
  `pay_to_platform_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '商户总支付额价税合计总额=服务外包费总额+身份验证费总额/个体户年费总额+第三方支付手续费总额',
  `total_tax_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '服务税费总额=服务外包费总额*服务税费率',
  `total_maker_net_income` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '创客到手总额',
  `service_rate` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '服务税费率',
  `sourcing_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '服务外包费总额',
  `enterprise_business_annual_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '商户年费总额，个体户，个独，有限公司都有年费，自然人没有年费',
  `identify_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '身份验证费总额',
  `service_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '第三方支付手续费总额',
  `maker_num` int(12) NOT NULL DEFAULT '0' COMMENT '创客数',
  `pay_memo` varchar(100) NOT NULL DEFAULT '' COMMENT '支付说明',
  `pay_state` varchar(50) NOT NULL COMMENT '支付给平台状态',
  `pay_confirm_date_time` datetime DEFAULT NULL COMMENT '支付确认日期时间',
  `confirm_date_time` datetime DEFAULT NULL COMMENT '确认回款日期时间',
  `employee_id` bigint(50) DEFAULT NULL COMMENT '确认到款人员ID',
  `audit_state` varchar(50) NOT NULL COMMENT '审核状态。驳回的还可以再提交审核',
  `company_invoice_state` varchar(50) NOT NULL COMMENT '总包开票状态',
  `subcontracting_invoice_state` varchar(50) NOT NULL COMMENT '分包开票状态',
  `invoice_print_date` datetime DEFAULT NULL COMMENT '开票日期',
  `invoice_demond_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '开票要求说明',
  `total_pay_tax_sheet` varchar(1000) NOT NULL DEFAULT '' COMMENT '集合完税凭证',
  `total_pay_tax_list` varchar(1000) NOT NULL DEFAULT '' COMMENT '集合完税清单',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='总包支付清单表';

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
  `enterprise_pay_receipt_url` varchar(500) NOT NULL COMMENT '支付回单',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `maker_type` varchar(50) NOT NULL COMMENT '创客身份',
  `individual_id` varchar(50) DEFAULT NULL COMMENT '个体户/个独ID',
  `total_fee` decimal(12,2) NOT NULL COMMENT '商户总支付额价税合计=服务外包费+身份验证费/个体户年费+第三方支付手续费',
  `maker_net_income` decimal(12,2) NOT NULL COMMENT '创客到手=服务外包费-创客税费=服务外包费*服务税费率',
  `maker_tax_fee` decimal(12,2) NOT NULL COMMENT '服务税费=服务外包费*服务税费率',
  `maker_ne_income` decimal(12,2) NOT NULL COMMENT '服务外包费=创客到手/(1-服务税费率)',
  `service_rate` decimal(5,2) NOT NULL COMMENT '服务税费率',
  `enterprise_business_annual_fee` decimal(12,2) NOT NULL COMMENT '商户年费总额，个体户，个独，有限公司都有年费，自然人没有年费',
  `audit_fee` decimal(12,2) NOT NULL COMMENT '身份验证费',
  `pay_fee` decimal(12,2) NOT NULL COMMENT '第三方支付手续费',
  `pay_state` varchar(50) NOT NULL COMMENT '1：待支付；2:商户已申请支付；3：商户已支付；4：平台已支付；5：已确认收款',
  `company_apply_datetime` datetime DEFAULT NULL COMMENT '商户申请支付日期时间',
  `company_pay_ok_datetime` datetime DEFAULT NULL COMMENT '商户支付确认日期时间',
  `platform_pay_ok_datetime` datetime DEFAULT NULL COMMENT '平台支付确认日期时间',
  `maker_confirm_datetime` datetime DEFAULT NULL COMMENT '取交付支付确认函的确认到款日期时间',
  `maker_tax_state` varchar(50) NOT NULL COMMENT '完税证明开票状态:1:已开；0：未开',
  `maker_invoice_state` varchar(50) NOT NULL COMMENT '发票开票状态:1:已开；0：未开',
  `pay_memo` varchar(100) NOT NULL DEFAULT '' COMMENT '支付说明',
  `maker_invoice_category` varchar(1000) NOT NULL DEFAULT '' COMMENT '创客发票类目:默认取订单中的默认信息，可更改，根据具体业务开，如*现代服务*市场推广费',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`pay_enterprise_id`,`maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分包支付明细表';

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
  `maker_pay_receipt_url` varchar(500) NOT NULL COMMENT '支付回单',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分包支付明细回单表';

-- ----------------------------
-- Records of diyi_pay_maker_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_platform_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_platform_invoice`;
CREATE TABLE `diyi_platform_invoice` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `application_id` bigint(50) DEFAULT NULL COMMENT '总包开票申请ID',
  `invoice_print_date` datetime NOT NULL COMMENT '开票日期',
  `invoice_total_amount` decimal(12,2) NOT NULL COMMENT '开票总额',
  `opened_invoice_total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '已开总额',
  `invoice_numbers` int(11) NOT NULL COMMENT '发票张数',
  `invoice_print_person` varchar(50) NOT NULL COMMENT '开票人',
  `express_sheet_no` varchar(50) NOT NULL COMMENT '快递单号',
  `express_company_name` varchar(50) NOT NULL COMMENT '快递公司',
  `invoice_desc` varchar(50) DEFAULT NULL COMMENT '开票说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `invoice_type_no` varchar(50) NOT NULL DEFAULT '' COMMENT '发票代码',
  `invoice_serial_no` varchar(50) NOT NULL DEFAULT '' COMMENT '发票号码',
  `invoice_datetime` datetime NOT NULL COMMENT '开票日期',
  `invoice_category` varchar(100) NOT NULL DEFAULT '' COMMENT '货物或应税劳务、服务名称',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '价税合计',
  `sales_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '金额合计',
  `tax_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '税额合计',
  `invoice_person` varchar(100) NOT NULL DEFAULT '' COMMENT '开票人',
  `sale_company` varchar(100) NOT NULL DEFAULT '' COMMENT '销售方名称',
  `company_invoice_url` varchar(500) NOT NULL COMMENT '总包发票',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记录服务商开具给商户的总包发票关联的支付清单';

-- ----------------------------
-- Records of diyi_platform_invoice_pay_list
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_rel_bureau`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_rel_bureau`;
CREATE TABLE `diyi_rel_bureau` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `rel_bureau_state` varchar(50) NOT NULL COMMENT '相关局账户状态',
  `rel_bureau_type` varchar(50) NOT NULL COMMENT '相关局类型',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `rel_bureau_user_name` varchar(50) NOT NULL COMMENT '用户名',
  `rel_bureau_pwd` varchar(100) NOT NULL COMMENT '密码',
  `rel_bureau_name` varchar(50) NOT NULL COMMENT '相关局名称',
  `province` varchar(50) NOT NULL COMMENT '省',
  `city` varchar(50) NOT NULL COMMENT '市',
  `area` varchar(50) NOT NULL COMMENT '区',
  `detailed_address` varchar(100) NOT NULL COMMENT '详细地址',
  `rel_bureau_website` varchar(500) NOT NULL COMMENT '网址',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `contact_position` varchar(50) NOT NULL COMMENT '联系人职位',
  `contact_phone` varchar(50) NOT NULL COMMENT '联系人电话手机（必填）',
  `contact_mail` varchar(50) NOT NULL COMMENT '联系人邮箱',
  `contact_wechat` varchar(50) NOT NULL COMMENT '联系人微信',
  `director_name` varchar(50) NOT NULL COMMENT '局长姓名',
  `director_phone` varchar(50) NOT NULL COMMENT '局长联系电话',
  `vice_director_name` varchar(50) NOT NULL COMMENT '副局长姓名',
  `vice_director_phone` varchar(50) NOT NULL COMMENT '副局长联系电话',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`rel_bureau_type`,`rel_bureau_user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='相关局管理表';

-- ----------------------------
-- Records of diyi_rel_bureau
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_rel_bureau_contract`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_rel_bureau_contract`;
CREATE TABLE `diyi_rel_bureau_contract` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `rel_bureau_id` bigint(50) NOT NULL COMMENT '相关局编号',
  `contract_name` varchar(50) NOT NULL COMMENT '合同名称',
  `contract_desc` varchar(100) NOT NULL COMMENT '合同说明',
  `contract_url` varchar(1000) NOT NULL COMMENT '合同内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`rel_bureau_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='相关局合作协议表';

-- ----------------------------
-- Records of diyi_rel_bureau_contract
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_rel_bureau_file`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_rel_bureau_file`;
CREATE TABLE `diyi_rel_bureau_file` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `rel_bureau_id` bigint(50) NOT NULL COMMENT '相关局编号',
  `file_title` varchar(50) NOT NULL COMMENT '文件标题',
  `file_desc` varchar(100) NOT NULL COMMENT '通知文件',
  `file_url` varchar(500) NOT NULL COMMENT '监管文件',
  `publish_datetime` datetime DEFAULT NULL COMMENT '发布日期时间',
  `file_state` varchar(50) NOT NULL COMMENT '监管文件状态',
  `cancel_datetime` datetime DEFAULT NULL COMMENT '作废日期时间',
  `contact_person` varchar(50) NOT NULL DEFAULT '' COMMENT '发布联系人',
  `contact_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人手机',
  `contact_wechat` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人微信',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='相关局监管文件管理表';

-- ----------------------------
-- Records of diyi_rel_bureau_file
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_rel_bureau_file_read`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_rel_bureau_file_read`;
CREATE TABLE `diyi_rel_bureau_file_read` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `rel_bureau_file_id` bigint(50) NOT NULL COMMENT '通知ID',
  `servicer_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `servicer_provider_worker_id` bigint(50) NOT NULL COMMENT '服务商员工ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`rel_bureau_file_id`,`servicer_provider_worker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='相关局监管文件阅读管理表';

-- ----------------------------
-- Records of diyi_rel_bureau_file_read
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_rel_bureau_notice`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_rel_bureau_notice`;
CREATE TABLE `diyi_rel_bureau_notice` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `rel_bureau_id` bigint(50) NOT NULL COMMENT '相关局ID',
  `notice_title` varchar(50) NOT NULL COMMENT '通知标题',
  `notice_desc` varchar(100) NOT NULL COMMENT '通知摘要',
  `notice_url` varchar(500) NOT NULL COMMENT '通知文件',
  `publish_datetime` datetime DEFAULT NULL COMMENT '发布日期时间',
  `notice_state` varchar(50) NOT NULL COMMENT '通知状态',
  `cancel_datetime` datetime DEFAULT NULL COMMENT '作废日期时间',
  `contact_person` varchar(50) NOT NULL DEFAULT '' COMMENT '发布联系人',
  `contact_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人手机',
  `contact_wechat` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人微信',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='相关局通知管理表';

-- ----------------------------
-- Records of diyi_rel_bureau_notice
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_rel_bureau_notice_read`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_rel_bureau_notice_read`;
CREATE TABLE `diyi_rel_bureau_notice_read` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `rel_bureau_notice_id` bigint(50) NOT NULL COMMENT '通知ID',
  `servicer_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `servicer_provider_worker_id` bigint(50) NOT NULL COMMENT '服务商员工ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`rel_bureau_notice_id`,`servicer_provider_worker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='相关局通知阅读管理表';

-- ----------------------------
-- Records of diyi_rel_bureau_notice_read
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_rel_bureau_service_provider`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_rel_bureau_service_provider`;
CREATE TABLE `diyi_rel_bureau_service_provider` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `rel_bureau_id` bigint(50) NOT NULL COMMENT '相关局ID',
  `rel_bureau_type` varchar(50) NOT NULL COMMENT '相关局的类型',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID，一个服务商只能属于一个相关局监管',
  `cooperate_status` varchar(50) NOT NULL COMMENT '关联状态',
  `match_desc` varchar(100) DEFAULT NULL COMMENT '分配说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`rel_bureau_id`,`service_provider_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`rel_bureau_type`,`service_provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='相关局与服务商关联表';

-- ----------------------------
-- Records of diyi_rel_bureau_service_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_role`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_role`;
CREATE TABLE `diyi_role` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `role_name` varchar(50) NOT NULL COMMENT '角色名',
  `user_type` varchar(50) NOT NULL COMMENT '用户类型',
  `account` bigint(50) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of diyi_role
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_role_menu`;
CREATE TABLE `diyi_role_menu` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `menu_id` bigint(50) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(50) NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`menu_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单表';

-- ----------------------------
-- Records of diyi_role_menu
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
  `maker_type` varchar(50) NOT NULL COMMENT '开票人身份类别',
  `list_file` varchar(500) NOT NULL COMMENT '开票清单文件',
  `pay_type` varchar(50) NOT NULL COMMENT '众包支付模式',
  `total_pay_provider_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总需支付服务商税费=总服务税费+总开票手续费+总身份验证费，自动计算',
  `service_and_tax_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总服务税费=总服务外包费*服务税费率',
  `service_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总服务外包费：合计，明细价税合计，依据明细自动计算',
  `service_rate` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '服务税费率',
  `service_tax` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总税费，一般不填',
  `service_invoice_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总开票手续费',
  `idendity_confirm_fee` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总身份验证费',
  `current_state` varchar(50) NOT NULL COMMENT '当前状态',
  `apply_num` int(3) NOT NULL DEFAULT '0' COMMENT '申请次数',
  `confirm_price_datetime` datetime DEFAULT NULL COMMENT '核价时间',
  `address_id` bigint(50) NOT NULL COMMENT '收件地址ID',
  `extend_pay_invoices` varchar(100) NOT NULL DEFAULT '' COMMENT '扩展支付税费发票',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `basic_account_bank` varchar(50) NOT NULL DEFAULT '' COMMENT '基本存款账号',
  `bool_default` bit(1) NOT NULL COMMENT '是否默认',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票主表ID',
  `apply_state` varchar(50) NOT NULL COMMENT '申请状态',
  `apply_desc` varchar(100) DEFAULT NULL COMMENT '申请说明',
  `audit_desc` varchar(100) DEFAULT NULL COMMENT '审核说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票ID',
  `invoice_people_name` varchar(50) NOT NULL COMMENT '开票人姓名',
  `project_name` varchar(300) DEFAULT NULL COMMENT '项目名称',
  `maker_type` varchar(50) NOT NULL COMMENT '开票人身份类别',
  `maker_id` bigint(50) DEFAULT NULL COMMENT '创客ID',
  `none_maker_invoice_person_id` bigint(50) DEFAULT NULL COMMENT '非创客开票人ID',
  `individual_id` bigint(50) DEFAULT NULL COMMENT '各类商户ID',
  `invoice_print_state` varchar(50) NOT NULL COMMENT '开票状态',
  `invoice_type` varchar(50) NOT NULL COMMENT '开票类目',
  `value_added_tax_rate` decimal(5,2) NOT NULL COMMENT '增值税税率',
  `charge_money_num` decimal(12,2) NOT NULL COMMENT '价税合计额',
  `flow_contract_url` varchar(500) NOT NULL COMMENT '流水回单',
  `business_contract_url` varchar(500) NOT NULL COMMENT '业务合同',
  `account_balance_url` varchar(500) NOT NULL DEFAULT '' COMMENT '账户余额，个独需要',
  `service_invoice_fee` decimal(12,2) NOT NULL COMMENT '开票手续费',
  `idendity_confirm_fee` decimal(12,2) NOT NULL COMMENT '身份验证费',
  `pay_provider_fee` decimal(12,2) NOT NULL COMMENT '需支付服务商税费=价税合计额*服务税费率+开票手续费+身份验证费',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `self_help_invoice_apply_provider_id` bigint(50) NOT NULL COMMENT '自助开票服务商ID',
  `express_no` varchar(100) NOT NULL COMMENT '快递单号',
  `express_company_name` varchar(100) NOT NULL COMMENT '快递公司',
  `express_file_url` varchar(500) DEFAULT '' COMMENT '快递回单或二维码',
  `special_desc` varchar(100) DEFAULT '' COMMENT '特殊说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`self_help_invoice_apply_provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票快递表';

-- ----------------------------
-- Records of diyi_self_help_invoice_express
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_fee`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_fee`;
CREATE TABLE `diyi_self_help_invoice_fee` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票ID',
  `give_price_date` datetime NOT NULL COMMENT '核价日期',
  `total_tax_fee` decimal(12,2) NOT NULL COMMENT '总税费',
  `basic_tax_fee` decimal(12,2) NOT NULL COMMENT '基础税费',
  `basic_tax_fee_rate` decimal(5,2) NOT NULL COMMENT '基础税费率',
  `invoice_fee` decimal(12,2) NOT NULL COMMENT '开票手续费',
  `identify_fee` decimal(12,2) NOT NULL COMMENT '身份验证费',
  `pay_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '支付说明',
  `pay_certificate` varchar(500) NOT NULL DEFAULT '' COMMENT '支付回单',
  `pay_type` varchar(50) DEFAULT NULL COMMENT '支付方式',
  `hand_pay_account_id` bigint(50) NOT NULL COMMENT '自助开票收款账号ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
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
  `idcard_no` varchar(50) NOT NULL COMMENT '身份证号码',
  `idcard_name` varchar(50) NOT NULL COMMENT '身份证姓名',
  `idcard_pic` varchar(500) NOT NULL COMMENT '身份证正面图',
  `idcard_pic_back` varchar(500) NOT NULL COMMENT '身份证反面图',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`idcard_no`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票非创客开票人表';

-- ----------------------------
-- Records of diyi_self_help_invoice_person
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_sp`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_sp`;
CREATE TABLE `diyi_self_help_invoice_sp` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_id` bigint(50) NOT NULL COMMENT '自助开票ID',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `apply_state` varchar(50) DEFAULT NULL COMMENT ' 申请状态',
  `apply_desc` varchar(100) DEFAULT '' COMMENT '申请说明',
  `audit_desc` varchar(100) DEFAULT '' COMMENT '结果说明',
  `charge_money_num` decimal(12,2) DEFAULT NULL COMMENT '价税合计额',
  `value_money_num` decimal(12,2) DEFAULT NULL COMMENT '总开票金额合计',
  `service_rate` decimal(5,2) DEFAULT NULL COMMENT '服务税费率',
  `service_and_tax_money` decimal(12,2) DEFAULT NULL COMMENT '总服务税费',
  `service_invoice_fee` decimal(12,2) DEFAULT NULL COMMENT '总开票手续费',
  `idendity_confirm_fee` decimal(12,2) DEFAULT NULL COMMENT '总身份验证费',
  `pay_total_num` decimal(12,2) DEFAULT NULL COMMENT '支付总额',
  `address_id` bigint(50) NOT NULL COMMENT '收件地址ID',
  `address_type` varchar(50) NOT NULL COMMENT '收件地址性质',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`self_help_invoice_id`,`service_provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助开票-服务商关联表';

-- ----------------------------
-- Records of diyi_self_help_invoice_sp
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_self_help_invoice_sp_detail`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_self_help_invoice_sp_detail`;
CREATE TABLE `diyi_self_help_invoice_sp_detail` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `self_help_invoice_detail_id` bigint(50) NOT NULL COMMENT '自助开票明细ID',
  `invoice_scan_pictures` varchar(1000) NOT NULL DEFAULT '' COMMENT ' 发票扫描件（多张）',
  `tax_scan_pictures` varchar(1000) NOT NULL DEFAULT '' COMMENT ' 税票扫描件（多张）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`self_help_invoice_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商开票明细表';

-- ----------------------------
-- Records of diyi_self_help_invoice_sp_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider`;
CREATE TABLE `diyi_service_provider` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_state` varchar(50) NOT NULL COMMENT '服务商账户状态',
  `service_provider_name` varchar(50) NOT NULL COMMENT '服务商名称',
  `legal_person_name` varchar(50) NOT NULL COMMENT '法人代表名称',
  `legal_person_mobile` varchar(50) NOT NULL DEFAULT '' COMMENT '法人手机号',
  `legal_person_idcard` varchar(50) NOT NULL DEFAULT '' COMMENT '法人身份证',
  `social_credit_no` varchar(50) NOT NULL COMMENT '统一社会信用代码',
  `biz_licence_url` varchar(500) NOT NULL COMMENT '营业执照正本',
  `biz_licence_copy_url` varchar(500) NOT NULL DEFAULT '' COMMENT '营业执照副本',
  `service_provider_url` varchar(500) NOT NULL COMMENT '商户网址',
  `working_address` varchar(100) NOT NULL DEFAULT '' COMMENT '办公地址(快递地址）',
  `working_rel_name` varchar(50) NOT NULL DEFAULT '' COMMENT '收发票/税票快递【到付】联系人姓名',
  `working_rel_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '收发票/税票快递【到付】联系人手机号',
  `invoice_enterprise_name` varchar(50) NOT NULL COMMENT '开票资料-公司名称',
  `invoice_tax_no` varchar(50) NOT NULL COMMENT '开票资料-税号',
  `invoice_address_phone` varchar(100) NOT NULL COMMENT '开票资料-地址和电话',
  `invoice_bank_name_account` varchar(100) NOT NULL COMMENT '开票资料-开户银行和账号',
  `contact1_name` varchar(50) NOT NULL COMMENT '联系人1姓名（一般为老板/财务负责人）',
  `contact1_position` varchar(50) NOT NULL COMMENT '联系人1职位',
  `contact1_phone` varchar(50) NOT NULL COMMENT '联系人1电话手机（必填）',
  `contact1_mail` varchar(50) NOT NULL COMMENT '联系人1邮箱',
  `contact2_name` varchar(50) NOT NULL COMMENT '联系人2姓名',
  `contact2_position` varchar(50) NOT NULL COMMENT '联系人2职位',
  `contact2_phone` varchar(50) NOT NULL COMMENT '联系人2电话手机（必填）',
  `contact2_mail` varchar(50) NOT NULL COMMENT '联系人2邮箱',
  `spec_demmand` varchar(100) NOT NULL DEFAULT '' COMMENT '特殊需求',
  `create_type` varchar(50) NOT NULL COMMENT '创建类型：平台创建，自注册',
  `saler_id` bigint(50) DEFAULT NULL COMMENT '营销人员',
  `runner_id` bigint(50) DEFAULT NULL COMMENT '运营人员',
  `industry_type` varchar(50) NOT NULL DEFAULT '' COMMENT '行业分类',
  `main_business_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '主营业务描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`service_provider_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`social_credit_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商信息表';

-- ----------------------------
-- Records of diyi_service_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider_account`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider_account`;
CREATE TABLE `diyi_service_provider_account` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `account_type` varchar(50) NOT NULL COMMENT '账户类型',
  `account_name` varchar(100) NOT NULL COMMENT '账户名称',
  `account_no` varchar(50) NOT NULL COMMENT '银行账号',
  `account_bank` varchar(100) NOT NULL COMMENT '开户银行',
  `basic_account_bank` varchar(100) NOT NULL DEFAULT '' COMMENT '基本存款账号，对公账号才需要',
  `maker_safe_account` varchar(100) NOT NULL DEFAULT '' COMMENT '个人创客到手业务外包费账户',
  `provider_tax_and_fee_account` varchar(100) NOT NULL DEFAULT '' COMMENT '服务税费账户',
  `bool_default` bit(1) NOT NULL COMMENT '是否默认',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`service_provider_id`,`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商收款账户信息表';

-- ----------------------------
-- Records of diyi_service_provider_account
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider_cert`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider_cert`;
CREATE TABLE `diyi_service_provider_cert` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` varchar(50) NOT NULL COMMENT '服务商ID',
  `certificate_type` varchar(50) NOT NULL COMMENT '类别',
  `certificate_name` varchar(50) NOT NULL COMMENT '资格名称',
  `certificate_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '资格说明',
  `certificate_main_url` varchar(500) NOT NULL COMMENT '资格证书正本',
  `certificate_supply_url` varchar(500) NOT NULL DEFAULT '' COMMENT '资格证书副本',
  `certificate_other_url` varchar(500) NOT NULL DEFAULT '' COMMENT '资格证书辅助文件',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商资格信息表';

-- ----------------------------
-- Records of diyi_service_provider_cert
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider_invoice_catalogs`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider_invoice_catalogs`;
CREATE TABLE `diyi_service_provider_invoice_catalogs` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `apply_scope` varchar(50) NOT NULL COMMENT '应用范围',
  `invoice_catalog_name` varchar(50) NOT NULL COMMENT '发票类目名称',
  `set_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '设置说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户-服务商开票类目表';

-- ----------------------------
-- Records of diyi_service_provider_invoice_catalogs
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider_maker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider_maker`;
CREATE TABLE `diyi_service_provider_maker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `rel_type` varchar(50) NOT NULL COMMENT '关联类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`service_provider_id`,`enterprise_id`,`maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商-创客关联表';

-- ----------------------------
-- Records of diyi_service_provider_maker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider_rule`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider_rule`;
CREATE TABLE `diyi_service_provider_rule` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `maker_rules` varchar(500) NOT NULL DEFAULT '' COMMENT '个人创客规则要求',
  `enterprise_rules` varchar(500) NOT NULL DEFAULT '' COMMENT '商户规则要求',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`service_provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商-业务合规要求表';

-- ----------------------------
-- Records of diyi_service_provider_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_service_provider_worker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_service_provider_worker`;
CREATE TABLE `diyi_service_provider_worker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `service_provider_id` bigint(50) NOT NULL COMMENT '服务商ID',
  `role_id` bigint(50) DEFAULT NULL COMMENT '角色ID',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `service_provider_worker_state` varchar(50) NOT NULL COMMENT '服务商员工账户状态',
  `worker_name` varchar(50) NOT NULL COMMENT '姓名',
  `worker_sex` varchar(50) NOT NULL DEFAULT '' COMMENT '性别',
  `position_name` varchar(50) NOT NULL COMMENT '岗位性质',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `up_level_id` bigint(50) DEFAULT NULL COMMENT '上级主管',
  `employee_user_name` varchar(50) NOT NULL COMMENT '用户名',
  `employee_pwd` varchar(100) NOT NULL COMMENT '密码',
  `super_admin` bit(1) NOT NULL COMMENT '管理员权限',
  `admin_power` bit(1) NOT NULL COMMENT '管理员特性',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`employee_user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商员工表';

-- ----------------------------
-- Records of diyi_service_provider_worker
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_worksheet`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_worksheet`;
CREATE TABLE `diyi_worksheet` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `enterprise_id` bigint(50) NOT NULL COMMENT '商户ID',
  `worksheet_no` varchar(50) NOT NULL COMMENT '工单流水号',
  `worksheet_name` varchar(50) NOT NULL COMMENT '工单名称',
  `up_person_num` int(5) NOT NULL COMMENT '上限人数',
  `work_days` int(5) NOT NULL COMMENT '工作天数',
  `worksheet_fee_low` decimal(12,2) NOT NULL COMMENT '最低费用',
  `worksheet_fee_high` decimal(12,2) NOT NULL COMMENT '最高费用',
  `worksheet_type` varchar(50) NOT NULL COMMENT '工单类型',
  `worksheet_mode` varchar(50) NOT NULL COMMENT '工单模式',
  `maker_type` varchar(50) NOT NULL COMMENT '创客身份',
  `worksheet_state` varchar(50) NOT NULL COMMENT '工单状态',
  `destroy_datetime` datetime DEFAULT NULL COMMENT '作废时间',
  `destroy_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '作废说明',
  `close_worksheet_date` datetime DEFAULT NULL COMMENT '关单时间',
  `close_worksheet_type` varchar(50) DEFAULT NULL COMMENT '手动关单;自动关单',
  `finish_date` datetime DEFAULT NULL COMMENT '完毕日期',
  `worksheet_memo` varchar(100) NOT NULL DEFAULT '' COMMENT '工单说明',
  `worksheet_desc_files` varchar(500) NOT NULL DEFAULT '' COMMENT '工单说明图文',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`worksheet_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单表';

-- ----------------------------
-- Records of diyi_worksheet
-- ----------------------------

-- ----------------------------
-- Table structure for `diyi_worksheet_maker`
-- ----------------------------
DROP TABLE IF EXISTS `diyi_worksheet_maker`;
CREATE TABLE `diyi_worksheet_maker` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `worksheet_id` bigint(50) NOT NULL COMMENT '工单ID',
  `get_type` varchar(50) NOT NULL COMMENT '获得方式',
  `worksheet_maker_state` varchar(50) NOT NULL COMMENT '工单创客的状态',
  `achievement_desc` varchar(1000) NOT NULL DEFAULT '' COMMENT '工作成果说明',
  `achievement_files` varchar(1000) NOT NULL DEFAULT '' COMMENT '工作成果附件',
  `achievement_date` datetime DEFAULT NULL COMMENT '提交工作成果日期',
  `check_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '验收金额',
  `check_date` datetime DEFAULT NULL COMMENT '验收时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `bool_deleted` bit(1) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`maker_id`,`worksheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单-创客关联表';

-- ----------------------------
-- Records of diyi_worksheet_maker
-- ----------------------------

ALTER TABLE `diyi_online_sign_pic`
ADD COLUMN `audit_state` varchar(50) NOT NULL COMMENT '审核状态' AFTER `sign_pic`,
ADD COLUMN `rejected_explanation` varchar(100) NOT NULL DEFAULT '' COMMENT '驳回说明' AFTER `audit_state`;

ALTER TABLE `diyi_maker`
ADD COLUMN `authorization_audit` varchar(50) NOT NULL COMMENT '一建授权状态' AFTER `video_audit_person_id`,
ADD COLUMN `authorization_audit_date` datetime DEFAULT NULL COMMENT '授权审核日期' AFTER `authorization_audit`,
ADD COLUMN `authorization_audit_person_id` bigint(50) DEFAULT NULL COMMENT '授权审核人员' AFTER `authorization_audit_date`;


ALTER TABLE `diyi_online_agreement_template`
ADD COLUMN `object_type` varchar(50) DEFAULT NULL COMMENT '对象身份' AFTER `id`,
ADD COLUMN `object_id` bigint(50) DEFAULT NULL COMMENT '对象ID' AFTER `object_type`;
