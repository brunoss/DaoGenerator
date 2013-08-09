import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import dao.SupplierDao;
import entities.Supplier;

public class DaoSupplierTest extends DaoOperationTest<SupplierDao> {
    public DaoSupplierTest() {
        super(SupplierDao.class);
    }

    @Test
    public void test_read_with_supplier() {
        Supplier[] prods = new Supplier[] {};
        Iterator<Supplier> it = dao.getAll().iterator();
        for (int i = 0; i < prods.length; ++i) {
            Supplier p = it.next();
            assertAllEquals(prods[i], p);
        }
    }

    @Test
    public void test_readOne_with_supplier() {
        Assert.assertSame(dao.getById(1), dao.getById(1));
    }

    @Test
    public void test_create_with_supplier() {
        Supplier p1 = dao.insert("company", "contact", "title", "adress", "city");
        Supplier p2 = dao.getById(p1.getId());

        assertAllEquals(p1, p2);
    }

    @Test
    public void test_delete_with_supplier() {
        Supplier p1 = dao.insert("company", "contact", "title", "adress", "city");
        dao.delete(p1.getId());
        Assert.assertEquals(null, dao.getById(p1.getId()));
    }

    @Test
    public void test_update_with_supplier() {
        dao.update("company", "contact", "title", "adress", "city", 1);
        Supplier p1 = dao.getById(1);

        Assert.assertEquals("company", p1.getCompanyName());
        Assert.assertEquals("contact", p1.getContactName());
        Assert.assertEquals("title", p1.getContactTitle());
        Assert.assertEquals("adress", p1.getAddress());
        Assert.assertEquals("city", p1.getCity());
    }
}
