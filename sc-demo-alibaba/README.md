 SpringCloudAlibaba 的几大核心组件示例
---  

* IDEA中选择[Project Structure]的Project子菜单, 进行如下操作导入:  
      `绿色加号 -> Import Module -> 选择pom.xml -> OK -> ... `
      
### 说明
* 集成的组件有 Nacos、Sentinel、Gateway、Feign
* 至于微服务链路跟踪由于已经写过一套成熟的方案zipkin+sleuth， 所以就没写SkyWalking这东西
* 服务之间的通信是用的默认的(HttpClient)，没上Dubbo，也是因为写过一套Dubbo+Zookeeper的演示了

### 完成了哪些功能？
* 服务治理、服务熔断、服务通信、服务网关
* 网关限流、鉴权(假的鉴权，只是个demo)


*关于Spring Cloud Alibaba的几个需要单独部署的组件的docker镜像:*
```shell
# 默认账号密码是nacos/nacos
docker pull nacos/nacos-server
docker run --env MODE=standalone --name nacos -d -p 8848:8848 nacos/nacos-server
```

```shell
# 默认账号密码都是sentinel
docker pull bladex/sentinel-dashboard
docker run --name sentinel -d -p 8858:8858 -d bladex/sentinel-dashboard
```