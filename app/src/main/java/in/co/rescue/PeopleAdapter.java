package in.co.rescue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private Context dContext ;
    private List<People> lstpeople ;


    public PeopleAdapter(Context dContext, List<People> lstpeople) {
        this.dContext = dContext;
        this.lstpeople = lstpeople;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_people,parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final People user = lstpeople.get(position);

        holder.state.setText("State: "+user.getState());
        holder.email.setText("Email: "+user.getEmail());
        holder.phone.setText("Phone: "+user.getPhone());
        holder.name.setText("Name: "+user.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(dContext, "Bhaag Bosdi",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstpeople.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView state,name,phone,email;
        CardView cardView ;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            state = (TextView) itemView.findViewById(R.id.tvstate);
            name = (TextView) itemView.findViewById(R.id.tvname);
            phone = (TextView) itemView.findViewById(R.id.tvphone);
            email = (TextView) itemView.findViewById(R.id.tvemail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id_people);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.mainlinearpost);
        }
    }

}