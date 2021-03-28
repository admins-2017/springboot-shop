package com.zhike.core.configuration;

import com.zhike.core.hack.AutoPrefixUrl;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 根据包名获取controller前缀
 */
@Component
public class AutoPrefixConfiguration implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new AutoPrefixUrl();
    }
}
