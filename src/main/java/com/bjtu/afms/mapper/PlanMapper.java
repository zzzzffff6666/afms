package com.bjtu.afms.mapper;

import com.bjtu.afms.model.Plan;
import com.bjtu.afms.model.PlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlanMapper {
    long countByExample(PlanExample example);

    int deleteByExample(PlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Plan record);

    int insertSelective(Plan record);

    List<Plan> selectByExample(PlanExample example);

    Plan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Plan record, @Param("example") PlanExample example);

    int updateByExample(@Param("record") Plan record, @Param("example") PlanExample example);

    int updateByPrimaryKeySelective(Plan record);

    int updateByPrimaryKey(Plan record);
}