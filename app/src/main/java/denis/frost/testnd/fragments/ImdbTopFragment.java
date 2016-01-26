package denis.frost.testnd.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import denis.frost.testnd.MovieService;
import denis.frost.testnd.R;
import denis.frost.testnd.adapter.MovieRecyclerAdapter;
import denis.frost.testnd.database.MovieDBManager;
import denis.frost.testnd.extras.Constants;
import denis.frost.testnd.movie.Movies;


public class ImdbTopFragment extends Fragment {
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
            movieListDB = db.getAll();
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
                    movieListDB = db.getAll();
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

}
