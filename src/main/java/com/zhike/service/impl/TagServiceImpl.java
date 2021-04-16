package com.zhike.service.impl;

import com.zhike.model.Tag;
import com.zhike.repository.TagRepository;
import com.zhike.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> getAll(Integer typeId) {
        return tagRepository.findAllByType(typeId);
    }


}
