package denis.frost.testnd;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import denis.frost.testnd.database.MovieDBManager;
import denis.frost.testnd.extras.Constants;
import denis.frost.testnd.movie.Movies;


public class MovieService extends Service {
    private static final String LOG_TAG = "myServiceLog";
    List<Movies> movieList;
    NotificationManager nm;
    public MovieService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new AsyncHttpTask().execute("http://api.myapifilms.com/imdb/top?start=1&end=20&token=f836266a-c461-451d-b697-7e01d11b1a3b&format=json&data=1");
        sendNotif();
        return super.onStartCommand(intent, flags, startId);
    }

    private void parseResult(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            JSONObject jsonData = obj.getJSONObject("data");
            JSONArray jsonarray = jsonData.getJSONArray("movies");
            movieList = new ArrayList<>();

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject json = jsonarray.optJSONObject(i);
                Movies movies = Movies.getItemFromJson(json);
                MovieDBManager db = new MovieDBManager(getBaseContext());
                Movies moviesDB = db.getImdbTop250(i);
                if (movies.equals(moviesDB)) {
                    db.updateMovies(movies);
                    db.deleteAll();
//                    db.delete(movies);
                    db.saveImdbTop250(movies);
                }
                else
                   db.saveImdbTop250(movies);
                movieList.add(movies);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
        }
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
            Intent intent = new Intent(Constants.BROADCAST_ACTION);
            intent.putExtra(Constants.PARAM_STATUS, Constants.STATUS_START);
            sendBroadcast(intent);
            if (result == 1) {
                intent.putExtra(Constants.PARAM_STATUS, Constants.STATUS_START);
                sendBroadcast(intent);
            } else {
                intent.putExtra(Constants.PARAM_STATUS, Constants.STATUS_STOP);
                sendBroadcast(intent);
            }
        }
    }
    void sendNotif() {
        Context context = getApplicationContext();
        Notification.Builder builder = new Notification.Builder(MovieService.this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.FILE_NAME, "somefile");
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setAutoCancel(false)
                .setTicker("See the movies")
                .setContentTitle("Update DB")
                .setContentText("Information about movies update")
                .setSmallIcon(R.drawable.movie_notif)
                .setContentIntent(pIntent)
                .setWhen(System.currentTimeMillis())
                .build();
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFY_ID, notification);
    }
}
