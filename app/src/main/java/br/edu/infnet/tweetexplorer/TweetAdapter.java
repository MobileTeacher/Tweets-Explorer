package br.edu.infnet.tweetexplorer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;

public class TweetAdapter extends ArrayAdapter<Tweet> {
    public TweetAdapter(@NonNull Context context, int resource, @NonNull List<Tweet> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return new TweetView(getContext(), getItem(position));
    }
}