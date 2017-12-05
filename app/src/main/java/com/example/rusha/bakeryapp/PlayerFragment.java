package com.example.rusha.bakeryapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

/**
 * Created by rusha on 6/27/2017.
 */

public class PlayerFragment extends Fragment {

    private SimpleExoPlayerView player;
    private SimpleExoPlayer mExoPlayer;
    private TextView text;
    private String url;
    private Button next;
    private Fragment fragment;
    private ImageView image;
    private Button prev;

    public PlayerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.player_fragment, container, false);

        if (PlayerActivity.enter) {
            if (container != null)
                container.removeAllViews();
            player = (SimpleExoPlayerView) root.findViewById(R.id.playerView);
            text = (TextView) root.findViewById(R.id.textDesc);
            image = (ImageView) root.findViewById(R.id.images);
            next = (Button) root.findViewById(R.id.next);
            prev = (Button) root.findViewById(R.id.prev);
            if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                PlayerActivity.enter = true;
                player.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                prev.setVisibility(View.GONE);
            } else {
                if (DetailActivity.Reqpos == 0)
                    prev.setVisibility(View.GONE);
                else if (DetailActivity.Reqpos == ListAdapter.recipies.get(MainActivity.mPos).getSteps().size() - 1)
                    next.setVisibility(View.GONE);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PlayerActivity.enter = true;
                        if (DetailActivity.Reqpos < ListAdapter.recipies.get(MainActivity.mPos).getSteps().size() - 1) {
                            DetailActivity.Reqpos++;
                            fragment = new PlayerFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragmentid, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                });
                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PlayerActivity.enter = true;
                        if (DetailActivity.Reqpos > 0) {
                            DetailActivity.Reqpos--;
                            fragment = new PlayerFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragmentid, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                    }
                });
                text.setText("");
            }
            int x = DetailActivity.Reqpos;
            url = ListAdapter.recipies.get(MainActivity.mPos).getSteps().get(x).getVideourl();
            text.setText(ListAdapter.recipies.get(MainActivity.mPos).getSteps().get(DetailActivity.Reqpos).getDescription());

            if (!TextUtils.isEmpty(url))
                initializePlayer(Uri.parse(url));
            else {
                image.setVisibility(View.VISIBLE);
                player.setVisibility(View.GONE);
                String thumb = ListAdapter.recipies.get(MainActivity.mPos).getSteps().get(x).getThumbnail();
                if (!TextUtils.isEmpty(thumb))
                    Picasso.with(getContext()).load(thumb).into(image);
                else {
                    if (MainActivity.mPos == 0)
                        Picasso.with(getContext()).load("android.resource://com.example.rusha.bakeryapp/drawable/nutella").into(image);
                    else if (MainActivity.mPos == 1)
                        Picasso.with(getContext()).load("android.resource://com.example.rusha.bakeryapp/drawable/brownie").into(image);
                    else if (MainActivity.mPos == 2)
                        Picasso.with(getContext()).load("android.resource://com.example.rusha.bakeryapp/drawable/yellow").into(image);
                    else
                        Picasso.with(getContext()).load("android.resource://com.example.rusha.bakeryapp/drawable/cheese").into(image);

                }
            }
        }
        return root;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            player.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Bakery");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            PlayerActivity.enter = false;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }


    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

}


