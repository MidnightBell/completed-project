package per.zs.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** 
* Create time 2021年5月7日 下午1:13:43 
* @author sheng.zhong 
* @Description  
*/
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "per.zs")
public class ForumApp {
    public static void main(String[] args) {
        SpringApplication.run(ForumApp.class, args);
    }
}
