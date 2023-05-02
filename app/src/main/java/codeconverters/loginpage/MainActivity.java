package codeconverters.loginpage;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    static String gUsername;
    static String gPIName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);


       MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {

               Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();

               Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("studId").equalTo(username.getText().toString());
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String systemPassword= snapshot.child(username.getText().toString()).child("password").getValue(String.class);
                            if (systemPassword.equals(password.getText().toString())){
                                Toast.makeText(MainActivity.this, "Logging in....", Toast.LENGTH_SHORT).show();
                                gUsername=username.getText().toString();
                                gPIName=snapshot.child(username.getText().toString()).child("studName").getValue(String.class);
                                openDashboard();
                            }else{
                                Toast.makeText(MainActivity.this, "Please ensure you have entered the correct password!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Please ensure you enter the correct username!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Try again later...Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        TextView edtForgotPsswrd = findViewById(R.id.forgotpass);

        edtForgotPsswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Loading...",Toast.LENGTH_SHORT).show();
                openForgotPasswrd();
            }
        });

        TextView edtRegister = findViewById(R.id.registerLink);

        edtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Redirecting to Register...",Toast.LENGTH_SHORT).show();
                openRegister();
            }
        });


    }

    public void openDashboard(){
        Intent intent =new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void openRegister(){
        Intent intent =new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openForgotPasswrd(){
        Intent intent =new Intent(this, ForgotpasswrdActivity.class);
        startActivity(intent);
    }

    public static String getgUsername(){
        return gUsername;
    }

    public static String getgPIName() {
        return gPIName;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
