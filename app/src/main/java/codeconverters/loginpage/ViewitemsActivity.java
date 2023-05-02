package codeconverters.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class ViewitemsActivity extends AppCompatActivity implements MyAdapter.OnInfoListener {
        //implements AdapterView.OnItemSelectedListener

    Spinner spinnerCategory,spinnerFilter;
    EditText edtSearch;
    ImageButton btnSearch,btnBack;
    static String ItemcodeAtPos;
    RecyclerView recyclerView;
    ArrayList<String> itemId;
    ArrayList<String> itemDesc;
    ArrayList<String> itemPrice;
    ArrayList<itemFirebase> items;
    SearchView searchView;
    List<itemFirebase> itemList;
    DatabaseReference dbReference;
    FirebaseDatabase rootNode;
    ValueEventListener eventListener;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewitems);

        edtSearch=findViewById(R.id.editTextSearch);
        btnSearch=findViewById(R.id.imageButtonSearch);
        btnBack=findViewById(R.id.imageButtonBack);
        recyclerView=findViewById(R.id.RecyclerViewItems);

        showData();



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validate.isText(edtSearch.getText().toString())){
                    searchdata(edtSearch.getText().toString());
                }else{
                    Toast.makeText(ViewitemsActivity.this, "Please enter in letters only in the search box!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });


    }

    private void searchdata(String text){
        rootNode= FirebaseDatabase.getInstance();
        dbReference=rootNode.getReference("Items");

        Query query =dbReference.orderByChild("bSold").equalTo(false);

        items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(ViewitemsActivity.this,items);
        recyclerView.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    itemFirebase item=dataSnapshot.getValue(itemFirebase.class);
                    if (item.isbSold()==false){
                        if(item.getItemDesc().toLowerCase().contains(text.toLowerCase())){
                                items.add(item);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    private void back(){
        Intent intent =new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public static String getItemcodeAtPos() {
        return ItemcodeAtPos;
    }

    @Override
    public void onBtnClick(int pos) {
        Toast.makeText(this, "Info clicked", Toast.LENGTH_SHORT).show();
       Intent intent =new Intent(this,IteminfoActivity.class);
       //ItemcodeAtPos=itemId.get(pos);
       //Toast.makeText(this, "View Page: "+itemId.get(pos), Toast.LENGTH_SHORT).show();
       startActivity(intent);
    }


    private void showData(){

        rootNode= FirebaseDatabase.getInstance();
        dbReference=rootNode.getReference("Items");

        Query query =dbReference.orderByChild("bSold").equalTo(false);

        items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(ViewitemsActivity.this,items);
        recyclerView.setAdapter(adapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(ViewitemsActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    itemFirebase item=dataSnapshot.getValue(itemFirebase.class);
                    if (item.isbSold()==false){
                        items.add(item);
                    }
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewitemsActivity.this, "Something went wrong!Try again later", Toast.LENGTH_SHORT).show();
            }
        });

        

    }

}