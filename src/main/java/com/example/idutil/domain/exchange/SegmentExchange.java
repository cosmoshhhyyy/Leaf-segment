package com.example.idutil.domain.exchange;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qcby.idutil.dao.entity.SegmentEntity;
import com.qcby.idutil.dao.mapper.SegmentMapper;
import com.qcby.idutil.service.cache.CaffeineCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 分段交换
 *
 * @author cong.zhen
 * @date 2024/02/25
 */
@Component
@Slf4j
public class SegmentExchange {

    private final ReadWriteLock lock;

    private static final ExecutorService SEGMENT_QUEUE_EXECUTOR = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNamePrefix("segment_queue-queue-thread").build());

    @Resource
    private SegmentMapper segmentMapper;

    @Resource
    private CaffeineCache caffeineCache;
    /**
     * key: 应用编码
     * value:当前id
     */
    private static Map<String, AtomicLong> segmentMap = new ConcurrentHashMap<>();


    /**
     * key: 应用编码
     * value:buffer
     */
    private static Map<String, AtomicInteger> segmentStepMap = new ConcurrentHashMap<>();

    public SegmentExchange() {
        this.lock = new ReentrantReadWriteLock();
    }


    /**
     * 获取当前id
     *
     * @param businessCode 业务代码
     * @return {@link AtomicLong}
     */
    public Long getCurrentId(String businessCode) {
        lock.readLock().lock();
        boolean flag = true;
        try {
            int step = segmentStepMap.get(businessCode).getAndIncrement();
            if (step <= caffeineCache.getStepByCode(businessCode)) {
                Long id = segmentMap.get(businessCode).getAndIncrement();
                return id;
            }
            lock.readLock().unlock();
            flag = false;
            updateCurrentId(businessCode);
            return getCurrentId(businessCode);

        } catch (Exception e) {
            log.info("我进报错的了");
            return getCurrentId(businessCode);
        } finally {
            if (flag) {
                lock.readLock().unlock();
            }

        }

    }

    /**
     * 更新当前id
     *
     * @param businessCode 业务代码
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateCurrentId(String businessCode) {
        lock.writeLock().lock();
        try {
            segmentMapper.updateMaxId(businessCode);
            SegmentEntity segmentEntity = segmentMapper.selectOne(new LambdaQueryWrapper<SegmentEntity>().
                    select(SegmentEntity::getMaxId, SegmentEntity::getStep)
                    .eq(SegmentEntity::getBusinessCode, businessCode));
            Long updateId = segmentEntity.getMaxId();
            segmentMap.put(businessCode, new AtomicLong(updateId));
            segmentStepMap.put(businessCode, new AtomicInteger(1));
        } catch (Exception e) {

        } finally {
            lock.writeLock().unlock();
        }


    }

    @PostConstruct
    private void init() {
        List<SegmentEntity> segmentEntities = segmentMapper.selectList(null);
        segmentEntities.stream().forEach(item -> {
            updateCurrentId(item.getBusinessCode());
        });
    }

}
