package com.example.data_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ImageDeleteActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView imageNameTextView;
    private Button deleteButton;
    private String imageUrl;
    private String imageName;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageView = findViewById(R.id.image_view_detail);
        imageNameTextView = findViewById(R.id.image_name_detail);
        deleteButton = findViewById(R.id.delete_button);


        imageUrl = getIntent().getStringExtra("imageUrl");
        imageName = getIntent().getStringExtra("imageName");


        Picasso.get().load(imageUrl).into(imageView);
        imageNameTextView.setText(imageName);


        databaseReference = FirebaseDatabase.getInstance().getReference("Images");


        deleteButton.setOnClickListener(v -> deleteImage());
    }

    private void deleteImage() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userImagesRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Images");

        userImagesRef.child(imageName).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ImageDeleteActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
