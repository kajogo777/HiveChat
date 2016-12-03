package jawbreakergummybear.hivechat.core;

/**
 * Created by georgesedky on 12/3/16.
 */
public class ChatMessage{
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
