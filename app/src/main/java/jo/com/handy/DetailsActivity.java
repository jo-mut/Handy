package jo.com.handy;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.noteEditText)EditText noteEditText;
    @Bind(R.id.timeTextView)TextView timeTextView;

    private HandyRepository handyRepository;
    private HandyViewModel handyViewModel;
    private static final String NOTE_ID = "note id";
    private static final String TITLE = "title";
    private static final String NOTE = "note";
    private String handyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editHandyNote();
            }
        });

        // initializze the view model
        handyViewModel = ViewModelProviders.of(this).get(HandyViewModel.class);

        if (getIntent().getExtras() != null){
            handyId = getIntent().getStringExtra(NOTE_ID);
        }

        getHandyById();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_delete){
            deleteHandy();
        }

        if (id == R.id.action_edit_title){
            editHandyTitle();
        }


        if (id == R.id.action_share){
            editShareHandy();
        }



        return super.onOptionsItemSelected(item);
    }

    /***get the details of the note**/
    private void getHandyById(){
        handyViewModel.getItemById(handyId).observe(this, new Observer<Handy>() {
            @Override
            public void onChanged(@Nullable Handy handy) {
               if (handy != null){
                   String title = handy.getTitle();
                   String note = handy.getNote();

                   if (!TextUtils.isEmpty(title)){
                       toolbar.setTitle(title);
                   }

                   if (!TextUtils.isEmpty(note)){
                       noteEditText.setText(note);
                       noteEditText.setSelection(note.length());
                   }

                   timeTextView.setText(DateFormat.format("dd MMM, yy", handy.getDate()));
               }
            }
        });

    }

    /**edit the title of the note*/
    private void editHandyTitle(){
        handyViewModel.getItemById(handyId).observe(this, new Observer<Handy>() {
            @Override
            public void onChanged(@Nullable Handy handy) {
                String title = handy.getTitle();


            }
        });
    }

    /** edit the note*/
    private void editHandyNote(){
        handyViewModel.getItemById(handyId).observe(this, new Observer<Handy>() {
            @Override
            public void onChanged(@Nullable Handy handy) {
                final String note = handy.getNote();
                final String edited = noteEditText.getText().toString();
                if (!note.equals(edited)){
                    handy.setNote(edited);
                    handyViewModel.update(handy);
                }

                finish();

            }
        });
    }

    /**share handy to your networks*/
    private void editShareHandy(){
        handyViewModel.getItemById(handyId).observe(this, new Observer<Handy>() {
            @Override
            public void onChanged(@Nullable Handy handy) {
                final String title = handy.getTitle();
                final String note = handy.getNote();

                String joinedNote = title + " "  + note;
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, joinedNote);
                shareIntent.setType("text");
                startActivity(Intent.createChooser(shareIntent, "Share with your "));
                finish();

            }
        });
    }


    /**delete handy permanently*/
    private void deleteHandy(){
        handyViewModel.getItemById(handyId).observe(this, new Observer<Handy>() {
            @Override
            public void onChanged(@Nullable Handy handy) {
                handyViewModel.deleteHandy(handy);
            }
        });
    }

    private void editHnady(){

    }

    @Override
    protected void onStart() {
        super.onStart();
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
    protected void onResume() {
        super.onResume();
    }


}
