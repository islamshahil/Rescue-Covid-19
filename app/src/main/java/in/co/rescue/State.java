package in.co.rescue;

public class State {

    String state,count;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public State(String state, String count) {
        this.state = state;
        this.count = count;
    }
}
