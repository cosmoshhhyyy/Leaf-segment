package com.example.idutil.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.idutil.dao.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试映射器
 *
 */
@Mapper
public interface TestMapper extends BaseMapper<TestEntity> {
}
