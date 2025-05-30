import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ApiClient {
        private static final String API = "http://localhost/sari-sari/api.php";


    public List<Product> getProducts() throws Exception {
        URL url = URI.create(API).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) sb.append(line);
        in.close();

        return new Gson().fromJson(sb.toString(),
            new TypeToken<List<Product>>(){}.getType()
        );
    }


    public void addProduct(Product p) throws Exception {
        doPost("POST", p);
        System.out.println("✅ Product added.");
    }

    public void updateProduct(Product p) throws Exception {
        doPost("PUT", p);
        System.out.println("✅ Product updated.");
    }

public void deleteProduct(int id) throws Exception {
    URL url = URI.create("http://localhost/sari-sari/delete_products.php").toURL();
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    conn.setDoOutput(true);

    String params = "id=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
    try (OutputStream os = conn.getOutputStream()) {
        os.write(params.getBytes("utf-8"));
    }

    int code = conn.getResponseCode();
    if (code == 200) {
        System.out.println("✅ Product deleted.");
    } else {
        throw new RuntimeException("DELETE failed: HTTP " + code);
    }
}




    private void doPost(String method, Product p) throws Exception {
        URL url = URI.create(API).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String json = new Gson().toJson(p);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes("utf-8"));
        }

        int code = conn.getResponseCode();
        if (code < 200 || code >= 300) {
            throw new RuntimeException(method + " failed: HTTP " + code);
        }
    }
}
