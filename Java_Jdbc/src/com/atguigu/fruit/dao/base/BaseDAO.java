package com.atguigu.fruit.dao.base;

import java.sql.*;
import java.util.List;

public abstract class BaseDAO <T>{ //抽象类
    public final String DRIVE = "com.mysql.cj.jdbc.Driver";
    public final String URL = "jdbc:mysql://localhost:3306/fruitdb";
    public final String USER = "root";
   public final String PASSWORD = "123456";
    public Connection conn;
    public PreparedStatement pstmt;
    public ResultSet rs;
    public BaseDAO() {

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
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
                return pstmt.executeUpdate();
            }
        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rs,pstmt,conn);
        }
        return 0;
    }

}
