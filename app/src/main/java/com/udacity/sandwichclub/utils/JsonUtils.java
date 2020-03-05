package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private JsonUtils() {}

    private static final String LOG_JSON_PARSING = "JSON-Parsing";
    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";
    private static final String SANDWICH_INGREDIENTS = "ingredients";
    private static final String SANDWICH_DATA = "sandwichData";
    private static final String NOT_APPLICABLE = "n/a";

    public static Sandwich parseSandwichJson(String jsonString) throws JSONException {
        if (jsonString != null) {
            JSONObject sandwichData = new JSONObject(jsonString);
            Sandwich sandwich = new Sandwich();
            Log.d(LOG_JSON_PARSING, SANDWICH_DATA + " : " + sandwichData.toString());

            sandwich.setImage(sandwichData.optString(SANDWICH_IMAGE));
            Log.d(LOG_JSON_PARSING, SANDWICH_IMAGE + " : " + sandwich.getImage());

            JSONObject sandwichName = sandwichData.optJSONObject(SANDWICH_NAME);
            Log.d(LOG_JSON_PARSING, SANDWICH_NAME + " : " + sandwichName.toString());

            sandwich.setMainName(sandwichName.optString(SANDWICH_MAIN_NAME, NOT_APPLICABLE));
            Log.d(LOG_JSON_PARSING, SANDWICH_MAIN_NAME + " : " + sandwich.getMainName());

            sandwich.setAlsoKnownAs(getSandwichArrayData(sandwichName, SANDWICH_ALSO_KNOWN_AS));

            sandwich.setPlaceOfOrigin(sandwichData.optString(SANDWICH_PLACE_OF_ORIGIN, NOT_APPLICABLE));
            Log.d(LOG_JSON_PARSING, SANDWICH_PLACE_OF_ORIGIN + " : " + sandwich.getPlaceOfOrigin());

            sandwich.setIngredients(getSandwichArrayData(sandwichData, SANDWICH_INGREDIENTS));

            sandwich.setDescription(sandwichData.optString(SANDWICH_DESCRIPTION, NOT_APPLICABLE));
            Log.d(LOG_JSON_PARSING, SANDWICH_DESCRIPTION + " : " + sandwich.getDescription());

            return sandwich;
        }
        return null;
    }

    /**
     * Convenience method to parse JSON String Arrays for a given key
     * @param sandwichObject sandwichObject
     * @param key key
     * @return List of generic sandwich data
     * @throws JSONException
     */
    private static List<String> getSandwichArrayData(JSONObject sandwichObject, String key) throws JSONException {
        JSONArray sandwichArray = sandwichObject.optJSONArray(key);
        List<String> sandwichDataList = new ArrayList<>();
        if (sandwichArray != null) {
            for (int i = 0; i < sandwichArray.length(); i++) {
                sandwichDataList.add(sandwichArray.getString(i));
                Log.d(LOG_JSON_PARSING, key + "[" + i + "]" + sandwichArray.getString(i));
            }
            Log.d(LOG_JSON_PARSING, key + " : " + sandwichDataList);
        }
        return sandwichDataList;
    }
}
