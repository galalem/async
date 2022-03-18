package com.galalem.async;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        JSONArray json = null;
        try {
            json = new JSONArray("");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertNotNull(json);
        try {
            json = new JSONArray("{\"key1\": \"value1\", \"key2\": \"value2\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertNotNull(json);
        try {
            json = new JSONArray("\"hoho\":[{\"key1\": \"value1\", \"key2\": \"value2\"}, {\"key1\": \"value1\", \"key2\": \"value2\"}]");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertNotNull(json);
    }
}