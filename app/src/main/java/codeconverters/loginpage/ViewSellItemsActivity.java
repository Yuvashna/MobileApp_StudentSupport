package codeconverters.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewSellItemsActivity extends AppCompatActivity {


    DatabaseReference dbReference;
    FirebaseDatabase rootNode;
    RecyclerView recyclerView;
    ArrayList<String> itemId,itemDesc,itemPrice;

    ArrayList<itemFirebase> items;
    Spinner spinnerCategory,spinnerFilter;
    EditText edtSearch;
    ImageButton btnSearch,btnBack;

    ItemsAdapter adapter;

    Button btnAdd;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsellitems);

        //spinnerCategory=findViewById(R.id.spinnerCategory);
        edtSearch=findViewById(R.id.editTextSearch);
        btnSearch=findViewById(R.id.imageButtonSearch);
        btnBack=findViewById(R.id.imageButtonBack);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView =(RecyclerView) findViewById(R.id.RecyclerViewPersonalItem);


        try {
            showData();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate.isText(edtSearch.getText().toString())){
                    searchdata(edtSearch.getText().toString());
                }else{
                    Toast.makeText(ViewSellItemsActivity.this, "Please enter in letters only in the search box!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAdd=findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddItems();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                showData();
            }
        });

    }

    private void showData(){

        rootNode= FirebaseDatabase.getInstance();
        dbReference=rootNode.getReference("Items");

        Query query =dbReference.orderByChild("bSold").equalTo(false);

        items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ItemsAdapter(ViewSellItemsActivity.this,items);
        recyclerView.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(ViewSellItemsActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    itemFirebase item=dataSnapshot.getValue(itemFirebase.class);
                    if (item.isbSold()==false){
                        //Toast.makeText(ViewSellItemsActivity.this, "items username"+item.getStudId(), Toast.LENGTH_SHORT).show();
                       // Toast.makeText(ViewSellItemsActivity.this, "MA username"+item.getStudId(), Toast.LENGTH_SHORT).show();
                        if (item.getStudId().contains(MainActivity.getgUsername())){
                            //Toast.makeText(ViewSellItemsActivity.this, "inside 2nd if", Toast.LENGTH_SHORT).show();
                            items.add(item);
                        }

                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewSellItemsActivity.this, "Something went wrong!Try again later", Toast.LENGTH_SHORT).show();
            }
        });
        


    }



    public void openAddItems(){
        Intent intent =new Intent(this, SellitemsActivity.class);
        startActivity(intent);
    }
    private void searchdata(String text){

        rootNode= FirebaseDatabase.getInstance();
        dbReference=rootNode.getReference("Items");

        Query query =dbReference.orderByChild("bSold").equalTo(false);

        items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ItemsAdapter(ViewSellItemsActivity.this,items);
        recyclerView.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    itemFirebase item=dataSnapshot.getValue(itemFirebase.class);
                    if (item.isbSold()==false){
                        if(item.getStudId().contains(MainActivity.getgUsername())){
                            if(item.getItemDesc().toLowerCase().contains(text.toLowerCase())){
                                items.add(item);
                            }
                        }

                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewSellItemsActivity.this, "Something went wrong! Try again later.", Toast.LENGTH_SHORT).show();
            }
                        
        });


        
    }

    private void back(){
        Intent intent =new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}


