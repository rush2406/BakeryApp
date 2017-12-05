package com.example.rusha.bakeryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rusha on 6/27/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngreHolder> {

    private Context mContext;
    private int mPos;

    public IngredientAdapter(Context context, int pos) {
        mContext = context;
        mPos = pos;
    }

    @Override
    public IngreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.ingre_display, parent, false);
        IngreHolder holder = new IngreHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(IngreHolder holder, int position) {

        Ingredients i = ListAdapter.recipies.get(mPos).getIngredient().get(position);
        holder.quan.setText(i.getQuantity());
        holder.measure.setText(i.getMeasure());
        holder.ingredient.setText(i.getIngredient());
    }

    @Override
    public int getItemCount() {
        return ListAdapter.recipies.get(mPos).getIngredient().size();
    }

    class IngreHolder extends RecyclerView.ViewHolder {
        private TextView quan;
        private TextView measure;
        private TextView ingredient;

        public IngreHolder(View itemView) {
            super(itemView);
            quan = (TextView) itemView.findViewById(R.id.quan);
            measure = (TextView) itemView.findViewById(R.id.measure);
            ingredient = (TextView) itemView.findViewById(R.id.ingredient);
        }
    }
}


