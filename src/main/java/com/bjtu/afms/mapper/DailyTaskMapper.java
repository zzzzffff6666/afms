package com.bjtu.afms.mapper;

import com.bjtu.afms.model.DailyTask;
import com.bjtu.afms.model.DailyTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DailyTaskMapper {
    long countByExample(DailyTaskExample example);

    int deleteByExample(DailyTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DailyTask record);

    int insertSelective(DailyTask record);

    List<DailyTask> selectByExample(DailyTaskExample example);

    DailyTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DailyTask record, @Param("example") DailyTaskExample example);

    int updateByExample(@Param("record") DailyTask record, @Param("example") DailyTaskExample example);

    int updateByPrimaryKeySelective(DailyTask record);

    int updateByPrimaryKey(DailyTask record);
}