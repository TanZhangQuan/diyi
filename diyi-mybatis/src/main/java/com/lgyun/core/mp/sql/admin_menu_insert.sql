###商户管理1
INSERT INTO `sys_menu` VALUES ('1123598815738675301',                 '0', 'ShopIndex', '商户管理', 'menu', '/shop/index',              'shop', '1', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');

###服务商管理2
INSERT INTO `sys_menu` VALUES ('1123598815738675310',                 '0', 'ServiceIndex', '服务商管理', 'menu', '/service/index',              'service', '2', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');

###自然人管理3
INSERT INTO `sys_menu` VALUES ('1123598815738675320',                   '0', 'Worker',     '自然人管理', 'menu', '/worker',      'worker', '3', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675321',                   '1123598815738675320', 'WorkerIndex',     '认证创客', 'menu', '/worker/index',      '', '1', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675322',                   '1123598815738675320', 'WorkerUncertified',     '待认证创客', 'menu', '/worker/uncertified',      '', '2', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');

###合同管理4
INSERT INTO `sys_menu` VALUES ('1123598815738675330',                   '0', 'Contract',     '合同管理', 'menu', '/contract',      'contract', '4', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675331',                   '1123598815738675330', 'ContractWorkerIndex',     '自然人创客', 'menu', '/contract/worker/index',      '', '1', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675332',                   '1123598815738675330', 'ContractShopIndex',     '商户', 'menu', '/contract/shop/index',      '', '2', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675333',                   '1123598815738675330', 'ContractServiceIndex',     '服务商', 'menu', '/contract/service/index',      '', '3', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');

###支付管理5
INSERT INTO `sys_menu` VALUES ('1123598815738675340',                   '0', 'Pay',     '支付管理',           'menu', '/pay',   'zhifu', '5', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675341', '1123598815738675340', 'PayShop', '商户支付管理', 'menu', '/pay/shop',  '', '1', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675342', '1123598815738675340', 'PayService',   '服务商支付管理', 'menu', '/pay/service',    '', '2', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');

###发票/税票管理6
INSERT INTO `sys_menu` VALUES ('1123598815738675350',                   '0', 'Tax',      '发票/税票管理',        'menu', '/tax',   'fapiao', '6', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675351', '1123598815738675350', 'TaxTotalIndex', '【总包+分包】总包发票', 'menu', '/tax/total/index',  '', '1', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675352', '1123598815738675350', 'TaxSubIndex',   '【总包+分包】分包发票', 'menu', '/tax/sub/index',    '', '2', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');

###个体户管理7
INSERT INTO `sys_menu` VALUES ('1123598815738675360',                   '0', 'Personal',        '个体户管理', 'menu', '/personalBusiness/Index',           'geti', '7', '1', '0', '1', '','ADMIN',  null, NOW(), null, NOW(), '1', '0');

###个独管理8
INSERT INTO `sys_menu` VALUES ('1123598815738675370',                   '0', 'Individual',          '个独管理', 'menu', '/individualBusiness/index',           'gedu', '8', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');

###权限管理9
INSERT INTO `sys_menu` VALUES ('1123598815738675380',                 '0', 'Auth', '权限管理', 'menu', '/auth', 'auth', '9', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675381',                 '1123598815738675380', 'AuthIndex', '账号管理', 'menu', '/auth/index', '', '1', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675382',                 '1123598815738675380', 'AuthRole', '角色管理', 'menu', '/auth/role', '', '2', '1', '0', '1', '', 'ADMIN', null, NOW(), null, NOW(), '1', '0');


















