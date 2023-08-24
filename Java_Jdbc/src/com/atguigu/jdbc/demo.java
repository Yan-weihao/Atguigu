package com.atguigu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class demo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动
        String url = "jdbc:mysql://localhost:3306/fruitdb";
        String user = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url,user,password); //获取连接
        System.out.println("conn"+conn);
    }
}
