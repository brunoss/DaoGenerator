import junit.framework.Assert;

import org.junit.Test;

import dao.ShipperDao;
import entities.Shipper;

public class DaoShipperTest extends DaoOperationTest<ShipperDao> {
    public DaoShipperTest() {
        super(ShipperDao.class);
    }

    @Test
    public void test_readOne_with_shipper() {
        Assert.assertSame(dao.getById(1), dao.getById(1));
    }

    @Test
    public void test_create_with_shipper() {
        Shipper p1 = dao.insert("A name", "+351212121212");
        Shipper p2 = dao.getById(p1.getId());

        assertAllEquals(p1, p2);
    }

    @Test
    public void test_delete_with_shipper() {
        Shipper p1 = dao.insert("A name", "+351212121212");
        dao.delete(p1.getId());
        Assert.assertEquals(null, dao.getById(p1.getId()));
    }

    @Test
    public void test_update_with_shipper() {
        dao.update("A name", "+351212121212", 1);
        Shipper p1 = dao.getById(1);

        Assert.assertEquals("A name", p1.getCompanyName());
        Assert.assertEquals("+351212121212", p1.getPhone());
        Assert.assertEquals(1, p1.getId());
    }
}
