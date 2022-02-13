package com.bjtu.afms.mapper;

import com.bjtu.afms.model.Alert;
import com.bjtu.afms.model.AlertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlertMapper {
    long countByExample(AlertExample example);

    int deleteByExample(AlertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Alert record);

    int insertSelective(Alert record);

    List<Alert> selectByExample(AlertExample example);

    Alert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Alert record, @Param("example") AlertExample example);

    int updateByExample(@Param("record") Alert record, @Param("example") AlertExample example);

    int updateByPrimaryKeySelective(Alert record);

    int updateByPrimaryKey(Alert record);
}