package com.example.idutil.service;

/**
 * id
 *
 * @author cong.zhen
 * @date 2024/02/23
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
