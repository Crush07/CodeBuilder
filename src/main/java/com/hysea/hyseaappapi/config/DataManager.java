package com.hysea.hyseaappapi.config;

import com.hysea.hyseaappapi.dao.TableMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;
 
public class DataManager {
    //实例化数据库会话工厂类
    static SqlSessionFactory factory;
    static {

        try {
            //加载主配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            //创建工厂对象
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            factory = builder.build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSession(){
        return factory.openSession();
    }
    public static void close(SqlSession sqlSession){
        sqlSession.close();
    }
}