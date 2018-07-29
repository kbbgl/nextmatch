package kobbigal.nextmatch.backgroundtasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kobbigal.nextmatch.MainActivity;
import kobbigal.nextmatch.R;
import kobbigal.nextmatch.adapters.GameListAdapter;
import kobbigal.nextmatch.obj.Game;

public class DownloadWebsiteSourceTask extends AsyncTask<Void, Void, List<Game>> {

    private final String TAG = this.getClass().getSimpleName();
    private static final String ONE_LINK = "http://www.one.co.il";
    private WeakReference<MainActivity> mainActivityWeakReference;
    private LinearLayoutManager layoutManager;
    private GameListAdapter adapter;

    public DownloadWebsiteSourceTask(MainActivity activity, LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            this.mainActivityWeakReference = new WeakReference<>(activity);
    }


    @Override
    protected List<Game> doInBackground(Void... voids) {

        List<Game> gameList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

            try {
                Document document = Jsoup.connect(ONE_LINK).get();

                Elements rootElement = document.select("#TVContent");
                Elements games = rootElement.select("dd");
                Log.i(TAG, "There are " + games.size() + " games today");

                for (Element game : games) {

                    String competition = game.text().split(":")[0].trim();
                    String teams = game.text().split(":")[1];

                    String hometeam = teams.split(" - ")[0].trim();
                    String awayteam = teams.split(" - ")[1].trim();
                    gameList.add(new Game(hometeam, awayteam, competition, calendar));
                }

            } catch (UnknownHostException uhe){

                Log.e(TAG, uhe.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        return gameList;
    }

    @Override
    protected void onPostExecute(List<Game> games) {
        super.onPostExecute(games);

        Log.d(TAG, "Retrieved " + games.size() + " games");
        for (Game game :
                games) {
            Log.d(TAG, "Game: " + game.toString());
        }

        Activity activity = mainActivityWeakReference.get();
        Log.d(TAG, "onPostExecute: " + activity.getApplication().toString());
        RecyclerView recyclerView = activity.findViewById(R.id.game_list);
        adapter = new GameListAdapter(games);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }
}