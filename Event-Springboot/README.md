 SpringBoot实现异步事件驱动(发布、监听)编程
---  
  
* IDEA中选择[Project Structure]的Project子菜单, 进行如下操作导入:    
     `绿色加号 -> Import Module -> 选择build.gradle -> OK -> ... `
* test 文件夹下有测试类,直接启动即可
* 有序监听没写,没什么用,严格要求有序处理业务为啥不一个个调用方法。  
    `有序监听实现 SmartApplicationListener 就行了。`
 