###首页1
INSERT INTO `diyi_menu` VALUES ('1123598815738675101', NULL, '首页', '/dashboard', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);

###工单管理2
INSERT INTO `diyi_menu` VALUES ('1123598815738675102', NULL, '工单管理', '/task', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675103', '1123598815738675102', '发布中', '/task/processing', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675104', '1123598815738675102', '已关单', '/task/link', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675105', '1123598815738675102', '验收中', '/task/accepting', 'MENU', 'ENTERPRISE', '3', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675106', '1123598815738675102', '已完毕', '/task/done', 'MENU', 'ENTERPRISE', '4', '', NOW(), NOW(), FALSE);

###合同管理3
INSERT INTO `diyi_menu` VALUES ('1123598815738675107', NULL, '合同管理', '/contract', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675108', '1123598815738675107', '合作平台合同', '/contract/platform', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675111', '1123598815738675108', '商户加盟平台合同', '/contract/platform/index', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675112', '1123598815738675108', '商户承诺函', '/contract/platform/promise', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675109', '1123598815738675107', '合作服务商合同', '/contract/provider', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675113', '1123598815738675109', '服务商加盟平台合同', '/contract/provider/index', 'MENU','ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675114', '1123598815738675109', '商户和服务商补充协议', '/contract/provider/promise', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675110', '1123598815738675107', '合作创客合同', '/contract/worker', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675115', '1123598815738675110', '创客加盟合同', '/contract/worker/index', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675116', '1123598815738675110', '商户和创客补充协议', '/contract/worker/promise', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675117', '1123598815738675110', '众包&众采合同', '/contract/worker/crowd', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);

###自然人创客管理4
INSERT INTO `diyi_menu` VALUES ('1123598815738675118', NULL, '自然人创客管理', '/worker', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675119', '1123598815738675118', '关联创客', '/worker/index', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675120', '1123598815738675118', '关注创客关联', '/worker/attention', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);

###支付管理5
INSERT INTO `diyi_menu` VALUES ('1123598815738675121', NULL, '支付管理', '/pay', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675122', '1123598815738675121', '总包+分包-总包支付', '/pay/total', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675123', '1123598815738675121', '总包+分包-分包支付', '/pay/sub', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675124', '1123598815738675121', '众包/众采', '/pay/crowd', 'MENU', 'ENTERPRISE', '3', '', NOW(), NOW(), FALSE);

###交付支付验收单管理6
INSERT INTO `diyi_menu` VALUES ('1123598815738675125', NULL, '交付支付验收单管理', '/payCheck', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675126', '1123598815738675125', '【总包+分包】', '/payCheck/totalSub', 'MENU','ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675127', '1123598815738675125', '【众包】', '/payCheck/crowd', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);

###发票/税票管理7
INSERT INTO `diyi_menu` VALUES ('1123598815738675128', NULL, '发票/税票管理', '/tax', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675129', '1123598815738675128', '【总包+分包】总包发票', '/tax/total', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675130', '1123598815738675128', '【总包+分包】分包发票', '/tax/sub', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675131', '1123598815738675128', '【众包】发票和税票', '/tax/crowd', 'MENU', 'ENTERPRISE', '3', '', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675132', '1123598815738675130', '汇总代开发票', '/tax/sub/summary', 'MENU','ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675133', '1123598815738675130', '门征单开发票', '/tax/sub/portal', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);


###个体户管理8
INSERT INTO `diyi_menu` VALUES ('1123598815738675134', NULL, '个体户管理', '/personalBusiness', 'MENU','ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675135', '1123598815738675134', '已注册', '/personalBusiness/registered', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675136', '1123598815738675134', '已注销', '/personalBusiness/cancel', 'MENU','ENTERPRISE', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675137', '1123598815738675134', '申请中', '/personalBusiness/application', 'MENU', 'ENTERPRISE', '3', '', NOW(), NOW(), FALSE);

###个独管理9
INSERT INTO `diyi_menu` VALUES ('1123598815738675138', NULL, '个独管理', '/individualBusiness', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675139', '1123598815738675138', '已注册', '/individualBusiness/registered', 'MENU','ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675140', '1123598815738675138', '已注销', '/individualBusiness/cancel', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675141', '1123598815738675138', '申请中', '/individualBusiness/application', 'MENU', 'ENTERPRISE', '3', '', NOW(), NOW(), FALSE);

###自助开票管理10
INSERT INTO `diyi_menu` VALUES ('1123598815738675142', NULL, '自助开票管理', '/self', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675143', '1123598815738675142', '自然人', '/self/natural', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675144', '1123598815738675142', '个体户', '/self/person', 'MENU','ENTERPRISE', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675145', '1123598815738675142', '个独', '/self/individual', 'MENU', 'ENTERPRISE', '3', '', NOW(), NOW(), FALSE);

###合作的服务商管理11
INSERT INTO `diyi_menu` VALUES ('1123598815738675146', NULL, '合作的服务商管理', '/service/index', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);

###商户基本信息维护12
INSERT INTO `diyi_menu` VALUES ('1123598815738675147', NULL, '商户基本信息维护', '/shop',  'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675148', '1123598815738675147', '基本信息', '/shop/base', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675149', '1123598815738675147', '联系人信息', '/shop/person', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675150', '1123598815738675147', '开票信息', '/shop/tax', 'MENU','ENTERPRISE', '3', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675151', '1123598815738675147', '快递信息', '/address/index', 'MENU', 'ENTERPRISE', '4', '', NOW(), NOW(), FALSE);

###权限管理13
INSERT INTO `diyi_menu` VALUES ('1123598815738675152', NULL, '权限管理', '/auth', 'MENU', 'ENTERPRISE', '0', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675153', '1123598815738675152', '账号管理', '/auth/index', 'MENU', 'ENTERPRISE', '1', '', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675154', '1123598815738675152', '角色管理', '/auth/role', 'MENU', 'ENTERPRISE', '2', '', NOW(), NOW(), FALSE);