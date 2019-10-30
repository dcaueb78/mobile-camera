package com.caue.controledegasosa.adaptador;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.caue.controledegasosa.ListaAbastecimentoActivity;
import com.caue.controledegasosa.R;
import com.caue.controledegasosa.modelo.Abastecimento;

import java.text.DateFormat;

public class AbastecimentoViewHolder extends RecyclerView.ViewHolder {
    private TextView tvDescricao;
    private TextView tvData;
    private TextView tvPosto;
    private String idDoAbastecimento;
    private ImageView ivLogo;
    private ConstraintLayout clPai;


    public AbastecimentoViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListaAbastecimentoActivity) v.getContext()).modificarAbastecimento(v, idDoAbastecimento);
            }
        });

        tvDescricao = itemView.findViewById(R.id.tvDescricao);
        tvData = itemView.findViewById(R.id.tvData);
        tvPosto = itemView.findViewById(R.id.tvPosto);
        ivLogo = itemView.findViewById(R.id.ivLogo);
        clPai = (ConstraintLayout) itemView;
    }

    public void atualizaGavetaComOObjetoQueChegou(Abastecimento a){
        idDoAbastecimento = a.getId();

        String kmAtual = String.valueOf(a.getKmAtual());
        String abastecido = String.valueOf(a.getAbastecido());
        String posto = a.getPosto();

        if (posto.equals("Texaco")) {
            ivLogo.setImageResource(R.mipmap.ic_texaco_round);
        } else if (posto.equals("Ipiranga")) {
            ivLogo.setImageResource(R.mipmap.ic_ipiranga_round);
        } else if (posto.equals("Shell")) {
            ivLogo.setImageResource(R.mipmap.ic_shell_round);
        } else if (posto.equals("Petrobrás")) {
            ivLogo.setImageResource(R.mipmap.ic_petrobras_round);
        } else {
            ivLogo.setImageResource(R.mipmap.ic_launcher_round);
        }

        tvDescricao.setText("Abastecido " + abastecido + " litros. Km veículo " + kmAtual);
        tvPosto.setText(posto);

        DateFormat formatador = android.text.format.DateFormat.getDateFormat(tvData.getContext());
        String dataFormatada = formatador.format(a.getData().getTime());
        tvData.setText(dataFormatada);
    }
}
