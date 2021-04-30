package per.zs.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/** 
* Create time 2021年4月27日 下午3:29:08 
* @author sheng.zhong 
* @Description  
*/
@SpringBootApplication
@EnableEurekaServer
public class EurekaModelApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaModelApp.class, args);
    }
}
