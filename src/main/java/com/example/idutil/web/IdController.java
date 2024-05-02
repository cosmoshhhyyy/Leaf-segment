package com.example.idutil.web;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.http.HttpUtil;
import com.qcby.idutil.common.enums.IdType;
import com.qcby.idutil.dao.entity.TestEntity;
import com.qcby.idutil.dao.mapper.TestMapper;
import com.qcby.idutil.service.IdHandleHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * id控制器
 *
 * @author cong.zhen
 * @date 2024/02/23
 */
@RestController
public class IdController {


    private static final ExecutorService SEGMENT_QUEUE_EXECUTOR = ExecutorBuilder.create()
            .setCorePoolSize(8)
            .setMaxPoolSize(16)
            .setKeepAliveTime(1000, TimeUnit.SECONDS)
            .setWorkQueue(new LinkedBlockingDeque<>(10000))
            .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
            .setThreadFactory(ThreadFactoryBuilder.create().setNamePrefix("test").build()).build();

    @Resource
    private IdHandleHolder idHandleHolder;


    @Resource
    private TestMapper testMapper;


    @GetMapping("/api/get/{businessCode}")
    public String getId(@PathVariable("businessCode") String businessCode){
        return idHandleHolder.route(IdType.SEGMENT_MYSQL.getType()).getId(businessCode);
    }

    @GetMapping("/test")
    public String test(){
        for (int i = 0; i <= 1000; i++) {

            CompletableFuture.runAsync(() -> {
                String s = HttpUtil.get("http://127.0.0.1:8080/api/get/leaf-segment-test");
                TestEntity testEntity = new TestEntity();
                testEntity.setId(Long.valueOf(s));
                testMapper.insert(testEntity);
            }, SEGMENT_QUEUE_EXECUTOR);
        }
        return "aa";
    }
}
