package br.com.lucenasistemas.realmexemple.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import br.com.lucenasistemas.realmexemple.R;
import br.com.lucenasistemas.realmexemple.model.Produto;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Weverton on 13/06/2017.
 */

public class ProdutoAdapter extends RealmBaseAdapter<Produto> implements ListAdapter {

    public ProdutoAdapter(Context context, RealmResults<Produto> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public long getItemId(int i) {
        return realmResults.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_produto,parent,false);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.nomeProduto = (TextView) convertView.findViewById(R.id.txtProduto);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Produto produto = realmResults.get(position);
        holder.nomeProduto.setText(produto.getNome());

        return convertView;
    }

    private static class ViewHolder{
        TextView nomeProduto;

    }
}
