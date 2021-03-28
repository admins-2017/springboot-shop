package com.zhike.api.v1;

import com.zhike.core.interceptors.ScopeLevel;
import com.zhike.dto.PersonDTO;
import com.zhike.exception.HttpException.NotFoundException;
import com.zhike.model.Banner;
import com.zhike.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 * Validated 如果该类中的方法，属性需要做验证 则需要在类上开启 Validated
 */
@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/name/{name}")
    @ScopeLevel(6)
    public Banner getByName(@PathVariable @NotBlank String name){
        Banner banner = bannerService.getByName(name);
        if(banner == null){
            throw new NotFoundException(30005);
        }
        return banner;
    }

    @GetMapping("/id/{bid}")
    public Banner getById(@PathVariable @NotNull Long bid){
        Banner banner = bannerService.getById(bid);
        if(banner == null){
            throw new NotFoundException(30005);
        }
        return banner;
    }


    /**
     * PathVariable 接收路径中的参数
     * RequestParam 接收路径后面拼接的参数 ？name="a"
     * RequestBody 接收请求的body对象 用于接收json传递的对象
     * Max 限制参数最大不能超过11
     * Validated 参数需要开启Validated 对参数的属性开启校验
     * @param id id
     */
    @PostMapping("/test/{id}")
    @ScopeLevel(8)
    public PersonDTO test(@PathVariable @Max(value = 11,message = "id不能大于10") Integer id, @RequestParam String name,
                          @Validated  @RequestBody PersonDTO person){
        PersonDTO yuan = PersonDTO.builder().name("袁依群").age(18).build();
        System.out.println(id);
        System.out.println(name);
        System.out.println(person.toString());
        return yuan;
//        int a = 1/0;
//        throw new NotFoundException(10001);
    }


}
