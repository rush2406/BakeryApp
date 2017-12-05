package com.example.rusha.bakeryapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by rusha on 6/27/2017.
 */

public class NetworkUtils {

    public static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private NetworkUtils() {
    }

    public static ArrayList<Recipe> fetchData(String requestURL) {
        // Create URL object
        URL url = createURL(requestURL);
        Log.v(LOG_TAG, url.toString());

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        // Parse JSON string and create an {@ArrayList<NewsItem>} object
        return extractMovieData(jsonResponse);
    }

    public static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
            Log.v(LOG_TAG, url.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error Creating URL", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHTTPRequest(URL url) throws IOException {

        // If the url is empty, return early
        String jsonResponse = null;
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200), then read the input stream and
            // parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results", e);
        } finally {
            // Close connection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            // Close stream
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputstream) throws IOException {
        StringBuilder streamOutput = new StringBuilder();
        if (inputstream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputstream, Charset
                    .forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                streamOutput.append(line);
                line = reader.readLine();
            }
        }
        return streamOutput.toString();
    }

    private static ArrayList<Recipe> extractMovieData(String DataJSON) {
        // If the JSON string is empty or null, then return early.
        Log.v(LOG_TAG, "enter");
        if (TextUtils.isEmpty(DataJSON)) {
            return null;
        }

        ArrayList<Recipe> recipies = new ArrayList<>();

        try {

            JSONArray root = new JSONArray(DataJSON);
            Log.v(LOG_TAG, "Size = " + root.length());
            // Variables for JSON parsing
            int id;
            String title;
            ArrayList<Ingredients> ingre = new ArrayList<>();
            ArrayList<Steps> steps = new ArrayList<>();
            ingre.clear();
            steps.clear();

            for (int i = 0; i < root.length(); i++) {
                JSONObject current = root.getJSONObject(i);
                if (current.has("id")) {
                    id = current.getInt("id");
                } else
                    id = 0;
                if (current.has("name")) {
                    title = current.getString("name");
                    Log.v(LOG_TAG, "Name = " + title);
                } else
                    title = null;
                try {
                    String quantity;
                    String measure;
                    String ingredient;
                    JSONArray ingredients = current.getJSONArray("ingredients");
                    for (int j = 0; j < ingredients.length(); j++) {
                        JSONObject object = ingredients.getJSONObject(j);
                        if (object.has("quantity")) {
                            quantity = object.getString("quantity");
                        } else
                            quantity = null;
                        if (object.has("measure")) {
                            measure = object.getString("measure");
                        } else
                            measure = null;
                        if (object.has("ingredient")) {
                            ingredient = object.getString("ingredient");
                        } else
                            ingredient = null;
                        ingre.add(new Ingredients(quantity, measure, ingredient));
                    }
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Json Error");
                }

                try {

                    int Id;
                    String shortDesc;
                    String desc;
                    String video;
                    String thumbnail;
                    JSONArray steparray = current.getJSONArray("steps");
                    for (int k = 0; k < steparray.length(); k++) {
                        JSONObject object = steparray.getJSONObject(k);
                        if (object.has("id"))
                            Id = object.getInt("id");
                        else
                            Id = 0;
                        if (object.has("shortDescription")) {
                            shortDesc = object.getString("shortDescription");
                            Log.v(LOG_TAG, "Desc = " + shortDesc);
                        } else
                            shortDesc = null;
                        if (object.has("description"))
                            desc = object.getString("description");
                        else
                            desc = null;
                        if (object.has("videoURL"))
                            video = object.getString("videoURL");
                        else
                            video = null;
                        if (object.has("thumbnailURL"))
                            thumbnail = object.getString("thumbnailURL");
                        else
                            thumbnail = null;
                        steps.add(new Steps(Id, shortDesc, desc, video, thumbnail));
                    }

                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Json Error");
                }

                // Create the Item object and add it to the ArrayList
                Recipe recipe = new Recipe(id, title, ingre, steps);
                recipies.add(recipe);
                ingre = new ArrayList<>();
                steps = new ArrayList<>();
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }

        // Return the list of Items
        return recipies;
    }

}


