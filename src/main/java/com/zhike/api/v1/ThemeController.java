package com.zhike.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.zhike.exception.httpexception.NotFoundException;
import com.zhike.model.Theme;
import com.zhike.service.ThemeService;
import com.zhike.vo.ThemePureVO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/theme")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/by/names")
    public List<ThemePureVO> getThemeGroupByNames(@RequestParam(name = "names") String names) {
//        将前端返回的多个name分割为集合
        List<String> nameList = Arrays.asList(names.split(","));
        List<Theme> themes = themeService.findByNames(nameList);
        List<ThemePureVO> list = new ArrayList<>();

        themes.forEach(theme -> {
            Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            ThemePureVO vo = mapper.map(theme, ThemePureVO.class);
            list.add(vo);
        });

        return list;
    }

    //VIP分组 16
    //User分组 2

    /**
     * 获取主题的详情信息
     * @param themeName 主题名
     * @return
     */
    @GetMapping("/name/{name}/with_spu")
    public Theme getThemeByNameWithSpu(@PathVariable(name = "name") String themeName){
        Optional<Theme> optionalTheme = this.themeService.findByName(themeName);
        //判空 如果查询结果为空 抛出异常 不为空返回查询结果
        return optionalTheme.orElseThrow(()-> new NotFoundException(30003));
    }

}
