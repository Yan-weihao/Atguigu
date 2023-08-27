package com.atguigu.fruit.dao;

import com.atguigu.fruit.pojo.Fruit;

import java.util.ArrayList;
import java.util.List;

public interface FruitDAO {
    public List<Fruit> getAllFruit();

    public boolean addFruit(Fruit fruit);

    public boolean updateFruit(Fruit fruit);

    public boolean deleteFruit(String name);

    public Fruit getFruitByName(String name);

    public Fruit getFruitById(int id);
}
