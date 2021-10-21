package com.wt.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//手动获取bean的工具类
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
     //保留下来的共工厂
    private  static ApplicationContext applicationContext;
   //将创建好的工厂以参数形式传入给这类
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext=applicationContext;
    }
    //提供在工厂中获取对象的方法
    public static Object getBean(String beanName){
      return applicationContext.getBean(beanName);
    }

}
