package br.edu.infnet.tweetexplorer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    ArrayAdapter adapter;
    ListView listView;
    //ProgressBar progressBar;

    SwipeRefreshLayout swipeRefreshLayout;
    SearchService searchService;
    String lastSearch = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //progressBar = findViewById(R.id.progressBar);

        Twitter.initialize(this);
        String[] items = {"Tweet1", "Tweet2", "Tweet3", "Tweet4", "Tweet5", "Tweet6"};
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);


        listView = findViewById(R.id.tweetsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DEBUG", Integer.toString(position));
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        //StatusesService statusesService = twitterApiClient.getStatusesService();
        searchService = twitterApiClient.getSearchService();

    }

    public void searchTweets(View v){
        //progressBar.setVisibility(View.VISIBLE);
        EditText searchField = findViewById(R.id.search_field);
        lastSearch = searchField.getText().toString();
        searchField.setText("");

        swipeRefreshLayout.setRefreshing(true);
        updateData(lastSearch);
    }


    private void updateData(String searchString){
        Call<Search> searcher = searchService.tweets(searchString,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        searcher.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                ArrayList<String> tweetsTextList = new ArrayList<String>();
                for (Tweet tweet : response.body().tweets){
                    String content = "[";
                    content = content + tweet.user.name + "]: " + tweet.text;
                    tweetsTextList.add(content);
                }
                String[] tweetsArray = tweetsTextList.toArray(new String[0]);
                /*adapter = new ArrayAdapter<>(MainActivity.this,
                        R.layout.tweetview,
                        R.id.tweet_format,
                        tweetsArray);*/
                adapter = new TweetAdapter(MainActivity.this, 0, response.body().tweets);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d("TWITTER", "FALHOU");
                Toast.makeText(MainActivity.this, "Busca no Twitter falhou!", Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onRefresh() {
        updateData(lastSearch);
    }
}
