package com.example.rusha.bakeryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rusha on 6/27/2017.
 */

public class ViewFragment extends Fragment implements StepsAdapter.ListClickHandler {
    @InjectView(R.id.steps)
    RecyclerView list;
    private StepsAdapter adapter;
    @InjectView(R.id.ingre)
    TextView textView;
    private Recipe recipe;
    private ListClick mClick;

    public ViewFragment() {

    }

    public interface ListClick {
        void ClickHandler(int position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_view, container, false);
        textView = (TextView) rootView.findViewById(R.id.ingre);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), IngredientDisplayActivity.class);
                startActivity(intent);
            }
        });
        recipe = ListAdapter.recipies.get(MainActivity.mPos);
        // Log.v(ViewFragment.class.getSimpleName(), "onCreate " + DetailActivity.pos);
        ButterKnife.inject(getActivity());
        list = (RecyclerView) rootView.findViewById(R.id.steps);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setHasFixedSize(true);
        adapter = new StepsAdapter(getContext(), this);
        list.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        list.addItemDecoration(dividerItemDecoration);


        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mClick = (ListClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Error");
        }
    }


    @Override
    public void listClick(int pos) {
        mClick.ClickHandler(pos);
    }
}

