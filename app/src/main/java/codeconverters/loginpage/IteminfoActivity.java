package codeconverters.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.CursorWindow;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class IteminfoActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btnContact;

    EditText  edtItemid,edtitemDesc,edtitemPrice;

    ImageView imgView;
    DatabaseReference dbReference;
    FirebaseDatabase rootNode;
    ArrayList<itemFirebase> items;

    String personTo,personInterested,personIEmail;
    String sellerUsername;
    String itemId;
    String sellerEmail;
    String emailAddr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteminfo);
        edtItemid=findViewById(R.id.editTextitemId);
        edtitemDesc=findViewById(R.id.editTextitemDesc);
        edtitemPrice=findViewById(R.id.editTextitemPrice);
        btnBack=findViewById(R.id.imageButtonBack);
        btnContact=findViewById(R.id.buttonContact);
        imgView=findViewById(R.id.imageViewItemImage);

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             itemId= extras.getString("itemId");
             String itemDesc =extras.getString("itemDesc");
             String itemPrice=extras.getString("itemPrice");
             sellerUsername=extras.getString("studId");
             getsellerEmail();

             edtItemid.setText(itemId);
             edtitemDesc.setText(itemDesc);
             edtitemPrice.setText(itemPrice);



             FirebaseStorage storage = FirebaseStorage.getInstance();
             StorageReference storageRef = storage.getReference().child("images/"+itemId);

            final long ONE_MEGABYTE = 1024 * 1024*100;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(IteminfoActivity.this, "Something went wrong. Try again later!", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(this, "Something went wrong. Try again later!", Toast.LENGTH_SHORT).show();
        }

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(IteminfoActivity.this, SendEmailActivity.class);
                intent.putExtra("itemId",edtItemid.getText().toString());
                intent.putExtra("itemDesc",edtitemDesc.getText().toString());
                intent.putExtra("email",sellerEmail);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IteminfoActivity.this,ViewitemsActivity.class);
                startActivity(intent);
            }
        });
    }



    public void getsellerEmail(){
        sellerEmail="";

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("studId").equalTo(sellerUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    sellerEmail=snapshot.child(sellerUsername).child("email").getValue(String.class);

                }else{
                    Toast.makeText(IteminfoActivity.this, "Something went wrong! Try again later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(IteminfoActivity.this, "Try again later...Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }




}