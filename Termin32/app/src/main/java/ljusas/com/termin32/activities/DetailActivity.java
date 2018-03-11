package ljusas.com.termin32.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String name = getIntent().getStringExtra("name");
        String sername = getIntent().getStringExtra("sername");
        String cv = getIntent().getStringExtra("cv");
        String year = getIntent().getStringExtra("year");
        Float rating1 = getIntent().getFloatExtra("rating", 0);

        TextView name1 = DetailActivity.this.findViewById(R.id.tv_name);
        name1.setText(String.format("Name: %s", name));

        TextView sername1 = DetailActivity.this.findViewById(R.id.tv_sername);
        sername1.setText(String.format("Sername: %s", sername));

        TextView cv1 = DetailActivity.this.findViewById(R.id.tv_cv);
        cv1.setText(String.format("CV: %s", cv));

        TextView year1 = DetailActivity.this.findViewById(R.id.tv_year);
        year1.setText(String.format("Name: %s", year));

        RatingBar ratingBar = DetailActivity.this.findViewById(R.id.actor_rating);
        ratingBar.setRating(rating1);

        List<Movie> list = null;
        try {
            list = getDatabaseHelper().getMovieDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ArrayAdapter adapter = new ArrayAdapter<Movie>(this, R.layout.list_item, list);

        final ListView listView = (ListView)this.findViewById(R.id.movie_list);

        listView.setAdapter(adapter);

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

            final EditText movieName = (EditText) dialog.findViewById(R.id.movie_title);
            final EditText movieType = (EditText) dialog.findViewById(R.id.movie_type);
            final EditText movieYear = (EditText) dialog.findViewById(R.id.movie_year);

            Button ok = (Button) dialog.findViewById(R.id.button_movie_add);

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

                    try {
                        getDatabaseHelper().getMovieDao().create(movie);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    refreshMovie();
                    Toast.makeText(DetailActivity.this, "Movie inserted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        if (id == R.id.action_delete) {
            Toast.makeText(this, "Actor deleted", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_edit) {
            Toast.makeText(this, "Actor edited", Toast.LENGTH_SHORT).show();
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
        ListView listview = (ListView) findViewById(R.id.movie_list);

        if (listview != null){
            ArrayAdapter<Movie> adapter = (ArrayAdapter<Movie>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Movie> list = getDatabaseHelper().getMovieDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
