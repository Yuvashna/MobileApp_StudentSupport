package codeconverters.loginpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<itemFirebase>itemsList;
    private OnInfoListener mOnInfoListener;

    public String itemidClick;
    public String StudId;


    public MyAdapter(Context context,List list) {
        this.context = context;
        this.itemsList=list;
    }

    public MyAdapter(Context context,ArrayList list, OnInfoListener onInfoListener) {
        this.context = context;
        this.itemsList=list;
        this.mOnInfoListener=onInfoListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itementry,parent,false);

        return new MyViewHolder(v,mOnInfoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemId.setText(itemsList.get(position).getItemId());
        holder.itemDesc.setText(itemsList.get(position).getItemDesc());
        holder.itemPrice.setText("R "+itemsList.get(position).getdPrice());
        holder.studId.setText(itemsList.get(position).getStudId());


        holder.btnInfo.setOnClickListener(new View.OnClickListener() {
            String text = holder.itemId.getText().toString();
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,IteminfoActivity.class);
                intent.putExtra("itemId",text);
                intent.putExtra("itemDesc",holder.itemDesc.getText().toString());
                intent.putExtra("itemPrice",holder.itemPrice.getText().toString());
                intent.putExtra("studId",holder.studId.getText().toString());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView itemId,itemDesc,itemPrice,studId;
        ImageButton btnInfo;
        ImageView imgView;
        OnInfoListener onInfoListener;
        CardView recCard;

        public MyViewHolder(@NonNull View itemView, OnInfoListener onInfoListener) {
            super(itemView);
            itemId=itemView.findViewById(R.id.textItemId);
            itemDesc=itemView.findViewById(R.id.textItemDesc);
            itemPrice=itemView.findViewById(R.id.textitemPrice);
            studId=itemView.findViewById(R.id.textStudId);
            btnInfo=itemView.findViewById(R.id.imageButtonInfo);

            //imgView=itemView.findViewById(R.id.imageView);
            //recCard=itemView.findViewById(R.id.recCard);
            //this.onInfoListener=onInfoListener;
            //itemView.setOnClickListener(this);


        }


    }



    public Intent openInfo(){
        Intent intent;
        return intent=new Intent(context,IteminfoActivity.class);
    }

    public interface OnInfoListener{
        void onBtnClick(int pos);

    }


}
