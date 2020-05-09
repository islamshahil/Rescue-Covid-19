package in.co.rescue;

public class People {
    String name, email, phone, state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public People(String name, String email, String phone, String state) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.state = state;
    }
}
