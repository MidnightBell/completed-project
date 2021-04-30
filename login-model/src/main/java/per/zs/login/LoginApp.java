package per.zs.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** 
* Create time 2021年4月27日 下午3:46:52 
* @author sheng.zhong 
* @Description  
*/
@EnableTransactionManagement
@SpringBootApplication
public class LoginApp {
    public static void main(String[] args) {
        SpringApplication.run(LoginApp.class, args);
    }
}
