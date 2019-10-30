package com.caue.controledegasosa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.caue.controledegasosa.modelo.Abastecimento;
import com.caue.controledegasosa.modelo.AbastecimentoDAO;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FormularioActivity extends AppCompatActivity {
    private Abastecimento objetoAbastecimento;
    private String idDoAbastecimento;
    private TextInputEditText etKmAtual;
    private TextInputEditText etLtAbastecido;
    private TextView tvData;
    private Spinner spPosto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        spPosto = findViewById(R.id.spPosto);
        etKmAtual = findViewById(R.id.etKmAtual);
        etLtAbastecido = findViewById(R.id.etAbastecido);
        tvData = findViewById(R.id.tvData);
        tvData.setKeyListener(null);


        String[] opcoesPosto = getResources().getStringArray(R.array.opcoes_posto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesPosto);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPosto.setAdapter(adapter);

        idDoAbastecimento = getIntent().getStringExtra("idDoAbastecimento");
        if (idDoAbastecimento == null) {
            objetoAbastecimento = new Abastecimento();


            if(AbastecimentoDAO.obterInstancia().obterLista().size() > 0) {
                Abastecimento ultimoAbastecimento = AbastecimentoDAO.obterInstancia().obterLista().get(AbastecimentoDAO.obterInstancia().obterLista().size() - 1);

                Float ultimoKmRegistrado = ultimoAbastecimento.getKmAtual();

                etKmAtual.setHint(String.valueOf(ultimoKmRegistrado));
            }


            Button btExcluir = findViewById(R.id.btExlcuir);
            btExcluir.setVisibility(View.INVISIBLE);

        } else {
            objetoAbastecimento = AbastecimentoDAO.obterInstancia().obterAbastecimentoPeloId(idDoAbastecimento);
            etKmAtual.setText(String.valueOf(objetoAbastecimento.getKmAtual()));
            etLtAbastecido.setText(String.valueOf(objetoAbastecimento.getAbastecido()));

            for(int i = 0; i < spPosto.getAdapter().getCount(); i++) {
                if (spPosto.getAdapter().getItem(i).toString().equals(objetoAbastecimento.getPosto())){
                    spPosto.setSelection(i);
                    break;
                }
            }

            DateFormat formatador = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            String dataSelecionadaFormatada = formatador.format( objetoAbastecimento.getData().getTime());
            tvData.setText( dataSelecionadaFormatada );

        }
    }

    public void salvar(View v){
        objetoAbastecimento.setKmAtual(Float.parseFloat(etKmAtual.getText().toString()));
        objetoAbastecimento.setAbastecido(Integer.parseInt(etLtAbastecido.getText().toString()));
        objetoAbastecimento.setPosto(spPosto.getSelectedItem().toString());

        if(idDoAbastecimento == null) {
            if(AbastecimentoDAO.obterInstancia().obterLista().size() > 0) {

                Abastecimento ultimoAbastecimento = AbastecimentoDAO.obterInstancia().obterLista().get(AbastecimentoDAO.obterInstancia().obterLista().size() - 1);
                Float ultimoKmRegistrado = ultimoAbastecimento.getKmAtual();
                if(objetoAbastecimento.getKmAtual() < ultimoKmRegistrado) {
                    Toast.makeText(this, "KM atual menor que o último registrado", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            AbastecimentoDAO.obterInstancia().adicionarNaLista(objetoAbastecimento);
            setResult(201);
        } else {
            int posicaoDoObjeto = AbastecimentoDAO.obterInstancia().atualizaNaLista(objetoAbastecimento);
            Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
            intencaoDeFechamentoDaActivityFormulario.putExtra("posicaoDoObjetoEditado", posicaoDoObjeto);
            setResult(200, intencaoDeFechamentoDaActivityFormulario);
        }

        finish();
    }

    public void selecionarData(View v){
        Calendar dataPadrao = objetoAbastecimento.getData();;
        if(dataPadrao == null){
            dataPadrao = Calendar.getInstance();
        }

        DatePickerDialog dialogoParaPegarData = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar dataSelecionada = Calendar.getInstance();
                        dataSelecionada.set(year,month,dayOfMonth);
                        objetoAbastecimento.setData(dataSelecionada);

                        DateFormat formatador = android.text.format.DateFormat.getDateFormat( getApplicationContext() );
                        String dataSelecionadaFormatada = formatador.format( dataSelecionada.getTime() );
                        tvData.setText( dataSelecionadaFormatada );
                    }
                },
                dataPadrao.get(Calendar.YEAR),
                dataPadrao.get(Calendar.MONTH),
                dataPadrao.get(Calendar.DAY_OF_MONTH)
        );
        dialogoParaPegarData.show();
    }

    public void excluir(View v){
        new AlertDialog.Builder(this)
                .setTitle("Você tem certeza?")
                .setMessage("Você quer mesmo excluir?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int posicaoDoObjeto = AbastecimentoDAO.obterInstancia().excluiDaLista(objetoAbastecimento);
                        Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
                        intencaoDeFechamentoDaActivityFormulario.putExtra("posicaoDoObjetoExcluido", posicaoDoObjeto);
                        setResult(202, intencaoDeFechamentoDaActivityFormulario);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
