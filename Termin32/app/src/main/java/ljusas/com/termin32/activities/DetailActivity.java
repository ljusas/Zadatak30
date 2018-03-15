package ljusas.com.termin32.activities;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import ljusas.com.termin32.R;
import ljusas.com.termin32.db.DatabaseHelper;
import ljusas.com.termin32.db.model.Actor;
import ljusas.com.termin32.db.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Actor a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int actorID = getIntent().getExtras().getInt("actorID");

        try {
            a = getDatabaseHelper().getActorDao().queryForId(actorID);

            TextView name1 = findViewById(R.id.tv_name);
            name1.setText(a.getmName());

            TextView sername1 = findViewById(R.id.tv_sername);
            sername1.setText(a.getmSername());

            TextView cv1 = findViewById(R.id.tv_cv);
            cv1.setText(a.getCv());

            TextView year1 = findViewById(R.id.tv_year);
            year1.setText(a.getYear());

            RatingBar ratingBar = findViewById(R.id.actor_rating);
            ratingBar.setRating(a.getRating());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView listView = findViewById(R.id.movie_list);
        try {
            List<Movie> list = getDatabaseHelper().getMovieDao().queryBuilder()
                    .where()
                    .eq(Movie.FIELD_NAME_ACTOR, a.getmId())
                    .query();

            ListAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie m = (Movie) listView.getItemAtPosition(position);
                    Toast.makeText(DetailActivity.this, m.getName()+" "+m.getType()+" "+m.getYear(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_movie) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.input_movie);

            final EditText movieName = dialog.findViewById(R.id.movie_title);
            final EditText movieType = dialog.findViewById(R.id.movie_type);
            final EditText movieYear = dialog.findViewById(R.id.movie_year);

            Button ok = dialog.findViewById(R.id.button_movie_add);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = movieName.getText().toString();
                    String type = movieType.getText().toString();
                    String year = movieYear.getText().toString();

                    Movie movie = new Movie();
                    movie.setName(name);
                    movie.setType(type);
                    movie.setYear(year);
                    movie.setmActor(a);

                    try {
                        getDatabaseHelper().getMovieDao().create(movie);
                        refreshMovie();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(DetailActivity.this, "Movie inserted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        if (id == R.id.action_delete) {

            try {
                getDatabaseHelper().getActorDao().delete(a);
                Toast.makeText(this, "Actor deleted", Toast.LENGTH_SHORT).show();
                finish();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Context context = getApplicationContext();
            android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(context);
            builder.setContentTitle("Obaveštenje");
            builder.setContentText("Upravo ste obrisali kontakt.");
            builder.setSmallIcon(R.drawable.ic_stat_name);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            manager.notify(1,builder.build());
        }

        if (id == R.id.action_edit) {
            final Dialog dialog1 = new Dialog(this);
            dialog1.setContentView(R.layout.input_dialog);

            final EditText actorName = dialog1.findViewById(R.id.actor_name);
            final EditText actorSername = dialog1.findViewById(R.id.actor_sername);
            final EditText actorCV = dialog1.findViewById(R.id.actor_cv);
            final RatingBar ratingBar = dialog1.findViewById(R.id.actor_rating);
            final EditText actorYear = dialog1.findViewById(R.id.actor_year);

            Button ok = dialog1.findViewById(R.id.button_actor_add);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = actorName.getText().toString();
                    String sername = actorSername.getText().toString();
                    String cv = actorCV.getText().toString();
                    float rating = ratingBar.getRating();
                    String year = actorYear.getText().toString();

                    a.setmName(name);
                    a.setmSername(sername);
                    a.setCv(cv);
                    a.setRating(rating);
                    a.setYear(year);

                    try {
                        getDatabaseHelper().getActorDao().update(a);
                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(DetailActivity.this, "Actor edited", Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();
                }
            });
            dialog1.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void refreshMovie() {
        ListView listview = findViewById(R.id.movie_list);

        if (listview != null){
            ArrayAdapter<Movie> adapter = (ArrayAdapter<Movie>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Movie> list = getDatabaseHelper().getMovieDao().queryBuilder()
                            .where()
                            .eq(Movie.FIELD_NAME_ACTOR, a.getmId())
                            .query();

                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
