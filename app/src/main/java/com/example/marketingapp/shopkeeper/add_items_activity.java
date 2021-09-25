package com.example.marketingapp.shopkeeper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.marketingapp.R;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.databinding.ActivityAddItemsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class add_items_activity extends AppCompatActivity {

    ActivityAddItemsBinding binding;
    String shopId = " ";

    Uri filepath;
    Bitmap bitmap;
    StorageReference storageReference;
    String photoUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityAddItemsBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        binding.addprodimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.uploadNow.setVisibility(View.VISIBLE);
                binding.imageadded.setVisibility(View.VISIBLE);
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select image"), 1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Shopkeeper");
        storageReference= FirebaseStorage.getInstance().getReference();

        binding.uploadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(add_items_activity.this);
                progressDialog.setTitle("File Uploader");
                progressDialog.show();
                final StorageReference uploader = storageReference.child("Product/" + "img" + System.currentTimeMillis());
                uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                photoUrl=uri.toString();
                                binding.uploadNow.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_task_alt_24));

                                progressDialog.dismiss();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploading : " + (int) percent + "%");
                    }
                });


            }
        });

        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shopkeeper shopkeeper = snapshot.getValue(Shopkeeper.class);
                shopId = shopkeeper.getUniqueId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.addprodctsFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChk()) {
                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Products");
                    String i = getrandomstring(6);
                    model_shopcontent model = new model_shopcontent(i, shopId, photoUrl,
                            binding.prodnamefrag.getEditText().getText().toString(),
                            binding.prodqtavailable.getEditText().getText().toString(),
                            binding.proddesc.getEditText().getText().toString(), "Rs. "+
                            binding.prodprice.getEditText().getText().toString(),
                            binding.prodofferprice.getEditText().getText().toString()+"%");
                    ref1.child(i).setValue(model);
                    Toasty.normal(getApplicationContext(),"Product Image will be Updated Soon...").show();

                }
            }
        });

    }

    private boolean isChk() {
        if (photoUrl.equalsIgnoreCase(""))
        {
            Toasty.error(getApplicationContext(),"Please add an image of Your Product").show();
            return false;
        }
        if (binding.prodnamefrag.getEditText().getText().toString().isEmpty() || binding.prodqtavailable.getEditText().getText().toString().isEmpty()
                || binding.proddesc.getEditText().getText().toString().isEmpty() || binding.prodprice.getEditText().getText().toString().isEmpty() ||
                binding.prodofferprice.getEditText().getText().toString().isEmpty())
        {
            Toasty.error(getApplicationContext(),"Please enter all the fields").show();
            return false;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            InputStream inputStream = null;
            ContentResolver contentResolver = this.getContentResolver();
            try {
                inputStream = contentResolver.openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                binding.imageadded.setVisibility(View.VISIBLE);
                binding.uploadNow.setVisibility(View.VISIBLE);
                binding.imageadded.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getrandomstring(int i) {
        final String chaaracters = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJklMNOPQRSTUV";
        StringBuilder result = new StringBuilder();
        while (i > 0) {
            Random rand = new Random();
            result.append(chaaracters.charAt(rand.nextInt(chaaracters.length())));
            i--;
        }
        return result.toString();
    }


}