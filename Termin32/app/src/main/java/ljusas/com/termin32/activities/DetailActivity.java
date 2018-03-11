package ljusas.com.termin32.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import ljusas.com.termin32.R;
import ljusas.com.termin32.db.DatabaseHelper;
import ljusas.com.termin32.db.model.Actor;

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
            Toast.makeText(this, "Movie add", Toast.LENGTH_SHORT).show();
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
}
