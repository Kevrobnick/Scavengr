package kevrobnick.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kevin on 3/12/2015.
 * Updated by Rob on 3/18/2015
 *
 */
public class MyDBHandler extends SQLiteOpenHelper {

    //All static variables

    //Database Version
    private static final int DDATABASE_VERSION =1;
    //Database Name
    private static final String DATABASE_NAME = "Scavengrdb";

    //table name
    private static final  String TABLE_CREATEGAME = "Create Game";

    //Table Column Names
    private static final String KEY_ID = "id";
    private static final String KEY_USER ="user";
    private static final String KEY_IMAGE = "image";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GAME_TABLE = "CREATE TABLE " + TABLE_CREATEGAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER + " TEXT,"
                + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_GAME_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATEGAME);
        // Create tables again
        onCreate(db);
    }
    public void addGame (Game game){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER,game._name);

        db.insert(TABLE_CREATEGAME, null,values);
        db.close();
    }
    Game getGame(int id){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CREATEGAME, new String[] {KEY_ID, KEY_USER,KEY_IMAGE},KEY_ID+"=",new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();
        Game game = new Game(Integer.parseInt((cursor.getString(0))),cursor.getString(1),cursor.getBlob(1));
        return game;
    }
}
