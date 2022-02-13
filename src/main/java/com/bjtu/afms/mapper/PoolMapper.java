package com.bjtu.afms.mapper;

import com.bjtu.afms.model.Pool;
import com.bjtu.afms.model.PoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PoolMapper {
    long countByExample(PoolExample example);

    int deleteByExample(PoolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Pool record);

    int insertSelective(Pool record);

    List<Pool> selectByExample(PoolExample example);

    Pool selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Pool record, @Param("example") PoolExample example);

    int updateByExample(@Param("record") Pool record, @Param("example") PoolExample example);

    int updateByPrimaryKeySelective(Pool record);

    int updateByPrimaryKey(Pool record);
}