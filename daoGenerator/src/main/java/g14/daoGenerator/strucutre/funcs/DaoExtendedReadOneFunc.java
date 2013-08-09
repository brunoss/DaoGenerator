package g14.daoGenerator.strucutre.funcs;

import g14.daoGenerator.strucutre.DomainEntity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import orm.JdbcCmd;
import orm.JdbcExecutor;

@SuppressWarnings("rawtypes")
public class DaoExtendedReadOneFunc<Dao, Domain> extends DaoExtendedReadFunc<Domain, Domain>
        implements ICache<Domain> {

    protected final Map<Object, Domain> cache = new HashMap<Object, Domain>();
    
    public DaoExtendedReadOneFunc(Class<Dao> daoType, DomainEntity entity, JdbcExecutor executor) {
        super(getMethod(daoType, "getById", int.class), entity, executor);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Domain executeDataOperation(JdbcExecutor executor, JdbcCmd cmd, Object[] args)
            throws SQLException {
        Iterator<Domain> it = executor.executeQuery(cmd, args).iterator();
        if (it.hasNext()){
            Domain result = it.next();
            return result;
        }
        return null;
    }

    @Override
    public final void invalidateCache(Object key) {
        cache.remove(key);
    }

    @Override
    public final void cache(Object key, Domain value) {
        cache.put(key, value);
    }

    @Override
    public final Domain getCachedValue(Object k) {
        return cache.get(k);
    }
}
