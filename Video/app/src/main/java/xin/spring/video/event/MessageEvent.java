package xin.spring.video.event;

/**
 * 定义消息事件MessageEvent
 */
public class MessageEvent{

    private String message;

    public  MessageEvent(){}

    public  MessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}