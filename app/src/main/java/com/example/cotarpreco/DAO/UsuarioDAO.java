package com.example.cotarpreco.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cotarpreco.model.Usuario;

public class UsuarioDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public UsuarioDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    public void salvar(Usuario usuario){

        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUNA_ID_FIREBASE, usuario.getId());
        cv.put(DbHelper.COLUNA_NOME, usuario.getNome());

        try {
            write.insert(DbHelper.TABELA_USUARIO, null, cv);
            Log.i("INFO_DB", "onCreate: Sucesso ao salvar a tabela.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao salvar a tabela.");
        }

    }

    public Usuario getUsuario(){
        Usuario usuario = null;

        String sql = " SELECT * FROM " + DbHelper.TABELA_USUARIO + ";";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()){
            @SuppressLint("Range")
            String id_firebase = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ID_FIREBASE));
            @SuppressLint("Range")
            String nome = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_NOME));

            usuario = new Usuario();
            usuario.setId(id_firebase);
            usuario.setNome(nome);

        }
        return usuario;
    }

    public void removerEmpresa(){
        try {
            write.delete(DbHelper.TABELA_USUARIO, null, null);
            Log.i("INFO_DB", "onCreate: Sucesso ao remover a tabela.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao remover a tabela.");
        }
    }



}
