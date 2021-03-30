package com.zhike.service.impl;

import com.zhike.model.Banner;
import com.zhike.repository.BannerRepository;
import com.zhike.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Banner getByName(String name) {
       return bannerRepository.findOneByName(name);
    }

    @Override
    public Banner getById(Long id) {
        return bannerRepository.findOneById(id);
    }
}
