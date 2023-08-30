package com.atguigu.fruit.service;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

public interface FruitService {
    //分页查询
    public List<Fruit> getFruitByPage(String keyword, Integer pageNum);
    //根据id查询
    public Fruit getFruitById(Integer fid);
    //添加一个库存
    public void addFruit(Fruit fruit);
    //删除一个库存
    public void deleteFruit(Integer fid);
    //修改库存数量
    public void updateFruit(Fruit fruit);
    //总页数
    public int getFruitCount(String keyword);

}
