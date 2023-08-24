package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.base.BaseDAO;
import com.atguigu.fruit.pojo.Fruit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {



    @Override
    public List<Fruit> getAllFruit() {
        List<Fruit> fruits;
        try {

            String sql = "SELECT * FROM t_fruit";

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

        } catch ( SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, pstmt, conn);
        }
        return fruits;
    }

    @Override
    public Fruit getFruitByName(String name) {
        try {

            String sql = "SELECT * FROM t_fruit WHERE fname = ?";

            if (rs.next()) {
                int fid = rs.getInt("fid");
                String fname = rs.getString("fname");
                int price = rs.getInt("price");
                int fcount = rs.getInt("fcount");
                String remark = rs.getString("remark");
                return new Fruit(fid, fname, price, fcount, remark);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rs, pstmt, conn);
        }
        return null;
    }

    @Override
    public boolean addFruit(Fruit fruit) {
        String sql = "INSERT INTO t_fruit VALUES(0,?,?,?,?)";
        return executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(),fruit.getPrice()) >0;
    }

    @Override
    public boolean updateFruit(Fruit fruit) {

            String sql = "UPDATE t_fruit SET fcount = ? WHERE fid = ? ";
            int sum = executeUpdate(sql, fruit.getFname(), fruit.getFid());
            return sum > 0;
    }
    @Override
    public boolean deleteFruit(String name) { //使用删除

        String sql = "DELETE FROM t_fruit WHERE fname =?";
        return executeUpdate(sql, name) > 0;

    }

}
