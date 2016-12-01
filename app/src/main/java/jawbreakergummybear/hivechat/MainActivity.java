package jawbreakergummybear.hivechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jawbreakergummybear.hivechat.core.ChatClient;
import jawbreakergummybear.hivechat.core.Hive;

public class MainActivity extends AppCompatActivity {

    private ChatClient myClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
