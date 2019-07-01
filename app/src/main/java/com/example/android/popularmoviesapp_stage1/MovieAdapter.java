package com.example.android.popularmoviesapp_stage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesapp_stage1.model.MovieData;
import com.example.android.popularmoviesapp_stage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private List<MovieData> mMovieItemsList;
    private final Context mContext;
    private final MovieAdapterOnClickHandler mOnClickListener;


    public interface MovieAdapterOnClickHandler {
        void OnListItemClick(MovieData movieItem);
    }


    public MovieAdapter(List<MovieData> movieItemList, MovieAdapterOnClickHandler onClickListener, Context context) {
        mMovieItemsList = movieItemList;
        mOnClickListener = onClickListener;
        mContext = context;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mMovieItemImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieItemImageView = view.findViewById(R.id.movie_list_item_poster_iv);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieData movieItem = mMovieItemsList.get(adapterPosition);
            mOnClickListener.OnListItemClick(movieItem);
        }

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        MovieData currentMovieData = mMovieItemsList.get(position);
        String posterPathURL = NetworkUtils.buildUrlForMoviePoster(currentMovieData.getMoviePoster());
        try {
            Picasso.with(mContext)
                    .load(posterPathURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(movieAdapterViewHolder.mMovieItemImageView);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return (mMovieItemsList != null ? mMovieItemsList.size() : 0);
    }

    public void setMovieData(List<MovieData> mMovieItemsList) {
        this.mMovieItemsList = mMovieItemsList;
        notifyDataSetChanged();
    }

}
