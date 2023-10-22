package com.hysea.hyseaappapi.dao;

import com.hysea.hyseaappapi.entity.Column;
import com.hysea.hyseaappapi.entity.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: hyy
 * Assistants:
 * Date: 2022/6/22 10:48
 * Annotation:
 */
@Mapper
public interface TableMapper {

    List<Table> selectTableNameList(String baseName);

    List<Column> selectColumnAll(@Param("tableName") String tableName,
                           @Param("baseName") String baseName);

}
