package jawbreakergummybear.hivechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import jawbreakergummybear.hivechat.core.ChatClient;
import jawbreakergummybear.hivechat.core.Peer;

/**
 * Created by Omar on 12/2/16.
 */
public class PrivateChatList extends AppCompatActivity {
        public ChatClient myClient = MainActivity.myClient;
        public ArrayList<Peer> peers= myClient.getPeers();
        public ArrayList<String> onlineUsers=new ArrayList<String>(peers.size());
    protected void onCreate(Bundle savedInstanceState){
        getNames();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_chat_list);
        ListView listView =(ListView) findViewById(R.id.online_list);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,onlineUsers);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                String item = ((TextView)view).getText().toString();
                Intent k = new Intent(PrivateChatList.this,PrivateChat.class);
                k.putExtra("userName",item);
                startActivity(k);
            }
        });
    }
    public void getNames(){
        for(int i=0;i<peers.size();i++){
            onlineUsers.add(i,peers.get(i).name);
        }
    }
}
