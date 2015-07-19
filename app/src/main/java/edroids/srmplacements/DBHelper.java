package edroids.srmplacements;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="MyDatabase.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table notifications " +"(id integer primary key, content text,date text,replied text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notifications");
        onCreate(db);
    }

    public Map getAllNotifications()
    {
        Map<Integer,String[]> notifmap=new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from notifications", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            int id=res.getInt(res.getColumnIndex("id"));
            String content=res.getString(res.getColumnIndex("content"));
            String date=res.getString(res.getColumnIndex("date"));
            String replied=res.getString(res.getColumnIndex("replied"));
            String values[]={content,date,replied};
            notifmap.put(id, values);
            res.moveToNext();
        }
        return notifmap;
    }

    public boolean insertNotification  (int id, String content, String date, String replied)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("content", content);
        contentValues.put("date", date);
        contentValues.put("replied", replied);
        db.insert("notifications", null, contentValues);
        return true;
    }
}