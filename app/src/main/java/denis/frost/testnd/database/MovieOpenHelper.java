package denis.frost.testnd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import denis.frost.testnd.movie.Movies;


public class MovieOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_SCRIPT_IMDB_TOP_250 = "CREATE TABLE " +
                            Movies.TABLE_IMDB_TOP_250 + "(" +
                            Movies.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            Movies.FIELD_TITLE+" TEXT NOT NULL, "+
                            Movies.FIELD_THUMBNAIL+" TEXT NOT NULL, "+
                            Movies.FIELD_YEAR+" INTEGER NOT NULL, "+
                            Movies.FIELD_RANKING+" INTEGER NOT NULL, "+
                            Movies.FIELD_RATING+" REAL NOT NULL, "+
                            Movies.FIELD_PLOT+" TEXT NOT NULL "+");";
    private static final String DATABASE_CREATE_SCRIPT_IN_THEATERS = "CREATE TABLE " +
            Movies.TABLE_IN_THEATERS + "(" +
            Movies.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Movies.FIELD_TITLE+" TEXT NOT NULL, "+
            Movies.FIELD_THUMBNAIL+" TEXT NOT NULL, "+
            Movies.FIELD_YEAR+" INTEGER NOT NULL, "+
            Movies.FIELD_RELEASE_DATE+" INTEGER NOT NULL, "+
            Movies.FIELD_RATING+" REAL NOT NULL, "+
            Movies.FIELD_PLOT+" TEXT NOT NULL "+");";
    private static final String DATABASE_CREATE_SCRIPT_COMING_SOON = "CREATE TABLE " +
            Movies.TABLE_COMING_SOON + "(" +
            Movies.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Movies.FIELD_TITLE+" TEXT NOT NULL, "+
            Movies.FIELD_THUMBNAIL+" TEXT NOT NULL, "+
            Movies.FIELD_YEAR+" INTEGER NOT NULL, "+
            Movies.FIELD_RELEASE_DATE+" INTEGER NOT NULL, "+
            Movies.FIELD_RATING+" REAL NOT NULL, "+
            Movies.FIELD_PLOT+" TEXT NOT NULL "+");";
    private Context mContext;

    public MovieOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public MovieOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT_IMDB_TOP_250);
        db.execSQL(DATABASE_CREATE_SCRIPT_IN_THEATERS);
        db.execSQL(DATABASE_CREATE_SCRIPT_COMING_SOON);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_CREATE_SCRIPT_IMDB_TOP_250);
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_CREATE_SCRIPT_IN_THEATERS);
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_CREATE_SCRIPT_COMING_SOON);
        // Создаём новую таблицу
        onCreate(db);
    }
}
