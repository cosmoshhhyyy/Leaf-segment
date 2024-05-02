package com.example.idutil.dao.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author cong.zhen
 * @date 2024/02/25
 */
@Data
@TableName("leaf_alloc")
public class SegmentEntity {

    /**
     * 应用编码
     */
    @TableId
    private String businessCode;


    /**
     * 当前最大id
     */
    private Long maxId;


    /**
     * 步长
     */
    private Integer step;


    /**
     * 更新时间
     */
    private Date updateTime;
}
