package com.hysea.hyseaappapi.util;

import com.hysea.hyseaappapi.constant.MapperConstant;
import com.hysea.hyseaappapi.constant.MybatisConstant;
import com.hysea.hyseaappapi.entity.Column;
import com.hysea.hyseaappapi.entity.Table;

import java.util.List;

import static com.hysea.hyseaappapi.constant.MybatisConstant.WHERE_ID;

public class CodingBuilderUtils {

    public static void builderColumn(List<Column> columnList,String nowRes,Integer index,List<String> columnRes){

        if(index == columnList.size()){
            String s = nowRes.replaceAll("^,", "");
            if(!s.equals("")){
                columnRes.add(s);
            }
        }else{
            builderColumn(columnList,nowRes+","+columnList.get(index).getJavaName(),index+1,columnRes);

            builderColumn(columnList,nowRes,index+1,columnRes);
        }
    }

    public static void builderWhere(List<Column> columnList,String nowRes,Integer index,List<String> columnRes){

        if(index == columnList.size()){
            String s = nowRes.replaceAll("^ and ", "");
            columnRes.add(s);
        }else{
            builderColumn(columnList,nowRes +
                    " and " +
                    columnList.get(index).getColumnName() +
                    " = #{" +
                    columnList.get(index).getJavaName() +
                    "} \n",index+1,columnRes);

            builderColumn(columnList,nowRes,index+1,columnRes);
        }
    }

    public static void builderFunctionWhere(List<Column> columnList,String nowRes,Integer index,List<String> columnRes){

        if(index == columnList.size()){
            columnRes.add(nowRes);
        }else{
            builderColumn(columnList,nowRes +
                    CodingStandardUtils.camelCase(columnList.get(index).getColumnName(),true),index+1,columnRes);
            builderColumn(columnList,nowRes,index+1,columnRes);
        }
    }

    public static String buildSelectObjectById(Table table){

        String select = MapperConstant.OPERATE_CONTENT.replace(MapperConstant.OPERATE, "select");
        String id = "select_"+table.getTableName()+"_by_id";
        select = select.replace(MapperConstant.ID,CodingStandardUtils.camelCase(id,false));
        select = select.replace(MapperConstant.RESULT_MAP,CodingStandardUtils.camelCase(table.getTableName(),true));

        String sql = MybatisConstant.SELECT_SQL.replace(MybatisConstant.COLUMN,"*");
        sql = sql.replace(MybatisConstant.WHERE,MybatisConstant.WHERE_ID);
        sql = sql.replace(MybatisConstant.TABLE_NAME,table.getTableName());

        select = select.replace(MapperConstant.SQL,sql);

        return select;
    }

    public static String buildInsertObject(Table table){
        String insert = MapperConstant.OPERATE_CONTENT.replace(MapperConstant.OPERATE, "insert");
        String id = "insert_"+table.getTableName();
        insert = insert.replace(MapperConstant.ID,CodingStandardUtils.camelCase(id,false));
        insert = insert.replace(MapperConstant.RESULT_MAP,"");
        insert = insert.replace("resultMap=\"\"","");


        StringBuilder insertColumn = new StringBuilder();
        StringBuilder insertValue = new StringBuilder();

        for(int i = 0;i < table.getColumnList().size();i++){
            if(i < table.getColumnList().size() - 1){
                insertColumn.append(table.getColumnList().get(i).getColumnName()).append(", ");
                insertValue.append("#{").append(table.getColumnList().get(i).getJavaName()).append("}, ");
            }else{
                insertColumn.append(table.getColumnList().get(i).getColumnName());
                insertValue.append("#{").append(table.getColumnList().get(i).getJavaName()).append("}");
            }
        }

        String sql = MybatisConstant.INSERT_SQL.replace(MybatisConstant.COLUMN, insertColumn.toString());
        sql = sql.replace(MybatisConstant.VALUES,insertValue.toString());
        sql = sql.replace(MybatisConstant.TABLE_NAME,table.getTableName());

        insert = insert.replace(MapperConstant.SQL,sql);

        return insert;
    }

    public static String buildUpdateObjectById(Table table){
        String update = MapperConstant.OPERATE_CONTENT.replace(MapperConstant.OPERATE, "update");
        String id = "update_"+table.getTableName() + "_by_id";
        update = update.replace(MapperConstant.ID,CodingStandardUtils.camelCase(id,false));
        update = update.replace(MapperConstant.RESULT_MAP,"");
        update = update.replace("resultMap=\"\"","");

        StringBuilder setColumn = new StringBuilder();

        for(int i = 0;i < table.getColumnList().size();i++){
            String setIf = MybatisConstant.UPDATE_SET_IF.replace(MybatisConstant.COLUMN_NAME, table.getColumnList().get(i).getColumnName()).replace(MybatisConstant.JAVA_NAME, table.getColumnList().get(i).getJavaName());
            if(i < table.getColumnList().size() - 1){
                setColumn.append(setIf);
            }else{
                setIf = setIf.replace(",\n\t\t\t</if>\n","\n\t\t\t</if>\n");
                setColumn.append(setIf);
            }
        }
        String sql = MybatisConstant.UPDATE_SQL.replace(MybatisConstant.SET,MybatisConstant.UPDATE_SET.replace(MybatisConstant.SET,setColumn.toString()));
        sql = sql.replace(MybatisConstant.WHERE,MybatisConstant.WHERE_ID);
        sql = sql.replace(MybatisConstant.TABLE_NAME,table.getTableName());

        update = update.replace(MapperConstant.SQL,sql);

        return update;
    }

    public static String buildDeleteObjectById(Table table){
        String delete = MapperConstant.OPERATE_CONTENT.replace(MapperConstant.OPERATE, "delete");
        String id = "delete_" + table.getTableName() + "_by_id";
        delete = delete.replace(MapperConstant.ID,CodingStandardUtils.camelCase(id,false));
        delete = delete.replace(MapperConstant.RESULT_MAP,"");
        delete = delete.replace("resultMap=\"\"","");

        String sql = MybatisConstant.DELETE_SQL.replace(MybatisConstant.WHERE,MybatisConstant.WHERE_ID)
                .replace(MybatisConstant.TABLE_NAME,table.getTableName());

        delete = delete.replace(MapperConstant.SQL,sql);

        return delete;
    }

    public static String buildSelectAll(Table table){
        String select = MapperConstant.OPERATE_CONTENT.replace(MapperConstant.OPERATE, "select");
        String id = "select_all";
        select = select.replace(MapperConstant.ID,CodingStandardUtils.camelCase(id,false));
        select = select.replace(MapperConstant.RESULT_MAP,CodingStandardUtils.camelCase(table.getTableName(),true));

        String sql = MybatisConstant.SELECT_SQL.replace(MybatisConstant.COLUMN,"*")
                .replace(MybatisConstant.WHERE,"")
                .replace(MybatisConstant.TABLE_NAME,table.getTableName());

        select = select.replace(MapperConstant.SQL,sql);

        return select;
    }

    public static String buildSelectAllBySequence(Table table,Long selectNum,Long whereNum,boolean isList){
        String select = MapperConstant.OPERATE_CONTENT.replace(MapperConstant.OPERATE, "select");
        select = select.replace(MapperConstant.RESULT_MAP,CodingStandardUtils.camelCase(table.getTableName(),true));

        StringBuilder selectStr = new StringBuilder(Long.toBinaryString(selectNum));
        StringBuilder whereStr = new StringBuilder(Long.toBinaryString(whereNum));

        int length = selectStr.length();
        int length1 = whereStr.length();

        for(int i = 0;i < table.getColumnList().size() - length;i++){
            selectStr.insert(0, "0");
        }

        for(int i = 0;i < table.getColumnList().size() - length1;i++){
            whereStr.insert(0, "0");
        }

        StringBuilder selectName = new StringBuilder();
        StringBuilder whereName = new StringBuilder();
        StringBuilder selectSql = new StringBuilder();
        StringBuilder whereSql = new StringBuilder("\t\twhere ");

        for(int i = 0;i < table.getColumnList().size();i++){
            if(selectStr.toString().charAt(i) == '1'){
                selectName.append(CodingStandardUtils.camelCase(table.getColumnList().get(i).getColumnName(),true));
                selectSql.append(table.getColumnList().get(i).getColumnName()).append(",");
            }

            if(whereStr.toString().charAt(i) == '1'){
                whereName.append(CodingStandardUtils.camelCase(table.getColumnList().get(i).getColumnName(),true)).append("And");
                whereSql.append(table.getColumnList().get(i).getColumnName()).append(" = #{")
                        .append(CodingStandardUtils.camelCase(table.getColumnList().get(i).getColumnName(),false))
                        .append("}\n \t\tand ");
            }
        }

        selectSql = new StringBuilder(selectSql.toString().replaceAll(",$",""));
        whereName = new StringBuilder(whereName.toString().replaceAll("And$",""));
        whereSql = new StringBuilder(whereSql.toString().replaceAll("and $",""));

        String id;
        if(isList){
            id = "select" + selectName + "ListBy" + whereName;
        }else{
            id = "select" + selectName + "By" + whereName;
        }
        select = select.replace(MapperConstant.ID,CodingStandardUtils.camelCase(id,false));

        String sql = MybatisConstant.SELECT_SQL.replace(MybatisConstant.COLUMN,selectSql.toString())
                .replace(MybatisConstant.WHERE,whereSql.toString())
                .replace(MybatisConstant.TABLE_NAME,table.getTableName());

        select = select.replace(MapperConstant.SQL,sql);

        return select;
    }

    public static String buildSelectAllByPage(Table table){
        String select = MapperConstant.OPERATE_CONTENT.replace(MapperConstant.OPERATE, "select");
        String id = "select_all_by_page";
        select = select.replace(MapperConstant.ID,CodingStandardUtils.camelCase(id,false));
        select = select.replace(MapperConstant.RESULT_MAP,CodingStandardUtils.camelCase(table.getTableName(),true));

        String sql = MybatisConstant.SELECT_SQL.replace(MybatisConstant.COLUMN,"*")
                .replace(MybatisConstant.WHERE,"")
                .replace(MybatisConstant.TABLE_NAME,table.getTableName());

        select = select.replace(MapperConstant.SQL,sql);

        return select;
    }

}
