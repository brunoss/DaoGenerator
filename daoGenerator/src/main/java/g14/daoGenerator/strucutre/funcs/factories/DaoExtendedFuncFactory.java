package g14.daoGenerator.strucutre.funcs.factories;

import g14.daoGenerator.strucutre.funcs.DaoAbstractFunc;
import g14.daoGenerator.strucutre.funcs.DaoExtendedReadManyFunc;
import g14.daoGenerator.strucutre.funcs.DaoExtendedReadOneFunc;

public class DaoExtendedFuncFactory<Domain, Dao> extends DaoAbstractFactory<Domain, Dao> {

    @Override
    protected DaoAbstractFunc<Domain> getReadOneFunc() {
        return new DaoExtendedReadOneFunc<Dao, Domain>(daoType, entity, executor);
    }

    @Override
    protected DaoAbstractFunc<Iterable<Domain>> getReadManyFunc() {
        return new DaoExtendedReadManyFunc<Dao, Domain>(daoType, entity, executor);
    }

}
