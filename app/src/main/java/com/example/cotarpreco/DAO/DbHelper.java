package com.example.cotarpreco.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DB_COTARPRECO";

    public static final String TABELA_USUARIO = "usuario";
    public static final String TABELA_ITEM_PEDIDO = "item_pedido";
    public static final String TABELA_ENTREGA = "entrega";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_ID_FIREBASE = "id_firebase";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_MARCA = "marca";
    public static final String COLUNA_QUANTIDADE = "quantidade";
    public static final String COLUNA_DESCRICAO = "descricao";
    public static final String COLUNA_ENDERECO_LOGRADOURO = "logradouro";
    public static final String COLUNA_ENDERECO_BAIRRO = "bairro";
    public static final String COLUNA_ENDERECO_REFERENCIA = "referencia";
    public static final String COLUNA_ENDERECO_MUNICIPIO = "municipio";

    public DbHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String TABLE_USUARIO = " CREATE TABLE IF NOT EXISTS " + TABELA_USUARIO
                + " (id_firebase TEXT NOT NULL, " +
                " nome TEXT NOT NULL); ";

        String TABLE_ITEM_PEDIDO = " CREATE TABLE IF NOT EXISTS " + TABELA_ITEM_PEDIDO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " id_firebase TEXT NOT NULL, " +
                " nome TEXT NOT NULL, " +
                " marca TEXT NOT NULL, " +
                " quantidade INTEGER NOT NULL, " +
                " descricao TEXT); ";

        String TABLE_ENTREGA = " CREATE TABLE IF NOT EXISTS " + TABELA_ENTREGA
                + " (logradouro TEXT NOT NULL, " +
                " bairro TEXT NOT NULL, " +
                " referencia TEXT NOT NULL, " +
                " municipio INTEGER NOT NULL); ";

        try {
            sqLiteDatabase.execSQL(TABLE_USUARIO);
            sqLiteDatabase.execSQL(TABLE_ITEM_PEDIDO);
            sqLiteDatabase.execSQL(TABLE_ENTREGA);
            Log.i("INFO_DB", "onCreate: Tabela criada com sucesso.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao criar tabela.");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
