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
                    menu.getAllFruits();
                    break;
                case 2:
                    menu.addFruits();
                    break;

                case 3:
                    menu.getFruitsByNames();
                    break;
                case 4:
                    menu.deleteFruits();
                    break;
                case 5:
                    flag = menu.eixt();
                    System.out.println("再见！");
                    break;
                default:
                    System.out.println("错误");
            }
        }
    }
}
