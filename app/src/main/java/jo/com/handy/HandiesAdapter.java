package jo.com.handy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HandiesAdapter extends RecyclerView.Adapter<HandiesViewHolder> {
    private Context context;
    private List<Handy> handies = new ArrayList<>();
    private static final String NOTE_ID = "note id";

    public HandiesAdapter(Context context) {
        this.context = context;
    }

    public void setHandies(List<Handy> handies){
        this.handies = handies;
    }

    @Override
    public HandiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.handies_layout, parent, false);
        return new HandiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HandiesViewHolder holder, int position) {
        Handy handy =  handies.get(position);
        final int id = handy.getId();
        final String title = handy.getTitle();
        final String note = handy.getNote();
        final long date = handy.getDate();

        holder.handyTitleTextView.setText(title);
        holder.handyNoteTextView.setText(note);
        holder.timeTextView.setText(DateFormat.format("dd/MM/yy", date));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(HandiesAdapter.NOTE_ID, id + "");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return handies.size();
    }
}
