 SpringBoot 集成 RabbitMQ; 消息发送/接收、死信队列
---  

* IDEA中选择[Project Structure]的Project子菜单, 进行如下操作导入:  
      `绿色加号 -> Import Module -> 选择build.gradle -> OK -> ... `
      
* 两个项目的 test 文件夹下均有演示测试类
* 测试前先启动Consumer服务器,两个服务的yml配置文件都要改为可以连上的RabbitMQ

<br>
<br>

### 完成的功能
* 生产者消费者基础功能，消息收发
* 注解形式/配置类形式 创建Queue、ExChange、Binding
* 配置监听器工厂，指定消息限流次数、并发线程数
* 死信队列相关配置，使用死信队列实现延时消息的收/发
