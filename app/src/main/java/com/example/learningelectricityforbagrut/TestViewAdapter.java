package com.example.learningelectricityforbagrut;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.StringJoiner;

public class TestViewAdapter extends RecyclerView.Adapter<TestViewAdapter.ViewHolder> {

    private List<Test> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    TestViewAdapter(Context context, List<Test> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.test_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get date from timeTaken
        String timeTaken=mData.get(position).getTimeTaken();
        String[] array = timeTaken.split(".");
        Resources res = holder.itemView.getContext().getResources();
        String date = res.getString(R.string.test_date);
        for(int i=2;i>=0;i--)
            date.concat(array[i]+".");
        holder.testTitle.setText(date);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView testTitle;

        ViewHolder(View itemView) {
            super(itemView);
            testTitle = itemView.findViewById(R.id.testTitle);
            
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    // convenience method for getting data at click position
    public Test getItem(int id) {
        return mData.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}