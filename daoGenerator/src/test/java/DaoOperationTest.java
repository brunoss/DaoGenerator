import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;

import orm.JdbcExecutor;
import orm.JdbcExecutorSingleConnection;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import entities.Ignore;
import g14.daoGenerator.strucutre.Builder;
import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.strucutre.funcs.factories.DaoExtendedFuncFactory;

public class DaoOperationTest<T> {
    JdbcExecutor executor;
    protected T dao;
    SQLServerDataSource ds;
    Class<T> type;

    public DaoOperationTest(Class<T> type) {
        this.type = type;
        ds = new SQLServerDataSource();
        ds.setUser("myAppUser");
        ds.setPassword("fcp");
    }

    @SuppressWarnings("unchecked")
    public <E> void assertAllEquals(E expected, E other) {
        Class<E> type = (Class<E>) expected.getClass();
        Method[] methods = type.getMethods();
        String aux = this.type.getAnnotation(DomainEntity.class).key();
        String key = Character.toString(aux.charAt(0)).toUpperCase() + aux.substring(1);
        for (Method m : methods) {
            if (m.getName().startsWith("get") && !m.getName().equals("get" + key)) {
                if(m.getAnnotation(Ignore.class) == null)continue;
                try {
                    Object o1 = m.invoke(expected);
                    Object o2 = m.invoke(other);
                    Assert.assertEquals(o1, o2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void startUP() {
        executor = new JdbcExecutorSingleConnection(ds, false);
        dao = (T) Builder.make(type, executor, new DaoExtendedFuncFactory());
    }

    @After
    public void tearDown() throws Exception {
        executor.close();
    }
}
