package xin.spring.video.event;

/**
 * 定义消息事件MessageEvent
 */
public class MessageEvent{

    private String message;

    private int type;

    public  MessageEvent(String message, int type){
        this.message=message;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "message='" + message + '\'' +
                ", type=" + type +
                '}';
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}