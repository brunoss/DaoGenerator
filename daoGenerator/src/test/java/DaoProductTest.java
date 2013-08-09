import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import dao.ProductDao;
import entities.Category;
import entities.Product;
import entities.Supplier;

public class DaoProductTest extends DaoOperationTest<ProductDao> {
    public DaoProductTest() {
        super(ProductDao.class);
    }

    @Test
    public void test_read_with_product() {
        Product[] prods = new Product[] { new Product("Chai", 18, 39),
                new Product("Chang", 19, 17), new Product("Aniseed Syrup", 10, 13),
                new Product("Chef Anton's Cajun Seasoning", 22, 53),
                new Product("Chef Anton's Gumbo Mix", 21.35, 0),
                new Product("Grandma's Boysenberry Spread", 25, 120),
                new Product("Uncle Bob's Organic Dried Pears", 30, 15),
                new Product("Northwoods Cranberry Sauce", 40, 6),
                new Product("Mishi Kobe Niku", 97, 29), new Product("Ikura", 31, 31),
                new Product("Queso Cabrales", 21, 22),
                new Product("Queso Manchego La Pastora", 38, 86), new Product("Konbu", 6, 24),
                new Product("Tofu", 23.25, 35), new Product("Genen Shouyu", 15.50, 39),
                new Product("Pavlova", 17.45, 29), new Product("Alice Mutton", 39, 0),
                new Product("Carnarvon Tigers", 62.5, 42),
                new Product("Teatime Chocolate Biscuits", 9.2, 25),
                new Product("Sir Rodney's Marmalade", 81, 40) };
        Iterator<Product> it = dao.getAll().iterator();
        for (int i = 0; i < prods.length; ++i) {
            Product p = it.next();
            Category c = p.getCategory();
            Supplier s = p.getSupllier();
            Assert.assertNotNull(c);
            Assert.assertNotNull(s);
            assertAllEquals(prods[i], p);
        }
    }

    @Test
    public void test_readOne_with_product() {
        Assert.assertSame(dao.getById(1), dao.getById(1));
    }

    @Test
    public void test_create_with_product() {
        Product p1 = dao.insert("Giant Potatos", 100, 1000);
        Product p2 = dao.getById(p1.getId());

        assertAllEquals(p1, p2);
    }

    @Test
    public void test_delete_with_product() {
        Product p1 = dao.insert("Giant Potatos", 100, 1000);
        dao.delete(p1.getId());
        Assert.assertEquals(null, dao.getById(p1.getId()));
    }

    @Test
    public void test_update_with_product() {
        dao.update("xpto", 9001, 10, 1);
        Product p1 = dao.getById(1);

        Assert.assertEquals("xpto", p1.getProductName());
        Assert.assertEquals(9001.0, p1.getUnitPrice());
        Assert.assertEquals(10, p1.getUnitsInStock());
    }
}
