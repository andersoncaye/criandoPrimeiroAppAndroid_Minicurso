package com.example.minicurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.minicurso.Class.DAOArquivo;

import java.util.ArrayList;

public class MainActivityTable extends AppCompatActivity {

    ArrayList<String> lista;
    ListView lvItens;
    Button btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table);

        lvItens = (ListView) findViewById(R.id.lv_itens);
        btVoltar = (Button) findViewById(R.id.bt_voltar);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivityTable.this, MainActivity.class);
                startActivity(it);
            }
        });

        Intent it = getIntent();
        lista = it.getStringArrayListExtra("lista");

        caregarListView();

        lvItens.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                lista.remove(position);

                if( DAOArquivo.salvarArquivo(lista) ){
                    caregarListView();
                    Toast.makeText(getApplicationContext(), "Item removido com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ops! Erro ao remover o item!", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

    }

    private void caregarListView(){
        String[] dados = new String[lista.size()];
        for ( int i = 0; i < lista.size(); i++){
            dados[i] = lista.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dados);
        lvItens.setAdapter(adapter);
    }
}
