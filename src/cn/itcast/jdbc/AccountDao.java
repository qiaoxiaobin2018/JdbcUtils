package cn.itcast.jdbc;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;

public class AccountDao {
    public void  update(String name,double money) throws Exception{
        //DBUtils
        QueryRunner queryRunner = new TxQueryRunner();
        String sql = "UPDATE account SET balance=balance+? WHERE name=?";
        Object[] params = {money,name};
        int i = queryRunner.update(sql, params);
        System.out.println(i+" 行受到影响！");
    }
}
