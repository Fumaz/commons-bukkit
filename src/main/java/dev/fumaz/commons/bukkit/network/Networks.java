package dev.fumaz.commons.bukkit.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.craftbukkit.libs.org.apache.http.HttpEntity;
import org.bukkit.craftbukkit.libs.org.apache.http.HttpResponse;
import org.bukkit.craftbukkit.libs.org.apache.http.NameValuePair;
import org.bukkit.craftbukkit.libs.org.apache.http.client.HttpClient;
import org.bukkit.craftbukkit.libs.org.apache.http.client.entity.UrlEncodedFormEntity;
import org.bukkit.craftbukkit.libs.org.apache.http.client.methods.HttpPost;
import org.bukkit.craftbukkit.libs.org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DO NOT USE ANY OF THESE METHODS ON THE MAIN THREAD
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class Networks {

    private final static Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    private Networks() {
    }

    /**
     * Fetches a URL's contents as a JSON Object
     *
     * @param url the url
     * @return the contents
     * @throws NetworkException when an error occurs while fetching data, or the URL is malformed
     */
    public static JsonObject fetchJSON(String url) {
        String text = fetchString(url);

        return gson.fromJson(text, JsonObject.class);
    }

    /**
     * Fetches a URL's contents as a string
     *
     * @param url the url
     * @return the contents
     * @throws NetworkException when an error occurs while fetching data, or the URL is malformed
     */
    public static String fetchString(String url) {
        try {
            InputStream inputStream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new NetworkException("Error whilst fetching data from url", e);
        }
    }

    /**
     * Sends a POST request to the given URL, with the given parameters
     *
     * @param url    the url
     * @param params the parameters
     * @return the response, as a UTF-8 string
     * @throws NetworkException when an error occurs while making the request
     */
    public static String post(String url, NameValuePair... params) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);

            List<NameValuePair> paramList = Arrays.asList(params);
            post.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            return IOUtils.toString(entity.getContent(), "UTF-8");
        } catch (IOException e) {
            throw new NetworkException("Error whilst making post request.", e);
        }
    }


}
