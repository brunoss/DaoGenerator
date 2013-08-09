package g14.daoGenerator.strucutre.funcs.factories;

import g14.daoGenerator.strucutre.funcs.IDaoFunc;

public interface IDaoFuncFactory<K> {
    IDaoFunc<K> makeReadOneFunc();

    IDaoFunc<Iterable<K>> makeReadManyFunc();

    IDaoFunc<K> makeCreateFunc();

    IDaoFunc<Void> makeUpdateFunc();

    IDaoFunc<Void> makeDeleteFunc();
}
