###商户管理1
INSERT INTO `diyi_menu` VALUES ('1123598815738675301', NULL, '商户管理', '/shop/index', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);

###服务商管理2
INSERT INTO `diyi_menu` VALUES ('1123598815738675310', NULL, '服务商管理', '/service/index', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);

###自然人管理3
INSERT INTO `diyi_menu` VALUES ('1123598815738675320', NULL, '自然人管理', '/worker', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675321', '1123598815738675320', '认证创客', '/worker/index', 'MENU', 'ADMIN', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675322', '1123598815738675320', '待认证创客', '/worker/uncertified', 'MENU', 'ADMIN', '2', '', NOW(), NOW(), FALSE);

###合同管理4
INSERT INTO `diyi_menu` VALUES ('1123598815738675330', NULL, '合同管理', '/contract', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675331', '1123598815738675330', '自然人创客', '/contract/worker/index', 'MENU', 'ADMIN', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675332', '1123598815738675330', '商户', '/contract/shop/index', 'MENU', 'ADMIN', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675333', '1123598815738675330', '服务商', '/contract/service/index', 'MENU', 'ADMIN', '3', '', NOW(), NOW(), FALSE);

###支付管理5
INSERT INTO `diyi_menu` VALUES ('1123598815738675340', NULL, '支付管理', '/pay', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675341', '1123598815738675340', '商户支付管理', '/pay/shop', 'MENU', 'ADMIN', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675342', '1123598815738675340', '服务商支付管理', '/pay/service', 'MENU', 'ADMIN', '2', '', NOW(), NOW(), FALSE);

###发票/税票管理6
INSERT INTO `diyi_menu` VALUES ('1123598815738675350', NULL, '发票/税票管理', '/tax', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675351', '1123598815738675350', '【总包+分包】总包发票', '/tax/total/index', 'MENU', 'ADMIN', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675352', '1123598815738675350', '【总包+分包】分包发票', '/tax/sub/index', 'MENU', 'ADMIN', '2', '', NOW(), NOW(), FALSE);

###个体户管理7
INSERT INTO `diyi_menu` VALUES ('1123598815738675360', NULL, '个体户管理', '/personalBusiness/Index', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);

###个独管理8
INSERT INTO `diyi_menu` VALUES ('1123598815738675370', NULL, '个独管理', '/individualBusiness/index', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);

###权限管理9
INSERT INTO `diyi_menu` VALUES ('1123598815738675380', NULL, '权限管理', '/auth', 'MENU', 'ADMIN', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675382', '1123598815738675380', '角色管理', '/auth/role', 'MENU', 'ADMIN', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675381', '1123598815738675380', '账号管理', '/auth/index', 'MENU', 'ADMIN', '1', '', NOW(), NOW(), FALSE);