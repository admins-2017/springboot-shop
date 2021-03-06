package com.zhike.api.v1;

import com.zhike.bo.PageCounter;
import com.zhike.exception.httpexception.NotFoundException;
import com.zhike.model.Spu;
import com.zhike.service.SpuService;
import com.zhike.util.CommonUtil;
import com.zhike.vo.PagingDozer;
import com.zhike.vo.SpuSimplifyVO;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/spu")
@Validated
public class SpuController {

    private final SpuService spuService;

    public SpuController(SpuService spuService) {
        this.spuService = spuService;
    }

    @GetMapping("/id/{id}/detail")
    public Spu getSpuDetailById(@PathVariable @Positive Long id){
        Spu spu = spuService.getSpuDetailById(id);
        if (spu == null){
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/latest")
    public PagingDozer<Spu,SpuSimplifyVO> getLatestSpu(@RequestParam(defaultValue = "0") Integer start,@RequestParam(defaultValue = "10") Integer count){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = spuService.getLatestSpu(pageCounter.getPage(), pageCounter.getCount());
        return new PagingDozer<>(page,SpuSimplifyVO.class);
    }

    /**
     * @Positive(message = "{id.positive}") 读取ValidationMessages中的值
     * @param id 分类id
     * @param isRoot 是否为根节点
     * @param start 开始位置
     * @param count 条数
     * @return 商品集合
     */
    @GetMapping("/by/category/{id}")
    public PagingDozer<Spu,SpuSimplifyVO> getSpuListByCategoryId(@PathVariable @Positive(message = "{id.positive}") Long id
            ,@RequestParam(name = "is_root",defaultValue = "false")Boolean isRoot,@RequestParam(defaultValue = "0") Integer start,@RequestParam(defaultValue = "10") Integer count){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = spuService.getSpuListByCategoryId(pageCounter.getPage(), pageCounter.getCount(),id,isRoot);
        return new PagingDozer<>(page,SpuSimplifyVO.class);
    }
}
