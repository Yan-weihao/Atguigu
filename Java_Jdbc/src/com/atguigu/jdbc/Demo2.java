package com.atguigu.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Demo2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/fruitdb","root","123456");
        String sql = "select * from t_fruit";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Fruit01> fruit01s = new ArrayList<>();
        while (rs.next()){
            int fid = rs.getInt("fid");
            String fname = rs.getString("fname");
            int price = rs.getInt("price");
            int fcount = rs.getInt("fcount");
            String remark = rs.getString("remark");
            Fruit01 fruit01 = new Fruit01(fid,fname,price,fcount,remark);
            fruit01s.add(fruit01);
        }
        rs.close();
        ps.close();
        conn.close();
        fruit01s.forEach(System.out::println);
    }
}
