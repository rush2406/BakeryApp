package com.example.rusha.bakeryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rusha on 6/27/2017.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.RecipeHolder> {

    private Context mContext;
    public static ArrayList<Recipe> recipies = new ArrayList<>();
    private ListItemHandler mClickListener;

    public ListAdapter(Context context, ArrayList<Recipe> arrayList, ListItemHandler listItemHandler) {
        mContext = context;
        recipies = arrayList;
        mClickListener = listItemHandler;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.fragment_display, parent, false);
        RecipeHolder r = new RecipeHolder(v);
        return r;
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {

        Recipe recipe = recipies.get(position);
        Log.v(ListAdapter.class.getSimpleName(), recipe.getName());
        holder.text.setText(recipe.getName());
    }


    @Override
    public int getItemCount() {
        return recipies.size();
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;

        public RecipeHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.recipe_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            mClickListener.ListItemClick(pos);
        }
    }

    public interface ListItemHandler {


        void ListItemClick(int position);

    }
}

