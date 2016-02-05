package denis.frost.testnd.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import denis.frost.testnd.MovieService;
import denis.frost.testnd.R;
import denis.frost.testnd.adapter.MovieRecyclerAdapter;
import denis.frost.testnd.database.MovieDBManager;
import denis.frost.testnd.extras.Constants;
import denis.frost.testnd.movie.Movies;


public class ImdbTopFragment extends Fragment implements SearchView.OnQueryTextListener {
    public static final int LAYOUT = R.layout.content_main;
    private List<Movies> movieListDB;
    private RecyclerView movieRV;
    private MovieRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private SharedPreferences sp;
    private TextView noContent;
    private ViewPager mViewPager;

    private MovieDBManager db;
    private View view;

    public static ImdbTopFragment getInstace () {
        Bundle args = new Bundle();
        ImdbTopFragment imdbFragment = new ImdbTopFragment();
        imdbFragment.setArguments(args);
        return imdbFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().stopService(new Intent(getContext(), MovieService.class));
        getActivity().unregisterReceiver(br);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        movieRV = (RecyclerView) view.findViewById(R.id.rv);
        movieRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        db = new MovieDBManager(getContext());
        try {
            movieListDB = db.getAllImdbTop250();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new MovieRecyclerAdapter(getContext(), movieListDB);
        movieRV.setAdapter(adapter);
        getActivity().startService(new Intent(getContext(), MovieService.class));
        getActivity().registerReceiver(br, new IntentFilter(Constants.BROADCAST_ACTION));
        return view;
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(Constants.PARAM_STATUS, 0);
            if (status == Constants.STATUS_START) {
                try {
                    movieListDB = db.getAllImdbTop250();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Failed download information!", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        final List<Movies> filteredMovieList = filter(movieListDB, query);
        adapter.setFilter(filteredMovieList);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        adapter.setFilter(movieListDB);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });
    //    return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    private List<Movies> filter(List<Movies> mMovies, String query) {
        query = query.toLowerCase();
        final List<Movies> filteredModelList = new ArrayList<>();
        for (Movies movies : mMovies) {
            final String text = movies.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(movies);
            }
        }
        return filteredModelList;
    }
}
