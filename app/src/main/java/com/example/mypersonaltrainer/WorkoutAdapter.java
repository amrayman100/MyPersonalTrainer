package com.example.mypersonaltrainer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class  WorkoutAdapter  extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;
        private final TextView routine;

        private WorkoutViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            routine = itemView.findViewById(R.id.textView2);
        }
    }

    private final LayoutInflater mInflater;
    private List<Workout> mWords; // Cached copy of words

    WorkoutAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WorkoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        if (mWords != null) {
            Workout current = mWords.get(position);
            holder.wordItemView.setText(current.getName());
            holder.routine.setText(current.getRoutine());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Workout> words){
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}