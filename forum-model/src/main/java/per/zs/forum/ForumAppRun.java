package per.zs.forum;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author changhan.li
 * @version 1.0
 * @date 2021-03-16 9:38
 */
@Component
public class ForumAppRun implements ApplicationRunner {
    
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        System.out.println("··········论坛模块程序启动············");
//        new Thread(() -> {
//            try {
//                
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).run();
    }
}
