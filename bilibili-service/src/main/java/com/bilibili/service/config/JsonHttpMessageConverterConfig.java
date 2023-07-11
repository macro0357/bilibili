package com.bilibili.service.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JsonHttpMessageConverterConfig {

    /**
     * 循环引用
     * fastjson add时 发现list中有相同的元素会返回他的引用
     */
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        Object o = new Object();
        list.add(o);
        list.add(o);
        System.out.println(list.size());
        System.out.println(JSONObject.toJSONString(list));
        System.out.println(JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Bean
    @Primary
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.MapSortField,
                SerializerFeature.DisableCircularReferenceDetect//关闭循环引用
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }
}
