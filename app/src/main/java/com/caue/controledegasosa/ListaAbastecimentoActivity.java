package com.caue.controledegasosa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.caue.controledegasosa.adaptador.AbastecimentoAdapter;
import com.caue.controledegasosa.modelo.Abastecimento;
import com.caue.controledegasosa.modelo.AbastecimentoDAO;

import java.util.Calendar;
import java.util.Random;

public class ListaAbastecimentoActivity extends AppCompatActivity {

    private AbastecimentoAdapter adaptador;
    private RecyclerView rvAbastecimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_abastecimento);


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);


        adaptador = new AbastecimentoAdapter();
        rvAbastecimentos.setLayoutManager(new LinearLayoutManager(this));
        rvAbastecimentos.setAdapter(adaptador);

        findViewById(R.id.button).setOnClickListenet(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tirarFoto();
            }
        });

    }


    public void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);

    }


    protected  void onActivityRedult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            imageViewFoto.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (resultCode == 200){
                int posicao = data.getIntExtra("posicaoDoObjetoEditado", -1);
                adaptador.notifyItemChanged( posicao );
                rvAbastecimentos.smoothScrollToPosition(posicao);
            }else if (resultCode == 201){
                Toast.makeText(this, "Abastecimento inserido com sucesso", Toast.LENGTH_LONG).show();
                int posicao = AbastecimentoDAO.obterInstancia().obterLista().size()-1;
                adaptador.notifyItemInserted( posicao );
                rvAbastecimentos.smoothScrollToPosition(posicao);
            }else if (resultCode == 202){
                Toast.makeText(this, "Abastecimento excluído com sucesso", Toast.LENGTH_LONG).show();
                int posicao = data.getIntExtra("posicaoDoObjetoExcluido", -1);
                adaptador.notifyItemRemoved(posicao);
            }
        }
    }

    public void modificarAbastecimento(View v, String idDoAbastecimento){
        Intent intencao = new Intent( this, FormularioActivity.class );
        intencao.putExtra("idDoAbastecimento", idDoAbastecimento);
        startActivityForResult(intencao, 1);
    }

    public void adicionarAbastecimento(View v) {
        Intent intencao = new Intent(this, FormularioActivity.class);
        startActivityForResult(intencao, 1);
    }
}
