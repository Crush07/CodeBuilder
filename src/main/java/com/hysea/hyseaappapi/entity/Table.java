package com.hysea.hyseaappapi.entity;

import lombok.Data;

import java.util.List;

@Data
public class Table {

    private String tableName;

    private String entityName;

    private String className;

    private List<Column> columnList;

    private Column primaryKey;

}
