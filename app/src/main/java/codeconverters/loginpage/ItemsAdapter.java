package codeconverters.loginpage;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private Context context;
    private ArrayList itemId, itemDesc, itemPrice;
    private ArrayList<itemFirebase> itemsList;

    ImageButton btnSold,btnDelete;

    public ItemsAdapter(Context context, ArrayList<itemFirebase> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public ItemsAdapter(Context context, ArrayList itemId, ArrayList itemDesc, ArrayList itemPrice) {
        this.context = context;
        this.itemId = itemId;
        this.itemDesc = itemDesc;
        this.itemPrice = itemPrice;
    }

    @NonNull
    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itementry2,parent,false);

        return new ItemsAdapter.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        itemFirebase item=itemsList.get(position);
        holder.itemId.setText(item.getItemId());
        holder.itemDesc.setText(item.getItemDesc());
        holder.itemPrice.setText(String.valueOf(item.getdPrice()));

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemId,itemDesc,itemPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemId=itemView.findViewById(R.id.textItemId);
            itemDesc=itemView.findViewById(R.id.textItemDesc);
            itemPrice=itemView.findViewById(R.id.textitemPrice);

            btnSold=itemView.findViewById(R.id.imageButtonSold);
            btnDelete=itemView.findViewById(R.id.imageButtonDelete);

            btnSold.setOnClickListener(view -> {
                rootNode= FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Items");

                DatabaseReference recordRef=reference.child(itemId.getText().toString());
                recordRef.child("bSold").setValue(true);
                Toast.makeText(context, "Successfully updated item!", Toast.LENGTH_SHORT).show();

/*
                reference.child("Items").child(itemId.getText().toString()).child("bSold").setValue(true);
                Toast.makeText(context, "Successfully changed!", Toast.LENGTH_SHORT).show();


 */

            });

            btnDelete.setOnClickListener(view -> {
                rootNode= FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Items");

                DatabaseReference recordRef=reference.child(itemId.getText().toString());
                recordRef.removeValue();
                Toast.makeText(context, "Successfully deleted item!", Toast.LENGTH_SHORT).show();

            });
        }
    }

}



