package denis.frost.testnd.recycleradapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import denis.frost.testnd.R;
import denis.frost.testnd.database.MovieDBManager;
import denis.frost.testnd.movie.MovieInfoActivity;
import denis.frost.testnd.movie.Movies;


public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {
    private List<Movies> movieList;
    private Context mContext;
    private MovieDBManager mdb;
    private static final String PREFS_NAME = "movie_prefs";

    public MovieRecyclerAdapter(Context context, List<Movies> moviesList) {
        this.movieList = moviesList;
        this.mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder movieViewHolder, final int i) {
        final Movies movies = movieList.get(i);
        mdb = new MovieDBManager(mContext);

        Picasso.with(mContext).load(movies.getThumbnailUrl())
                .into(movieViewHolder.imageView);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieViewHolder holder = (MovieViewHolder) view.getTag();
                int position = holder.getPosition();
                Movies movies = movieList.get(position);

                Intent intentToMovieInfo = new Intent(mContext, MovieInfoActivity.class);
                intentToMovieInfo.putExtra("title", movies.getTitle());
                intentToMovieInfo.putExtra("year", movies.getYear());
                intentToMovieInfo.putExtra("rating", movies.getRating());
                intentToMovieInfo.putExtra("ranking", movies.getRanking());
//                Передача массива по интенту
//                Bundle bundle=new Bundle(); //создаем посылочку
//                ArrayList<String> genre = movies.getGenre();
//                bundle.putInt("StrArrays", genre.size()); //пишем размер массива массивов
//                for(int n=0; n < genre.size(); n++) bundle.putString("Array"+n, genre.get(n));//пишем нумерованный массив
//                intentToMovieInfo.putExtra("mas", bundle); // засылаем посылочку в Intent

//                ArrayList<String> genre = movies.getGenre();
//                for (int i = 0;i<genre.size();i++) {
//                    intentToMovieInfo.putExtra("genres", movies.getGenre());
//                }
                intentToMovieInfo.putExtra("plot", movies.getPlot());
                intentToMovieInfo.putExtra("thumblUrl", movies.getThumbnailUrl());
                mContext.startActivity(intentToMovieInfo);
            }
        };
        final SharedPreferences setFavoritesChb = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFavoriteChB = setFavoritesChb.getBoolean(movies.getTitle(), false);
        (movieViewHolder).checkFavorite.setChecked(isFavoriteChB);
        (movieViewHolder).checkFavorite.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SharedPreferences.Editor editor = setFavoritesChb.edit();
                        editor.putBoolean(movies.getTitle(), isChecked);
                        editor.apply();
                        if ((movieViewHolder).checkFavorite.isChecked()) {
                            Toast.makeText(mContext, "Film favorite", Toast.LENGTH_SHORT).show();
                            //     ClassForMovie.classForMovie.add(movieList.get(i));
                            try {
                                mdb.save(movieList.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Film not favorite", Toast.LENGTH_SHORT).show();
                            //   ClassForMovie.classForMovie.remove(movieList.get(i));
                            movieViewHolder.checkFavorite.setChecked(false);
                            try {
                                mdb.delete(movieList.get(i));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        movieViewHolder.imageView.setOnClickListener(clickListener);
        movieViewHolder.imageView.setTag(movieViewHolder);
    }
    public void setFilter(List<Movies> moviesFilter) {
        movieList = new ArrayList<>();
        movieList.addAll(moviesFilter);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != movieList ? movieList.size() : 0);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected CheckBox checkFavorite;
        public MovieViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.checkFavorite = (CheckBox) view.findViewById(R.id.check_favorite);
        }
    }
}