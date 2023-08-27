package com.atguigu.fruit.dao.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class BaseDAO <T>{ //抽象类
    public final String DRIVE = "com.mysql.cj.jdbc.Driver";
    public final String URL = "jdbc:mysql://localhost:3306/fruitdb";
    public final String USER = "root";
   public final String PASSWORD = "123456";
    public Connection conn;
    public PreparedStatement pstmt;
    public ResultSet rs;
    private Class entityClass; ;

    public BaseDAO() {
        //获取BaseDAO的Class,得到他自己的Class，他自己的类是在使用的时候赋予它的
        Type genericType = getClass().getGenericSuperclass();//
//        ParameterizedType 参数化类型
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();

        Type actualType = (Type) actualTypeArguments[0];
        try {
            entityClass = Class.forName(actualType.getTypeName());//获取到实体类
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {

        try {
            Class.forName(DRIVE);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException  | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (pstmt!= null) {
                pstmt.close();
            }
            if (conn!= null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected int executeUpdate(String sql, Object... params) { //增删改（更新）
        boolean insertfalg = false;
        try {
            conn = getConnection();
            insertfalg = sql.trim().toUpperCase().startsWith("INSERT");
            if (insertfalg){ //是插入语句
                pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            }else {
                pstmt = conn.prepareStatement(sql);
            }
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            int count = pstmt.executeUpdate();
            if (insertfalg) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return ((Long)rs.getLong(1)).intValue();
                }
            }
            return count;
        } catch ( SQLException e) {
            e.printStackTrace();
        }finally {
            close(rs,pstmt,conn);
        }
        return 0 ;
    }

    //通过反射技术给obj对象的property属性赋propertyValue值(将值给到响应的类)
    protected void setValue(T entity, String columnName, Object columnValue) {
        Class claszz = entity.getClass(); //或者
        try {
            Field field = claszz.getDeclaredField(columnName);
            if (field!= null) {
                field.setAccessible(true);
                field.set(entity, columnValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    //总数 返回例如统计结果
    protected Object [] cont(String sql, Object... params) {
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            rs = pstmt.executeQuery(); //
            ResultSetMetaData rsmd = rs.getMetaData(); //获取值返回的元数据
            int columnCount = rsmd.getColumnCount();//或者列数
            Object[] columnCountArr = new Object[columnCount]; //列数数组
            if (rs.next() ) {
                for (int i = 0; i <columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1); // 列中数据          3
                    columnCountArr[i] = columnValue;
                }
                return columnCountArr;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

      return null;
    }


    protected List<T> executeQuery(String sql, Object... params) {
        List<T> list = new ArrayList<>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            rs = pstmt.executeQuery(); //
            ResultSetMetaData rsmd = rs.getMetaData(); //获取值返回的元数据
            int columnCount = rsmd.getColumnCount(); //或者列数
            while (rs.next()) {
                T entity = (T) entityClass.newInstance(); //反射中new一个对象
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i+1);           //列名字          //fid   fname   price
                    Object columnValue = rs.getObject(i+1);    // 列中数据          33    苹果      5
                    setValue(entity,columnName,columnValue);
                }
                list.add(entity);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }finally {
            close(rs,pstmt,conn);
        }
        return list;
    }
    protected T load(String sql,Object... params){ //单个查询
        T entity = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                entity = (T) entityClass.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i+1);
                    Object columnValue = rs.getObject(i+1);
                    setValue(entity,columnName,columnValue);
                }
            }
            return entity;
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
