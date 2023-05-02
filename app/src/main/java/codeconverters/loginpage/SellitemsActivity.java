package codeconverters.loginpage;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.net.Uri;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class SellitemsActivity extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView imageViewPicture;
    Spinner spinnerCategorySell;
    Button btnSelectImage, btnSubmit;
    Switch swtchDonate;
    Bitmap selectedImageBitmap;
    ImageButton btnBack;
    Uri selectedImageUri;

    StorageReference storageReference;

    EditText edtPrice, edtItemDesc;
    Double dprice;
    boolean bSell, bDonate;

    private final int PICK_IMAGE_REQUEST = 22;
    private String filePathImage;
    Uri imageUri;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellitems);

        spinnerCategorySell = findViewById(R.id.spinnerCategorySell);
        btnSelectImage = findViewById(R.id.ButtonSelectImage);
        imageViewPicture = findViewById(R.id.ImageViewPicture);
        btnBack = findViewById(R.id.imageButtonBack);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SellitemsActivity.this, "Redirecting...", Toast.LENGTH_SHORT).show();
                SelectImage();
            }
        });

        edtPrice = findViewById(R.id.editTextPrice);
        edtItemDesc = findViewById(R.id.edtItemDescr);

        swtchDonate = findViewById(R.id.switchDonate);
        swtchDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swtchDonate.isChecked()) {
                    edtPrice.setVisibility(View.VISIBLE);
                } else {
                    edtPrice.setVisibility(View.INVISIBLE);
                }
            }
        });


        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Items");
                itemFirebase itemhelper;

                if (spinnerCategorySell.getSelectedItemPosition()==0){
                    Toast.makeText(SellitemsActivity.this, "Please select a category!!", Toast.LENGTH_SHORT).show();
                }else{
                    uploadImage();

                    if (edtPrice.getVisibility()==View.INVISIBLE){
                         itemhelper=new itemFirebase(fileName,edtItemDesc.getText().toString(),spinnerCategorySell.getSelectedItem().toString(),true,
                                true,false,0.00,fileName,MainActivity.getgUsername());
                    }else{
                        double price = Double.valueOf(edtPrice.getText().toString());
                        Toast.makeText(SellitemsActivity.this,edtPrice.getText().toString(), Toast.LENGTH_SHORT).show();
                        itemhelper=new itemFirebase(fileName,edtItemDesc.getText().toString(),spinnerCategorySell.getSelectedItem().toString(),true,
                                false,false,price,fileName, MainActivity.getgUsername());
                    }

                    reference.child(fileName).setValue(itemhelper);
                    Toast.makeText(SellitemsActivity.this,"Item added successfully",Toast.LENGTH_SHORT).show();
                    openviewPersonalitems();
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

    private void uploadImage() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
        Date now = new Date();
        fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        filePathImage=uri.toString();
                        Toast.makeText(SellitemsActivity.this, "Image uploaded to database", Toast.LENGTH_SHORT).show();
                    }
                });
                imageViewPicture.setImageURI(null);
                //Toast.makeText(SellitemsActivity.this, "Picture Successfully uploaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SellitemsActivity.this, "Failed! Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        selectedImageUri = data.getData();

                        selectedImageBitmap = BitmapFactory.decodeResource(this.getResources(),
                                R.drawable.baseline_hide_image_24);
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageViewPicture.setImageBitmap(selectedImageBitmap);
                    }
                }
            });

    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {

            imageUri = data.getData();
            imageViewPicture.setImageURI(imageUri);

        }
    }



    public void openviewPersonalitems() {
        //open Personal items page
        Intent intent = new Intent(this, ViewSellItemsActivity.class);
        startActivity(intent);
    }

    public void back() {
        Intent intent = new Intent(this, ViewSellItemsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,ViewSellItemsActivity.class);
        startActivity(intent);
    }


}