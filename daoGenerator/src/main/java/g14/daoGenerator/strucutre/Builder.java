package g14.daoGenerator.strucutre;

import g14.daoGenerator.strucutre.funcs.factories.DaoAbstractFactory;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import orm.JdbcExecutor;

public class Builder {
    @SuppressWarnings("rawtypes")
    private static Map<Class, DaoInvocationHandler> mappedHandlers = new HashMap<Class, DaoInvocationHandler>();

    @SuppressWarnings("unchecked")
    public static <Dao, Domain> Dao make(Class<Dao> type, JdbcExecutor executor, DaoAbstractFactory<Domain, Dao> factory) {
        DaoInvocationHandler<Domain, Dao> handler = mappedHandlers.get(type);
        if (handler == null){
            factory.setDaoType(type);
            factory.setExecutor(executor);
            factory.setEntity(type.getAnnotation(DomainEntity.class));
            handler = new DaoInvocationHandler<Domain, Dao>(type, executor, factory);
            mappedHandlers.put(type, handler);
        }
        return (Dao) Proxy.newProxyInstance(type.getClassLoader(), new Class[] { type }, handler);
    }
}
