package kobbigal.nextmatch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import kobbigal.nextmatch.R;
import kobbigal.nextmatch.obj.Game;

public class GameListAdapter extends android.support.v7.widget.RecyclerView.Adapter<GameListAdapter.GamesViewHolder> {

    private List<Game> games;
    public GameListAdapter(List<Game> games){
        this.games = games;
    }

    static class GamesViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView hometeamTV;
        TextView awayteamTV;
        TextView matchTimeTV;

        GamesViewHolder(View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            hometeamTV =itemView.findViewById(R.id.hometeam);
            awayteamTV = itemView.findViewById(R.id.awayteam);
            matchTimeTV = itemView.findViewById(R.id.gamedate);

        }
    }
    @NonNull
    @Override
    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item,parent, false);
        return new GamesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesViewHolder holder, int position) {

        holder.hometeamTV.setText(games.get(position).homeTeam);
        holder.awayteamTV.setText(games.get(position).awayTeam);
        holder.matchTimeTV.setText(games.get(position).time);

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


}
