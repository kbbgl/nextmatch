package kobbigal.nextmatch.backgroundtasks;

import android.app.Activity;
import android.content.Context;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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

import kobbigal.nextmatch.BuildConfig;
import kobbigal.nextmatch.MainActivity;
import kobbigal.nextmatch.R;
import kobbigal.nextmatch.adapters.GameListAdapter;
import kobbigal.nextmatch.obj.Game;

import static kobbigal.nextmatch.MainActivity.USER_INITIATED;

public class DownloadGameScheduleTask extends AsyncTask<Void, Void, List<Game>> {

    private final String TAG = this.getClass().getSimpleName();
    private static final String ONE_LINK = "http://www.one.co.il";
    private WeakReference<MainActivity> mainActivityWeakReference;
    private LinearLayoutManager layoutManager;
    private String date;

    public DownloadGameScheduleTask(MainActivity activity, LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.mainActivityWeakReference = new WeakReference<>(activity);
    }

    private Activity getActivity(){
        return mainActivityWeakReference.get();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Game> doInBackground(Void... voids) {
        return gameList();
    }

    @Override
    protected void onPostExecute(List<Game> games) {
        super.onPostExecute(games);

        RecyclerView recyclerView = getActivity().findViewById(R.id.game_list);
        TextView dateTitle = getActivity().findViewById(R.id.gameday_title);

        GameListAdapter adapter = new GameListAdapter(games);

        dateTitle.setText(date);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Log.d(TAG, "onPostExecute: recyclerView updated");

    }

    private List<Game> gameList() {

        List<Game> gameList = new ArrayList<>();

        try {
            TrafficStats.setThreadStatsTag(USER_INITIATED);
            Document document = Jsoup.connect(ONE_LINK).get();

            Elements dateElement = document.select("#TVDate");
            date = dateElement.text();
            Log.d(TAG, "date: " + date);

            Elements rootElement = document.select("#TVContent");
            Elements games = rootElement.select("dd");
            Elements gameTimes = rootElement.select("dt");
            int numberOfGames = games.size();

            Log.i(TAG, "There are " + numberOfGames + " games today");

            for (int i = 0; i < numberOfGames; i++) {

                String comp;
                String teams;
                String hometeam;
                String awayteam;


                if (games.get(i).text().split(":")[0].contains("-"))
                {
                    comp = "";
                    teams = games.get(i).text().split(":")[0];
                    hometeam = teams.split(" - ")[0].trim();
                    awayteam = teams.split(" - ")[1].trim();
                }
                else {
                    comp = games.get(i).text().split(":")[0].trim();
                    teams = games.get(i).text().split(":")[1];
                    hometeam = teams.split(" - ")[0].trim();
                    awayteam = teams.split(" - ")[1].trim();

                }

                String gameTime = gameTimes.get(i).text().trim();

                Game game = new Game(hometeam, awayteam, comp, gameTime);
                Log.d(TAG, "Game: " + (i + 1) + ": " + game.toString());
                gameList.add(game);

            }
        } catch (UnknownHostException uhe) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.root_layout_main), "Connection issue", Snackbar.LENGTH_LONG);
            snackbar.show();
            Log.e(TAG, uhe.getMessage());
        } catch (IndexOutOfBoundsException io){
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.root_layout_main), "Parsing error", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            TrafficStats.clearThreadStatsTag();
        }
        return gameList;
    }
}