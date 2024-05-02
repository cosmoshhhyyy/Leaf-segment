package com.example.idutil.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * id类型
 *
 */
@Getter
@AllArgsConstructor
public enum IdType {

    UUID("UUID","UUID"),
    SNOWFLAKE("snowflake","雪花算法"),
    SEGMENT_MYSQL("segment_mysql","号段模式-mysql"),
    SEGMENT_REDIS("segment_redis","号段模式-redis");

    private String type;
    private String desc;
}
