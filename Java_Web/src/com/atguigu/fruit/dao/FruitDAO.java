package com.atguigu.fruit.dao;

import com.atguigu.fruit.pojo.Fruit;

import java.util.ArrayList;
import java.util.List;

public interface FruitDAO {
    public List<Fruit> getAllFruit();

    public List<Fruit> getFruitPageNo(int pageNo);

    public boolean addFruit(Fruit fruit);

    public boolean updateFruit(Fruit fruit);

    public boolean deleteFruit(int id);

    public Fruit getFruitByName(String name);

    public Fruit getFruitById(int id);

    public int getFruitCount();
}
