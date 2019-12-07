package application.messaging.requests;

public class ForwardMessage {
    private int nextIndex;
    private String nextTag;
    private String message;

    public ForwardMessage(int nextIndex, String nextTag, String message) {
        this.nextIndex = nextIndex;
        this.nextTag = nextTag;
        this.message = message;
    }

    public ForwardMessage() {
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    public String getNextTag() {
        return nextTag;
    }

    public void setNextTag(String nextTag) {
        this.nextTag = nextTag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
