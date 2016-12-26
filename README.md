# spring-cloud-samples

## 简介

springcloud的示例项目.更多细节参见源码.

## 启动顺序和端口号
- eureka:700x
- config:701x
- feign:703x
- ribbon:704x
- zuul:705x
- turbine:706x
- sleuth:707x
- xService:708x
- nonJavaService:709x
- admin:710x


- x从0开始,有多个相同节点时递增.


## 注意

hosts中加入如下两条记录:
```
127.0.0.1 peer1
127.0.0.1 peer2
```

config项目中的git仓库配置需自行更改.

bookmarks.html是通过chrome导出的一些链接,用于测试samples

## eureka
- 服务注册中心

## config
- 配置中心

## feign
- 编写http client的利器.

## ribbon
- 客户端负载

## zuul
- 服务网关

## turbine
- 聚合断路器统计信息

## sleuth
- 全链路调用追踪

## xService
- 业务服务

## nonJavaService
- 不使用eureka clinet lib实现服务注册与发现.模拟非java语言使用eureka的情景.

## admin
- springboot项目监控.