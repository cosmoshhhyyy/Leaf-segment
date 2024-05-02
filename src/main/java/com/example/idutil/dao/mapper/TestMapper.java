package com.example.idutil.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcby.idutil.dao.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试映射器
 *
 * @author cong.zhen
 * @date 2024/02/26
 */
@Mapper
public interface TestMapper extends BaseMapper<TestEntity> {
}
