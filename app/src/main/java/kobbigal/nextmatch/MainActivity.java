package kobbigal.nextmatch;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import kobbigal.nextmatch.adapters.GameListAdapter;
import kobbigal.nextmatch.backgroundtasks.DownloadWebsiteSourceTask;
import kobbigal.nextmatch.interfaces.IMatches;
import kobbigal.nextmatch.obj.Game;

public class MainActivity extends AppCompatActivity {

    private IMatches imatches;
    private final String TAG = this.getClass().getSimpleName();
    private boolean isConnected;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.game_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // TODO: 7/28/18 implement adapter
//        adapter = new GameListAdapter();


        // Check for internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            isConnected = networkInfo != null && networkInfo.isConnected();
        }

        Log.i(TAG, "Network connected: " + isConnected);

        if (isConnected){
            new DownloadWebsiteSourceTask(this, layoutManager).execute();
        }


    }



}