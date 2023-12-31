package com.atguigu.fruit.controller;

import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;

import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner input  = new Scanner(System.in);
    FruitDAOImpl fruitDaoImpl = new FruitDAOImpl();
    public int showMenu(){
        System.out.println("=================欢迎使用水果库存系统=====================");
        System.out.println("1.查看水果库存列表");
        System.out.println("2.添加水果库存信息");
        System.out.println("3.查看特定水果库存信息");
        System.out.println("4.水果下架");
        System.out.println("5.退出");
        System.out.println("======================================================");
        System.out.print("请选择：");
        return input.nextInt();
    }
    public boolean eixt(){
        System.out.print("是否确认退出？(Y/N)");
        String slt = input.next();
        return !slt.equalsIgnoreCase("Y");
    }

    public void getAllFruits() {

         List<Fruit> lists = fruitDaoImpl.getAllFruit();

         lists.forEach(System.out::println);
    }

    public void addFruits() {
        Fruit fruit = new Fruit();
        System.out.print("输入你要添加的水果:");
        String name = input.next();
        System.out.print("请输入库存数量: ");
        int count = input.nextInt();
        fruit = fruitDaoImpl.getFruitByName(name);
        if (fruit!= null) {
            updateFruits(fruit);
        }else {
            insertFruits(name, count);
        }
    }

    public void insertFruits(String name , int count) {
        Fruit fruit = new Fruit();
        fruit.setFname(name);
        fruit.setFcount(count);
        System.out.print("请输入单价: ");
        fruit.setPrice(input.nextInt());
        System.out.print("请输入添加备注: ");
        fruit.setRemark(input.next());
        fruitDaoImpl.addFruit(fruit);
    }
    public void updateFruits(Fruit fruit) {
        fruitDaoImpl.updateFruit(fruit);
    }
    public void getFruitsByNames() {
        System.out.print("输入你要查询的水果:");
        String name = input.next();
        Fruit fruit = fruitDaoImpl.getFruitByName(name);
        if (fruit!= null) {
            System.out.println( fruit.toString());
        }
        else {
            System.out.println("数据库中没有这个水果");
        }
    }


    public void deleteFruits() {
        System.out.print("请输入要删除的水果名称: ");
        String name = input.next();
        fruitDaoImpl.deleteFruit(name);

    }
}
