package jawbreakergummybear.hivechat.core;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

import jawbreakergummybear.hivechat.MainActivity;

/**
 * Created by georgesedky on 11/29/16.
 */

public class ChatClient {

    private Hive hive;
    private ArrayList<ChatMessage> inbox;
    private ArrayList<Peer> peers;
    private Crypto myCrypto;

    public ChatClient(MainActivity activity){
        inbox = new ArrayList<ChatMessage>();
        peers = new ArrayList<Peer>();
        myCrypto = new Crypto();

        hive = new Hive(activity, this);

        hive.attach();
    }

    public void sendPublicMessage(String message){
        hive.broadcast(Crypto.encodeKey(myCrypto.keyPair.getPublic()), Hive.INTRODUCE);
        hive.broadcast(message.getBytes(), Hive.SHOUT);
    }

    public void sendPrivateMessage(Peer p, String message){
        hive.broadcast(Crypto.encodeKey(myCrypto.keyPair.getPublic()), Hive.INTRODUCE);
        hive.broadcast(encryptMessageTo(p, getMyName() + "$$$" + message), Hive.WHISPER);
    }

    public void sendAnonPrivateMessage(Peer p, String message){
        hive.broadcast(encryptMessageTo(p, message), Hive.WHISPER);
    }

    private byte[] encryptMessageTo(Peer p, String message){
        byte[] encrypted = Crypto.encrypt(p.pKey, message);
        byte[] full_message = new byte[encrypted.length + 24];
        System.arraycopy(p.name.getBytes(), 0, full_message, 0, 24);
        System.arraycopy(encrypted, 0, full_message, 24, full_message.length);
        return full_message;
    }

    private String getMyName(){
        byte[] myPk = Crypto.encodeKey(myCrypto.keyPair.getPublic());
        String me = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(myPk);
            me = Base64.encodeToString(thedigest, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return me;
    }

    public void recievedPublicMessage(String message){
        inbox.add(new ChatMessage(message));
    }

    public void recievedPrivateMessage(byte[] encryptedMessage){
        byte[] check = getMyName().getBytes();

        for(int i = 0; i < 24; i++)
            if(encryptedMessage[i] != check[i])
                return;

        byte[] emessage = Arrays.copyOfRange(encryptedMessage, 24, encryptedMessage.length);
        byte[] data = myCrypto.decrypt(emessage);
        String message = new String(data);
        String[] parsed = message.split("\\$\\$\\$");

        ChatMessage cm;
        if(parsed.length > 1){
            cm = new ChatMessage(parsed[0], parsed[1]);
        }else{
            cm = new ChatMessage("ANONYMOUS", parsed[0]);
        }
        inbox.add(cm);
    }

    public void newPeer(byte[] peer){
        Peer temp = new Peer(peer);
        boolean found = false;
        for(Peer p : peers)
            if(p.name.equals(temp.name))
                found = true;
        if(!found)
            peers.add(temp);
    }

    public void close(){
        hive.detach();
    }

    public ArrayList<ChatMessage> getInbox() {
        return inbox;
    }
}

class Peer{
    public PublicKey pKey;
    public String name;

    public Peer(byte[] key){
        pKey = Crypto.decodeKey(key);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(key);
            name = Base64.encodeToString(thedigest, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
