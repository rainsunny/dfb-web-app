package com.doufangbian.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Jie
 * Date: 12/7/13
 * Time: 3:37 PM
 */
public class SpringUtil {

    private static ApplicationContext context = null;

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        }
        return context.getBean(beanName, clazz);
    }

}
