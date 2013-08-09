package g14.daoGenerator.strucutre.funcs;

import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import orm.JdbcCmd;
import orm.JdbcConverter;
import orm.JdbcExecutor;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DaoReadOneFunc<T> extends DaoAbstractFunc<T> implements ICache<T> {

    protected final Map<Object, T> cache = new HashMap<Object, T>();

    protected Class<T> type;

    public DaoReadOneFunc(DomainEntity entity, JdbcExecutor executor) {
        super(executor);
        this.type = (Class<T>) entity.value();
    }

    @Override
    protected JdbcConverter getConverter(final int nArgs) {
        final Constructor<T> ctor = ReflectionUtils.matchCtor(type, nArgs);
        return new JdbcConverter<T>() {
            @Override
            public T convert(ResultSet rs) throws SQLException {
                // As exercise says I assume there is a ctor for the read operation
                return ReflectionUtils.instaciate(ctor, rs);
            }
        };
    }

    @Override
    public final void invalidateCache(Object key) {
        cache.remove(key);
    }

    @Override
    public final void cache(Object key, T value) {
        cache.put(key, value);
    }

    @Override
    public final T getCachedValue(Object k) {
        return cache.get(k);
    }

    @Override
    protected T executeDataOperation(JdbcExecutor executor, JdbcCmd cmd, Object[] args)
            throws SQLException {
        // this read will be cached by DaoCacheFunc
        Iterator<T> it = executor.executeQuery(cmd, args).iterator();
        if (it.hasNext()) {
            T value = it.next();
            return value;
        }
        return null;
    }
}
