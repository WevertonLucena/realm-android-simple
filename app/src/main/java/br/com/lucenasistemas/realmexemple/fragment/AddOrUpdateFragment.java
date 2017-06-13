package br.com.lucenasistemas.realmexemple.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.lucenasistemas.realmexemple.R;
import br.com.lucenasistemas.realmexemple.model.Produto;

public class AddOrUpdateFragment extends Fragment{

    private EditText edtNomeProduto;
    private ProdutoListener listener;
    private Produto produto;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (! (activity instanceof ProdutoListener)){
            throw new RuntimeException("Implementar ProdutoListener");
        }
        listener = (ProdutoListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_or_update, container, false);
        edtNomeProduto = (EditText) view.findViewById(R.id.edtNomeProduto);
        edtNomeProduto.requestFocus();
        Button btnConfirmar = (Button) view.findViewById(R.id.btnConfirmar);
        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelar);

        if (produto != null){
            edtNomeProduto.setText(produto.getNome());
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtNomeProduto.getText().toString().equals("")){
                    if (listener != null) {
                        Produto p2 = new Produto();
                        p2.setNome(edtNomeProduto.getText().toString());
                        p2.setId(produto == null ? null : produto.getId());
                        listener.onConfirm(p2);
                    }
                }else{
                    Toast.makeText(getActivity(),"Preencha o nome",Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });

        return view;
    }

    public void addProduto(Produto produto){
        this.produto = produto;
    }

    public interface ProdutoListener {
        void onConfirm(Produto produto);
        void onCancel();
    }
}
