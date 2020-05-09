package in.co.rescue.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import in.co.rescue.MainActivity;
import in.co.rescue.R;
import in.co.rescue.State;
import in.co.rescue.StateAdapter;
import in.co.rescue.url;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    RecyclerView recyclerView;
    public static RecyclerView.Adapter<StateAdapter.ViewHolder> adapter;
    public static List<State> lststate;

    ProgressDialog pDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });

        lststate = new ArrayList<State>();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview_state);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        try {
            Thread.sleep(1000);
            loadstates();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void loadstates() {

        pDialog= ProgressDialog.show(getContext(),"","Loading...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.URL_GETSTATES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject request = jsonArray.getJSONObject(i);

                                String state = request.getString("state");
                                String count = request.getString("count");

                                lststate.add(new State(state,count));
                            }
                            adapter = new StateAdapter(getContext(), lststate);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
