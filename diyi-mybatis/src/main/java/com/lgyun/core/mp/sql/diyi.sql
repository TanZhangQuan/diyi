/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : diyi

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-06-24 16:16:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for diyi_agreement
-- ----------------------------
DROP TABLE IF EXISTS `diyi_agreement`;
CREATE TABLE `diyi_agreement` (
  `agreement_id` bigint(50) NOT NULL COMMENT '平台合同的信息ID',
  `sign_type` varchar(50) NOT NULL COMMENT '签署类型：纸质协议，平台在线协议，三方在线协议-法大大',
  `sign_date` datetime NOT NULL COMMENT '签署日期',
  `sign_state` varchar(50) NOT NULL COMMENT '签署状态：签署中，已完毕',
  `agreement_type` varchar(50) NOT NULL COMMENT '协议类型：互联网众包项目合作协议，众包-三方合作协议，众包-三方服务订单，总包-双方合作协议，总包-双方服务订单，分包-三方合作协议，分包-三方服务订单，分包-双方合作协议，分包-双方服务订单，单独税务事项委托授权书，单独支付事项委托授权书',
  `agreement_no` varchar(50) NOT NULL COMMENT '协议编号',
  `run_company_id` bigint(50) NOT NULL COMMENT '发包方ID',
  `platform_id` bigint(50) NOT NULL COMMENT '平台方ID',
  `maker_id` bigint(50) NOT NULL COMMENT '分包方/承包方ID',
  `upload_person` varchar(50) NOT NULL COMMENT '上传人员',
  `upload_datetime` datetime NOT NULL COMMENT '上传日期',
  `start_datetime` datetime DEFAULT NULL COMMENT '在线协议发起时间',
  `paper_agreement_url` varchar(100) DEFAULT '' COMMENT '纸质协议URL',
  `third_online_agreement_url` varchar(100) DEFAULT '' COMMENT '三方在线协议URL',
  `platform_online_agreement_url` varchar(100) DEFAULT '' COMMENT '平台在线协议文本',
  `poaplatform_signature_url` varchar(100) DEFAULT '' COMMENT '平台在线协议平台方签字',
  `poamaker_signature_url` varchar(100) DEFAULT '' COMMENT '平台在线协议分包方签字',
  `poacompany_signature_url` varchar(100) DEFAULT '' COMMENT '平台在线协议发包方签字',
  `poaplatform_sign_datetime` datetime DEFAULT NULL COMMENT '平台在线协议平台方操作日期',
  `poamaker_sign_datetime` datetime DEFAULT NULL COMMENT '平台在线协议分包方操作日期',
  `poacompany_sign_datetime` datetime DEFAULT NULL COMMENT '平台在线协议发包方操作日期',
  `poawith_signature_url` varchar(100) DEFAULT '' COMMENT '平台在线协议文本集合签字',
  `paa_state` varchar(50) DEFAULT NULL COMMENT '纸质协议上传状态：未上传, 已上传',
  `poa_state` varchar(50) DEFAULT NULL COMMENT '平台在线协议状态：签署中，已签署完毕',
  `toa_state` varchar(50) DEFAULT NULL COMMENT '三方在线协议状态：签署中，已签署完毕',
  `platform_sign_state` varchar(50) DEFAULT NULL COMMENT '平台签署状态：签署中，已签署，无需签署（没参与）',
  `maker_sign_state` varchar(50) DEFAULT NULL COMMENT '创客签署状态：签署中，已签署，无需签署（没参与）',
  `company_sign_state` varchar(50) DEFAULT NULL COMMENT '发包方签署状态：签署中，已签署，无需签署（没参与）',
  `create_user` bigint (50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint (50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`agreement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_agreement
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_charge_detail
-- ----------------------------
DROP TABLE IF EXISTS `diyi_charge_detail`;
CREATE TABLE `diyi_charge_detail` (
  `charge_detail_id` bigint(50) NOT NULL COMMENT '外包企业充值明细信息ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `run_company_id` bigint(50) NOT NULL COMMENT '运营公司ID',
  `charge_money_num` decimal(12,2) NOT NULL COMMENT '充值金额',
  `charge_date_time` datetime NOT NULL COMMENT '充值日期',
  `charge_money_platform_person` varchar(50) NOT NULL COMMENT '充值人员',
  `charge_state` varchar(50) NOT NULL COMMENT '充值状态：已充值，已打款',
  `bank_receipt_url` varchar(100) NOT NULL DEFAULT '' COMMENT '银行回单URL',
  `bank_receipt_upload_date` datetime DEFAULT NULL COMMENT '银行回单上传日期',
  `charge_money_en_person` varchar(50) NOT NULL DEFAULT '' COMMENT '打款人员',
  `create_user` bigint (50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint (50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`charge_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_charge_detail
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_deliver_material
-- ----------------------------
DROP TABLE IF EXISTS `diyi_deliver_material`;
CREATE TABLE `diyi_deliver_material` (
  `deliver_material_id` bigint(50) NOT NULL COMMENT '创客交付材料信息信息ID',
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
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`deliver_material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_deliver_material
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_employee
-- ----------------------------
DROP TABLE IF EXISTS `diyi_employee`;
CREATE TABLE `diyi_employee` (
  `employee_id` bigint(50) NOT NULL COMMENT '平台工作人员的基本信息（销售、运营和管理人员等）ID',
  `employee_name` varchar(50) NOT NULL COMMENT '姓名',
  `gender` varchar(50) NOT NULL COMMENT '性别：男，女',
  `position_name` varchar(50) NOT NULL COMMENT '岗位性质：营销人员，客服人员，运营人员，管理人员，其他',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `up_level_id` bigint(50) NOT NULL COMMENT '上级主管',
  `employee_user_name` varchar(50) NOT NULL COMMENT '用户名: 手机号码和用户名皆可',
  `employee_pwd` varchar(50) NOT NULL COMMENT '密码',
  `admin_power` bit(1) NOT NULL COMMENT '是否管理员',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_employee
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise`;
CREATE TABLE `diyi_enterprise` (
  `enterprise_id` bigint(50) NOT NULL COMMENT '外包企业（发包方）的基本信息ID',
  `en_user_name` varchar(50) NOT NULL COMMENT '用户名',
  `en_user_pwd` varchar(50) NOT NULL COMMENT '密码',
  `invite_no` varchar(50) NOT NULL COMMENT '创客加入邀请码',
  `enterprise_name` varchar(50) NOT NULL COMMENT '客户名称',
  `legal_person` varchar(50) NOT NULL COMMENT '法人代表名称',
  `legal_person_card` varchar(50) NOT NULL COMMENT '法人身份证',
  `social_credit_no` varchar(100) NOT NULL COMMENT '统一社会信用代码',
  `biz_licence_url` varchar(200) NOT NULL COMMENT '营业执照图片地址',
  `enterprise_url` varchar(100) NOT NULL COMMENT '企业网址',
  `working_address` varchar(100) NOT NULL COMMENT '办公地址(快递地址）',
  `working_rel_name` varchar(50) NOT NULL COMMENT '收发票/税票快递【到付】联系人姓名',
  `working_rel_phone` varchar(50) NOT NULL COMMENT '收发票/税票快递【到付】联系人手机号',
  `invoice_en_name` varchar(50) NOT NULL COMMENT '开票资料-公司名称',
  `invoice_tax_no` varchar(50) NOT NULL COMMENT '开票资料-税号',
  `invoice_address` varchar(100) NOT NULL COMMENT '开票资料-地址',
  `invoice_tel_no` varchar(50) NOT NULL COMMENT '开票资料-电话',
  `invoice_bank_name` varchar(50) NOT NULL COMMENT '开票资料-开户银行',
  `invoice_account_name` varchar(50) NOT NULL COMMENT '开票资料-账户名',
  `invoice_account` varchar(50) NOT NULL COMMENT '开票资料-账号',
  `en_business_pattern` varchar(50) NOT NULL COMMENT '业务外包模式：自然人众包（3%普票），自然人总包+分包（6%专票），个体户众包（3%专票），个体户总包+分包（6%专票），个体户众包（3%普票）',
  `crowd_source_pay_path` varchar(50) NOT NULL COMMENT '众包支付通路：通联支付代发，招商银行代发，系统集成代发，平台代收代付，平台预存支付',
  `service_price` decimal(5,2) NOT NULL COMMENT '综合税费率',
  `contact1_name` varchar(50) NOT NULL COMMENT '联系人1姓名（一般为老板/财务负责人）',
  `contact1_position` varchar(50) NOT NULL COMMENT '联系人1职位',
  `contact1_phone` varchar(50) NOT NULL COMMENT '联系人1电话手机（必填）',
  `contact1_mail` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人1邮箱',
  `contact2_name` varchar(50) NOT NULL COMMENT '联系人2姓名',
  `contact2_position` varchar(50) NOT NULL COMMENT '联系人2职位',
  `contact2_phone` varchar(50) NOT NULL COMMENT '联系人2电话手机（必填）',
  `contact2_mail` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人2邮箱',
  `spec_demmand` varchar(500) NOT NULL DEFAULT '' COMMENT '特殊需求',
  `en_create_type` varchar(50) NOT NULL COMMENT '创建类型：平台创建，自注册',
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
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`enterprise_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`en_user_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`invite_no`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k3` (`enterprise_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_individual_business
-- ----------------------------
DROP TABLE IF EXISTS `diyi_individual_business`;
CREATE TABLE `diyi_individual_business` (
  `individual_business_id` bigint(50) NOT NULL COMMENT '个体工商户信息ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `ibname` varchar(50) NOT NULL COMMENT '个体户名称',
  `ibtax_no` varchar(50) NOT NULL COMMENT '统一社会信用代码',
  `build_datetie` datetime NOT NULL COMMENT '营业执照的注册日期',
  `business_address` varchar(100) NOT NULL COMMENT '经营场所',
  `net_business_address` varchar(100) NOT NULL COMMENT '网络经营场所',
  `business_licence_url` varchar(100) NOT NULL COMMENT '营业执照图片地址',
  `ibstate` varchar(50) NOT NULL COMMENT '个体工商户状态：注册中，税务登记中，运营中，已注销',
  `submit_date_time` datetime NOT NULL COMMENT '提交日期',
  `tax_register_date_time` datetime DEFAULT NULL COMMENT '税务登记日期',
  `logout_date_time` datetime DEFAULT NULL COMMENT '注销日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`individual_business_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`ibname`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`ibtax_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_individual_business
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_individual_business_annual_fee
-- ----------------------------
DROP TABLE IF EXISTS `diyi_individual_business_annual_fee`;
CREATE TABLE `diyi_individual_business_annual_fee` (
  `individual_business_annual_fee_id` bigint(50) NOT NULL COMMENT '个体工商户年费信息ID',
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
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`individual_business_annual_fee_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`individual_business_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_individual_business_annual_fee
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_maker
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker`;
CREATE TABLE `diyi_maker` (
  `maker_id` bigint(50) NOT NULL COMMENT '创客（分包方）的基本信息ID',
  `wechat_id` varchar(50) NOT NULL DEFAULT '' COMMENT '微信关联ID',
  `openid` varchar(50) NOT NULL DEFAULT '' COMMENT '微信open_id',
  `session_key` varchar(50) NOT NULL DEFAULT '' COMMENT '微信session_key',
  `wechat_nickname` varchar(50) NOT NULL DEFAULT '' COMMENT '微信昵称',
  `rel_date` datetime DEFAULT NULL COMMENT '微信关联日期',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `politic_state` varchar(50) NOT NULL DEFAULT '' COMMENT '政治面貌',
  `nationality` varchar(50) NOT NULL DEFAULT '' COMMENT '民族',
  `levelofedu` varchar(50) NOT NULL DEFAULT '' COMMENT '文化程度',
  `email_address` varchar(100) NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `idcard_no` varchar(50) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `due_date` datetime DEFAULT NULL COMMENT '到期日期',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `phone_number2` varchar(50) NOT NULL DEFAULT '' COMMENT '手机号码2',
  `login_pwd` varchar(100) NOT NULL DEFAULT '' COMMENT '登录密码',
  `bank_card_no` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡号',
  `bank_name` varchar(100) NOT NULL DEFAULT '' COMMENT '开户银行',
  `sub_bank_name` varchar(100) NOT NULL DEFAULT '' COMMENT '开户支行',
  `idcard_pic` varchar(100) NOT NULL DEFAULT '' COMMENT '身份证正面图',
  `idcard_pic_back` varchar(100) NOT NULL DEFAULT '' COMMENT '身份证反面图',
  `self_pic` varchar(100) NOT NULL DEFAULT '' COMMENT '正面自拍照',
  `idcar_copy` varchar(100) NOT NULL DEFAULT '' COMMENT '身份证复印件图',
  `idcard_hand` varchar(100) NOT NULL DEFAULT '' COMMENT '手持证件照',
  `idcard_verify_status` varchar(50) NOT NULL COMMENT '身份证验证状态：未验证，验证通过，验证未通过',
  `idcard_verify_date` datetime DEFAULT NULL COMMENT '身份证验证日期时间',
  `face_verify_status` varchar(50) NOT NULL COMMENT '刷脸验证状态：未验证，验证通过，验证未通过',
  `face_verify_date` datetime DEFAULT NULL COMMENT '刷脸验证日期时间',
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
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`maker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_maker
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_maker_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker_enterprise`;
CREATE TABLE `diyi_maker_enterprise` (
  `maker_enterprise_id` bigint(50) NOT NULL COMMENT '创客和外包企业的关联关系ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `position_id` bigint(50) NOT NULL COMMENT '外包岗位ID',
  `rel_date` datetime NOT NULL COMMENT '关联日期',
  `rel_type` varchar(50) DEFAULT NULL COMMENT '关联类型：创客主动关联，企业主动关联，平台关联',
   `relationship_type` int(1) DEFAULT '0' COMMENT '0，关联，1是关注',
  `rel_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '关联备注',
  `cooperate_status` varchar(50) NOT NULL COMMENT '合作状态：合作中，停止合作；首次关联时默认为合作中',
  `first_cooperation` bit(1) NOT NULL COMMENT '创客第一次合作',
  `cooperation_start_time` datetime NOT NULL COMMENT '合作开始日期',
  `cooperation_end_time` datetime DEFAULT NULL COMMENT '合作终止日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`maker_enterprise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_maker_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_maker_tax_record
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker_tax_record`;
CREATE TABLE `diyi_maker_tax_record` (
  `maker_tax_record_id` bigint(50) NOT NULL COMMENT '创客开具的完税证明ID',
  `pay_id` bigint(50) NOT NULL COMMENT '创客支付ID',
  `order_id` bigint(50) NOT NULL COMMENT '订单ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `maker_type` varchar(50) NOT NULL COMMENT '创客类别：自然人，个体户',
  `individual_business_name` varchar(50) NOT NULL DEFAULT '' COMMENT '个体户名称',
  `voice_type_no` varchar(50) NOT NULL COMMENT '票证号码',
  `voice_serial_name` varchar(50) NOT NULL COMMENT '金库名称',
  `maker_tax_amount` decimal(12,2) NOT NULL COMMENT '总税金',
  `fill_tax_name` varchar(50) NOT NULL COMMENT '填票人',
  `fill_tax_date` datetime DEFAULT NULL COMMENT '填发日期',
  `taxpayer_name` varchar(50) NOT NULL COMMENT '纳税人名称',
  `taxpayer _no` varchar(50) NOT NULL COMMENT '纳税人识别号',
  `sale_company` varchar(50) NOT NULL COMMENT '税务机关',
  `help_make_organation_name` varchar(50) NOT NULL COMMENT '证明类型',
  `maker_tax_url` varchar(100) NOT NULL COMMENT '完税证明URL',
  `maker_tax_get_date_time` datetime NOT NULL COMMENT '完税证明开具日期',
  `maker_tax_upload_date_time` datetime NOT NULL COMMENT '完税证明上传日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`maker_tax_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_maker_tax_record
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_maker_voice
-- ----------------------------
DROP TABLE IF EXISTS `diyi_maker_voice`;
CREATE TABLE `diyi_maker_voice` (
  `maker_voice_id` bigint(50) NOT NULL COMMENT '创客开具的发票ID',
  `pay_id` bigint(50) NOT NULL COMMENT '创客支付ID',
  `order_id` bigint(50) NOT NULL COMMENT '订单ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `maker_type` varchar(50) NOT NULL COMMENT '创客类别：自然人，个体户',
  `individual_business_name` varchar(50) NOT NULL DEFAULT '' COMMENT '个体户名称',
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
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`maker_voice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_maker_voice
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_order
-- ----------------------------
DROP TABLE IF EXISTS `diyi_order`;
CREATE TABLE `diyi_order` (
  `order_id` bigint(50) NOT NULL COMMENT '订单ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `run_company_id` bigint(50) NOT NULL COMMENT '运营公司ID',
  `order_title` varchar(50) NOT NULL COMMENT '订单主题',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `worksheet_num` int(10) NOT NULL COMMENT '工单数量',
  `order_pattern` varchar(50) NOT NULL COMMENT '发单模式：公开抢单，不公开抢单',
  `order_business_pattern` varchar(50) NOT NULL COMMENT '业务模式：个体户-总包+分包，自然人-总包+分包，个体户-众包，自然人-众包',
  `order_pay_type` varchar(50) NOT NULL COMMENT '支付方式：总包+分包标准支付，总包+分包自助支付，众包企业自行支付，众包平台代收代付，总包+分包通联支付，众包通联支付',
  `total_tax_rate` decimal(5,2) NOT NULL COMMENT '综合税费率',
  `order_state` varchar(50) NOT NULL COMMENT '订单状态：抢单中，已关单，支付中，已支付',
  `order_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '订单说明',
  `manange_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '管理备注',
  `mass_agreement_no` varchar(50) NOT NULL COMMENT '众包合同编号',
  `order_agreement_no` varchar(50) NOT NULL COMMENT '服务订单编号',
  `platform_voice_category` varchar(50) NOT NULL COMMENT '平台发票类目',
  `maker_voice_category` varchar(50) NOT NULL COMMENT '创客发票默认类目',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_order
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_pay
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay`;
CREATE TABLE `diyi_pay` (
  `pay_id` bigint(50) NOT NULL COMMENT '平台支付信息ID',
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
  `maker_voice_state` varchar(1) NOT NULL COMMENT '创客发票开票状态: 未开，已开',
  `pay_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '支付说明',
  `maker_voice_category` varchar(50) NOT NULL COMMENT '创客发票类目',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_pay
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_paysheet
-- ----------------------------
DROP TABLE IF EXISTS `diyi_paysheet`;
CREATE TABLE `diyi_paysheet` (
  `paysheet_id` bigint(50) NOT NULL COMMENT '确认函ID',
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
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`paysheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_paysheet
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_enterprise_pay
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_pay`;
CREATE TABLE `diyi_enterprise_pay` (
  `enterprise_pay_id` bigint(50) NOT NULL COMMENT '外包企业支付给平台的信息ID',
  `order_id` bigint(50) NOT NULL COMMENT '订单ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `pay_to_platform_amount` decimal(12,2) NOT NULL COMMENT '企业支付给平台金额,根据支付方式计算出企业支付给平台金额',
  `pay_to_platform_man_fee` decimal(12,2) NOT NULL COMMENT '企业支付管理服务费,根据支付方式计算出企业支付给管理服务费',
  `pay_to_platform_maker_tax` decimal(12,2) NOT NULL COMMENT '众包企业代付创客税费,根据支付方式计算出',
  `pay_to_maker_net_fee` decimal(12,2) NOT NULL COMMENT '众包企业直接支付给创客服务费,根据支付方式计算出',
  `pay_memo` varchar(500) NOT NULL DEFAULT '' COMMENT '支付说明',
  `en_pay_state` varchar(50) NOT NULL COMMENT '支付给平台状态：待支付，已支付，已确认收款',
  `pay_confirm_date_time` datetime DEFAULT NULL COMMENT '支付确认日期时间',
  `confirm_date_time` datetime DEFAULT NULL COMMENT '确认回款日期时间',
  `employee_id` bigint(50) NOT NULL COMMENT '确认到款人员ID',
  `company_invoice_state` varchar(50) NOT NULL COMMENT '平台给企业开票状态：未开，已开',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`enterprise_pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_enterprise_pay
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_enterprise_pay_receipt
-- ----------------------------
DROP TABLE IF EXISTS `diyi_enterprise_pay_receipt`;
CREATE TABLE `diyi_enterprise_pay_receipt` (
  `enterprise_pay_receipt_id` bigint(50) NOT NULL COMMENT '外包企业支付给平台的信息ID',
  `enterprise_pay_id` bigint(50) NOT NULL COMMENT '支付ID',
  `enterprise_pay_receipt_url` varchar(100) NOT NULL COMMENT '支付回单图片URL地址',
  `upload_date_time` datetime NOT NULL COMMENT '上传日期时间',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`enterprise_pay_receipt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_enterprise_pay_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_pay_receipt
-- ----------------------------
DROP TABLE IF EXISTS `diyi_pay_receipt`;
CREATE TABLE `diyi_pay_receipt` (
  `pay_receipt_id` bigint(50) NOT NULL COMMENT '支付回单ID',
  `pay_id` bigint(50) NOT NULL COMMENT '创客支付ID',
  `maker_pay_receipt_url` varchar(100) NOT NULL COMMENT '支付回单',
  `upload_date_time` datetime NOT NULL COMMENT '上传日期时间',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`pay_receipt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_pay_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_platform_voice
-- ----------------------------
DROP TABLE IF EXISTS `diyi_platform_voice`;
CREATE TABLE `diyi_platform_voice` (
  `platform_voice_id` bigint(50) NOT NULL COMMENT '平台开具给发包方的发票ID',
  `enterprise_pay_id` bigint(50) NOT NULL COMMENT '支付ID',
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
  `company_voice_upload_date_time` datetime COMMENT '发票上传日期',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`platform_voice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_platform_voice
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_position
-- ----------------------------
DROP TABLE IF EXISTS `diyi_position`;
CREATE TABLE `diyi_position` (
  `position_id` bigint(50) NOT NULL COMMENT '外包岗位的基本信息ID',
  `position_name` varchar(50) NOT NULL COMMENT '外包岗位名称',
  `position_desc` varchar(500) NOT NULL COMMENT '外包岗位描述',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`position_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`position_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_position
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_run_company
-- ----------------------------
DROP TABLE IF EXISTS `diyi_run_company`;
CREATE TABLE `diyi_run_company` (
  `run_company_id` bigint(50) NOT NULL COMMENT '平台运营公司（平台方）信息ID',
  `company_name` varchar(50) NOT NULL COMMENT '公司名称',
  `employee_name` varchar(100) NOT NULL COMMENT '公司地址',
  `tax_no` varchar(50) NOT NULL COMMENT '纳税人识别号',
  `bank_name` varchar(50) NOT NULL COMMENT '银行账户名',
  `bank_account` varchar(50) NOT NULL COMMENT '银行账户',
  `contacter_name` varchar(50) NOT NULL COMMENT '联系人',
  `phone_no` varchar(50) NOT NULL COMMENT '联系电话',
  `email_address` varchar(50) NOT NULL COMMENT '联系邮箱',
  `set_date` datetime NOT NULL COMMENT '配置日期',
  `memo_info` varchar(500) NOT NULL COMMENT '备注',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`run_company_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`company_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`tax_no`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k3` (`bank_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_run_company
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_setup
-- ----------------------------
DROP TABLE IF EXISTS `diyi_setup`;
CREATE TABLE `diyi_setup` (
  `setup_id` bigint(50) NOT NULL COMMENT '特殊配置ID',
  `allow_first_edit` bit(1) NOT NULL COMMENT '允许第一次关联企业更新创客信息',
  `allow_all_edit` bit(1) NOT NULL COMMENT '允许所有关联企业更新创客信息',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`setup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_setup
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_worksheet
-- ----------------------------
DROP TABLE IF EXISTS `diyi_worksheet`;
CREATE TABLE `diyi_worksheet` (
  `worksheet_id` bigint(50) NOT NULL COMMENT '平台运营工单信息ID',
  `order_id` bigint(50) NOT NULL COMMENT '订单ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `worksheet_no` varchar(100) NOT NULL COMMENT '工单编号',
  `get_order_date` datetime NOT NULL COMMENT '抢单日期',
  `get_order_desc` varchar(500) NOT NULL COMMENT '抢单说明',
  `worksheet_state` varchar(50) NOT NULL COMMENT '工单状态：正常，已作废',
  `destroy_datetime` datetime DEFAULT NULL COMMENT '作废日期',
  `destroy_person` varchar(100) DEFAULT NULL COMMENT '作废人员',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`worksheet_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`worksheet_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_worksheet
-- ----------------------------

-- ----------------------------
-- Table structure for diyi_worksheet_attention
-- ----------------------------
DROP TABLE IF EXISTS `diyi_worksheet_attention`;
CREATE TABLE `diyi_worksheet_attention` (
  `worksheet_attention_id` bigint(50) NOT NULL COMMENT '工单关注ID',
  `maker_id` bigint(50) NOT NULL COMMENT '创客ID',
  `enterprise_id` bigint(50) NOT NULL COMMENT '企业ID',
  `worksheet_attention_no` bigint(50) NOT NULL COMMENT '工单关注编号',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint (1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint (1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  PRIMARY KEY (`worksheet_attention_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`worksheet_attention_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of diyi_worksheet_attention
-- ----------------------------
--
ALTER TABLE `diyi_maker`
ADD COLUMN `makerType`  varchar(50) NOT NULL COMMENT '创客类别：自然人，个体户' AFTER `is_deleted`


--工作成果表
CREATE TABLE `diyi_work_achievement` (
  `work_achievement_id` bigint(50) NOT NULL COMMENT '工作成果Id',
  `work_explain` varchar(200) NOT NULL COMMENT '工作成果说明',
  `work_url` varchar(2000) NOT NULL COMMENT '工作结果url',
  `check_money_num` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '验收金额',
  `work_achievement_state` varchar(50) NOT NULL DEFAULT 'beAccepted' COMMENT '工作成果状态 1：待验收，2验收通过，3验收不通过',
  `create_user` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` bigint(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '状态[1:正常]',
  `is_deleted` tinyint(1) NOT NULL COMMENT '状态[0:未删除,1:删除]',
  `worksheet_id` bigint(50) NOT NULL COMMENT '工单id',
  PRIMARY KEY (`work_achievement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
