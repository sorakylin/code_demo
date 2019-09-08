使用技术为: SpringBoot+SpringSecurity+MyBatis+jjwt
---

标准的RBAC权限设计，基于动态查询数据库的权限判定 (  *以接口为粒度，即 Request URL+Request Method*  ) 基于JWT的认证授权流程。  



注: 此项目未写前端,所有的接口请求/API调用/测试 均使用第三方工具 (我是用的postman)

<br>

#### 导入:  
* IDEA中选择[Project Structure]的Project子菜单, 进行如下操作导入:    
     `绿色加号 -> Import Module -> 选择pom.xml -> OK -> ... `
* 导入pom.xml之后，再分别导入此项目下的两个子项目的pom.xml,同上
<br>

#### 模块说明:
- spring-security-demo： 总体架构，统一管理
- spring-security-demo-dependencies： 依赖统一定义
- spring-security-demo-server： 具体安全服务实现

#### spring-security-demo-server 重要包目录:
- com.skypyb.security.config：  核心配置，全体集成
- com.skypyb.security.filter.access：   访问安全资源、决策管理
- com.skypyb.security.filter.authentication：   认证控制器，分发令牌
  
  <br>
  <br>
#### 注:

测试接口在 com.skypyb.test.controller 下  
resources 目录下的 init_sql 目录中含有 sql 脚本  
