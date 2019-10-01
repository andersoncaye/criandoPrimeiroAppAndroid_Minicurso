package com.example.minicurso.Class;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class DAOArquivo {

    private static String nomeArquivo= "listaDeItens.txt";

    private static String obterDiretorio(){
        File root = android.os.Environment.getExternalStorageDirectory();
        return root.toString();
    }

    public static void definirNomeArquivo(String nome){
        nomeArquivo = nome;
    }

    public static String obterNomeArquivo () {
        return nomeArquivo;
    }

    public static ArrayList<String> carregarArquivo(){
        File arquivo;
        ArrayList<String> dadosArquivo = new ArrayList();;
        try {
            arquivo = new File(Environment.getExternalStorageDirectory(), obterNomeArquivo());
            BufferedReader br =  new BufferedReader(new FileReader(arquivo));
            String lerLinha;
            while ((lerLinha = br.readLine()) != null){
                dadosArquivo.add(lerLinha);
            }
        } catch (Exception e) {

        }
        return dadosArquivo;
    }

    public static boolean salvarArquivo(ArrayList<String> dadosArquivo){
        boolean estado = false;

        File arquivo;
        byte[] dados;

        try {
            arquivo = new File(Environment.getExternalStorageDirectory(), obterNomeArquivo());
            FileOutputStream fos = new FileOutputStream(arquivo);
            String tempDados = "";
            for(int i = 0; i < dadosArquivo.size(); i++) {
                tempDados += dadosArquivo.get(i) + "\n";
            }
            dados = tempDados.getBytes();
            fos.write(dados);
            fos.flush();
            fos.close();
            estado = true;
        } catch (Exception e) {

        }

        return estado;
    }

}
