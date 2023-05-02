package codeconverters.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotpasswrdActivity extends AppCompatActivity {

    EditText edtToEmailAddr,edtPassword,edtUsername;

    Button btnReset;
    TextView textlogin;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpasswrd);

        edtToEmailAddr=findViewById(R.id.resetEmailaddr);
        edtUsername=findViewById(R.id.resetUsername);
        edtPassword=findViewById(R.id.resetPassword);
        btnReset=findViewById(R.id.resetpassbtn);


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean bValidPassword=Validate.isValidPassword(edtPassword.getText().toString());

                if ((edtUsername.getText().toString() != "") && (bValidPassword==true)){
                    rootNode=FirebaseDatabase.getInstance();
                    reference=rootNode.getReference("Users");

                    Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("studId").equalTo(edtUsername.getText().toString());
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String systememail= snapshot.child(edtUsername.getText().toString()).child("email").getValue(String.class);
                                if (systememail.equals(edtToEmailAddr.getText().toString())){
                                    reference.child(edtUsername.getText().toString()).child("password").setValue(edtPassword.getText().toString());
                                    Toast.makeText(ForgotpasswrdActivity.this, "Successfully changed!", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(ForgotpasswrdActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(ForgotpasswrdActivity.this, "Please ensure you have entered the correct email!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(ForgotpasswrdActivity.this, "Please ensure you enter the correct username!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ForgotpasswrdActivity.this, "Try again later...Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(ForgotpasswrdActivity.this, "Please ensure you have entered the details correctly!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ForgotpasswrdActivity.this, "Enter in a password of 8 to 15 characters!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        textlogin=findViewById(R.id.loginLinkForgotpass);
        textlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotpasswrdActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });




    }
}