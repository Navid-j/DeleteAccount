package com.example.deleteaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String number;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.Btn_Send_Request);
        final EditText phoneNumber = findViewById(R.id.editText);
        final EditText securityCode = findViewById(R.id.veryfyCode);
        securityCode.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = "phone=" + phoneNumber.getText().toString();
                url = "https://my.telegram.org/auth/send_password";


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String hash = object.getString("random_hash");
                                    Log.d("hash",hash);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                securityCode.setVisibility(View.VISIBLE);
                                phoneNumber.setEnabled(false);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<>();
                        params.put("phone",number);

                        return params;
                    }
                };
                queue.add(postRequest);


            }
        });


    }
}
