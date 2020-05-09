package in.co.rescue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private Context dContext ;
    private List<State> lststate ;


    public StateAdapter(Context dContext, List<State> lststate) {
        this.dContext = dContext;
        this.lststate = lststate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_state,parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final State user = lststate.get(position);

        holder.state.setText(user.getState());
        holder.count.setText("People: "+user.getCount());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(dContext, "Bhaag Bosdi",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lststate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView state,count;
        CardView cardView ;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            state = (TextView) itemView.findViewById(R.id.tvstate);
            count = (TextView) itemView.findViewById(R.id.tvcount);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id_state);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.mainlinearpost);
        }
    }


}
