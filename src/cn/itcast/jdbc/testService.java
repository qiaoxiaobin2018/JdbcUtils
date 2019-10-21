package cn.itcast.jdbc;

import org.junit.Test;

import java.sql.SQLException;

public class testService {
    private AccountDao dao = new AccountDao();

    @Test
    public void updateService() throws Exception {
        try {
            JdbcUtils.beginTransAction();
            dao.update("zs",-1000);

            if(true) throw new RuntimeException("转账出错！");

            dao.update("ls",1000);
            JdbcUtils.commitTransAction();
        } catch (Exception e) {
            try {
                JdbcUtils.rollbackTransAction();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw e;
        }

    }
}
