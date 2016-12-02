package jawbreakergummybear.hivechat.core;

import android.app.Activity;

import java.security.PublicKey;
import java.util.ArrayList;

import jawbreakergummybear.hivechat.MainActivity;

/**
 * Created by georgesedky on 11/29/16.
 */

public class ChatClient {

    private Hive hive;
    private ArrayList<ChatMessage> inbox;
    private ArrayList<PublicKey> peerKeys;

    public ChatClient(MainActivity activity){
        inbox = new ArrayList<ChatMessage>();
        peerKeys = new ArrayList<PublicKey>();

        hive = new Hive(activity, this);

        hive.attach();
    }

    public void recievedPublicMessage(String message){

    }

    public void recievedPrivateMessage(byte[] encryptedMessage){

    }

    public void newPeer(byte[] peer){

    }

    public void close(){
        hive.detach();
    }
}



class ChatMessage{
    private String from;
    private String text;

    public ChatMessage(String text){
        this.from = "PUBLIC";
        this.text = text;
    }
    public ChatMessage(String from, String text){
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
