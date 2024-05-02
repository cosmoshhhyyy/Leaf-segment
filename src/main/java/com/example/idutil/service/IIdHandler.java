package com.example.idutil.service;

/**
 * id
 *
 */
public interface IIdHandler {


    /**
     * 获取id
     *
     * @param businessCode 业务代码
     * @return {@link String}
     */
    String getId(String businessCode);
}
