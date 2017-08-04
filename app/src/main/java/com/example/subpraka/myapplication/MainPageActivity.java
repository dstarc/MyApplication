package com.example.subpraka.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

//import com.android.volley.toolbox.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    private Button logoutBtn;
    private ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

//        /** Shared preferences content **/
////        final SessionManager session = new SessionManager(this);
////
////        if (!session.isLoggedIn()) {
////            session.setLogin(false);
////            finish();
////            startActivity(new Intent(MainPageActivity.this, MainActivity.class));
////        }
//        logoutBtn = (Button) findViewById(R.id.btnLogout);
//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent logoutIntent = new Intent(MainPageActivity.this,MainActivity.class);
////
//
//                /** Shared preferences content **/
//             //   session.setLogin(false);
//                finish();
//                startActivity(new Intent(MainPageActivity.this, MainActivity.class));
//                Toast.makeText(MainPageActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
//            }
//        });



        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        lvMovies = (ListView) findViewById(R.id.lvMovies);

        new JSONTask().execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesData.txt");

    }

    public class JSONTask extends AsyncTask<String, String, List<MovieModel>> {

        @Override
        protected List<MovieModel> doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();

                //BufferReader will help to read the
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJSOn = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJSOn);
                JSONArray parentArray = parentObject.getJSONArray("movies");
                // StringBuffer finalBufferedData = new StringBuffer();

                List<MovieModel> movieModelList = new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);
                    MovieModel movieModel = new MovieModel();
                    movieModel.setMovie(finalObject.getString("movie"));
                    movieModel.setYear(finalObject.getInt("year"));
                    movieModel.setRating((float) finalObject.getDouble("rating"));
                    movieModel.setDuration(finalObject.getString("duration"));
                    movieModel.setDirector(finalObject.getString("director"));
                    movieModel.setTagline(finalObject.getString("tagline"));
                    movieModel.setImage(finalObject.getString("image"));
                    movieModel.setStory(finalObject.getString("story"));
                    int year = finalObject.getInt("year");
                    float rating = finalObject.getInt("rating");
                    String duration = finalObject.getString("duration");
                    String director = finalObject.getString("director");
                    String tagline = finalObject.getString("tagline");


                    List<MovieModel.Cast> castList = new ArrayList<>();
                    for (int j = 0; j < finalObject.getJSONArray("cast").length(); j++) {
                        MovieModel.Cast cast = new MovieModel.Cast();
                        //JSONObject castJSONObject = finalObject.getJSONArray("cast").getJSONObject(j);
                        // cast.setName(castJSONObject.getString("name"));
                        //Instead of above two lines this line can be used
                        cast.setName(finalObject.getJSONArray("cast").getJSONObject(j).getString("name"));
                        castList.add(cast);
                    }
                    movieModel.setCastList(castList);
                    //adding the final object in the list
                    movieModelList.add(movieModel);


                }
                return movieModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MovieModel> result) {
            super.onPostExecute(result);

            MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row, result);
            lvMovies.setAdapter(adapter);


            //Need to set data to the list
        }
    }

    class MovieAdapter extends ArrayAdapter {

        private List<MovieModel> movieModelList;
        private int resource;
        private LayoutInflater inflater;

        public MovieAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MovieModel> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row, null);
            }

            ImageView ivIcon;
            TextView tvMovie;
            TextView tvTagline;
            TextView tvYear;
            TextView tvDuration;
            TextView tvDirector;
            RatingBar rbMovieRating;
            TextView tvCast;
            TextView tvStory;
            final ProgressBar progressBar;


            ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            tvMovie = (TextView) convertView.findViewById(R.id.tvMovieName);
            tvTagline = (TextView) convertView.findViewById(R.id.tvTagline);
            tvYear = (TextView) convertView.findViewById(R.id.tvYear);
            tvDuration = (TextView) convertView.findViewById(R.id.tvDuration);
            tvDirector = (TextView) convertView.findViewById(R.id.tvDirector);
            rbMovieRating = (RatingBar) convertView.findViewById(R.id.rbMovie);
            tvCast = (TextView) convertView.findViewById(R.id.tvCast);
            tvStory = (TextView) convertView.findViewById(R.id.tvStory);
            progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

            // Then later, when you want to display image
            ImageLoader.getInstance().displayImage(movieModelList.get(position).getImage(), ivIcon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(view.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(view.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(view.GONE);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(view.GONE);

                }
            });

            String yearString = "<b>" + "Year: " + "</b> " + movieModelList.get(position).getYear();
            String directorString = "<b>" + "Director: " + "</b> " + movieModelList.get(position).getDirector();
            String durationString = "<b>" + "Duration: " + "</b> " + movieModelList.get(position).getDuration();
            String storyString = "<b>" + "Story: " + "</b> " + movieModelList.get(position).getStory();
            tvYear.setText(Html.fromHtml(yearString));
            tvDirector.setText(Html.fromHtml(directorString));
            tvDuration.setText(Html.fromHtml(durationString));
            tvStory.setText(Html.fromHtml(storyString));
            tvMovie.setText(movieModelList.get(position).getMovie());
            tvTagline.setText(movieModelList.get(position).getTagline());
            //tvYear.setText("Year: " + movieModelList.get(position).getYear());
            //tvDuration.setText("Duration: " + movieModelList.get(position).getDuration());
            //tvDirector.setText("Director: " + movieModelList.get(position).getDirector());

            //RatingBAr is divided by 2, because in main Json file the rating is given out of 10 ,But we want out of 5 stars only
            rbMovieRating.setRating(movieModelList.get(position).getRating() / 2);

            StringBuffer stringBuffer = new StringBuffer();
            for (MovieModel.Cast cast : movieModelList.get(position).getCastList()) {
                stringBuffer.append(cast.getName() + ",");
            }
            String castString = "<b>" + "Cast: " + "</b> " + stringBuffer;
            tvCast.setText(Html.fromHtml(castString));
            //String storyString = "<b>" + "Story: " + "</b> " +stringBuffer;
            //  tvStory.setText(Html.fromHtml(storyString));

            //tvCast.setText("Cast: " +stringBuffer);
            // tvStory.setText("Story: " + movieModelList.get(position).getStory());
            return convertView;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu ; this add items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        // automatically handles clicks on the Home/Up button ,so long
        // as you specify a parent activity in Android manifest.xml

//        int id = item.getItemId();
//        //No inspection , simplifiable If Statement
//        if (id == R.id.action_refresh) {
//            //new JSONTask().execute("https://files.000webhost.com/subhash6542/public_html/moviesData.txt");
//            new JSONTask().execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesData.txt");
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
