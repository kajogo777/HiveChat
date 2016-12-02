package jawbreakergummybear.hivechat.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;

import io.underdark.Underdark;
import io.underdark.transport.Link;
import io.underdark.transport.Transport;
import io.underdark.transport.TransportKind;
import io.underdark.transport.TransportListener;
import jawbreakergummybear.hivechat.MainActivity;


public class Hive implements TransportListener {

    private boolean running;
    private MainActivity activity;
    private long nodeId;
    private Transport transport;
    private ChatClient cc;

    private ArrayList<Link> links;

    public static final byte SHOUT = 1;
    public static final byte INTRODUCE = 11;
    public static final byte WHISPER = 111;


    public Hive(MainActivity activity, ChatClient cc){
        this.activity = activity;
        this.cc = cc;
        links = new ArrayList<Link>();

        do
        {
            nodeId = new Random().nextLong();
        } while (nodeId == 0);

        if(nodeId < 0)
            nodeId = -nodeId;

        EnumSet<TransportKind> kinds = EnumSet.of(TransportKind.BLUETOOTH, TransportKind.WIFI);

        this.transport = Underdark.configureTransport(
                77777,
                nodeId,
                this,
                null,
                activity.getApplicationContext(),
                kinds
        );
    }

    public void attach()
    {
        if(running)
            return;

        running = true;
        transport.start();
    }

    public void detach()
    {
        if(!running)
            return;

        running = false;
        transport.stop();
    }

    public void broadcast(byte[] data, byte type)
    {
        if(links.isEmpty())
            return;

        byte[] message = new byte[data.length + 1];

        message[0] = type;

        for(int i = 0; i < data.length; i++) message[i+1] = data[i];

        for(Link link : links)
            link.sendFrame(message);
    }

    @Override
    public void transportNeedsActivity(Transport transport, ActivityCallback activityCallback) {
        activityCallback.accept(activity);
    }

    @Override
    public void transportLinkConnected(Transport transport, Link link) {
        links.add(link);
    }

    @Override
    public void transportLinkDisconnected(Transport transport, Link link) {
        links.remove(link);
    }

    @Override
    public void transportLinkDidReceiveFrame(Transport transport, Link link, byte[] bytes) {
        byte type = bytes[0];
        byte[] data = Arrays.copyOfRange(bytes, 1, bytes.length);

        switch(type){
            case SHOUT:
                cc.recievedPublicMessage(new String(data));
                break;
            case WHISPER:
                cc.recievedPrivateMessage(data);
                break;
            case INTRODUCE:
                cc.newPeer(data);
                break;
        }
    }
}
