###商户管理
INSERT INTO `diyi_menu` VALUES ('1123598815738675801', NULL, '商户管理', '/shop/index', 'MENU', 'AGENTMAIN', '0', '', NOW(), NOW(), FALSE);

###服务商管理
INSERT INTO `diyi_menu` VALUES ('1123598815738675810', NULL, '服务商管理', '/service/index', 'MENU', 'AGENTMAIN', '0', '', NOW(), NOW(), FALSE);

###渠道商信息维护
INSERT INTO `diyi_menu` VALUES ('1123598815738675820', NULL, '渠道商信息维护', '/base', 'MENU', 'AGENTMAIN', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675821', '1123598815738675820', '基本信息', '/base/index', 'MENU', 'AGENTMAIN', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675822', '1123598815738675820', '联系人信息', '/base/person', 'MENU', 'AGENTMAIN', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675823', '1123598815738675820', '开票信息', '/base/tax', 'MENU', 'AGENTMAIN', '3', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675824', '1123598815738675820', '快递信息', '/address/index', 'MENU', 'AGENTMAIN', '4', '', NOW(), NOW(), FALSE);

###权限管理
INSERT INTO `diyi_menu` VALUES ('1123598815738675880', NULL, '权限管理', '/auth', 'MENU', 'AGENTMAIN', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675881', '1123598815738675880', '账号管理', '/auth/index', 'MENU', 'AGENTMAIN', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675882', '1123598815738675880', '角色管理', '/auth/role', 'MENU', 'AGENTMAIN', '2', '', NOW(), NOW(), FALSE);