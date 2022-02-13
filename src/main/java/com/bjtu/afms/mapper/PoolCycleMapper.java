package com.bjtu.afms.mapper;

import com.bjtu.afms.model.PoolCycle;
import com.bjtu.afms.model.PoolCycleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PoolCycleMapper {
    long countByExample(PoolCycleExample example);

    int deleteByExample(PoolCycleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PoolCycle record);

    int insertSelective(PoolCycle record);

    List<PoolCycle> selectByExample(PoolCycleExample example);

    PoolCycle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PoolCycle record, @Param("example") PoolCycleExample example);

    int updateByExample(@Param("record") PoolCycle record, @Param("example") PoolCycleExample example);

    int updateByPrimaryKeySelective(PoolCycle record);

    int updateByPrimaryKey(PoolCycle record);
}