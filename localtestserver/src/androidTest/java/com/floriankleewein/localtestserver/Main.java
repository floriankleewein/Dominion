import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        TestServer ts = new TestServer();

        try {
            ts.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
