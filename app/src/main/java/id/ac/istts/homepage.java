package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.sql.Connection;
import java.util.ArrayList;

public class homepage extends AppCompatActivity {


    ImageView profile;
    ArrayList<user> u;
    Integer idx=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent i = getIntent();
        if(i.hasExtra("user")){
            u = i.getParcelableArrayListExtra("user");
        }else{
            u = new ArrayList<>();
        }
        if(i.hasExtra("idx")){
            idx = i.getIntExtra("idx",0);
        }
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(homepage.this, profile.class);
                x.putExtra("user",u);
                x.putExtra("idx",idx);
                startActivity(x);
            }
        });
    }
}