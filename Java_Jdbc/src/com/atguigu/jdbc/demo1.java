package com.atguigu.jdbc;

import java.sql.*;

public class demo1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn =
                DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/fruitdb","root","123456");
        //fname price ,fcount ,remark
        String sql = "insert into t_fruit values(null,?,?,?,?)"; //sql语句
        PreparedStatement psmt = conn.prepareStatement(sql); //发给数据
        psmt.setString(1,"榴莲");
        psmt.setInt(2,100);
        psmt.setInt(3,10);
        psmt.setString(4,"好吃");
        int count = psmt.executeUpdate();//返回条数
        System.out.println(count>0?"成功":"失败");
        conn.close();
        psmt.close();
    }
}
