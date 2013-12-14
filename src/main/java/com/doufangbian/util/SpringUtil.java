package com.doufangbian.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * User: Jie
 * Date: 12/7/13
 * Time: 3:37 PM
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
