package demo.example.com.videodemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import demo.example.com.videodemo.listener.ItemClickListener;


public class VideoAdapter extends RecyclerView.Adapter<VideoFeedHolder> {
    private List mlist;
    private Context context;
    private RecyclerView recyclerView;
    private ItemClickListener itemClickListener;

    public VideoAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public VideoFeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent.setClipChildren(false);
        View inflater = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new VideoFeedHolder(inflater, context);
    }

    public void setList(List mlist) {
        this.mlist = mlist;
        this.mNotify();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void mNotify() {
        if (recyclerView != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private static final String TAG = "VideoAdapter";

    @Override
    public void onBindViewHolder(final VideoFeedHolder holder, final int position) {

        holder.update(position, mlist);
        View itemView = holder.video_masked;
    }

    private boolean isIntercep = false;

    public void setIntercep(boolean isIntercep) {
        this.isIntercep = isIntercep;
    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
