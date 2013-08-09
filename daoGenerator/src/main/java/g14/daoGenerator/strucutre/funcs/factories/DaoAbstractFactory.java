package g14.daoGenerator.strucutre.funcs.factories;

import g14.daoGenerator.strucutre.DomainEntity;
import g14.daoGenerator.strucutre.funcs.DaoAbstractFunc;
import g14.daoGenerator.strucutre.funcs.DaoCacheFunc;
import g14.daoGenerator.strucutre.funcs.DaoCreateFunc;
import g14.daoGenerator.strucutre.funcs.DaoDeleteFunc;
import g14.daoGenerator.strucutre.funcs.DaoUpdateFunc;
import g14.daoGenerator.strucutre.funcs.ICache;
import g14.daoGenerator.strucutre.funcs.IDaoFunc;
import orm.JdbcExecutor;

public abstract class DaoAbstractFactory<Domain, Dao> implements IDaoFuncFactory<Domain>, ICache<Domain>{

    private IDaoFunc<Domain> createFunc;
    private DaoCacheFunc<Domain, Domain> readOneFunc;
    private IDaoFunc<Iterable<Domain>> readManyFunc;
    private IDaoFunc<Void> updateFunc;
    private IDaoFunc<Void> deleteFunc;
    
    protected DomainEntity entity;
    protected JdbcExecutor executor;
    protected Class<Dao> daoType;
    
    public void setDaoType(Class<Dao> daoType){
        this.daoType = daoType;
    }

    public void setEntity(DomainEntity entity){
        this.entity = entity;
    }

    public void setExecutor(JdbcExecutor executor){
        this.executor = executor;
    }
    
    @Override
    public IDaoFunc<Domain> makeCreateFunc() {
        if (createFunc != null)
            return createFunc;
        DaoAbstractFunc<Domain> func = new DaoCreateFunc<>(entity, executor);
        createFunc = new DaoCacheFunc<Domain, Domain>(func, this);
        return createFunc;
    }
    
    @Override
    public IDaoFunc<Void> makeUpdateFunc() {
        if (updateFunc != null)
            return updateFunc;
        DaoAbstractFunc<Void> func = new DaoUpdateFunc<Domain>(executor);
        updateFunc = new DaoCacheFunc<Void, Domain>(func, this);
        return updateFunc;
    }

    @Override
    public IDaoFunc<Void> makeDeleteFunc() {
        if (deleteFunc != null)
            return deleteFunc;
        deleteFunc = new DaoDeleteFunc<Domain>(executor);
        return deleteFunc;
    }
    
    @Override
    public IDaoFunc<Domain> makeReadOneFunc() {
        if (readOneFunc != null)
            return readOneFunc;
        IDaoFunc<Domain> func = getReadOneFunc();
        readOneFunc = new DaoCacheFunc<Domain, Domain>((DaoAbstractFunc<Domain>) func, this);
        return readOneFunc;
    }

    protected abstract DaoAbstractFunc<Domain> getReadOneFunc();

    @Override
    public IDaoFunc<Iterable<Domain>> makeReadManyFunc() {
        if (readManyFunc != null)
            return readManyFunc;
        DaoAbstractFunc<Iterable<Domain>> func = getReadManyFunc();
        readManyFunc = new DaoCacheFunc<Iterable<Domain>, Domain>(func, this);
        return readManyFunc;
    }

    protected abstract DaoAbstractFunc<Iterable<Domain>> getReadManyFunc();
    
    public void invalidateCache(Object key){
        if(readOneFunc == null)return;
        readOneFunc.getCache().invalidateCache(key);
    }

    public void cache(Object key, Domain value){
        if(readOneFunc == null)return;
        readOneFunc.cache(key, value);
    }

    public Domain getCachedValue(Object k) {
        if(readOneFunc == null)return null;
        return readOneFunc.getCachedValue(k);
    }
}
