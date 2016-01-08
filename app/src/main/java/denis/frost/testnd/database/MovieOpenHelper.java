package denis.frost.testnd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import denis.frost.testnd.movie.Movies;


public class MovieOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_SCRIPT_MOVIE = "CREATE TABLE " +
                            Movies.TABLE_MOVIE + "(" +
                            Movies.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            Movies.FIELD_TITLE+" TEXT NOT NULL, "+
                            Movies.FIELD_THUMBNAIL+" TEXT NOT NULL, "+
                            Movies.FIELD_YEAR+" INTEGER NOT NULL, "+
                            Movies.FIELD_RANKING+" INTEGER NOT NULL, "+
                            Movies.FIELD_RATING+" REAL NOT NULL, "+
                            Movies.FIELD_PLOT+" TEXT NOT NULL "+");";
    private static final String DATABASE_CREATE_SCRIPT_MOVIE_FAVORITE = "CREATE TABLE " +
            Movies.TABLE_MOVIE_FAVORITE + "(" +
            Movies.FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Movies.FIELD_TITLE+" TEXT NOT NULL, "+
            Movies.FIELD_THUMBNAIL+" TEXT NOT NULL, "+
            Movies.FIELD_YEAR+" INTEGER NOT NULL, "+
            Movies.FIELD_RANKING+" INTEGER NOT NULL, "+
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
        db.execSQL(DATABASE_CREATE_SCRIPT_MOVIE);
        db.execSQL(DATABASE_CREATE_SCRIPT_MOVIE_FAVORITE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_CREATE_SCRIPT_MOVIE);
        // Создаём новую таблицу
        onCreate(db);
    }
}
