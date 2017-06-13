package br.com.lucenasistemas.realmexemple.dao;

import android.util.Log;

import br.com.lucenasistemas.realmexemple.model.Produto;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Weverton on 13/06/2017.
 */

public class ProdutoDao implements Dao<Produto> {


    private static ProdutoDao instance;
    private static Realm realm;

    private ProdutoDao(){}

    public static ProdutoDao getInstance(){
        if (instance == null)
            instance = new ProdutoDao();
        realm = Realm.getDefaultInstance();
        return instance;
    }

    @Override
    public  RealmResults<Produto> get() {
        return realm.where(Produto.class).findAll();
    }


    @Override
    public Produto get(Long id) {
        return realm.where(Produto.class).equalTo("id",id).findFirst();
    }

    @Override
    public void save(Produto produto) {
        realm.beginTransaction();
        if (produto.getId() == null)
            produto.setId(getNewId());
        realm.copyToRealmOrUpdate(produto);
        realm.commitTransaction();

    }

    @Override
    public void delete(Produto produto) {
        realm.beginTransaction();
        produto.removeFromRealm();
        realm.commitTransaction();
    }

    @Override
    public Long getNewId() {
        return realm.where(Produto.class).max("id") == null ? 1 : realm.where(Produto.class).max("id").longValue() + 1;
    }


    @Override
    public void close() throws Exception {
        realm.close();
    }
}
