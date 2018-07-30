package kobbigal.nextmatch;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

import kobbigal.nextmatch.backgroundtasks.DownloadGameScheduleTask;
import kobbigal.nextmatch.interfaces.IMatches;

public class MainActivity extends AppCompatActivity {

    public static final int USER_INITIATED = 0x1000;
    public static final int APP_INITIATED = 0x2000;

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

        // Check for internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            isConnected = networkInfo != null && networkInfo.isConnected();
        }

        Log.i(TAG, "Network connected: " + isConnected);

        if (isConnected){
            new DownloadGameScheduleTask(this, layoutManager).execute();
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.root_layout_main), "No network connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
    }
}