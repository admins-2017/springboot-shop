package com.zhike.api.v1;

import com.zhike.model.Tag;
import com.zhike.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("/type/{typeId}")
    public List<Tag> getAllTag(@PathVariable Integer typeId){
        List<Tag> list = tagService.getAll(typeId);
        return list;
    }


    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

}
