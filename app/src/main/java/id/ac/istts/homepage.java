package id.ac.istts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.sql.Connection;
import java.util.ArrayList;

public class homepage extends AppCompatActivity {


    ImageView profile;
    ArrayList<user> u;
    Integer idx=0;
    Integer idc = 0;
    ArrayList<cartItem> carts;
    ArrayList<barang> bar;
    RecyclerView rv;
    barangAdapter bad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent z = getIntent();
        if(z.hasExtra("user")){
            u = z.getParcelableArrayListExtra("user");
        }else{
            u = new ArrayList<>();
        }
        if(z.hasExtra("idx")){
            idx = z.getIntExtra("idx",0);
        }
        if(z.hasExtra("cart")){
            carts = z.getParcelableArrayListExtra("cart");
        }else{
            carts = new ArrayList<>();
        }
        if(z.hasExtra("barang")){
            bar = z.getParcelableArrayListExtra("barang");
        }else{
            bar = new ArrayList<>();
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
        rv = findViewById(R.id.rvhomepage);
        bad = new barangAdapter(getApplicationContext(),bar);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(bad);
        bad.setOnItemClickCallback(new barangAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(barang bb, View v) {
                PopupMenu pop = new PopupMenu(getApplicationContext(),v);
                pop.inflate(R.menu.popuphomepage);
                pop.show();
                final barang bx = bb;
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.addcart){
                            ArrayList<barang> btemp = new ArrayList<>();
                            btemp.add(bx);
                            for (int i = 0; i < carts.size(); i++) {
                                //Toast.makeText(getApplicationContext(), carts.get(i).getUser(), Toast.LENGTH_SHORT).show();
                                if(carts.get(i).getUser().equals(u.get(idx).getEmail())){
                                    carts.get(i).setBar(btemp);
                                    idc = i;
                                    Toast.makeText(getApplicationContext(), "Item ditambah ke cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        return true;
                    }
                });
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
            z.putExtra("cart",carts);
            z.putExtra("barang",bar);
            z.putExtra("idc",idc);
            startActivity(z);
        }else if(item.getItemId()==R.id.optionlogout){
            Intent z = new Intent(homepage.this,login.class);
            z.putExtra("user",u);
            z.putExtra("cart",carts);
            z.putExtra("barang",bar);
            startActivity(z);
        }
        return super.onOptionsItemSelected(item);
    }
}