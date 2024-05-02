package com.example.idutil.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qcby.idutil.common.enums.IdType;
import com.qcby.idutil.dao.entity.SegmentEntity;
import com.qcby.idutil.dao.mapper.SegmentMapper;
import com.qcby.idutil.domain.exchange.SegmentExchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 号段处理程序
 *
 * @author cong.zhen
 * @date 2024/02/26
 */
@Component
@Slf4j
public class SegmentHandler extends BaseIdHandler {

    public SegmentHandler(){idCode = IdType.SEGMENT_MYSQL.getType(); }


    @Resource
    private SegmentExchange segmentExchange;


    /**
     * todo 临界问题未解决
     *
     * @param businessCode 业务代码
     * @return {@link String}
     */
    @Override
    public String doIdAction(String businessCode) {

        long id = segmentExchange.getCurrentId(businessCode);
        log.info("id:{}",id);
        return StrUtil.toString(id);
    }
}
