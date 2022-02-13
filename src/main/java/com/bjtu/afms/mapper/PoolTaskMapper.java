package com.bjtu.afms.mapper;

import com.bjtu.afms.model.PoolTask;
import com.bjtu.afms.model.PoolTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PoolTaskMapper {
    long countByExample(PoolTaskExample example);

    int deleteByExample(PoolTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PoolTask record);

    int insertSelective(PoolTask record);

    List<PoolTask> selectByExample(PoolTaskExample example);

    PoolTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PoolTask record, @Param("example") PoolTaskExample example);

    int updateByExample(@Param("record") PoolTask record, @Param("example") PoolTaskExample example);

    int updateByPrimaryKeySelective(PoolTask record);

    int updateByPrimaryKey(PoolTask record);
}