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

    //declaracao das variaveis
    ArrayList<String> lista;
    ListView lvItens;
    Button btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table);

        //inicializar os campos, botoes e labels
        lvItens = (ListView) findViewById(R.id.lv_itens);
        btVoltar = (Button) findViewById(R.id.bt_voltar);

        //obter a acao de um clique do botao VOLTAR
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivityTable.this, MainActivity.class);
                startActivity(it);
            }
        });

        //obter a lista enviado pela transicao de tela
        Intent it = getIntent();
        lista = it.getStringArrayListExtra("lista");

        caregarListView();

        //obter resposta pra acao de clique longo, na lista de itens
        lvItens.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //remover o item pressionado, com a posicao pasada por parametro pelo proprio metodo
                lista.remove(position);

                //salvar a lista modificada
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

    //metodo para popular a list view
    private void caregarListView(){
        String[] dados = new String[lista.size()];
        for ( int i = 0; i < lista.size(); i++){
            dados[i] = lista.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dados);
        lvItens.setAdapter(adapter);
    }
}
