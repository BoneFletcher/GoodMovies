package denis.frost.testnd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import denis.frost.testnd.iterfaces.IDataManager;
import denis.frost.testnd.movie.Movies;


public class MovieDBManager implements IDataManager<Movies> {
    private SQLiteDatabase db;
    private MovieOpenHelper moh;
    public MovieDBManager(Context context) {
        moh = new MovieOpenHelper(context);
    }
    public void open() throws SQLException {
        db = moh.getWritableDatabase();
    }
    public void close() {
        if (db != null)
            db.close();
    }
    @Override
    public long save(Movies movies) throws IOException, SQLException {
        ContentValues cv = new ContentValues();
        cv.put(Movies.FIELD_TITLE, movies.getTitle());
        cv.put(Movies.FIELD_THUMBNAIL, movies.getThumbnailUrl());
        cv.put(Movies.FIELD_YEAR, movies.getYear());
        cv.put(Movies.FIELD_RANKING, movies.getRanking());
        cv.put(Movies.FIELD_RATING, movies.getRating());
        cv.put(Movies.FIELD_PLOT, movies.getPlot());
        open();
        long _id = db.insert(Movies.TABLE_MOVIE, null, cv);
        close();
        return _id;
    }
    public long updateMovies (Movies movies) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put(Movies.FIELD_TITLE, movies.getTitle());
        cv.put(Movies.FIELD_THUMBNAIL, movies.getThumbnailUrl());
        cv.put(Movies.FIELD_YEAR, movies.getYear());
        cv.put(Movies.FIELD_RANKING, movies.getRanking());
        cv.put(Movies.FIELD_RATING, movies.getRating());
        cv.put(Movies.FIELD_PLOT, movies.getPlot());
        open();
        long _id = db.update(Movies.TABLE_MOVIE, cv, null,null);
        close();
        return _id;
    }
    @Override
    public  boolean delete (Movies movies) throws SQLException {
        open();
        String[] whereArgs = {String.valueOf(movies.id)};
        int rows = db.delete(Movies.TABLE_MOVIE, Movies.FIELD_ID + " = ? ", whereArgs);
        close();
        return rows > 0;
    }

    @Override
    public Movies get(int id) throws SQLException {
        db = moh.getReadableDatabase();
        String[] whereArgs = {String.valueOf(id)};
        Cursor curs = db.query(Movies.TABLE_MOVIE,
                Movies.projections,
                " _id = ?",
                whereArgs,
                null,
                null,
                null);
        Movies mItem = new Movies();

        if (curs.moveToFirst()) {
            mItem = Movies.getItemFromCursor(curs);
        }
        db.close();
        return mItem;
    }

    @Override
    public List<Movies> getAll() throws SQLException {
        open();
        List<Movies> mItem = new ArrayList<>();
        Cursor curs = db.query(Movies.TABLE_MOVIE,
                Movies.projections,
                null,
                null,
                null,
                null,
                null);
        if (curs.moveToFirst()) {
            do {
                mItem.add(Movies.getItemFromCursor(curs));
            } while (curs.moveToNext());
        }
        close();
        return mItem;
    }

}
