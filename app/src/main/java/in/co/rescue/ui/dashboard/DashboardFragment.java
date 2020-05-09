package in.co.rescue.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import in.co.rescue.LoginActivity;
import in.co.rescue.R;
import in.co.rescue.SessionManager;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private SessionManager session;
    String name,email,phone,state;
    TextView tvname,tvemail,tvphone,tvstate;
    LinearLayout logout,share;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //shared pref
        SharedPreferences obj = this.getActivity().getSharedPreferences("mypref", 0);
        name = obj.getString("name", "");
        email = obj.getString("email", "");
        phone = obj.getString("phone", "");
        state = obj.getString("state", "");

        // Session manager
        session = new SessionManager(getContext());

        tvname = (TextView) root.findViewById(R.id.name);
        tvphone = (TextView) root.findViewById(R.id.phone);
        tvemail = (TextView) root.findViewById(R.id.email);
        tvstate = (TextView) root.findViewById(R.id.state);

        logout = root.findViewById(R.id.logout);
        share = root.findViewById(R.id.share);

        tvname.setText(name);
        tvphone.setText(phone);
        tvemail.setText(email);
        tvstate.setText(state);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // logoutUser();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Under Construction", Toast.LENGTH_LONG).show();
            }
        });



//
//        //check login
//        if (!session.isLoggedIn()) {
//            logoutUser();
//        }

//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });

        return root;
    }

    private void logoutUser() {

        session.setLogin(false);
        // Launching the login activity
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
