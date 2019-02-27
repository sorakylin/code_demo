 SpringBoot 集成RabbitMQ 进行消息的发送与接收示例  
---  

* IDEA中选择[Project Structure]的Project子菜单, 进行如下操作导入:  
      `绿色加号 -> Import Module -> 选择build.gradle -> OK -> ... `
      
* 在 RabbitMQ-Producer项目的 test 文件夹下有演示测试类
* 测试前先启动Consumer服务器,两个服务的yml配置文件都要改为可以连上的RabbitMQ
