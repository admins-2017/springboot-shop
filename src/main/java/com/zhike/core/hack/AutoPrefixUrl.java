package com.zhike.core.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 自定获取包名添加到路由前缀
 * @author Administrator
 */
public class AutoPrefixUrl extends RequestMappingHandlerMapping {

    /**
     * 获取根包 从配置文件中读取
     */
    @Value("${default.api-package}")
    private String apiPackagePath;

    /**
     * 重写父类的 getMappingForMethod方法
     * @param method
     * @param handlerType
     * @return
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method,handlerType);
        //如果不为空就修改路由信息
        if (mappingInfo != null ){
            String prefix = this.getPrefix(handlerType);
//            将路由前缀和controller中的requestMapping的value合并 例如 /v1/banner
            return RequestMappingInfo.paths(prefix).build().combine(mappingInfo);
        }
        return mappingInfo;
    }

    private String getPrefix(Class<?> handlerType){
//        获取当前controller的包名
        String packageName= handlerType.getPackage().getName();
//        将原来的包路径替换为空
        String dotPath = packageName.replaceAll(this.apiPackagePath,"");
//        将点 替换成 /
        return dotPath.replace(".","/");
    }
}
