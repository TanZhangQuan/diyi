###首页1
INSERT INTO `diyi_menu` VALUES ('1123598815738675101', '0', 'Dashboard', '首页', 'menu', '/dashboard', 'home', '1', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###工单管理2
INSERT INTO `diyi_menu` VALUES ('1123598815738675102', '0', 'Task', '工单管理', 'menu', '/task', 'task', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675103', '1123598815738675102', 'TaskProcessing', '发布中', 'menu', '/task/processing', '', '1', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675104', '1123598815738675102', 'TaskLink', '已关单', 'menu', '/task/link', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675105', '1123598815738675102', 'TaskAccepting', '验收中', 'menu', '/task/accepting', '', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675106', '1123598815738675102', 'TaskDone', '已完毕', 'menu', '/task/done', '', '4', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###合同管理3
INSERT INTO `diyi_menu` VALUES ('1123598815738675107', '0', 'Contract', '合同管理', 'menu', '/contract', 'hetong', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675108', '1123598815738675107', 'Platform', '合作平台合同', 'menu', '/contract/platform', '', '1', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675111', '1123598815738675108', 'PlatformIndex', '商户加盟平台合同', 'menu', '/contract/platform/index',   '', '1', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675112', '1123598815738675108', 'PlatformPromise',    '商户承诺函', 'menu', '/contract/platform/promise', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675109', '1123598815738675107', 'Provider','合作服务商合同', 'menu', '/contract/provider',   '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675113', '1123598815738675109', 'ProviderIndex', '服务商加盟平台合同', 'menu', '/contract/provider/index', '', '1', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675114', '1123598815738675109', 'ProviderPromise','商户和服务商补充协议', 'menu', '/contract/provider/promise', '', '2', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675110', '1123598815738675107', 'Worker1', '合作创客合同', 'menu', '/contract/worker', '', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675115', '1123598815738675110', 'WorkerIndex1', '创客加盟合同', 'menu', '/contract/worker/index', '', '1', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675116', '1123598815738675110', 'WorkerPromise', '商户和创客补充协议', 'menu', '/contract/worker/promise', '', '2', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675117', '1123598815738675110', 'WorkerCrowdPromise', '众包&众采合同', 'menu', '/contract/worker/crowd', '', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###自然人创客管理4
INSERT INTO `diyi_menu` VALUES ('1123598815738675118', '0', 'Worker', '自然人创客管理', 'menu', '/worker',      'chuangke', '4', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675119', '1123598815738675118', 'WorkerIndex',        '关联创客', 'menu', '/worker/index',     '', '1', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675120', '1123598815738675118', 'WorkerAttention', '关注创客关联', 'menu', '/worker/attention', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###支付管理5
INSERT INTO `diyi_menu` VALUES ('1123598815738675121', '0', 'Pay', '支付管理', 'menu', '/pay', 'zhifu', '5', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675122', '1123598815738675121', 'PayTotal', '总包+分包-总包支付', 'menu', '/pay/total',  '', '1', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675123', '1123598815738675121', 'PaySub',   '总包+分包-分包支付', 'menu', '/pay/sub',    '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675124', '1123598815738675121', 'PayCrowd',         '众包/众采', 'menu', '/pay/crowd',  '', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###交付支付验收单管理6
INSERT INTO `diyi_menu` VALUES ('1123598815738675125', '0', 'PayCheck', '交付支付验收单管理', 'menu', '/payCheck','zhifuyanshou', '6', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675126', '1123598815738675125', 'PayCheckTotalSub', '【总包+分包】', 'menu', '/payCheck/totalSub',   '', '1', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675127', '1123598815738675125', 'PayCheckCrowd', '【众包】', 'menu', '/payCheck/crowd', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###发票/税票管理7
INSERT INTO `diyi_menu` VALUES ('1123598815738675128', '0', 'Tax', '发票/税票管理', 'menu', '/tax', 'fapiao', '7', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675129', '1123598815738675128', 'TaxTotal', '【总包+分包】总包发票', 'menu', '/tax/total', '', '1', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675130', '1123598815738675128', 'TaxSub',   '【总包+分包】分包发票', 'menu', '/tax/sub', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675132', '1123598815738675130', 'TaxSubSummary', '汇总代开发票', 'menu', '/tax/sub/summary', '', '1', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675133', '1123598815738675130', 'TaxSubPortal',  '门征单开发票', 'menu', '/tax/sub/portal', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);

INSERT INTO `diyi_menu` VALUES ('1123598815738675131', '1123598815738675128', 'TaxCrowd', '【众包】发票和税票', 'menu', '/tax/crowd', '', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###个体户管理8
INSERT INTO `diyi_menu` VALUES ('1123598815738675134', '0', 'Personal', '个体户管理', 'menu', '/personalBusiness',           'geti', '8', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675135', '1123598815738675134', 'PersonalRegistered',  '已注册', 'menu', '/personalBusiness/registered',    '', '1', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675136', '1123598815738675134', 'PersonalCancel',      '已注销', 'menu', '/personalBusiness/cancel',        '', '2', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675137', '1123598815738675134', 'PersonalApplication', '申请中', 'menu', '/personalBusiness/application',   '', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###个独管理9
INSERT INTO `diyi_menu` VALUES ('1123598815738675138', '0', 'Individual', '个独管理', 'menu', '/individualBusiness', 'gedu', '9', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675139', '1123598815738675138', 'IndividualRegistered',  '已注册', 'menu', '/individualBusiness/registered', '', '1', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675140', '1123598815738675138', 'IndividualCancel',      '已注销', 'menu', '/individualBusiness/cancel', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675141', '1123598815738675138', 'IndividualApplication', '申请中', 'menu', '/individualBusiness/application', '', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###自助开票管理10
INSERT INTO `diyi_menu` VALUES ('1123598815738675142', '0', 'Self', '自助开票管理', 'menu', '/self', 'kaipiao', '10', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675143', '1123598815738675142', 'SelfNature', '自然人', 'menu', '/self/natural', '', '1', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675144', '1123598815738675142', 'SelfPerson', '个体户', 'menu', '/self/person', '', '2', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675145', '1123598815738675142', 'SelfIndividual', '个独',   'menu', '/self/individual', '', '3', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###合作的服务商管理11
INSERT INTO `diyi_menu` VALUES ('1123598815738675146', '0', 'ServiceIndex', '合作的服务商管理', 'menu', '/service/index', 'fuwushang', '11', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
###商户基本信息维护12
INSERT INTO `diyi_menu` VALUES ('1123598815738675147', '0', 'Shop',  '商户基本信息维护', 'menu', '/shop',  'shanghu', '12', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675148', '1123598815738675147', 'ShopBase', '基本信息', 'menu', '/shop/base', '', '1', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675149', '1123598815738675147', 'ShopPerson', '联系人信息', 'menu', '/shop/person', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675150', '1123598815738675147', 'ShopTax', '开票信息', 'menu', '/shop/tax', '', '3', '1', '0', '1', '','ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675151', '1123598815738675147', 'Address', '快递信息', 'menu', '/address/index','', '4', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
###权限管理13
INSERT INTO `diyi_menu` VALUES ('1123598815738675152', '0', 'Auth', '权限管理', 'menu', '/auth', 'auth', '13', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675153', '1123598815738675152', 'AuthIndex', '账号管理', 'menu', '/auth/index', '', '1', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);
INSERT INTO `diyi_menu` VALUES ('1123598815738675154', '1123598815738675152', 'AuthRole', '角色管理', 'menu', '/auth/role', '', '2', '1', '0', '1', '', 'ENTERPRISE', NOW(), NOW(), FALSE);