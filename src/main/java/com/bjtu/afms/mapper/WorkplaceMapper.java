package com.bjtu.afms.mapper;

import com.bjtu.afms.model.Workplace;
import com.bjtu.afms.model.WorkplaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkplaceMapper {
    long countByExample(WorkplaceExample example);

    int deleteByExample(WorkplaceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Workplace record);

    int insertSelective(Workplace record);

    List<Workplace> selectByExample(WorkplaceExample example);

    Workplace selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Workplace record, @Param("example") WorkplaceExample example);

    int updateByExample(@Param("record") Workplace record, @Param("example") WorkplaceExample example);

    int updateByPrimaryKeySelective(Workplace record);

    int updateByPrimaryKey(Workplace record);
}