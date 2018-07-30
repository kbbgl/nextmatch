package kobbigal.nextmatch.obj;

import java.io.Serializable;
import java.util.Calendar;

public class Game{

    public String homeTeam;
    public String awayTeam;
    public String competition;
    public String time;

    public Game(String homeTeam, String awayTeam, String competition, String time) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.competition = competition;
        this.time = time;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return homeTeam + " vs " + awayTeam + " @ " + time + ", " + competition;
    }
}
