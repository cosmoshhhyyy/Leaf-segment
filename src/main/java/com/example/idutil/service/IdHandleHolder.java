package com.example.idutil.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.idutil.common.enums.IdType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成方式--id策略映射关系
 *
 */
@Component
public class IdHandleHolder {

    private Map<String,IIdHandler> handlers = new HashMap<>(4);

    public void putHandler(String code,IIdHandler idHandler){
        handlers.put(code,idHandler);
    }

    /**
     * 路由到具体的生handler,默认是雪花算法
     *
     * @param code 代码
     * @return {@link IIdHandler}
     */
    public IIdHandler route(String code){
        if(ObjectUtil.isNull(handlers.get(code))){
            code = IdType.SNOWFLAKE.getType();
        }
        return handlers.get(code);
    }
}
