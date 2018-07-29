package kobbigal.nextmatch;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class GameParseTest {

    public static void main(String[] args) {

        String game = "גביע האלופות: צ`לסי - אינטר";
        String[] split = game.split(":");

        System.out.println(split.length);
        System.out.println(split[0]);
        System.out.println(split[1]);

    }

}