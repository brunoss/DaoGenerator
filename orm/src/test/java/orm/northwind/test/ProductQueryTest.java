package orm.northwind.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import orm.*;
import junit.framework.Assert;
import model.Product;

public class ProductQueryTest {
	JdbcQueryableExecutor exec; 
    JdbcCmd<Product> cmdProductLoadAll;
    
	final static JdbcConverter<Product> converter = new JdbcConverter<Product>() {
		@Override
		public Product convert(ResultSet rs) throws SQLException {
			return new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4));
		}
	};
	
    public ProductQueryTest() {
		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser("myAppUser");
		ds.setPassword("fcp");
      exec = new JdbcQueryableExecutor(new JdbcExecutorSingleConnection(ds, false));
      cmdProductLoadAll = new JdbcCmdTemplate<>("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products", 
    		  converter);
    }


    @Test
    public void test_load_all() throws SQLException{
      Iterable<Product> res = exec.executeQuery(cmdProductLoadAll);
      int i = 1;
      for (Product p : res) {
        Assert.assertEquals(i++, p.getId());
      }
    }
    
    @Test
    public void test_load_all_and_order_by() throws SQLException{
      Iterable<Product> res = exec.executeQuery(cmdProductLoadAll).orderBy("ProductName");
      int[] expected = new int[]{17, 3, 40, 60, 18, 1, 2, 39, 4};
      int i = 0;
      for (Product p : res) {
        Assert.assertEquals(expected[i++], p.getId());
        if(i >= expected.length) break;
      }
    }
    
    @Test
    public void test_load_all_and_where() throws SQLException{
      Iterable<Product> res = exec.executeQuery(cmdProductLoadAll).where("UnitPrice >= 50");
      int[]expected = {9,  18,  20,  29,  38,  51,  59};
      int i = 0;
      for (Product p : res) {
        Assert.assertEquals(expected[i++], p.getId());
      }

    }
    
    @Test
    public void test_load_all_and_where_and_orderby() throws SQLException{
      Iterable<Product> res = exec.executeQuery(cmdProductLoadAll).where("UnitPrice >= 50").orderBy("ProductName");
      int[] expected = new int[]{18, 38, 51, 9, 59, 20, 29};
      int i = 0;
      for (Product p : res) {
        Assert.assertEquals(expected[i++], p.getId());
      }
    }

}
