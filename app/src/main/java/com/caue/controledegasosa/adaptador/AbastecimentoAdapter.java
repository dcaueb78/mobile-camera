package com.caue.controledegasosa.adaptador;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.caue.controledegasosa.R;
import com.caue.controledegasosa.modelo.Abastecimento;
import com.caue.controledegasosa.modelo.AbastecimentoDAO;

public class AbastecimentoAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout elementoPrincipalXML = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.abastecimento_item, parent, false
        );
       AbastecimentoViewHolder gaveta = new AbastecimentoViewHolder(elementoPrincipalXML);
        return gaveta;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Abastecimento a = AbastecimentoDAO.obterInstancia().obterLista().get(position);
        AbastecimentoViewHolder gaveta = (AbastecimentoViewHolder) holder;

        gaveta.atualizaGavetaComOObjetoQueChegou(a);
        Log.d("AULA", "Atualizou com o item na posição "+position+" a gaveta : "+gaveta);

    }


    @Override
    public int getItemCount() {
        return AbastecimentoDAO.obterInstancia().obterLista().size();
    }

}
