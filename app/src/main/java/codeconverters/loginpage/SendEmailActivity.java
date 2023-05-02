package codeconverters.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SendEmailActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btnSubmit;
    EditText edtMessage;
    String itemId,itemDesc;
    String message;
    String emailAddr;
    String SellerUsername,sellerEmail;
    String SellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendemail);

        btnBack=findViewById(R.id.imageButtonBack);
        btnSubmit=findViewById(R.id.buttonSend);
        edtMessage=findViewById(R.id.editTextMessage);

        itemId="";
        itemDesc="";


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           //Toast.makeText(this, "Inside extras", Toast.LENGTH_SHORT).show();
            itemId = extras.getString("itemId");
            itemDesc = extras.getString("itemDesc");
            sellerEmail=extras.getString("email");

        }
        emailAddr=sellerEmail+",";

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SendEmailActivity.this, ViewitemsActivity.class);
                startActivity(intent);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message="Good day,\n\n"+edtMessage.getText().toString()+"\n\nYou can also contact me via email"+
                        "\nMany Thanks,\n"+MainActivity.getgPIName();

                    String [] addresses=emailAddr.split(",");
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL,addresses);
                    intent.putExtra(Intent.EXTRA_SUBJECT,"RE: "+itemDesc);
                    intent.putExtra(Intent.EXTRA_TEXT,message);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }else{
                        Toast.makeText(SendEmailActivity.this, "No Email app installed!", Toast.LENGTH_SHORT).show();
                    }


            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,ViewitemsActivity.class);
        startActivity(intent);
    }




}