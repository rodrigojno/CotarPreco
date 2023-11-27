package com.example.cotarpreco.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cotarpreco.model.ItemPedido;

import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public ItemPedidoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    public long salvar(ItemPedido itemPedido) {

        long id = 0;

        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUNA_ID_FIREBASE, itemPedido.getIdItem());
        cv.put(DbHelper.COLUNA_NOME, itemPedido.getItem());
        cv.put(DbHelper.COLUNA_MARCA, itemPedido.getMarca());
        cv.put(DbHelper.COLUNA_QUANTIDADE, itemPedido.getQuantidade());
        cv.put(DbHelper.COLUNA_DESCRICAO, itemPedido.getDescricao());

        try {
            id = write.insert(DbHelper.TABELA_ITEM_PEDIDO, null, cv);
            Log.i("INFO_DB", "onCreate: Sucesso ao salvar a tabela.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao salvar a tabela.");
        }
        return id;
    }

    public void atualizar(ItemPedido itemPedido) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.COLUNA_QUANTIDADE, itemPedido.getQuantidade());

        try {
            String where = "id=?";
            String[] args = {String.valueOf(itemPedido.getId())};
            write.update(DbHelper.TABELA_ITEM_PEDIDO, cv, where, args);
            Log.i("INFO_DB", "onCreate: Sucesso ao atualizar a tabela.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao atualizar a tabela.");
        }
    }

    public List<ItemPedido> getList() {
        List<ItemPedido> itemPedidoList = new ArrayList<>();

        String sql = " SELECT * FROM " + DbHelper.TABELA_ITEM_PEDIDO + ";";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range")
            Long id_local = cursor.getLong(cursor.getColumnIndex(DbHelper.COLUNA_ID));
            @SuppressLint("Range")
            String id_firebase = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_ID_FIREBASE));
            @SuppressLint("Range")
            String item_nome = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_NOME));
            @SuppressLint("Range")
            String item_marca = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_MARCA));
            @SuppressLint("Range")
            int quantidade = cursor.getInt(cursor.getColumnIndex(DbHelper.COLUNA_QUANTIDADE));
            @SuppressLint("Range")
            String item_descricao = cursor.getString(cursor.getColumnIndex(DbHelper.COLUNA_DESCRICAO));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setId(id_local);
            itemPedido.setIdItem(id_firebase);
            itemPedido.setItem(item_nome);
            itemPedido.setMarca(item_marca);
            itemPedido.setQuantidade(quantidade);
            itemPedido.setDescricao(item_descricao);

            itemPedidoList.add(itemPedido);

        }
        return itemPedidoList;
    }

    public void remover(Long id){
        try {
            String where = "id=?";
            String[] args = {String.valueOf(id)};
            write.delete(DbHelper.TABELA_ITEM_PEDIDO, where, args);
            Log.i("INFO_DB", "onCreate: Sucesso ao deletar item.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao deletar item.");
        }
    }

    public void removerTodos(){
        try {
            write.delete(DbHelper.TABELA_ITEM_PEDIDO, null, null);
            Log.i("INFO_DB", "onCreate: Sucesso ao deletar todos os itens.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao deletar todos os itens.");
        }
    }

    public void limparPedido(){
        try {
            write.delete(DbHelper.TABELA_USUARIO, null, null);
            write.delete(DbHelper.TABELA_ENTREGA, null, null);
            write.delete(DbHelper.TABELA_ITEM_PEDIDO, null, null);
            Log.i("INFO_DB", "onCreate: Sucesso ao limpar o pedido.");
        } catch (Exception e) {
            Log.i("INFO_DB", "onCreate: Erro ao limpar o pedido.");
        }
    }

}
