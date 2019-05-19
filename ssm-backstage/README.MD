一个使用Maven管理的、Spring+SpringMVC+MyBatis+Thymeleaf+Layui 完成的简单后台  
---  
  
* IDEA中选择[Project Structure]的Project子菜单, 进行如下操作导入:    
     `绿色加号 -> Import Module -> 选择 pom.xml -> OK -> ... `

#### 说明:  
SSM 均使用注解式开发。  
完成了基础的数据 CRUD。  
完成了基础的登陆。  
SpringMVC 设置了基础的拦截器。  
MyBatis 使用注解完成了基础的一对一、一对多语句。  
基本上 HTML 均为 Thymeleaf layout 式的页面，前端使用 Layui 作为模板。   
  
`resources/sql/ 文件夹下有本项目 Entity 的建表语句`

#### 本工程作用:
记录用, 方便二次使用，即拿即用。  
好久没用 Maven+SSM 这套东西了，这 XML 配置真心怪麻烦的。  
拦截器、Mybatis映射、Thymeleaf 这些玩意配置都得用 XML , 搞得头大。  
