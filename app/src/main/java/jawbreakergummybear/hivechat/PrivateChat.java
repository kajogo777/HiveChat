package jawbreakergummybear.hivechat;

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
import android.widget.TextView;

import jawbreakergummybear.hivechat.core.ChatClient;

/**
 * Created by Omar on 12/2/16.
 */

public class PrivateChat extends AppCompatActivity {
    String name= "";
    public ChatClient myClient = MainActivity.myClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.private_chat);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("userName");
        TextView textView = (TextView) findViewById(R.id.userName);
        textView.setText(name);
        final Button send_btn = (Button) findViewById(R.id.privateSendButton);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.privateUserTextField);
                TextView message = new TextView(v.getContext());
                myClient.sendPublicMessage(editText.getText().toString());
                LinearLayout linearLayout = new LinearLayout(v.getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout parent = (LinearLayout) findViewById(R.id.layout2);
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
    }
    public  void populateMessages(){

        for(int i=0;i<myClient.getInbox().size();i++){
            if(name.equals(myClient.getInbox().get(i).getFrom())){
                TextView message = new TextView(this);
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout parent = (LinearLayout) findViewById(R.id.layout2);
                linearLayout.setPadding(0,60,0,0);
                parent.addView(linearLayout);
                message.setText((CharSequence) "     "+ myClient.getInbox().get(i).getText());
                message.setBackground(getResources().getDrawable(R.drawable.rounded_corner1));
                message.setTextColor(Color.BLACK);
                message.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
                message.setPadding(0,30,0,30);
                linearLayout.addView(message);
            }
        }
    }
}
