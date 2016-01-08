package denis.frost.testnd.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import denis.frost.testnd.MainActivity;
import denis.frost.testnd.R;
import denis.frost.testnd.settings.SettingsActivity;


public class MovieInfoActivity extends AppCompatActivity {
    ImageView ivPoster;
    TextView tvTitle;
    TextView tvYear;
    TextView tvRating;
    TextView tvRanking;
    TextView tvGenres;
    TextView tvPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        ivPoster = (ImageView) findViewById(R.id.iv_thumbnailURL);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvRating = (TextView) findViewById(R.id.tv_rating);
        tvRanking = (TextView) findViewById(R.id.tv_ranking);
        tvGenres = (TextView) findViewById(R.id.tv_genres);
        tvPlot = (TextView) findViewById(R.id.tv_plot);
        String tumblUrl = "";
        String title = "";
        int year = 0;
        double rating = 0.0;
        int ranking = 0;
        String genres = " ";
        String plot = " ";
        title = getIntent().getExtras().getString("title");
        tumblUrl = getIntent().getExtras().getString("thumblUrl");
        year = getIntent().getExtras().getInt("year");
        rating = getIntent().getExtras().getDouble("rating");
        ranking = getIntent().getExtras().getInt("ranking");
        genres = getIntent().getExtras().getString("genres");
        plot = getIntent().getExtras().getString("plot");
        tvTitle.setText(title);
        tvYear.setText(String.valueOf(year));
        tvRating.setText(String.valueOf(rating));
        tvRanking.setText(String.valueOf(ranking));
        tvGenres.setText(genres);
        tvPlot.setText(plot);
        Picasso.with(getApplicationContext()).load(tumblUrl).into(ivPoster);
        //Передача массива по интенту
//        Bundle bundleMas=getIntent().getExtras().getBundle("mas");
//        int size = bundleMas.getInt("StrArrays");
//        String[] mas;
//        mas=new String[size];
//        for(int n = 0; n < mas.length; n++)
//            mas[n]=bundleMas.getString("Array"+n);
//
//    }
        setupActionBar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(MovieInfoActivity.this, MainActivity.class));
            finish();
            return true;
        }
        if (id == R.id.action_settings) {
            Intent i = new Intent(MovieInfoActivity.this, SettingsActivity.class);
               startActivity(i);
            return true;
        }
//        switch (item.getItemId()) {
//            case R.id.home:
//                startActivity(new Intent(MovieInfoActivity.this, MainActivity.class));
//                finish();
//                return true;
//            case R.id.action_settings:
//                Intent i = new Intent(MovieInfoActivity.this, SettingsActivity.class);
//                startActivity(i);
//                break;
//        }
        return super.onOptionsItemSelected(item);
    }


}
