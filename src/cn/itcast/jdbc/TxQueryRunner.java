package cn.itcast.jdbc;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * 这个类中的方法，自己来处理连接问题
 * 无需外界传递！
 * 通过JdbcUtils.getConnection()得到连接，可能是事务连接或者普通连接
 * 通过JdbcUtils.releaseConnection()完成对连接的释放，如果是普通连接，关闭之！
 * */
public class TxQueryRunner extends QueryRunner {
    @Override
    public int[] batch(String sql, Object[][] params) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        int[] ints = super.batch(connection, sql, params);
        JdbcUtils.releaseConnection(connection);
        return ints;
    }

    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        T t = super.query(connection, sql, rsh, params);
        JdbcUtils.releaseConnection(connection);

        return t;
    }

    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        T t = super.query(connection, sql, rsh);
        JdbcUtils.releaseConnection(connection);
        return t;
    }

    @Override
    public int update(String sql) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        int i = super.update(connection, sql);
        JdbcUtils.releaseConnection(connection);

        return i;
    }

    @Override
    public int update(String sql, Object param) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        int i = super.update(connection, sql, param);
        JdbcUtils.releaseConnection(connection);

        return i;
    }

    @Override
    public int update(String sql, Object... params) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        int i = super.update(connection, sql, params);
        JdbcUtils.releaseConnection(connection);

        return i;
    }

    @Override
    public <T> T insert(String sql, ResultSetHandler<T> rsh) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        T t = super.insert(connection, sql, rsh);
        JdbcUtils.releaseConnection(connection);

        return t;
    }

    @Override
    public <T> T insert(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        T t = super.insert(connection, sql, rsh, params);
        JdbcUtils.releaseConnection(connection);

        return t;
    }

    @Override
    public <T> T insertBatch(String sql, ResultSetHandler<T> rsh, Object[][] params) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        T t = super.insertBatch(connection, sql, rsh, params);
        JdbcUtils.releaseConnection(connection);

        return t;
    }

    @Override
    public int execute(String sql, Object... params) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        int i = super.execute(connection, sql, params);
        JdbcUtils.releaseConnection(connection);

        return i;
    }
}
