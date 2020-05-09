package in.co.rescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeopleActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    RecyclerView recyclerView;
    public static RecyclerView.Adapter<PeopleAdapter.ViewHolder> adapter;
    public static List<People> lstpeople;

    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        state = intent.getExtras().getString("state");

        lstpeople = new ArrayList<People>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_people);
        recyclerView.setLayoutManager(new LinearLayoutManager(PeopleActivity.this));

        try {
            Thread.sleep(1000);
            loadPeople();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PeopleActivity.this,ProfileActivity.class);
        startActivity(i);
    }

    private void loadPeople() {
        pDialog= ProgressDialog.show(PeopleActivity.this,"","Loading...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.URL_GETPEOPLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                        pDialog.hide();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject request = jsonArray.getJSONObject(i);

                                String name = request.getString("name");
                                String email = request.getString("email");
                                String phone = request.getString("phone");
                                String state = request.getString("state");

                                lstpeople.add(new People(name,email,phone,state));
                            }
                            adapter = new PeopleAdapter(PeopleActivity.this, lstpeople);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeopleActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("state",state);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
