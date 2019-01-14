package demo.chenj.springbootmybatisredisdemo.cache;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chenj
 * @date 2019-01-13 17:21
 * @email 924943578@qq.com
 */
@Component
public class MyApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MyApplicationContext.applicationContext = applicationContext;
    }

    public static Object getBean(String name){
        return MyApplicationContext.applicationContext.getBean(name);
    }
}
