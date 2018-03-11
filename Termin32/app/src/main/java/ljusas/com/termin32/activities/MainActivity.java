package ljusas.com.termin32.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import ljusas.com.termin32.R;
import ljusas.com.termin32.db.DatabaseHelper;
import ljusas.com.termin32.db.model.Actor;
import ljusas.com.termin32.dialog.AboutDialog;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    public interface OnItemSelectedListener {

        void onItemSelected(int position);
    }

    OnItemSelectedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"ljusa@sbb.rs"});  //developer 's email
                Email.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.subject)); // Email 's Subject
                Email.putExtra(Intent.EXTRA_TEXT, getString(R.string.developer) + "");  //Email 's Greeting text
                startActivity(Intent.createChooser(Email, getString(R.string.feedback)));
            }
        });

        List<Actor> list = null;
        try {
            list = getDatabaseHelper().getActorDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter<Actor>(this, R.layout.list_item, list);

        final ListView listView = (ListView)this.findViewById(R.id.list_actors);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Actor ac = (Actor) listView.getItemAtPosition(position);
                String posID = String.valueOf(ac.getmId());

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("position", posID);
                startActivity(intent);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.about){
            AlertDialog dialog = new AboutDialog(MainActivity.this).prepareDialog();
            dialog.show();
        }

        if (id == R.id.action_add){
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.input_dialog);

            final EditText actorName = (EditText) dialog.findViewById(R.id.actor_name);
            final EditText actorSername = (EditText) dialog.findViewById(R.id.actor_sername);
            final EditText actorCV = (EditText) dialog.findViewById(R.id.actor_cv);
            final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.actor_rating);
            final EditText actorYear = (EditText) dialog.findViewById(R.id.actor_year);

            Button ok = (Button) dialog.findViewById(R.id.button_actor_add);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = actorName.getText().toString();
                    String sername = actorSername.getText().toString();
                    String cv = actorCV.getText().toString();
                    float rating = ratingBar.getRating();
                    String year = actorYear.getText().toString();

                    Actor actor = new Actor();
                    actor.setmName(name);
                    actor.setmSername(sername);
                    actor.setCv(cv);
                    actor.setRating(rating);
                    actor.setYear(year);

                    try {
                        getDatabaseHelper().getActorDao().create(actor);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    refresh();
                    Toast.makeText(MainActivity.this, "Actor inserted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.list_actors);

        if (listview != null){
            ArrayAdapter<Actor> adapter = (ArrayAdapter<Actor>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Actor> list = getDatabaseHelper().getActorDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
