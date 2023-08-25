package com.atguigu.fruit.view;

import com.atguigu.fruit.controller.Menu;

public class Client {
    public static void main(String[] args) {
        boolean flag = true;
        Menu menu = new Menu();
        while (flag) {
            int slt = menu.showMenu();
            switch (slt){
                case 1:
                    menu.getAllFruits(); //查看全部
                    break;
                case 2:
                    menu.addFruits();//添加库存
                    break;

                case 3:
                    menu.getFruitsByNames();//获取特定的
                    break;
                case 4:
                    menu.deleteFruits();//删除
                    break;
                case 5:
                    flag = menu.eixt();//退出
                    System.out.println("再见！");
                    break;
                default:
                    System.out.println("错误");
            }
        }
    }
}
