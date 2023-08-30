package com.atguigu.myssm.trans;

import com.atguigu.myssm.baseDao.base.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务处理实现类
 * @throws SQLException
 */

public class TransactionManager {

    /**
     * 开启事务
     * @throws SQLException
     */

    public static void beginTrans() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
    }
    //提交事务
    public static void commit() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.commit();
        ConnUtil.closeConn(conn);
    }
    //回滚事务
    public static void rollback() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.rollback();
        ConnUtil.closeConn(conn);
    }
}
