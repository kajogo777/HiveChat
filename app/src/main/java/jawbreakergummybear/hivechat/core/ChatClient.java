package jawbreakergummybear.hivechat.core;

import android.app.Activity;

import java.util.ArrayList;

import jawbreakergummybear.hivechat.MainActivity;

/**
 * Created by georgesedky on 11/29/16.
 */

public class ChatClient {

    private Hive hive;
    private ArrayList<String> myMessages;

    public ChatClient(MainActivity activity){

        hive = new Hive(activity);
        hive.attach();
    }

    public void close(){
        hive.detach();
    }
}
