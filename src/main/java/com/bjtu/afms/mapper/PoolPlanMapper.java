package com.bjtu.afms.mapper;

import com.bjtu.afms.model.PoolPlan;
import com.bjtu.afms.model.PoolPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PoolPlanMapper {
    long countByExample(PoolPlanExample example);

    int deleteByExample(PoolPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PoolPlan record);

    int insertSelective(PoolPlan record);

    List<PoolPlan> selectByExample(PoolPlanExample example);

    PoolPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PoolPlan record, @Param("example") PoolPlanExample example);

    int updateByExample(@Param("record") PoolPlan record, @Param("example") PoolPlanExample example);

    int updateByPrimaryKeySelective(PoolPlan record);

    int updateByPrimaryKey(PoolPlan record);
}