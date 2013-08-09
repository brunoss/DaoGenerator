import junit.framework.Assert;

import org.junit.Test;

import dao.CategoryDao;
import entities.Category;

public class DaoCategoryTest extends DaoOperationTest<CategoryDao> {
    public DaoCategoryTest() {
        super(CategoryDao.class);
    }

    @Test
    public void test_readOne_with_category() {
        Assert.assertSame(dao.getById(1), dao.getById(1));
    }

    @Test
    public void test_create_with_category() {
        Category p1 = dao.insert("Misclaneous", "???");
        Category p2 = dao.getById(p1.getId());

        assertAllEquals(p1, p2);
    }

    @Test
    public void test_delete_with_category() {
        Category p1 = dao.insert("sports", "water sports");
        dao.delete(p1.getId());
        Assert.assertEquals(null, dao.getById(p1.getId()));
    }

    @Test
    public void test_update_with_category() {
        dao.update("xpto", "null", 1);
        Category p1 = dao.getById(1);
        Assert.assertEquals("xpto", p1.getName());
        Assert.assertEquals("null", p1.getDescription());
    }
}
