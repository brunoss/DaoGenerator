package g14.daoGenerator.strucutre.funcs.factories;

import g14.daoGenerator.strucutre.funcs.DaoAbstractFunc;
import g14.daoGenerator.strucutre.funcs.DaoReadManyFunc;
import g14.daoGenerator.strucutre.funcs.DaoReadOneFunc;
public class DaoFuncFactory<Domain, Dao> extends DaoAbstractFactory<Domain, Dao>{
    
    @Override
    protected DaoAbstractFunc<Domain> getReadOneFunc() {
        return new DaoReadOneFunc<>(entity, executor);
    }

    @Override
    protected DaoAbstractFunc<Iterable<Domain>> getReadManyFunc() {
        return new DaoReadManyFunc<Domain>(entity, executor);
    }
    
}
