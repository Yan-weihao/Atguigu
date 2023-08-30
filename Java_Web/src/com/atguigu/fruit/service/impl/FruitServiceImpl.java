package com.atguigu.fruit.service.impl;

import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.fruit.service.FruitService;

import java.util.List;

public class FruitServiceImpl implements FruitService {
    FruitDAOImpl fruitDAO = null;
    @Override
    public List<Fruit> getFruitByPage(String keyword, Integer pageNum) {
        return fruitDAO.getFruitPageNo(pageNum, keyword);
    }

    @Override
    public Fruit getFruitById(Integer fid) {
        return fruitDAO.getFruitById(fid);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }

    @Override
    public void deleteFruit(Integer fid) {
        fruitDAO.deleteFruit(fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDAO.updateFruit(fruit);
    }

    @Override
    public int getFruitCount(String keyword) {
        int pageCount = fruitDAO.getFruitCount(keyword);
        pageCount = (pageCount+4)/5;
        return pageCount;
    }
}
