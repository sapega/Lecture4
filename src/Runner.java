import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class Runner {
    public static void main(String[] args) {
        String query = "https://api.github.com/users/sapega";
        HttpURLConnection connection = null;
        try {

            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
           // connection.setConnectTimeout(250);
           // connection.setReadTimeout(250);
            connection.connect();

            StringBuilder ab = new StringBuilder();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = bf.readLine()) != null) {
                    ab.append(line);
                    ab.append("\n");
                }
                System.out.println(ab.toString());
            } else {
                System.out.println("fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
