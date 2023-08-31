package com.atguigu.myssm.baseDao.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 这时候一个获取和关闭connection的方法，是为了保证线程安全，
 * @author shkstart
 */

public class ConnUtil  {
    private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<>();
    public static final String DRIVE = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/fruitdb";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";
    public static Connection createConn(){ //创建一个connection
        try {
            Class.forName(DRIVE);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  static  Connection getConn(){
        Connection conn = threadLocalConn.get();
        if (conn == null){
            conn = createConn();
            threadLocalConn.set(conn);
        }
        return threadLocalConn.get();
    }

//    public static void setConn(Connection conn){
//
//    }

    public static void closeConn(Connection conn) throws SQLException {
        conn = threadLocalConn.get();
        if (conn == null){
            return;
        }
        if (!conn.isClosed()){
            conn.close();
            threadLocalConn.set(conn);
        }
    }
}
