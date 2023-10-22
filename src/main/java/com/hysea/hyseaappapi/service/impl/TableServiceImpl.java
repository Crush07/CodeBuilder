package com.hysea.hyseaappapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hysea.hyseaappapi.config.DataManager;
import com.hysea.hyseaappapi.dao.TableMapper;
import com.hysea.hyseaappapi.entity.Column;
import com.hysea.hyseaappapi.entity.Table;
import com.hysea.hyseaappapi.service.TableService;
import com.hysea.hyseaappapi.util.CodingStandardUtils;
import com.hysea.hyseaappapi.util.Matchers;
import lombok.extern.log4j.Log4j;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import java.util.logging.Logger;

public class TableServiceImpl implements TableService {
    private Logger logger = Logger.getLogger("");

    @Override
    public List<Table> getAllTableInformation() {

        String baseName = "";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            //builder
            DocumentBuilder builder = factory.newDocumentBuilder();
            //解析xml文档
            Document doc = builder.parse("src/main/resources/mybatis-config.xml");
            NodeList property = doc.getElementsByTagName("configuration")
                    .item(0).getOwnerDocument().getElementsByTagName("environments")
                    .item(0).getOwnerDocument().getElementsByTagName("environment")
                    .item(0).getOwnerDocument().getElementsByTagName("dataSource")
                    .item(0).getOwnerDocument().getElementsByTagName("property");

            for(int i = 0;i < property.getLength();i++){
                NamedNodeMap attributes = property.item(i).getAttributes();
                if("url".equals(attributes.getNamedItem("name").getTextContent())){
                    String value = attributes.getNamedItem("value").getTextContent();
                    baseName = Matchers.getStringByRegex("/[^/?]*\\?",value).get(0).replace("/","").replace("?","");
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        TableMapper tableMapper = DataManager.getSession().getMapper(TableMapper.class);

        List<Table> tables = tableMapper.selectTableNameList(baseName);
        for(int i = 0;i < tables.size();i++){
            tables.get(i).setColumnList(tableMapper.selectColumnAll(tables.get(i).getTableName(),baseName));
            tables.get(i).setEntityName(CodingStandardUtils.camelCase(tables.get(i).getTableName(), false));
            tables.get(i).setClassName(CodingStandardUtils.camelCase(tables.get(i).getTableName(), true));
            for (Column column : tables.get(i).getColumnList()) {
                column.setJavaName(CodingStandardUtils.camelCase(column.getColumnName(), false));
                column.setFunctionJavaName(CodingStandardUtils.camelCase(column.getColumnName(), true));
                column.setMybatisName("#{"+CodingStandardUtils.camelCase(column.getColumnName(), false)+"}");
                column.setQuoteMybatisName("#{?."+CodingStandardUtils.camelCase(column.getColumnName(), false)+"}");
                column.setInsertName("`"+column.getColumnName()+"`");
                column.setColumnType(CodingStandardUtils.toJavaType(column.getColumnType()));
                if(column.getIsPrimaryKey()){
                    tables.get(i).setPrimaryKey(column);
                }
            }
        }
        return tables;
    }
}
