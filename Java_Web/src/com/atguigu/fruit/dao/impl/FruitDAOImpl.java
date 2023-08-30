package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.myssm.baseDao.base.BaseDAO;
import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {



    @Override
    public List<Fruit> getAllFruit() {
        return executeQuery("SELECT * FROM t_fruit");
    }

    @Override
    public List<Fruit> getFruitPageNo(int pageNo ,String keyword) {
        pageNo = (pageNo - 1) * 5;
        return executeQuery("select * from t_fruit  WHERE remark LIKE ? OR fname LIKE ? LIMIT ? , 5", "%"+keyword+"%","%"+keyword+"%",pageNo);
    }

    @Override
    public Fruit getFruitByName(String name) {
        return load("SELECT * FROM t_fruit WHERE fname =?",name);
    }

    @Override
    public Fruit getFruitById(int id) {
        return load("SELECT * FROM t_fruit WHERE fid =?",id);
    }

    @Override
    public int getFruitCount(String keyword) {
      return ((Long)cont("select count(*) from t_fruit where fname like ? or remark like ?" , "%"+keyword+"%","%"+keyword+"%")[0]).intValue();
    }
    @Override
    public boolean addFruit(Fruit fruit) {
        String sql = "INSERT INTO t_fruit VALUES(0,?,?,?,?)";
        return executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(),fruit.getRemark()) >0;
    }

    @Override
    public boolean updateFruit(Fruit fruit) {

            String sql = "UPDATE t_fruit SET fname = ? , price = ? , fcount = ? , remark = ?  WHERE fid = ? ";
            int sum = executeUpdate(sql, fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(), fruit.getFid());
            return sum > 0;
    }
    @Override
    public boolean deleteFruit(int id) { //使用删除
        String sql = "DELETE FROM t_fruit WHERE fid =?";
        return executeUpdate(sql, id) > 0;
    }
}
