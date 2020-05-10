package in.co.rescue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etemail,etpassword;
    Button btnlogin, btnsignup, btnforgot;

    String email,password;

    ProgressDialog pDialog;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etemail =  findViewById(R.id.et_username);
        etpassword =  findViewById(R.id.et_password);
        btnsignup = findViewById(R.id.button_signup);
        btnforgot =  findViewById(R.id.button_forgot_password);
        btnlogin = findViewById(R.id.button_signin);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etemail.getText().toString();
                password = etpassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    login();
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter your details", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Email at: shahilislam@gmail.com", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

    private void login(){

        pDialog= ProgressDialog.show(LoginActivity.this,"","Logging In...",false,false);

        StringRequest strReq = new StringRequest(Request.Method.POST, url.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if(response.equalsIgnoreCase("Invalid credentials.")){
                    Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                } else {

                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject request = jsonArray.getJSONObject(i);

                            String name = request.getString("name");
                            String email = request.getString("email");
                            String phone = request.getString("phone");
                            String state = request.getString("state");


                            // shared preference reference important
                            SharedPreferences obj = getSharedPreferences("mypref",0);
                            SharedPreferences.Editor editor = obj.edit();
                            editor.putString("name",name);
                            editor.putString("email",email);
                            editor.putString("phone",phone);
                            editor.putString("state",state);
                            editor.commit();

                            pDialog.hide();
                            session.setLogin(true);
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this,"Welcome!!",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

}
