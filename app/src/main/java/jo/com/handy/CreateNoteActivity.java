package jo.com.handy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateNoteActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.titleEditText)EditText titleEditText;
    @Bind(R.id.noteEditText)EditText noteEditText;

    private static final String TITLE = "title";
    private static final String NOTE = "note";
    private HandyViewModel handyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_done){
           saveHandyNote();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveHandyNote(){
        final String title = titleEditText.getText().toString().trim();
        final String note = noteEditText.getText().toString().trim();
        Handy handy = new Handy();
        int id = new Random().nextInt();
        if (TextUtils.isEmpty(title)){
            handy.setNote(note);
        }

        if (TextUtils.isEmpty(note)){
            handy.setTitle(title);
        }
        handy.setId(id);
        handy.setDate(System.currentTimeMillis());
        handyViewModel.insert(handy);

        finish();
        titleEditText.setText("");
        noteEditText.setText("");
    }

}
