package jo.com.handy;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{
    //bind view
    @Bind(R.id.handiesRecyclerView)
    RecyclerView handiesRecyclerView;
    //view model
    private HandyViewModel viewModel;
    //adapter
    private HandiesAdapter handiesAdapter;
    //layout manager
    private LinearLayoutManager layoutManager;
    //new handy note request code;
    private static final int NEW_REQUEST_CODE = 1;
    private static final String TITLE = "title";
    private static final String NOTE = "note";
    private String title;
    private String note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setNotes();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_create){
            Intent intent = new Intent(this, CreateNoteActivity.class);
            startActivityForResult(intent, NEW_REQUEST_CODE);
        }



        return super.onOptionsItemSelected(item);
    }

    private void setNotes(){
        handiesAdapter = new HandiesAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        handiesRecyclerView.setAdapter(handiesAdapter);
        handiesRecyclerView.setLayoutManager(layoutManager);
        viewModel = ViewModelProviders.of(this).get(HandyViewModel.class);
        viewModel.getHandies().observe(this, new Observer<List<Handy>>() {
            @Override
            public void onChanged(@Nullable final List<Handy> handies) {
                handiesAdapter.setHandies(handies);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            if (requestCode == NEW_REQUEST_CODE){
                if (data != null){
                    title = data.getStringExtra(TITLE);
                    note  = data.getStringExtra(NOTE);
                    Handy handy = new Handy();
                    int id = new Random().nextInt();
                    handy.setId(id);
                    handy.setNote(note);
                    handy.setTitle(title);
                    handy.setDate(System.currentTimeMillis());
                    viewModel.insert(handy);
                }else {
                    Toast.makeText(this, "Try saving again", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Try saving saving again", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onLongClick(View v) {
        Handy handy = (Handy) v.getTag();
        viewModel.deleteHandy(handy);
        return true;
    }
}
