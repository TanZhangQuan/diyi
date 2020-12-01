###首页1
INSERT INTO `sys_menu` VALUES ('1123598815738675201',                 '0', 'Dashboard', '首页', 'menu', '/dashboard',              'home', '1', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###支付管理2
INSERT INTO `sys_menu` VALUES ('1123598815738675202',                   '0', 'Pay',     '支付管理',           'menu', '/pay',   'zhifu', '2', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675203', '1123598815738675202', 'PayTotal', '总包+分包-总包支付', 'menu', '/pay/total',  '', '1', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675204', '1123598815738675202', 'PaySub',   '总包+分包-分包支付', 'menu', '/pay/sub',    '', '2', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675205', '1123598815738675202', 'PayCrowd',         '众包/众采', 'menu', '/pay/crowd',  '', '3', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###合同管理3
INSERT INTO `sys_menu` VALUES ('1123598815738675207',                   '0', 'Contract',     '合同管理', 'menu', '/contract',      'hetong', '3', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675208', '1123598815738675207', 'Platform',  '加盟平台合同、承诺函', 'menu', '/contract/platform',   '', '1', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675209', '1123598815738675207', 'Provider','商户合同、承诺函', 'menu', '/contract/provider',   '', '2', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675210', '1123598815738675207', 'Worker1',    '创客合同、承诺函', 'menu', '/contract/worker',     '', '3', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###发票/税票管理4
INSERT INTO `sys_menu` VALUES ('1123598815738675218',                   '0', 'Tax',      '发票/税票管理',        'menu', '/tax',   'fapiao', '4', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675219', '1123598815738675218', 'TaxTotal', '【总包+分包】总包发票', 'menu', '/tax/total',  '', '1', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

INSERT INTO `sys_menu` VALUES ('1123598815738675220', '1123598815738675218', 'TaxSub',   '【总包+分包】分包发票', 'menu', '/tax/sub',    '', '2', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675221', '1123598815738675220', 'TaxSubSummary', '汇总代开发票', 'menu', '/tax/sub/summary',  '', '1', '1', '0', '1', '','SERVICEPROVIDER',  null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675222', '1123598815738675220', 'TaxSubPortal',  '门征单开发票', 'menu', '/tax/sub/portal',   '', '2', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675223', '1123598815738675220', 'TaxSubWait',  '待发票', 'menu', '/tax/sub/wait',            '', '3', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

INSERT INTO `sys_menu` VALUES ('1123598815738675224', '1123598815738675218', 'TaxCrowd', '【众包】发票和税票',    'menu', '/tax/crowd',  '', '3', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###自然人创客管理5
INSERT INTO `sys_menu` VALUES ('1123598815738675225',                   '0', 'Worker',     '自然人创客管理', 'menu', '/worker',      'chuangke', '5', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675226', '1123598815738675225', 'WorkerIndex',        '所有创客', 'menu', '/worker/index',       '', '1', '1', '0', '1', '','SERVICEPROVIDER',  null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675227', '1123598815738675225', 'WorkerAttention', '与商户关联创客', 'menu', '/worker/attention', '', '2', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675228', '1123598815738675225', 'WorkerDetail', '账单明细', 'menu', '/worker/detail',            '', '3', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###个体户管理6
INSERT INTO `sys_menu` VALUES ('1123598815738675229',                   '0', 'Personal',        '个体户管理', 'menu', '/personalBusiness/index',           'geti', '6', '1', '0', '1', '','SERVICEPROVIDER',  null, NOW(), null, NOW(), '0');

###个独管理7
INSERT INTO `sys_menu` VALUES ('1123598815738675230',                   '0', 'Individual',          '个独管理', 'menu', '/individualBusiness/index',           'gedu', '7', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###自助开票管理8
INSERT INTO `sys_menu` VALUES ('1123598815738675242',                   '0', 'Self',      '自助开票管理', 'menu', '/self',     'kaipiao', '8', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675243', '1123598815738675242', 'SelfNature',     '自然人', 'menu', '/self/natural',    '', '1', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675244', '1123598815738675242', 'SelfPerson',     '个体户', 'menu', '/self/person',     '', '2', '1', '0', '1', '','SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675245', '1123598815738675242', 'SelfIndividual', '个独',   'menu', '/self/individual', '', '3', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###商户基本信息维护9
INSERT INTO `sys_menu` VALUES ('1123598815738675247',                   '0', 'Shop',  '合作商户资料', 'menu', '/shop/index',  'hezuo', '9', '1', '0', '1', '','SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###服务商基本信息10
INSERT INTO `sys_menu` VALUES ('1123598815738675248',                   '0', 'BaseIndex', '服务商基本信息', 'menu', '/base/index', 'fuwushang', '10', '1', '0', '1', '','SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675249', '1123598815738675248', 'BaseBank',    '银行账户信息',     'menu', '/base/bank',    '', '1', '1', '0', '1', '','SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675250', '1123598815738675248', 'BaseContact', '联系人信息维护',   'menu', '/base/contact', '', '2', '1', '0', '1', '','SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675251', '1123598815738675248', 'BaseInvoice', '四大开票信息维护', 'menu', '/base/invoice', '', '3', '1', '0', '1', '','SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675252', '1123598815738675248', 'BaseAddress', '三大快递信息维护', 'menu', '/base/address', '', '4', '1', '0', '1', '','SERVICEPROVIDER', null, NOW(), null, NOW(), '0');

###权限管理11
INSERT INTO `sys_menu` VALUES ('1123598815738675253',                 '0', 'Auth', '权限管理', 'menu', '/auth', 'auth', '11', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675254',                 '1123598815738675253', 'AuthIndex', '账号管理', 'menu', '/auth/index', '', '1', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');
INSERT INTO `sys_menu` VALUES ('1123598815738675255',                 '1123598815738675253', 'AuthRole', '角色管理', 'menu', '/auth/role', '', '2', '1', '0', '1', '', 'SERVICEPROVIDER', null, NOW(), null, NOW(), '0');