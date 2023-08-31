package com.atguigu.myssm.baseDao.base;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO <T>{ //抽象类

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
            throw new DAOException("BaseDAO类加载失败");
        }

    }

    public Connection getConnection() {
        return ConnUtil.getConn();
    }

    public void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {

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
            throw new DAOException("执行update语句失败");
        }
    }

    //通过反射技术给obj对象的property属性赋propertyValue值(将值给到响应的类)
    private void setValue(Object obj ,  String property , Object propertyValue) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class clazz = obj.getClass();

        //获取property这个字符串对应的属性名 ， 比如 "fid"  去找 obj对象中的 fid 属性
        Field field = clazz.getDeclaredField(property);
        if(field!=null){

            //获取当前字段的类型名称
            String typeName = field.getType().getName();
            //判断如果是自定义类型，则需要调用这个自定义类的带一个参数的构造方法，创建出这个自定义的实例对象，然后将实例对象赋值给这个属性
            if(isMyType(typeName)){
                //假设typeName是"com.atguigu.qqzone.pojo.UserBasic"
                Class typeNameClass = Class.forName(typeName);
                Constructor constructor = typeNameClass.getDeclaredConstructor(java.lang.Integer.class);
                propertyValue = constructor.newInstance(propertyValue);
            }
            field.setAccessible(true);
            field.set(obj,propertyValue);
        }
    }


    private static boolean isNotMyType(String typeName){
        return "java.lang.Integer".equals(typeName)
                || "java.lang.String".equals(typeName)
                || "java.util.Date".equals(typeName)
                || "java.sql.Date".equals(typeName)
                || "java.sql.Timestamp".equals(typeName);
    }

    private static boolean isMyType(String typeName){
        return !isNotMyType(typeName);
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
            throw new DAOException("执行查询语句失败，统计中总数");
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
        } catch (Exception e) {
            throw new DAOException("执行查询语句失败");
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
        } catch (Exception e) {
            throw new DAOException("执行单个查询语句失败");
        }
    }
}
