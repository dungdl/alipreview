package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alimentopreview.R;

import java.util.ArrayList;

import model.Destination;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {

    //MARK:- Data
    private ArrayList<Destination> listDest;

    //MARK:- UI


    //MARK:- Constructor

    public DestinationAdapter(ArrayList<Destination> listDest) {
        this.listDest = listDest;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_layout, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Destination des = listDest.get(i);
        TextView tvDesAddress = viewHolder.tvDesAddress;
        TextView tvDesName = viewHolder.tvDesName;
        ImageView imvAva = viewHolder.imvAva;

        tvDesAddress.setText(des.getAddress());
        tvDesName.setText(des.getName());
    }

    @Override
    public int getItemCount() {
        return listDest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvAva;
        TextView tvDesName;
        TextView tvDesAddress;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imvAva = itemView.findViewById(R.id.imvAva);
            tvDesName = itemView.findViewById(R.id.tvDesName);
            tvDesAddress = itemView.findViewById(R.id.tvDesAddress);
        }
    }
}
