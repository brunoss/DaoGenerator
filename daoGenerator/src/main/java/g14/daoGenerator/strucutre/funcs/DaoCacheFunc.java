package g14.daoGenerator.strucutre.funcs;

import g14.daoGenerator.strucutre.funcs.factories.DaoAbstractFactory;
import g14.daoGenerator.utils.Func2;

import java.sql.SQLException;

import orm.JdbcCmd;

@SuppressWarnings("unchecked")
public class DaoCacheFunc<Result, Domain> implements IDaoFunc<Result>, ICache<Domain> {

    private final DaoAbstractFactory<Domain, ?> factory;

    private final DaoAbstractFunc<Result> func;

    public DaoCacheFunc(DaoAbstractFunc<Result> func, DaoAbstractFactory<Domain, ?> factory) {
        this.factory = factory;
        this.func = func;
    }

    @Override
    public void invalidateCache(Object key) {
        factory.invalidateCache(key);
    }

    // Could have a way of DaoCreate call this method to cache the object
    @Override
    public void cache(Object key, Domain value) {
        ICache<Domain> cache = (ICache<Domain>) func;
        cache.cache(key, value);
    }

    @Override
    public Domain getCachedValue(Object k) {
        ICache<Domain> cache = (ICache<Domain>) func;
        return cache.getCachedValue(k);
    }

    public ICache<Domain> getCache() {
        // this method is called from factory always under DaoRead instance
        if (!(func instanceof DaoReadOneFunc) && !(func instanceof DaoExtendedReadOneFunc))
            throw new UnsupportedOperationException("getCache shall be called under DaoRead Instance");
        return (ICache<Domain>) func;
    }

    @Override
    public final Func2<Class<?>[], Object[], Result> getDataOperation(final String query) {
        return new Func2<Class<?>[], Object[], Result>() {
            @SuppressWarnings("rawtypes")
            @Override
            public Result call(Class<?>[] types, Object[] t1) {
                try {
                    boolean isCache = func instanceof ICache;
                    if (t1.length > 0 && isCache) {
                        // if we reached here its because func is DaoReadFunc
                        Result result = (Result) getCachedValue(t1[0]);
                        if (result != null)
                            return result;
                    }
                    
                    JdbcCmd cmd = func.getCmd(query, types);
                    Result k = func.executeDataOperation(func.executor, cmd, t1);
                    if (k != null && isCache) {
                        cache(t1[0], (Domain) k);
                    }
                    if (t1.length > 0 && !isCache) {
                        // as getAll don't have any argument
                        // it won't invalidate cache
                        // assume that key is received as last parameter
                        invalidateCache(t1[t1.length - 1]);
                    }
                    return k;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
