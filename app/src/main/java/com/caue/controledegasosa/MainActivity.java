package com.caue.controledegasosa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.caue.controledegasosa.modelo.Abastecimento;
import com.caue.controledegasosa.modelo.AbastecimentoDAO;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvDescricao = findViewById(R.id.tvDescricao);

        float autonomia = this.autonomia();

        tvDescricao.setText(String.format(Locale.US,"%.2f",autonomia));

    }

    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_main);

        TextView tvDescricao = findViewById(R.id.tvDescricao);

        float autonomia = this.autonomia();

        tvDescricao.setText(String.format(Locale.US,"%.2f",autonomia));


    }


    public void avancar(View v) {
        Intent intencao = new Intent(this, ListaAbastecimentoActivity.class);
        startActivity(intencao);
    }



    private float autonomia() {
        ArrayList<Abastecimento> abastecimentosTotais = AbastecimentoDAO.obterInstancia().obterLista();

        float autonomia = 0;
        float totalKm = 0;
        int totalLt = 0;

        Abastecimento ultimoRegistro = abastecimentosTotais.get(0);
        float totalKmUltimoAbastecimento = ultimoRegistro.getKmAtual();

        abastecimentosTotais.remove(0);
        if (abastecimentosTotais.size() > 0) {

            Abastecimento primeiroRegistro = abastecimentosTotais.get(abastecimentosTotais.size() - 1);

            totalKm = primeiroRegistro.getKmAtual();

            for(int i = 0; i < abastecimentosTotais.size(); i++) {
                totalLt += abastecimentosTotais.get(i).getAbastecido(); 
            }

            totalKm = totalKmUltimoAbastecimento - totalKm;
            autonomia = totalKm / totalLt;
        }

        return autonomia;
    }
}
