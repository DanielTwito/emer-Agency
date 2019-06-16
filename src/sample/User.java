package sample;

public class User {

    private String userName;
    private String name;
    private String password;
    private int rank;
    private double score;
    private String userStatus;
    private String email;
    private boolean isAdmin;
    private int warnings;

    public User(String userName, String name, String password, int rank, double score, String userStatus, String email, boolean isAdmin, int warnings) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.rank = rank;
        this.score = score;
        this.userStatus = userStatus;
        this.email = email;
        this.isAdmin = isAdmin;
        this.warnings = warnings;
    }


    public void addEvent(){

    }
    public void addUpdate(){

    }
}
