package com.example.idutil.service.cache;

import com.example.idutil.dao.entity.SegmentEntity;
import com.example.idutil.dao.mapper.SegmentMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 *
 */
@Component
@Slf4j
public class CaffeineCache {

    Cache<String, Integer> stepCache = Caffeine.newBuilder()
            .expireAfterWrite(3, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    @Resource
    private SegmentMapper segmentMapper;

    @PostConstruct
    public void initStepCache() {
        List<SegmentEntity> segmentEntities = segmentMapper.selectList(null);
        segmentEntities.forEach(item->{stepCache.put(item.getBusinessCode(), item.getStep());});
    }

    public Integer getStepByCode(String businessCode){
        return stepCache.get(businessCode,k-> segmentMapper.selectById(businessCode).getStep());
    }


}
