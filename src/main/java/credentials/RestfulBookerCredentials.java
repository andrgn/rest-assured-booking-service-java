package credentials;

public enum RestfulBookerCredentials {
    ADMIN(System.getenv("ADMIN_LOGIN"), System.getenv("ADMIN_PASSWORD"));

    public final String login;
    public final String password;

    RestfulBookerCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
