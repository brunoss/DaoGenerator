package g14.daoGenerator.strucutre.funcs;

import g14.daoGenerator.utils.Func2;

public interface IDaoFunc<Result> {
    Func2<Class<?>[], Object[], Result> getDataOperation(final String query);
}
