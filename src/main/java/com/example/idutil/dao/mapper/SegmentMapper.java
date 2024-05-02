package com.example.idutil.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.idutil.dao.entity.SegmentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分段映射器
 *
 */
@Mapper
public interface SegmentMapper extends BaseMapper<SegmentEntity> {

    /**
     * 更新最大id
     *
     * @param businessCode 业务代码
     * @return boolean
     */
    public boolean updateMaxId(String businessCode);
}
