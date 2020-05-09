package in.co.rescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText etname, etemail, etphone, etpass;
    String name, email, phone, password, state;
    Button signup;
    ProgressDialog pDialog;
    Spinner sstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etname = findViewById(R.id.et_name);
        etemail = findViewById(R.id.et_email);
        etphone = findViewById(R.id.et_phone);
        etpass = findViewById(R.id.et_pass);
        signup = findViewById(R.id.button_signup);

        // spinner
        sstate = (Spinner)findViewById(R.id.sstate);

        List<String> list = new ArrayList<>();
        list.add("Select State");
        list.add("Andhra Pradesh");
        list.add("Arunachal Pradesh");
        list.add("Assam");
        list.add("Bihar");
        list.add("Chhattisgarh");
        list.add("Goa");
        list.add("Gujarat");
        list.add("Haryana");
        list.add("Himachal Pradesh");
        list.add("Jharkhand");
        list.add("Karnataka");
        list.add("Kerala");
        list.add("Madhya Pradesh");
        list.add("Maharashtra");
        list.add("Manipur");
        list.add("Meghalaya");
        list.add("Mizoram");
        list.add("Nagaland");
        list.add("Odisha");
        list.add("Punjab");
        list.add("Rajasthan");
        list.add("Sikkim");
        list.add("Tamil Nadu");
        list.add("Telangana");
        list.add("Tripura");
        list.add("Uttar Pradesh");
        list.add("Uttarakhand");
        list.add("West Bengal");
        list.add("Andaman and Nicobar Islands");
        list.add("Chandigarh");
        list.add("Dadra and Nagar Haveli and Daman and Diu");
        list.add("Delhi");
        list.add("Jammu and Kashmir");
        list.add("Ladakh");
        list.add("Lakshadweep");
        list.add("Puducherry");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_spinner_dropdown_item_custom,list);
        sstate.setAdapter(adapter);
        sstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   customer.setText(parent.getItemAtPosition(position).toString());
                state = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etname.getText().toString();
                email = etemail.getText().toString();
                phone = etphone.getText().toString();
                password = etpass.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !password.isEmpty() && !state.isEmpty() && !state.equals("Select State")) {
                   // register();
                    Toast.makeText(getApplicationContext(), state, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void register() {

        pDialog= ProgressDialog.show(SignupActivity.this,"","Registering...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.URL_REGISTER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignupActivity.this, response, Toast.LENGTH_LONG).show();
                        pDialog.hide();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",name);
                params.put("email ",email);
                params.put("phone", phone);
                params.put("password",password);
                params.put("state",state);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
