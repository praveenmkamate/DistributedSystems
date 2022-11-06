package service.messages;

public class RequestDeadline {

    private int id;

    public RequestDeadline(int SEED_ID) {
        this.id = SEED_ID;
    }

    public RequestDeadline() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
