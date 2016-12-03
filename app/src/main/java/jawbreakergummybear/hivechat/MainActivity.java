package jawbreakergummybear.hivechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jawbreakergummybear.hivechat.core.ChatClient;

public class MainActivity extends AppCompatActivity {

    public ChatClient getMyClient() {
        return myClient;
    }

    public static ChatClient myClient;
    public static final String firstUseMessage= "first_use";
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean firstUse = settings.getBoolean(firstUseMessage,false);
        if(!firstUse) {
            Intent k = new Intent(MainActivity.this, UserName.class);
            startActivity(k);
        }else{
            Intent k = new Intent(MainActivity.this, PublicChat.class);
            startActivity(k);
        }
        myClient = new ChatClient(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        //myClient.close();
    }
}
