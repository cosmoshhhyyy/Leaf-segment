package com.example.idutil.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 基本id处理程序
 *
 */
@Slf4j
@Component
public abstract class BaseIdHandler implements IIdHandler{
    /**
     * 标识生成策略的code
     * 子类初始化的时候指定
     */
    protected String idCode;

    @Resource
    private IdHandleHolder idHandleHolder;

    /**
     * 初始化具体策略与handle的映射关系
     */
    @PostConstruct
    private void init(){
        idHandleHolder.putHandler(idCode,this);
    }

    /**
     * 具体id生成方案
     *
     * @param businessCode 业务代码
     * @return {@link String}
     */
    public abstract String doIdAction(String businessCode);

    /**
     * 所有id生成都会经过，后期可以做监控
     *
     * @param businessCode 业务代码
     * @return {@link String}
     */
    @Override
    public String getId(String businessCode){
        if(log.isDebugEnabled()){
            log.debug("BaseIdHandler createId businessCode：{},idtype:{}",businessCode,idCode);
        }
        return doIdAction(businessCode);
    }
}
