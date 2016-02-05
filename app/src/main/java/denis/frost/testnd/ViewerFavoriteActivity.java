package denis.frost.testnd;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import denis.frost.testnd.database.MovieDBManager;
import denis.frost.testnd.movie.Movies;
import denis.frost.testnd.adapter.MovieRecyclerAdapter;


/**
 * Created by Shakhov on 02.01.2016.
 */
public class ViewerFavoriteActivity extends AppCompatActivity {
       private static final String LOG_TAG = "my_log";
       private List<Movies> movieList ;
       private RecyclerView movieRV;
       private MovieRecyclerAdapter adapter;
       private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_dbfavorite);
        // Initialize recycler view
        movieRV = (RecyclerView) findViewById(R.id.rv);
        MovieDBManager db = new MovieDBManager(getBaseContext());
        movieRV.setLayoutManager(new GridLayoutManager(this, 2));
        movieList = new ArrayList<>();
        for (int i =1; i<=20;i++){
            try {
                movieList.add(db.getImdbTop250(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        adapter = new MovieRecyclerAdapter(ViewerFavoriteActivity.this, movieList);
        movieRV.setAdapter(adapter);
    }

}
