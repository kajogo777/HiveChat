package jawbreakergummybear.hivechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserName extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String userNameMessage ="user_name";
    public static final String firstUseMessage= "first_use";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_name);
        final EditText edit_txt = (EditText) findViewById(R.id.editText);
        final Button submit_btn = (Button) findViewById(R.id.submit_button);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(UserName.this,PublicChat.class);
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(userNameMessage, edit_txt.getText().toString());
                editor.putBoolean(firstUseMessage,true);
                editor.commit();
                startActivity(k);
            }
        });
        edit_txt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit_btn.performClick();
                    return true;
                }
                return false;
            }
        });
    }
}
