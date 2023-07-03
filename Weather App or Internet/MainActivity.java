package com.sql.prog6;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText city_btn = findViewById(R.id.city_edt);
        Button search_btn = findViewById(R.id.search_btn);
        TextView temp = findViewById(R.id.temp);
        search_btn.setOnClickListener(v->{
            String city = city_btn.getText().toString();
            String url = "http://api.weatherapi.com/v1/current.json?key=020ada43587e4fbdbd223153231602&q="+city;
            StringRequest stringRequest = new StringRequest( Request.Method.GET,url, response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String temp_c = jsonResponse.getJSONObject("current").getString("temp_c");
                    String temp_f = jsonResponse.getJSONObject("current").getString("temp_f");
                    temp.setText(String.format("Temperature in Celsius : %s\nTemperature in Far : %s",temp_c,temp_f));
                }catch (JSONException ex){
                    Toast.makeText(this, "invalid JSON", Toast.LENGTH_SHORT).show();
                }
            },error -> {
                if (error.networkResponse.statusCode == 400){
                    Toast.makeText(this, "Check the city name", Toast.LENGTH_SHORT).show();
                }
            });
            Volley.newRequestQueue(this).add(stringRequest);
        });
    }
}
