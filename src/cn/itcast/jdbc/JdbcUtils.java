package cn.itcast.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtils {
    //事务专用连接
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();//内部为Map<线程名，Connection>
    /*
    * 配置文件的默认配置！必须给出c3p0-config.xml
    * */
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    /*
    * 返回连接对象
    * */
    public static Connection getConnection() throws SQLException {
        Connection connection = tl.get();
        //如果有事务，返回事务的Connection
        if(connection != null) return connection;
        return dataSource.getConnection();
    }
    /*
    * 返回连接池对象
    * */
    public static DataSource getDataSource(){
        return dataSource;
    }
    /*
    * 开启事务
    * 1.获取connection,并setAutoCommit(false)
    * 2. 还要保证Dao中使用的连接是我们刚刚创建的！
    * */
    public static void beginTransAction() throws Exception{
        Connection connection = tl.get();
        if(connection != null) throw new RuntimeException("已经开启了事务，不要重复开启！");
        /*
        * 1.获取Connection
        * 2.设置手动提交
        * */
        connection = getConnection();
        connection.setAutoCommit(false);
        //将Connection存到当前线程中
        tl.set(connection);

    }
    /*
    * 提交事务
    * 1.获取beginTransAction()提供的Connection，然后调用commit方法
    * */
    public static void commitTransAction() throws SQLException {
        Connection connection = tl.get();
        if(connection == null) throw new RuntimeException("还没有开启事务，不能提交！");
        /*
        * 直接提交
        * */
        connection.commit();
        connection.close();
        tl.remove();//将connection从当前线程中移除，表示事务已经结束，之后再次调用 getConnection返回的就不是此connection了
    }
    /*
     * 回滚事务
     * 1.获取beginTransAction()提供的Connection，然后调用rollback方法
     * */
    public static void rollbackTransAction() throws SQLException {
        Connection connection = tl.get();
        if(connection == null) throw new RuntimeException("还没有开启事务，不能回滚！");
        /*
        * 直接回滚
        * */
        connection.rollback();
        connection.close();
       tl.remove();

    }
    /*
    * 关闭连接
    * */
    public static void releaseConnection(Connection conn) throws SQLException {
        Connection connection = tl.get();
        /*
        * 判断 connection 是不是事务专用，如果是，就不关闭
        * 如果不是事务专用，就关闭！
        * */
        //如果现在就没有开启事务，那么conn一定不是事务专用，直接关闭
        if(connection == null) conn.close();
        /*
        * 如果有事务，那么需要判断事务的Connection与参数conn是否相等
        * 若相等，则conn即为事务的连接，不予关闭；
        * 若不相等，则conn不是事务的连接，直接关闭
        * */
        if(conn != connection) conn.close();

    }

}
