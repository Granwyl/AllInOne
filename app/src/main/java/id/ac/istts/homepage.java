package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class homepage extends AppCompatActivity {

    ImageView profile;
    user u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent i = getIntent();
        if(i.hasExtra("user")){
            u = i.getParcelableExtra("user");
        }
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(homepage.this, profile.class);
                x.putExtra("user",u);
                startActivity(x);
            }
        });
    }
}