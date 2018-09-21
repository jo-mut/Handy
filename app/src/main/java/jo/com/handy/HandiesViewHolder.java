package jo.com.handy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HandiesViewHolder extends RecyclerView.ViewHolder {
    Context mContext;
    View mView;
    @Bind(R.id.timeTextView)TextView timeTextView;
    @Bind(R.id.handyTitleTextView)TextView handyTitleTextView;
    @Bind(R.id.handyNoteTextView)TextView handyNoteTextView;

    public HandiesViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
