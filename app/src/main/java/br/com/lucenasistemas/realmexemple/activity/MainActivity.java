package br.com.lucenasistemas.realmexemple.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import br.com.lucenasistemas.realmexemple.R;
import br.com.lucenasistemas.realmexemple.adapter.ProdutoAdapter;
import br.com.lucenasistemas.realmexemple.dao.ProdutoDao;
import br.com.lucenasistemas.realmexemple.fragment.AddOrUpdateFragment;
import br.com.lucenasistemas.realmexemple.model.Produto;
import io.realm.RealmResults;

public class MainActivity extends Activity implements AddOrUpdateFragment.ProdutoListener {

    private AddOrUpdateFragment fragment;
    private RealmResults<Produto> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listProdutos = (ListView) findViewById(R.id.listProdutos);
        ProdutoDao produtoDao = ProdutoDao.getInstance();
        produtos = produtoDao.get();
        ProdutoAdapter adapter = new ProdutoAdapter(this, produtos, true);

        listProdutos.setAdapter(adapter);

        listProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Produto produto = produtos.get(position);
                openFragment(produto);

            }
        });

        listProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try(ProdutoDao produtoDao = ProdutoDao.getInstance()){
                    produtoDao.delete(produtoDao.get(id));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(MainActivity.this,"Produto removido",Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }

    public void onAdicionar(View view) {
        openFragment(null);
    }

    private void openFragment(Produto produto) {
        fragment = new AddOrUpdateFragment();
        if (produto != null)
            fragment.addProduto(produto);
        getFragmentManager().beginTransaction()
                .add(R.id.placeHolder,fragment,"AddOrUpdate")
                .commit();
    }

    @Override
    public void onConfirm(Produto produto) {
        try(ProdutoDao produtoDao = ProdutoDao.getInstance()) {
            produtoDao.save(produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCancel() {
        closeFragment();
    }

    private void closeFragment() {
        getFragmentManager().beginTransaction()
        .remove(fragment).commit();
    }


}
