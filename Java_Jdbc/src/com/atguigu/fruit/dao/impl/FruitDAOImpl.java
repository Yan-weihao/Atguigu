package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.pojo.Fruit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDAOImpl implements FruitDAO {


    final String DRIVE = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/fruitdb";
    final String USER = "root";
    final String PASSWORD = "123456";

    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;


    @Override
    public List<Fruit> getAllFruit() {
        List<Fruit> fruits;
        try {
            Class.forName(DRIVE);
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            String sql = "SELECT * FROM t_fruit";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            fruits = new ArrayList<>();
            while (rs.next()) {
                int fid = rs.getInt("fid");
                String fname = rs.getString("fname");
                int price = rs.getInt("price");
                int fcount = rs.getInt("fcount");
                String remark = rs.getString("remark");
                Fruit fruit = new Fruit(fid, fname, price, fcount, remark);
                fruits.add(fruit);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return fruits;
    }

    @Override
    public boolean addFruit(Fruit fruit) {
        try {
            Class.forName(DRIVE);
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            String sql = "INSERT INTO t_fruit VALUES(0,?,?,?,?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fruit.getFname());
            pstmt.setInt(2, fruit.getPrice());
            pstmt.setInt(3, fruit.getFcount());
            pstmt.setString(4, fruit.getRemark());
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
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
    }

    @Override
    public boolean updateFruit(Fruit fruit) {
        try {
            Class.forName(DRIVE);
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            String sql = "UPDATE t_fruit SET fcount = ? WHERE fid = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, fruit.getFcount());
            pstmt.setInt(2, fruit.getFid());
            return pstmt.executeUpdate()>0;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
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
    }
    @Override
    public boolean deleteFruit(String name) { //使用删除
        try {
            Class.forName(DRIVE);
            conn = DriverManager.getConnection(URL);
            String sql = "DELETE FROM t_fruit WHERE fname =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
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
    }

    @Override
    public Fruit getFruitByName(String name) {
        try {
            Class.forName(DRIVE);
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            String sql = "SELECT * FROM t_fruit WHERE fname = ?";
            pstmt =conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int fid = rs.getInt("fid");
                String fname = rs.getString("fname");
                int price = rs.getInt("price");
                int fcount = rs.getInt("fcount");
                String remark = rs.getString("remark");
                Fruit fruit = new Fruit(fid, fname, price, fcount, remark);
                return fruit;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
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
        return null;
    }
}
