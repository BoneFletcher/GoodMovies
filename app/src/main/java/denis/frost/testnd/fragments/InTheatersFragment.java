package denis.frost.testnd.fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import denis.frost.testnd.R;
import denis.frost.testnd.adapter.MovieRecyclerAdapter;
import denis.frost.testnd.database.MovieDBManager;
import denis.frost.testnd.movie.Movies;

public class InTheatersFragment extends Fragment {
    public static final int LAYOUT = R.layout.content_main;
    private View view;
    private static final String LOG_TAG = "my_log";


    private List<Movies> movieList;
    private List<Movies> movieListDB;
    private RecyclerView movieRV;
    private MovieRecyclerAdapter adapter;
    private SharedPreferences sp;
    private List<Movies> qp;
    private MovieDBManager db;

    public InTheatersFragment() {
        // Required empty public constructor
    }
    public static InTheatersFragment getInstace () {
        Bundle args = new Bundle();
        InTheatersFragment inftFragment = new InTheatersFragment();
        inftFragment.setArguments(args);
        return inftFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        movieRV = (RecyclerView) view.findViewById(R.id.rv);
        movieRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        new AsyncHttpTask().execute("http://www.myapifilms.com/imdb/inTheaters?token=f836266a-c461-451d-b697-7e01d11b1a3b&format=json&language=en-us");
        db = new MovieDBManager(getContext());
        try {
            movieListDB = db.getAllImdbTop250();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new MovieRecyclerAdapter(getContext(), movieListDB);
        movieRV.setAdapter(adapter);
        return view;
    }
    private void parseResult(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            JSONObject jsonData = obj.getJSONObject("data");
            JSONArray jsonArrayInTheaters = jsonData.getJSONArray("inTheaters");

        //    JSONArray jsonarray = jsonArrayInTheaters.optJSONArray(4);
            JSONObject jsson = jsonArrayInTheaters.optJSONObject(0);
            JSONArray jsonarray = jsson.getJSONArray("movies");
            movieList = new ArrayList<>();

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject json = jsonarray.optJSONObject(i);
                Movies movies = Movies.getItemFromJsonInTheaters(json);
                movieList.add(movies);
                MovieDBManager db = new MovieDBManager(getContext());
                try {
                    movies.id = (int) db.saveInTheaters(movies);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();
                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(LOG_TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            if (result == 1) {
                adapter = new MovieRecyclerAdapter(getContext(), movieList);
                movieRV.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
