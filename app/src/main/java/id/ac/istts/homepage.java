package id.ac.istts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.optioncart){
            Intent z = new Intent(homepage.this,cart.class);
            z.putExtra("user",u);
            z.putExtra("idx",idx);
            startActivity(z);
        }
        return super.onOptionsItemSelected(item);
    }
}