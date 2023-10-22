package com.hysea.hyseaappapi.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hysea.hyseaappapi.constant.SQLConstant;
import com.hysea.hyseaappapi.entity.Column;
import com.hysea.hyseaappapi.entity.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateUtils {

    private static final List<Table> tableList;

    static{
        tableList = TableUtils.getTableList();
    }

    public static String buildInsert(int tableIndex){

        Table table = tableList.get(tableIndex);
        String insert = SQLConstant.INSERT;

        //第一个? id
        String id = "insert"+table.getClassName();
        insert = insert.replaceFirst("\\?",id);

        //第二个? 主键
        insert = insert.replaceFirst("\\?",table.getPrimaryKey().getJavaName());

        //第三个? 表名
        insert = insert.replaceFirst("\\?",table.getTableName());

        //第四个? 列名排列
        insert = insert.replaceFirst("\\?",StringUtils.stringListToString(table.getColumnList().stream().map(Column::getInsertName).collect(Collectors.toList()),","));

        //第五个? 占位变量排列
        insert = insert.replaceFirst("\\?",StringUtils.stringListToString(table.getColumnList().stream().map(Column::getMybatisName).collect(Collectors.toList()),","));

        return insert;
    }

    public static String buildBatchInsert(int tableIndex){

        Table table = tableList.get(tableIndex);
        String insert = SQLConstant.BATCH_INSERT;

        //第一个? id
        String id = "batchInsert"+table.getClassName();
        insert = insert.replaceFirst("\\?",id);

        //第二个? 主键
        insert = insert.replaceFirst("\\?",table.getPrimaryKey().getJavaName());

        //第三个? 表名
        insert = insert.replaceFirst("\\?",table.getTableName());

        //第四个? 列名排列
        insert = insert.replaceFirst("\\?",StringUtils.stringListToString(table.getColumnList().stream().map(Column::getInsertName).collect(Collectors.toList()),","));

        //第五个? 集合名 批量插入的集合名默认为对象名加List
        insert = insert.replaceFirst("\\?",table.getEntityName()+"List");

        //第六个? 集合元素名 默认为对象名
        insert = insert.replaceFirst("\\?",table.getEntityName());

        //第七个? 占位变量排列 #{集合元素名.属性名}
        insert = insert.replaceFirst("\\?",StringUtils.stringListToString(table.getColumnList().stream().map(Column::getQuoteMybatisName).collect(Collectors.toList()),",").replace("?",table.getEntityName()));

        return insert;
    }

    public static String buildSelect(int tableIndex, List<Integer> columnIndex, List<Integer> whereIndex){
        Table table = tableList.get(tableIndex);
        String select = SQLConstant.SELECT;

        //获取column列名
        List<String> functionSelectJavaNameList = new ArrayList<>();
        for(int i = 0;i < columnIndex.size();i++){
            functionSelectJavaNameList.add(table.getColumnList().get(columnIndex.get(i)).getFunctionJavaName());
        }

        //获取where列名
        List<String> functionWhereJavaNameList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){
            functionWhereJavaNameList.add(table.getColumnList().get(whereIndex.get(i)).getFunctionJavaName());
        }

        //获取id 函数名 select后面的字符串
        String functionSelect = StringUtils.stringListToString(functionSelectJavaNameList, "And");

        //获取id 函数名 selectXXBy后面的字符串
        String functionByXX = StringUtils.stringListToString(functionWhereJavaNameList, "And");
        String id = "select";
        if(!"".equals(functionSelect)){
            id = id + functionSelect;
        }else{
            //如果select集合为空代表查全部列
            id = id + "All";
        }
        //如果where集合为空代表查全部行
        if(!"".equals(functionByXX)){
            id = id + "By" + functionByXX;
        }

        //第一个? id
        select = select.replaceFirst("\\?",id);

        //第二个? resultMap名 也是类名
        select = select.replaceFirst("\\?",table.getClassName());

        //第三个? 列名
        List<String> selectColumnNameList = new ArrayList<>();
        for(int i = 0;i < columnIndex.size();i++){
            selectColumnNameList.add(table.getColumnList().get(columnIndex.get(i)).getColumnName());
        }
        if(selectColumnNameList.size() == 0){
            select = select.replaceFirst("\\?","*");
        }else{
            select = select.replaceFirst("\\?",StringUtils.stringListToString(selectColumnNameList,","));
        }

        //第四个? 表名
        select = select.replaceFirst("\\?",table.getTableName());

        //第五个? where
        String whereElement =
                "? = #{?}";
        List<String> whereList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){

            String element =  whereElement.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getColumnName());
            element = element.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getJavaName());
            whereList.add(element);

        }
        String whereStr = StringUtils.stringListToString(whereList,"\n\tand ");
        if(!"".equals(whereStr)){
            whereStr = "\twhere " + whereStr;
            select = select.replaceFirst("\\?",whereStr);
        }else{
            select = select.replaceFirst("\\?\n",whereStr);
        }



        return select;
    }

    public static String buildSelectByPage(int tableIndex, List<Integer> columnIndex, List<Integer> whereIndex){
        Table table = tableList.get(tableIndex);
        String select = SQLConstant.SELECT;

        //获取column列名
        List<String> functionSelectJavaNameList = new ArrayList<>();
        for(int i = 0;i < columnIndex.size();i++){
            functionSelectJavaNameList.add(table.getColumnList().get(columnIndex.get(i)).getFunctionJavaName());
        }

        //获取where列名
        List<String> functionWhereJavaNameList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){
            functionWhereJavaNameList.add(table.getColumnList().get(whereIndex.get(i)).getFunctionJavaName());
        }

        //获取id 函数名 select后面的字符串
        String functionSelect = StringUtils.stringListToString(functionSelectJavaNameList, "And");

        //获取id 函数名 selectXXBy后面的字符串
        String functionByXX = StringUtils.stringListToString(functionWhereJavaNameList, "And");
        String id = "select";
        if(!"".equals(functionSelect)){
            id = id + functionSelect;
        }else{
            //如果select集合为空代表查全部列
            id = id + "All";
        }
        //如果where集合为空代表查全部行
        if(!"".equals(functionByXX)){
            id = id + "By" + functionByXX;
        }
        id = id + "ByPage";

        //第一个? id
        select = select.replaceFirst("\\?",id);

        //第二个? resultMap名 也是类名
        select = select.replaceFirst("\\?",table.getClassName());

        //第三个? 列名
        List<String> selectColumnNameList = new ArrayList<>();
        for(int i = 0;i < columnIndex.size();i++){
            selectColumnNameList.add(table.getColumnList().get(columnIndex.get(i)).getColumnName());
        }
        if(selectColumnNameList.size() == 0){
            select = select.replaceFirst("\\?","*");
        }else{
            select = select.replaceFirst("\\?",StringUtils.stringListToString(selectColumnNameList,","));
        }

        //第四个? 表名
        select = select.replaceFirst("\\?",table.getTableName());

        //第五个? where
        String whereElement =
                "? = #{?}";
        List<String> whereList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){

            String element =  whereElement.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getColumnName());
            element = element.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getJavaName());
            whereList.add(element);

        }
        String whereStr = StringUtils.stringListToString(whereList,"\n\tand ");
        if(!"".equals(whereStr)){
            whereStr = "\twhere " + whereStr;
        }

        select = select.replaceFirst("\\?",whereStr);

        return select;
    }

    public static String buildSelectCount(int tableIndex, List<Integer> whereIndex){
        Table table = tableList.get(tableIndex);
        String select = SQLConstant.SELECT;

        //获取where列名
        List<String> functionWhereJavaNameList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){
            functionWhereJavaNameList.add(table.getColumnList().get(whereIndex.get(i)).getFunctionJavaName());
        }

        //获取id 函数名 selectXXBy后面的字符串
        String functionByXX = StringUtils.stringListToString(functionWhereJavaNameList, "And");
        String id = "selectCount";
        //如果where集合为空代表查全部行
        if(!"".equals(functionByXX)){
            id = id + "By" + functionByXX;
        }

        //第一个? id
        select = select.replaceFirst("\\?",id);

        //第二个? resultMap名 也是类名
        select = select.replaceFirst("resultMap=\"\\?","resultType=\"java.lang.Integer");

        //第三个? count(*)
        select = select.replaceFirst("\\?","count(*)");

        //第四个? 表名
        select = select.replaceFirst("\\?",table.getTableName());

        //第五个? where
        String whereElement =
                "? = #{?}";
        List<String> whereList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){

            String element =  whereElement.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getColumnName());
            element = element.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getJavaName());
            whereList.add(element);

        }
        String whereStr = StringUtils.stringListToString(whereList,"\n\tand ");
        if(!"".equals(whereStr)){
            whereStr = "\twhere " + whereStr;
        }

        select = select.replaceFirst("\\?",whereStr);

        return select;
    }

    public static String buildUpdate(int tableIndex, List<Integer> whereIndex){
        Table table = tableList.get(tableIndex);
        String update = SQLConstant.UPDATE;

        //获取where列名
        List<String> functionJavaNameList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){
            functionJavaNameList.add(table.getColumnList().get(whereIndex.get(i)).getFunctionJavaName());
        }

        //获取id 函数名 updateXXBy后面的字符串
        String functionByXX = StringUtils.stringListToString(functionJavaNameList, "And");
        String id = "update" + table.getClassName();
        if(!"".equals(functionByXX)){
            id = id + "By" + functionByXX;
        }

        //第一个? id
        update = update.replaceFirst("\\?",id);

        //第二个? id 表名
        update = update.replaceFirst("\\?",table.getTableName());

        //第三个? if集合
        //构造一个if元素模板
        String ifElement =
                "\t<if test=\"? != null\">\n" +
                        "\t? = #{?},\n" +
                        "\t</if>";
        //构造if集合 (默认表第一个属性是id，不可更改) if集合没有id，和where中的属性
        List<String> ifList = new ArrayList<>();
        for(int i = 1;i < table.getColumnList().size();i++){

            int j;
            for(j = 0;j < whereIndex.size();j++){
                if(whereIndex.get(j) == i){
                    break;
                }
            }
            if(j < whereIndex.size()){
                continue;
            }

            String element =  ifElement.replaceFirst("\\?",table.getColumnList().get(i).getJavaName());
            element = element.replaceFirst("\\?",table.getColumnList().get(i).getColumnName());
            element = element.replaceFirst("\\?",table.getColumnList().get(i).getJavaName());
            ifList.add(element);

        }
        update = update.replaceFirst("\\?",StringUtils.stringListToString(ifList,"\n"));

        //第四个? where
        String whereElement =
                "? = #{?}";
        List<String> whereList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){

            String element =  whereElement.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getColumnName());
            element = element.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getJavaName());
            whereList.add(element);

        }
        String whereStr = StringUtils.stringListToString(whereList,"\n\tand ");
        if(!"".equals(whereStr)){
            whereStr = "\twhere " + whereStr;
        }
        update = update.replaceFirst("\\?",whereStr);

        return update;
    }

    public static String buildDelete(int tableIndex, List<Integer> whereIndex){
        Table table = tableList.get(tableIndex);
        String delete = SQLConstant.DELETE;

        //获取where列名
        List<String> functionJavaNameList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){
            functionJavaNameList.add(table.getColumnList().get(whereIndex.get(i)).getFunctionJavaName());
        }

        //获取id 函数名 updateXXBy后面的字符串
        String functionByXX = StringUtils.stringListToString(functionJavaNameList, "And");
        String id = "delete" + table.getClassName();
        if(!"".equals(functionByXX)){
            id = id + "By" + functionByXX;
        }

        //第一个? id
        delete = delete.replaceFirst("\\?",id);

        //第二个? 表名
        delete = delete.replaceFirst("\\?",table.getTableName());

        //第三个? where
        String whereElement =
                "? = #{?}";
        List<String> whereList = new ArrayList<>();
        for(int i = 0;i < whereIndex.size();i++){

            String element =  whereElement.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getColumnName());
            element = element.replaceFirst("\\?",table.getColumnList().get(whereIndex.get(i)).getJavaName());
            whereList.add(element);

        }
        String whereStr = StringUtils.stringListToString(whereList,"\n\tand ");
        if(!"".equals(whereStr)){
            whereStr = "\twhere " + whereStr;
        }

        delete = delete.replaceFirst("\\?",whereStr);

        return delete;
    }

    public static String buildSelectSqlByStr(String tableStr, String columnStr, String whereStr){
        String select = SQLConstant.SELECT_SQL;

        //第一个? 表名
        select = select.replaceFirst("\\?",columnStr);

        //第二个? 列名组合
        select = select.replaceFirst("\\?",tableStr);

        //第三个? 条件组合
        select = select.replaceFirst("\\?",whereStr);

        return select;
    }

    /**
     * 解析json生成sql
     * @param selectJson 示例
     * [
     *  {
     *      "table":xx,
     *      "select":[
     *          xx,
     *          xx
     *      ],
     *      "=":[
     *          xx
     *      ]
     *  },
     *  {
     *      "table":xx,
     *      "select":[
     *          xx,
     *          xx
     *      ],
     *      "=":[
     *          xx
     *      ],
     *      "!=":[
     *          xx
     *      ],
     *      "in sql":[
     *          {
     *              "table":xx,
     *              "select":[
     *                  xx,
     *                  xx
     *              ],
     *              "=":[
     *                  xx
     *              ],
     *              "!=":[
     *                  xx
     *              ]
     *          }
     *      ],
     *      "in":[
     *          xx
     *      ],
     *      "not in":[
     *          xx
     *      ],
     *      ">":[
     *          xx
     *      ],
     *      "<=":[
     *          xx
     *      ]
     *  }
     * ]
     * @return
     */
    public static String buildComplexSelect(JSONArray selectJson){

        List<String> tableStrList = new ArrayList<>();
        List<String> selectStrList = new ArrayList<>();
        List<String> whereStrList = new ArrayList<>();

        for(int i = 0;i < selectJson.size();i++){
            JSONObject temp = JSONObject.parseObject(JSONObject.toJSONString(selectJson.get(i)));
            if(i == 0){
                tableStrList.add(temp.getString("table"));
            }else{
                tableStrList.add("left join" + temp.getString("table") + "on ?");
            }

            selectStrList.addAll(JSONObject.parseArray(temp.getJSONArray("select").toJSONString(), String.class));

            String equalElement =
                    "? = #{?}";
            List<String> equalStrList = JSONObject.parseArray(temp.getJSONArray("=").toJSONString(), String.class);
            List<String> equalList = new ArrayList<>();
            for(int j = 0;j < equalStrList.size();j++){

                String element =  equalElement.replaceFirst("\\?",CodingStandardUtils.underLine(equalStrList.get(j)));
                element = element.replaceFirst("\\?",equalStrList.get(j));
                equalList.add(element);

            }
            String equalStr = StringUtils.stringListToString(equalList,"\n\tand ");
            whereStrList.add(equalStr);

//            String notEqualElement =
//                    "? != #{?}";
//            List<String> notEqualStrList = JSONObject.parseArray(temp.getJSONArray("!=").toJSONString(), String.class);
//            List<String> notEqualList = new ArrayList<>();
//            for(int j = 0;j < notEqualStrList.size();j++){
//
//                String element = notEqualElement.replaceFirst("\\?",CodingStandardUtils.underLine(notEqualStrList.get(j)));
//                element = element.replaceFirst("\\?",notEqualStrList.get(j));
//                notEqualList.add(element);
//
//            }
//            String notEqualStr = StringUtils.stringListToString(notEqualList,"\n\tand ");
//            whereStrList.add(notEqualStr);


        }

        String tableStr = StringUtils.stringListToString(tableStrList,"\n");
        String selectStr = StringUtils.stringListToString(selectStrList,",");
        String whereStr = StringUtils.stringListToString(whereStrList,"\n\tand ");
        String selectSql = buildSelectSqlByStr(tableStr, selectStr, whereStr);

        return "\t<select id=\"?\" resultType=\"java.util.HashMap\">\n" +
                selectSql +
                "\t</select>";
    }

}
