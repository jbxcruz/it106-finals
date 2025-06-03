import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

// imports






public class ApiClient {
    private static final String API = "http://localhost/sari-sari/api.php";
    private static final String DELETE_URL = "http://localhost/sari-sari/delete_products.php";



    public List<Product> getProducts() throws Exception {
        URL url = URI.create(API).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
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
        URL url = URI.create(API).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String json = new Gson().toJson(p);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes("utf-8"));
        }

        int code = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(
            (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream()
        ));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JsonElement element = JsonParser.parseString(response.toString());
        boolean ok;
        String errorMsg = null;

        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            ok = obj.get("success").getAsBoolean();
            if (!ok && obj.has("error")) {
                errorMsg = obj.get("error").getAsString();
            }
        } else {
            ok = element.getAsBoolean();
        }

        if (ok) {
            System.out.println("✅ Product updated.");
        } else {
            System.out.println("❌ Update failed: " + (errorMsg != null ? errorMsg : "Unknown error"));
        }
    }




public void deleteProduct(int id) {
    try {
        URL url = URI.create(DELETE_URL).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String params = "id=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes("utf-8"));
        }

        int code = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(
            (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream(),
            "UTF-8"
        ));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JsonElement element = JsonParser.parseString(response.toString());
        boolean ok;
        String errorMsg = null;

        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            ok = obj.get("success").getAsBoolean();
            if (!ok && obj.has("error")) {
                errorMsg = obj.get("error").getAsString();
            }
        } else {
            ok = element.getAsBoolean();
        }

        if (ok) {
            System.out.println("✅ Product deleted.");
        } else {
            System.out.println("❌ Delete failed: " + (errorMsg != null ? errorMsg : "Unknown error"));
        }
    } catch (Exception e) {
        System.out.println("❌ Delete failed: " + e.getMessage());
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
