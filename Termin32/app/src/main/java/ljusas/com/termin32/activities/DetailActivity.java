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

        final String dataID = getIntent().getStringExtra("position");
        showDetail(dataID);

    }

    private void showDetail(String dataID) {

        List<Actor> list = null;
        try {
            list = getDatabaseHelper().getActorDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < list.size(); i++){
            if (dataID.equals(list.get(i).getmId())) {

                TextView name = DetailActivity.this.findViewById(R.id.tv_name);
                name.setText(String.format("Name: %s", list.get(i).getmName()));

                TextView sername = DetailActivity.this.findViewById(R.id.tv_sername);
                sername.setText(String.format("Sername: %s", list.get(i).getmSername()));

                TextView cv = DetailActivity.this.findViewById(R.id.tv_cv);
                cv.setText(String.format("CV: %s", list.get(i).getCv()));

                TextView year = DetailActivity.this.findViewById(R.id.tv_year);
                year.setText(String.format("Name: %s", list.get(i).getYear()));

                RatingBar ratingBar = DetailActivity.this.findViewById(R.id.actor_rating);
                ratingBar.setRating(list.get(i).getRating());

            }
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
