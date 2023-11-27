package com.example.cotarpreco.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cotarpreco.model.Endereco;
import com.example.cotarpreco.model.EntregaPedido;

public class EntregaDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public EntregaDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    public void salvarEndereco(Endereco endereco){

        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUNA_ENDERECO_LOGRADOURO, endereco.getLogradouro());
        cv.put(DbHelper.COLUNA_ENDERECO_BAIRRO, endereco.getBairro());
        cv.put(DbHelper.COLUNA_ENDERECO_MUNICIPIO, endereco.getMunicipio());
        cv.put(DbHelper.COLUNA_ENDERECO_REFERENCIA, endereco.getReferencia());

        try {
            write.insert(DbHelper.TABELA_ENTREGA, null, cv);
            Log.i("INFO_DB", "onCreate: Sucesso ao salvar a tebela.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao salvar a tebela..");
        }

    }

    public void atualizarEndereco(Endereco endereco){

        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUNA_ENDERECO_LOGRADOURO, endereco.getLogradouro());
        cv.put(DbHelper.COLUNA_ENDERECO_BAIRRO, endereco.getBairro());
        cv.put(DbHelper.COLUNA_ENDERECO_MUNICIPIO, endereco.getMunicipio());
        cv.put(DbHelper.COLUNA_ENDERECO_REFERENCIA, endereco.getReferencia());

        try {
            write.update(DbHelper.TABELA_ENTREGA, cv, null, null);
            Log.i("INFO_DB", "onCreate: Sucesso ao atualizar o endereço.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao atualizar o endereço");
        }

    }

    public EntregaPedido getEntrega(){
        EntregaPedido entregaPedido = new EntregaPedido();
        Endereco endereco = new Endereco();

        String sql = " SELECT * FROM " + DbHelper.TABELA_ENTREGA + ";";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()){
            @SuppressLint("Range")
            String logradouro = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ENDERECO_LOGRADOURO));
            @SuppressLint("Range")
            String bairro = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ENDERECO_BAIRRO));
            @SuppressLint("Range")
            String municipio = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ENDERECO_MUNICIPIO));
            @SuppressLint("Range")
            String referencia = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ENDERECO_REFERENCIA));

            endereco.setLogradouro(logradouro);
            endereco.setBairro(bairro);
            endereco.setMunicipio(municipio);
            endereco.setReferencia(referencia);

            entregaPedido.setEndereco(String.valueOf(endereco));

        }

        return entregaPedido;
    }

    public Endereco getEndereco(){
        Endereco endereco = null;

        String sql = " SELECT * FROM " + DbHelper.TABELA_ENTREGA + ";";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()){
            @SuppressLint("Range")
            String logradouro = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ENDERECO_LOGRADOURO));
            @SuppressLint("Range")
            String bairro = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ENDERECO_BAIRRO));
            @SuppressLint("Range")
            String municipio = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ENDERECO_MUNICIPIO));
            @SuppressLint("Range")
            String referencia = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ENDERECO_REFERENCIA));

            endereco = new Endereco();
            endereco.setLogradouro(logradouro);
            endereco.setBairro(bairro);
            endereco.setMunicipio(municipio);
            endereco.setReferencia(referencia);
        }

        return endereco;
    }

}
