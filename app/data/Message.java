package data;

public class Message {
    public String text;
    //enum enum_var {str,str,...} str without quotes
    public enum Sender {USER,BOT}
    public FeedResponse feedResponse;
    public Sender sender;
}
