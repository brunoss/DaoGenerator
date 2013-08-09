package g14.daoGenerator.strucutre.cmdTypes;

import g14.daoGenerator.strucutre.DaoInvocationHandler;
import g14.daoGenerator.strucutre.funcs.IDaoFunc;

public interface DaoVisitable<Domain, Dao> {
    public IDaoFunc<Object> accept(DaoInvocationHandler<Domain, Dao> t);
}
