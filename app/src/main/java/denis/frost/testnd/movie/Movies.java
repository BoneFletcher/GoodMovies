package denis.frost.testnd.movie;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movies {
    public static final String TABLE_IMDB_TOP_250 = "imdbtop250_db";
    public static final String TABLE_IN_THEATERS = "intheaters_db";
    public static final String TABLE_COMING_SOON = "comingsoon_db";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_THUMBNAIL = "thumbnail";
    public static final String FIELD_YEAR = "year";
    public static final String FIELD_PLOT = "plot";
    public static final String FIELD_RATING = "rating";
    public static final String FIELD_RANKING = "ranking";
    public static final String FIELD_RELEASE_DATE = "releasedate";

    public static final String KEY_JSON_TITLE = "title";
    public static final String KEY_JSON_URLPOSTER = "urlPoster";
    public static final String KEY_JSON_YEAR = "year";
    public static final String KEY_JSON_RATING = "rating";
    public static final String KEY_JSON_RANKING = "ranking";
    public static final String KEY_JSON_PLOT = "plot";
    public static final String KEY_JSON_RELEASE_DATE = "releaseDate";

    public static String [] projections = {
            FIELD_ID,
            FIELD_TITLE,
            FIELD_THUMBNAIL,
            FIELD_YEAR,
            FIELD_RANKING,
            FIELD_RATING,
            FIELD_PLOT
    };
    public int id;
    private String title, thumbnailUrl;
    private int year;
    private int ranking;
    private String plot;
    private double rating;
    private int releaseDate;
    private ArrayList<String> genre;

    public Movies() {
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

//    public ArrayList<String> getGenre() {
//        return genre;
//    }
//
//    public void setGenre(ArrayList<String> genre) {
//        this.genre = genre;
//    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public static Movies getItemFromCursor(Cursor curs) {
        Movies mItem = new Movies();
        mItem.id = curs.getInt(curs.getColumnIndex(Movies.FIELD_ID));
        mItem.setTitle(curs.getString(curs.getColumnIndex(Movies.FIELD_TITLE)));
        mItem.setThumbnailUrl(curs.getString(curs.getColumnIndex(Movies.FIELD_THUMBNAIL)));
        mItem.setYear(curs.getInt(curs.getColumnIndex(Movies.FIELD_YEAR)));
        mItem.setRanking(curs.getInt(curs.getColumnIndex(Movies.FIELD_RANKING)));
        mItem.setRating(curs.getDouble(curs.getColumnIndex(Movies.FIELD_RATING)));
        mItem.setPlot(curs.getString(curs.getColumnIndex(Movies.FIELD_PLOT)));
        return mItem;
    }
    public static Movies getItemFromJson(JSONObject json) throws JSONException {
        Movies mItem = new Movies();
        mItem.title = json.getString(KEY_JSON_TITLE);
        mItem.thumbnailUrl = json.getString(KEY_JSON_URLPOSTER);
        mItem.year = json.getInt(KEY_JSON_YEAR);
        mItem.ranking = json.getInt(KEY_JSON_RANKING);
        mItem.rating = json.getDouble(KEY_JSON_RATING);
        mItem.plot = json.getString(KEY_JSON_PLOT);

        return mItem;
    } public static Movies getItemFromJsonInTheaters(JSONObject json) throws JSONException {
        Movies mItem = new Movies();
        mItem.title = json.getString(KEY_JSON_TITLE);
        mItem.thumbnailUrl = json.getString(KEY_JSON_URLPOSTER);
        mItem.year = json.getInt(KEY_JSON_YEAR);
        mItem.releaseDate = json.getInt(KEY_JSON_RELEASE_DATE);
 //       mItem.rating = json.getDouble(KEY_JSON_RATING);
        mItem.plot = json.getString(KEY_JSON_PLOT);

        return mItem;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movies movieItem = (Movies) o;

      //  if (title != movieItem.title) return false;
        return title.equals(movieItem.title);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        return result;
    }
}
