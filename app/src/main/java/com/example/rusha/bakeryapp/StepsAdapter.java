package com.example.rusha.bakeryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rusha on 6/27/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepHolder> {
    private Context mContext;
    private ListClickHandler mClickListener;
    public static ArrayList<Steps> stepsArrayList = new ArrayList<>();

    public StepsAdapter(Context context, ListClickHandler listClickHandler) {
        mContext = context;
        mClickListener = listClickHandler;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.fragment_display, parent, false);
        StepHolder stepHolder = new StepHolder(v);
        return stepHolder;
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        stepsArrayList = ListAdapter.recipies.get(MainActivity.mPos).getSteps();
        Steps step = stepsArrayList.get(position);
        holder.text.setText(step.getShortDesc());
    }

    @Override
    public int getItemCount() {
        return ListAdapter.recipies.get(MainActivity.mPos).getSteps().size();
    }


    class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;

        public StepHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.recipe_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickListener.listClick(position);

        }
    }

    public interface ListClickHandler {
        void listClick(int pos);
    }
}
