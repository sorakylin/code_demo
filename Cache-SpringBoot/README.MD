 SpringBoot的缓存操作
---  
  
* IDEA中选择[Project Structure]的Project子菜单, 进行如下操作导入:    
     `绿色加号 -> Import Module -> 选择build.gradle -> OK -> ... `
  
<br>  
<br>  
关于缓存,这里只涉及到SpringBoot自带的缓存实现，即默认的 SimpleCacheConfiguration 创建的 ConcurrentMapCacheManager ,不涉及Redis缓存。   
@CacheConfig  
@CacheEvict  
@CachePut  
@Cacheable  
以上注解都有用到，test 文件夹下有测试类举例说明,除注解配置之外，测试类中也有个简易的代码操作缓存演示。  
 