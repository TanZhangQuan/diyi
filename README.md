# diyi-cr

#### 介绍
零工云公司旗下地衣众包项目！

# DIYI-CR 微服务开发平台
* 采用前后端分离的模式，前端开源框架：[Saber](https://gitee.com/smallc/Saber) (基于 Vue、Element-UI)。
* 后端采用SpringCloud全家桶，并同时对其基础组件做了高度的封装。
* 集成Sentinel从流量控制、熔断降级、系统负载等多个维度保护服务的稳定性。
* 注册中心、配置中心选型Nacos，为工程瘦身的同时加强各模块之间的联动。
* 使用Traefik进行反向代理，监听后台变化自动化应用新的配置文件。
* 极简封装了多租户底层，用更少的代码换来拓展性更强的SaaS多租户系统。
* 借鉴OAuth2，实现了多终端认证系统，可控制子系统的token权限互相隔离。
* 借鉴Security，封装了Secure模块，采用JWT做Token认证，可拓展集成Redis等细颗粒度控制方案。
* 稳定生产了一年，经历了从Camden -> Greenwich的技术架构，也经历了从fat jar -> docker -> k8s + jenkins的部署架构
* 项目分包明确，规范微服务的开发模式，使包与包之间的分工清晰。

#### 软件架构
软件架构说明

## 架构图
[架构图](https://images.gitee.com/uploads/images/2020/0623/231204_92a859d4_1949382.jpeg "WechatIMG72.jpeg")

## 工程结构
``` 
diyi-cr
├── diyi-auth -- 授权服务提供
├── diyi-common -- 常用工具封装包
├── diyi-gateway -- Spring Cloud 网关
├── diyi-ops -- 运维中心
├    ├── diyi-admin -- spring-cloud后台管理
├    ├── diyi-develop -- 代码生成
├    ├── diyi-resource -- 资源管理
├    ├── diyi-seata-order -- seata分布式事务demo
├    ├── diyi-seata-storage -- seata分布式事务demo
├── diyi-service -- 业务模块
├    ├── diyi-desk -- 工作台模块 
├    ├── diyi-log -- 日志模块 
├    ├── diyi-system -- 系统模块 
├    └── diyi-user -- 用户模块 
├── diyi-service-api -- 业务模块api封装
├    ├── diyi-desk-api -- 工作台api 
├    ├── diyi-dict-api -- 字典api 
├    ├── diyi-system-api -- 系统api 
└──  └── diyi-user-api -- 用户api 
```
