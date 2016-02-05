package denis.frost.testnd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import denis.frost.testnd.adapter.MovieRecyclerAdapter;
import denis.frost.testnd.adapter.TabsPagerAdapterFragment;
import denis.frost.testnd.database.MovieDBManager;
import denis.frost.testnd.movie.Movies;
import denis.frost.testnd.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LOG_TAG = "my_log";

    private List<Movies> movieListDB;
    private RecyclerView movieRV;
    private MovieRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private SharedPreferences sp;
    private TextView noContent;
    private ViewPager mViewPager;

    private MovieDBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MovieDBManager(getBaseContext());
        initTabs();
        initNavigationDrawer();
        initFAB();
//        initUI();
        initDB();
//        sp = PreferenceManager.getDefaultSharedPreferences(this);
//        //     if (movieListDB.size() > 0) {
//        adapter = new MovieRecyclerAdapter(MainActivity.this, movieListDB);
//        movieRV.setAdapter(adapter);
        //   } else {
        //  noContent.setVisibility(View.VISIBLE);
        //   }
//        startService(new Intent(MainActivity.this, MovieService.class));
//        registerReceiver(br, new IntentFilter(Constants.BROADCAST_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  unregisterReceiver(br);
        stopService(new Intent(MainActivity.this, MovieService.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        final MenuItem item = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener(this);
//
//        MenuItemCompat.setOnActionExpandListener(item,
//                new MenuItemCompat.OnActionExpandListener() {
//                    @Override
//                    public boolean onMenuItemActionCollapse(MenuItem item) {
//                        adapter
// .setFilter(movieListDB);
//                        return true;
//                    }
//                    @Override
//                    public boolean onMenuItemActionExpand(MenuItem item) {
//                        return true;
//                    }
//                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                break;

            case R.id.favorite:
                startActivity(new Intent(MainActivity.this, ViewerFavoriteActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        final List<Movies> filteredMovieList = filter(movieListDB, query);
//        adapter.setFilter(filteredMovieList);
//        adapter.notifyDataSetChanged();
//        return true;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }
//
//    private List<Movies> filter(List<Movies> mMovies, String query) {
//        query = query.toLowerCase();
//        final List<Movies> filteredModelList = new ArrayList<>();
//        for (Movies movies : mMovies) {
//            final String text = movies.getTitle().toLowerCase();
//            if (text.contains(query)) {
//                filteredModelList.add(movies);
//            }
//        }
//        return filteredModelList;
//    }
    private void initTabs() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        TabsPagerAdapterFragment adapterFragment = new TabsPagerAdapterFragment(getSupportFragmentManager());
        mViewPager.setAdapter(adapterFragment);

        tabLayout.setupWithViewPager(mViewPager);

    }

    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initDB()  {
        try {
            movieListDB = db.getAllImdbTop250();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    private void initUI() {
//        movieRV = (RecyclerView) findViewById(R.id.rv);
//        movieRV.setLayoutManager(new GridLayoutManager(this, 2));
//        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
//        progressBar.setVisibility(View.VISIBLE);
//        //   noContent = (TextView) findViewById(R.id.no_content);
//    }

//    private BroadcastReceiver br = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            int status = intent.getIntExtra(Constants.PARAM_STATUS, 0);
//            if (status == Constants.STATUS_START) {
//                initDB();
//                progressBar.setVisibility(View.GONE);
//            } else {
//                Toast.makeText(MainActivity.this, "Failed download information!", Toast.LENGTH_SHORT).show();
//            }
//            adapter.notifyDataSetChanged();
//        }
//    };
}
