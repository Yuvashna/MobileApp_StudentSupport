package codeconverters.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText studId,studName,studEmail,contactNum,studPassword;

    TextView linkLogin;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSubmit=findViewById(R.id.loginbtn);

        studId=findViewById(R.id.username);
        studName=findViewById(R.id.fullname);
        studEmail=findViewById(R.id.email);
        contactNum=findViewById(R.id.edtContactnum);
        studPassword=findViewById(R.id.password);

        linkLogin=findViewById(R.id.textLinkLogin);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Validate.isText(studName.getText().toString()) &&
                Validate.isValidPassword(studPassword.getText().toString()))
                {
                    rootNode=FirebaseDatabase.getInstance();
                    reference=rootNode.getReference("Users");

                    studentFirebase Studhelper=new studentFirebase(studId.getText().toString(),
                            studName.getText().toString(),studEmail.getText().toString(),
                            contactNum.getText().toString(),studPassword.getText().toString());

                    reference.child(studId.getText().toString()).setValue(Studhelper);
                    Toast.makeText(RegisterActivity.this,"You have been added successfully",Toast.LENGTH_SHORT).show();
                    openLogin();
                }else{
                    Toast.makeText(RegisterActivity.this, "Please ensure you have entered your details properly using the correct characters", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "Please ensure your password is 8 to 15 characters!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }
    public void openLogin(){
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}