package jawbreakergummybear.hivechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import jawbreakergummybear.hivechat.core.ChatClient;

/**
 * Created by Omar on 11/28/16.
 */

public class PublicChat extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String userNameMessage ="user_name";
    public static final String firstUseMessage= "first_use";
    public static ChatClient myClient = MainActivity.myClient;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.public_chat);
        populateMessages();
        final Button private_btn = (Button) findViewById(R.id.private_chat);
        private_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(PublicChat.this,PrivateChatList.class);
                startActivity(k);
            }
        });
        final Button send_btn = (Button) findViewById(R.id.publicSendButton);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.publicTextField);
                TextView message = new TextView(v.getContext());
                myClient.sendPublicMessage(editText.getText().toString());
                LinearLayout linearLayout = new LinearLayout(v.getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout parent = (LinearLayout) findViewById(R.id.parentLayout);
                linearLayout.setPadding(0,60,0,0);
                parent.addView(linearLayout);
                message.setText((CharSequence) "     "+editText.getText().toString());
                message.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                message.setTextColor(Color.BLACK);
                message.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
                message.setPadding(0,30,0,30);
                linearLayout.addView(message);
            }
        });
        TextView textView = (TextView) findViewById(R.id.title);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String userName = settings.getString(userNameMessage,null);
        if(!settings.getBoolean(firstUseMessage,false)) {
            textView.setText("Hi," + userName + "\n" + "Public Chat"  );
        }else {
            textView.setText("Welcome back, "+userName +"\n" + "         Public Chat");
        }
        ScrollView sv = (ScrollView) findViewById(R.id.scrl);
        sv.fullScroll(sv.getBottom());


    }
    public void populateMessages() {
        for (int i =0; i < myClient.getInbox().size(); i++) {
            TextView message = new TextView(this);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout parent = (LinearLayout) findViewById(R.id.parentLayout);
            linearLayout.setPadding(0,60,0,0);
            parent.addView(linearLayout);
            message.setText((CharSequence) "     "+ myClient.getInbox().get(i).getText());
            message.setBackground(getResources().getDrawable(R.drawable.rounded_corner1));
            message.setTextColor(Color.BLACK);
            message.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            message.setPadding(0,30,0,30);
            linearLayout.addView(message);

        }
        ScrollView sv = (ScrollView) findViewById(R.id.scrl);
        sv.fullScroll(ScrollView.FOCUS_DOWN);
    }
}
