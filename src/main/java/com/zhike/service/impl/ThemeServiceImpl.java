package com.zhike.service.impl;

import com.zhike.model.Theme;
import com.zhike.repository.ThemeRepository;
import com.zhike.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeServiceImpl(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Override
    public List<Theme> findByNames(List<String> names){
        return themeRepository.findByNames(names);
    }

    @Override
    public Optional<Theme> findByName(String name) {
        return  themeRepository.findByName(name);
    }
}
