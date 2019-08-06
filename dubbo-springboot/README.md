 SpringBoot结合Apache dubbo+zookeeper实现远程服务调用
---  
<br>  

#### 导入:  
* IDEA中选择[Project Structure]的Project子菜单, 进行如下操作导入:    
     `绿色加号 -> Import Module -> 选择pom.xml -> OK -> ... `
<br>

#### 实现功能:

* dubbo+zookeeper 完成服务提供与消费
* 引入kryo高速序列化机制

#### zookeeper单机伪集群:
使用docker-compose 运行项目中的docker-compose.zookeeper.yml
```
docker pull zookeeper:3.4.14
docker network create zookeeper-service
docker-compose up -d
```
