package com.example.idutil.service;

import cn.hutool.core.util.StrUtil;
import com.example.idutil.common.enums.IdType;
import com.example.idutil.domain.exchange.SegmentExchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 号段处理程序
 *
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
