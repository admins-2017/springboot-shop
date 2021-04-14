package com.zhike.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhike.exception.httpexception.NotFoundException;
import com.zhike.exception.httpexception.ParameterException;
import com.zhike.model.User;
import com.zhike.repository.UserRepository;
import com.zhike.service.WxAuthenticationService;
import com.zhike.util.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @author Administrator
 */
@Service
public class WxAuthenticationServiceImpl implements WxAuthenticationService {

    private final ObjectMapper mapper;

    private final UserRepository userRepository;

    @Value("${wx.code2session}")
    private String code2SessionUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;

    public WxAuthenticationServiceImpl(ObjectMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public String code2Session(String code) {
        String url = MessageFormat.format(code2SessionUrl,code);
//        发送请求到微信服务器验证code是否有效
        RestTemplate restTemplate = new RestTemplate();
        String sessionText = restTemplate.getForObject(url, String.class);
        Map<String,Object> session = new HashMap<>(10);
//        获取微信响应的数据 将数据反序列化
        try {
            session = mapper.readValue(sessionText,Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException(10003);
        }
        return this.registerUser(session);
    }

    /**
     * 注册用户
     * @return
     */
    private String registerUser(Map<String,Object> session){
        String openid =(String) session.get("openid");
        if (openid == null){
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = userRepository.findByOpenid(openid);
//        判断用户是否存在
        if (userOptional.isPresent()){
            return JwtToken.makeToken(userOptional.get().getId());
        }
        User user = User.builder().openid(openid).build();
        userRepository.save(user);
        return JwtToken.makeToken(user.getId());
    }
}
