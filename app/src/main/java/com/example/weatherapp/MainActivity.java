package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volleyRequest();
    }

    private void volleyRequest(){
        final TextView textViewErrors = (TextView) findViewById(R.id.textViewError);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.open-meteo.com/v1/forecast?latitude=30.28&longitude=-97.76&hourly=" +
                "temperature_2m&daily=temperature_2m_max,temperature_2m_min,rain_sum,windspeed_10m_max&" +
                "temperature_unit=fahrenheit&timezone=America%2FChicago";

        JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject obj = response.getJSONObject("daily");
                            JSONArray times = obj.getJSONArray("time");
                            JSONArray mins = obj.getJSONArray("temperature_2m_max");
                            JSONArray maxs = obj.getJSONArray("temperature_2m_min");
                            JSONArray rain = obj.getJSONArray("rain_sum");
                            JSONArray wind = obj.getJSONArray("windspeed_10m_max");

                            ArrayList<Integer> avgs = new ArrayList<>();
                            for(int i = 0; i < mins.length(); i++){
                                avgs.add((mins.getInt(i) + maxs.getInt(i))/2);
                            }
                            TextView day1 = (TextView) findViewById(R.id.textView1);
                            day1.setText("Date: " + times.get(0).toString() + ", Average Temp: " + avgs.get(0) + ", Rain: " + rain.get(0) + ", Wind: " + wind.get(0));

                            TextView day2 = (TextView) findViewById(R.id.textView2);
                            day2.setText("Date: " + times.get(1).toString() + ", Average Temp: " + avgs.get(1));

                            TextView day3 = (TextView) findViewById(R.id.textView3);
                            day3.setText("Date: " + times.get(2).toString() + ", Average Temp: " + avgs.get(2));

                            TextView day4 = (TextView) findViewById(R.id.textView4);
                            day4.setText("Date: " + times.get(3).toString() + ", Average Temp: " + avgs.get(3));

                            TextView day5 = (TextView) findViewById(R.id.textView5);
                            day5.setText("Date: " + times.get(4).toString() + ", Average Temp: " + avgs.get(4));

                            TextView day6 = (TextView) findViewById(R.id.textView6);
                            day6.setText("Date: " + times.get(5).toString() + ", Average Temp: " + avgs.get(5));

                            TextView day7 = (TextView) findViewById(R.id.textView7);
                            day7.setText("Date: " + times.get(6).toString() + ", Average Temp: " + avgs.get(6));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    textViewErrors.setText(error.getMessage());
                }
            });
        queue.add(stringRequest);
    }
}