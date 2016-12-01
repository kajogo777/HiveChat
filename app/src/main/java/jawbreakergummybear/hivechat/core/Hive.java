package jawbreakergummybear.hivechat.core;

import java.util.ArrayList;
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

    private ArrayList<Link> links = new ArrayList<>();

    private ArrayList<String> messageBuffer;

    public Hive(MainActivity activity){
        this.activity = activity;
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

    public ArrayList<Link> getLinks()
    {
        return links;
    }

    public ArrayList<String> getMessageBuffer()
    {
        return messageBuffer;
    }

    public void broadcast(String message)
    {
        if(links.isEmpty())
            return;

        for(Link link : links)
            link.sendFrame(message.getBytes());
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
        messageBuffer.add(new String(bytes));
    }
}
