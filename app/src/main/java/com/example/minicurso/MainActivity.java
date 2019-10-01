package com.example.minicurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minicurso.Class.DAOArquivo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> lista;

    Button btSalvar;
    Button btMostar;
    TextView ptCampoItem;
    TextView tvContador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = DAOArquivo.carregarArquivo();

        btSalvar = (Button) findViewById(R.id.bt_salvar);
        btMostar = (Button) findViewById(R.id.bt_mostrar);
        ptCampoItem = (TextView) findViewById(R.id.pt_campo_item);
        tvContador = (TextView) findViewById(R.id.tv_contador);

        tvContador.setText("Total de itens cadastrados "+lista.size());

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !ptCampoItem.getText().toString().isEmpty() ) {
                    ArrayList<String> temp = lista;
                    temp.add(ptCampoItem.getText().toString());
                    boolean situacao = DAOArquivo.salvarArquivo(lista);
                    if (situacao){
                        tvContador.setText("Total de itens cadastrados "+lista.size());
                        lista = temp;
                        Toast.makeText(getApplicationContext(), "Item salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Ops! Erro ao salvar o item!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btMostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, MainActivityTable.class);
                it.putStringArrayListExtra("lista", lista);
//                Bundle bu = new Bundle();
//                bu.putStringArrayList("lista", lista);
                startActivity(it);
            }
        });

    }
}
