package com.bjtu.afms.mapper;

import com.bjtu.afms.model.Verify;
import com.bjtu.afms.model.VerifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VerifyMapper {
    long countByExample(VerifyExample example);

    int deleteByExample(VerifyExample example);

    int deleteByPrimaryKey(String phone);

    int insert(Verify record);

    int insertSelective(Verify record);

    List<Verify> selectByExample(VerifyExample example);

    Verify selectByPrimaryKey(String phone);

    int updateByExampleSelective(@Param("record") Verify record, @Param("example") VerifyExample example);

    int updateByExample(@Param("record") Verify record, @Param("example") VerifyExample example);

    int updateByPrimaryKeySelective(Verify record);

    int updateByPrimaryKey(Verify record);
}