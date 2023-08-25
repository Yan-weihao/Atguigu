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
        return executeQuery("SELECT * FROM t_fruit");
    }

    @Override
    public Fruit getFruitByName(String name) {
        return load("SELECT * FROM t_fruit WHERE fname =?",name);
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
