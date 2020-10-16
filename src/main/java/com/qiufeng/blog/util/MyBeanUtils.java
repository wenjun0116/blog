package com.qiufeng.blog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {

    /**
     * 将对象中的空值名存到数组中
     * @param o 需要排除空值的对象
     * @return  空值名数组
     */
    public static String[] getNullPropertyName(Object o){
        BeanWrapper beanWrapper = new BeanWrapperImpl(o);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyName = new ArrayList<>();
        for (PropertyDescriptor pd : pds){
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null){
                nullPropertyName.add(propertyName);
            }
        }
        return nullPropertyName.toArray(new String[nullPropertyName.size()]);
    }
}
