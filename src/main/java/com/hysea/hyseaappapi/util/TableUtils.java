package com.hysea.hyseaappapi.util;

import com.hysea.hyseaappapi.entity.Column;
import com.hysea.hyseaappapi.entity.Table;
import com.hysea.hyseaappapi.service.TableService;
import com.hysea.hyseaappapi.service.impl.TableServiceImpl;

import java.util.List;

public class TableUtils {

    public static List<Table> getTableList(){
        TableService tableService = new TableServiceImpl();
        List<Table> jar = tableService.getAllTableInformation();

        for(Table table : jar) {

            table.setEntityName(CodingStandardUtils.camelCase(table.getTableName(), false));

            table.setClassName(CodingStandardUtils.camelCase(table.getTableName(), true));

            List<Column> columnList = table.getColumnList();

            for (Column column : columnList) {
                column.setJavaName(CodingStandardUtils.camelCase(column.getColumnName(), false));
                column.setFunctionJavaName(CodingStandardUtils.camelCase(column.getColumnName(), true));
                column.setMybatisName("#{"+CodingStandardUtils.camelCase(column.getColumnName(), false)+"}");
                column.setQuoteMybatisName("#{?."+CodingStandardUtils.camelCase(column.getColumnName(), false)+"}");
                column.setInsertName("`"+column.getColumnName()+"`");
                column.setColumnType(CodingStandardUtils.toJavaType(column.getColumnType()));
            }
        }
        return jar;
    }

}
