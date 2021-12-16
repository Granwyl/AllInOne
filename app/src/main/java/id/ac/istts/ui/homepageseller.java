package id.ac.istts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONObject;

import java.util.ArrayList;

import id.ac.istts.R;
import id.ac.istts.data.barangAdapter;
import id.ac.istts.data.user;

public class homepageseller extends AppCompatActivity {
    ImageView profile;
    ArrayList<user> u;
    user ux;
    Integer idx=0;
    Integer idc = 0;
    ArrayList<JSONObject> bar=new ArrayList<>();
    RecyclerView rv;
    barangAdapter bad;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepageseller);

        bottomNavigationView= findViewById(R.id.botnavhomepageseller);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.sellernavhome:
                        Intent x = new Intent(homepageseller.this, homepageseller.class);
                        x.putExtra("user",ux);
                        startActivity(x);
                        break;
                    case R.id.sellernavorderlist:
                        /*Intent y = new Intent(homepageseller.this, homepageseller.class);
                        y.putExtra("user",ux);
                        startActivity(y);*/
                        break;
                    case R.id.sellernavaddbarang:
                        Intent as = new Intent(homepageseller.this, AddProduct.class);
                        as.putExtra("user",ux);
                        startActivity(as);
                        break;
                    default:
                        x = new Intent(homepageseller.this, homepageseller.class);
                        x.putExtra("user",ux);
                        startActivity(x);
                        break;
                }

                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentPlaceholder, fragment)
                            .commit();
                }catch (Exception e){
                    Log.e("MainActivity", e.getMessage());
                }
                return true;
            }
        });

        TextView sellerhomepagewelcome=findViewById(R.id.homepagesellerwelcome);
        try {
            sellerhomepagewelcome.setText("Welcome "+MainActivity.user.getString("username"));
        }
        catch (Exception ex)
        {

        }
        Intent z = getIntent();
        if(z.hasExtra("user")){
            ux = z.getParcelableExtra("user");
        }else{
            u = new ArrayList<>();
        }
        if(z.hasExtra("idx")){
            idx = z.getIntExtra("idx",0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.optioncart){
            Intent z = new Intent(homepageseller.this, cart.class);
            z.putExtra("user",ux);
            startActivity(z);
        }else if(item.getItemId()==R.id.optionlogout){
            Intent z = new Intent(homepageseller.this, login.class);
            startActivity(z);
        }else if(item.getItemId()==R.id.optionAdd){
            if(ux.getType().equals("seller")){
                Intent z = new Intent(homepageseller.this, AddProduct.class);
                z.putExtra("user",ux);
                startActivity(z);
            }else{
                Toast.makeText(getApplicationContext(), "Hanya seller yang bisa add product", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}