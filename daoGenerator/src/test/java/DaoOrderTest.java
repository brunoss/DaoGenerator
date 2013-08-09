import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import dao.OrderDao;
import entities.Order;

public class DaoOrderTest extends DaoOperationTest<OrderDao> {
    public DaoOrderTest() {
        super(OrderDao.class);
    }

    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void test_readOne_with_order() {
        Assert.assertSame(dao.getById(1), dao.getById(1));
    }

    @Test
    public void test_create_with_order() throws ParseException {
        Date d1 = format.parse("14/07/2013");
        Date d2 = format.parse("23/07/2013");
        Order p1 = dao.insert(d1, d2, 100, "Titanic");
        Order p2 = dao.getById(p1.getId());

        assertAllEquals(p1, p2);
    }

    @Test
    public void test_delete_with_order() {
        Order p1 = dao.insert(new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 1134452), 100, "Titanic");
        dao.delete(p1.getId());
        Assert.assertEquals(null, dao.getById(p1.getId()));
    }

    @Test
    public void test_update_with_order() throws ParseException {
        Date d1 = format.parse("14/07/2013");
        Date d2 = format.parse("23/07/2013");
        dao.update(d1, d2, 100.0f, "Titanic", 10248);
        Order p1 = dao.getById(10248);

        Assert.assertEquals("Titanic", p1.getShipName());
        boolean floats = Math.abs(100.f - p1.getFreight()) < 0.00000001f;
        Assert.assertTrue(floats);

        Assert.assertEquals(d1, p1.getOrderDate());
        Assert.assertEquals(d2, p1.getShippedDate());
    }

}
