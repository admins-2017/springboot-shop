package com.zhike.api.v1;

import com.zhike.bo.PageCounter;
import com.zhike.model.Spu;
import com.zhike.model.Tag;
import com.zhike.service.SpuService;
import com.zhike.service.TagService;
import com.zhike.util.CommonUtil;
import com.zhike.vo.PagingDozer;
import com.zhike.vo.SpuSimplifyVO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    public SearchController(SpuService spuService) {
        this.spuService = spuService;
    }

    private final SpuService spuService;

    @GetMapping
    public PagingDozer<Spu, SpuSimplifyVO>  getSpuBySearch(@RequestParam(defaultValue = "0") Integer start
            , @RequestParam(defaultValue = "10") Integer count, @RequestParam("q") String likeName){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> spuListLikeTag = spuService.getSpuListLikeTag(pageCounter.getPage(), pageCounter.getCount(), likeName);
        return new PagingDozer<>(spuListLikeTag,SpuSimplifyVO.class);
    }
}
