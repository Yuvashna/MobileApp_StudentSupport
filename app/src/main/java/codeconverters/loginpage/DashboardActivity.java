package codeconverters.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    Button btnBuy,btnSell;
    TextView textViewLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnBuy=findViewById(R.id.buttonBuy);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this,"Loading Items...",Toast.LENGTH_SHORT).show();
                openViewItems();
            }
        });

        btnSell=findViewById(R.id.buttonSell);
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this,"Loading Personal Items on Sale",Toast.LENGTH_SHORT).show();
                openSellitems();
            }
        });

        textViewLogOut=findViewById(R.id.logout);
        textViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                logout();
            }
        });

    }

    public void openViewItems(){
        Intent intent =new Intent(this, ViewitemsActivity.class);
        startActivity(intent);
    }

    public void openSellitems(){
        Intent intent =new Intent(this, ViewSellItemsActivity.class);
        startActivity(intent);
    }

    public void logout(){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}