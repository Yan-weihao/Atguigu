package com.atguigu.myssm.baseDao.base;


/**
 * 处理DAO层异常
 */
public class DAOException extends RuntimeException{
    public DAOException(String message) {
        super(message);
    }
}
