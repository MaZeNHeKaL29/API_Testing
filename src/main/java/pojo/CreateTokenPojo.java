package pojo;

public class CreateTokenPojo {

    private String username;
    private String password;

    // 🧱 Default constructor (needed for serialization)
    public CreateTokenPojo() {
    }

    // 🧱 Parameterized constructor
    public CreateTokenPojo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 🧾 Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
