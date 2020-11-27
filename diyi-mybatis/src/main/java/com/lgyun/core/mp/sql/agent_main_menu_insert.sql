###商户管理
INSERT INTO `sys_menu` VALUES ('1123598815738675801',                 '0', 'ShopIndex', '商户管理', 'menu', '/shop/index',              'shop', '1', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');

###服务商管理
INSERT INTO `sys_menu` VALUES ('1123598815738675810',                 '0', 'ServiceIndex', '服务商管理', 'menu', '/service/index',              'service', '2', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');

###渠道商信息维护
INSERT INTO `sys_menu` VALUES ('1123598815738675820',                   '0', 'Base',     '渠道商信息维护', 'menu', '/base',      'worker', '3', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675821',                   '1123598815738675820', 'BaseIndex',     '基本信息', 'menu', '/base/index',      '', '1', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675822',                   '1123598815738675820', 'BasePerson',     '联系人信息', 'menu', '/base/person',      '', '2', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675822',                   '1123598815738675820', 'BaseTax',     '开票信息', 'menu', '/base/tax',      '', '2', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675822',                   '1123598815738675820', 'AddressIndex',     '快递信息', 'menu', '/address/index',      '', '2', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');

###权限管理
INSERT INTO `sys_menu` VALUES ('1123598815738675880',                 '0', 'Auth', '权限管理', 'menu', '/auth', 'auth', '9', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675881',                 '1123598815738675880', 'AuthIndex', '账号管理', 'menu', '/auth/index', '', '1', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675882',                 '1123598815738675880', 'AuthRole', '角色管理', 'menu', '/auth/role', '', '2', '1', '0', '1', '', 'AGENTMAIN', null, NOW(), null, NOW(), '1', '0');