package xin.spring.video.event;

/**
 * 倒计时事件
 */
public class DateEvent {

    private int time;

    private int type;

    public DateEvent(int time, int type){
        this.time = time;
        this.type = type;
    }

    @Override
    public String toString() {
        return "DateEvent{" +
                "time=" + time +
                ", type=" + type +
                '}';
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
