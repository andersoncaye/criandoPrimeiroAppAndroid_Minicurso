package com.example.minicurso;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minicurso.Class.DAOArquivo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //declaracao das variaveis
    ArrayList<String> lista;
    Button btSalvar;
    Button btMostar;
    TextView ptCampoItem;
    TextView tvContador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Verificar se a permissao foi concedida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
        //menssagem
        Toast.makeText(getApplicationContext(), "Bem-Vindo!", Toast.LENGTH_SHORT).show();

        //inicializar o arquivo para leitura e gravacao
        lista = DAOArquivo.carregarArquivo();

        //inicializar os campos, botoes e labels
        btSalvar = (Button) findViewById(R.id.bt_salvar);
        btMostar = (Button) findViewById(R.id.bt_mostrar);
        ptCampoItem = (TextView) findViewById(R.id.pt_campo_item);
        tvContador = (TextView) findViewById(R.id.tv_contador);
        tvContador.setText("Total de itens cadastrados "+lista.size()); //R.string.app_name);

        //obter a acao de um clique do botao SALVAR
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifica se o compo nao esta vazio
                if ( !ptCampoItem.getText().toString().isEmpty() ) {
                    //add o conteudo do campo na lista
                    lista.add(ptCampoItem.getText().toString());
                    //salva a informacao no arquivo e retorna se deu certo ou não
                    boolean situacao = DAOArquivo.salvarArquivo(lista);
                    if (situacao){
                        //atualiza o total de itens registrados
                        tvContador.setText("Total de itens cadastrados "+lista.size());
                        //limpa o campo
                        ptCampoItem.setText("");
                        //mensagem de feedback para o usuario
                        Toast.makeText(getApplicationContext(), "Item salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Ops! Erro ao salvar o item!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        //obter a acao de um clique do botao MOSTRAR
        btMostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //preparar para abrir uma nova janela, uma outra activity
                Intent it = new Intent(MainActivity.this, MainActivityTable.class);
                it.putStringArrayListExtra("lista", lista);
//                Bundle bu = new Bundle();
//                bu.putStringArrayList("lista", lista);
//                it.putExtra(bu);
                startActivity(it);
                finish();
            }
        });

    }
}
