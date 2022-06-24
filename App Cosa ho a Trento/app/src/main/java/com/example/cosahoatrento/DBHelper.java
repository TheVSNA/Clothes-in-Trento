package com.example.cosahoatrento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class DBHelper extends SQLiteOpenHelper implements Serializable {
    public DBHelper(Context context) {
        super(context, "userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  IF NOT EXISTS vestiti(id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, qta INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addCategoriaVestiti(String tipo, int qta ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put("id",id);
        cv.put("tipo",tipo);
        cv.put("qta",qta);
        if(db.insert("vestiti",null,cv) == -1)
            return false;
        else
            return true;
    }

    public Boolean modificaVestiti(int id, String tipo, int qta ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tipo",tipo);
        cv.put("qta",qta);
        Cursor cursor = db.rawQuery("SELECT * FROM vestiti WHERE id=?",new String[] {String.valueOf(id)});
        if(cursor.getCount()>0) {
            if (db.update("vestiti", cv,"id=?",new String[] {String.valueOf(id)}) == -1)
                return false;
            else
                return true;
        }else
            return false;
    }
    public Boolean aggiungiTogliQta(int id, int qta ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("qta",qta);
        Cursor cursor = db.rawQuery("SELECT * FROM vestiti WHERE id=?",new String[] {String.valueOf(id)});
        if(cursor.getCount()>0) {
            if (db.update("vestiti", cv,"id=?",new String[] {String.valueOf(id)}) == -1)
                return false;
            else
                return true;
        }else
            return false;
    }
    public Cursor getElenco(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM vestiti",null);
        return c;
    }
    public boolean rimuoviCategoria(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM vestiti WHERE id=?",new String[] {String.valueOf(id)});
        if(cursor.getCount()>0){
            if(db.delete("vestiti","id=?",new String[] {String.valueOf(id)})==-1)
                return false;
            else
                return true;
        }else
            return false;
    }
}
