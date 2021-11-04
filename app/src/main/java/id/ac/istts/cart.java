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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class cart extends AppCompatActivity {

    Button back;
    ArrayList<user> u;
    ArrayList<barang> bar;
    ArrayList<cartItem> carts;
    Integer idx=0,idc = 0;
    RecyclerView rv;
    cartAdapter cad;
    ImageView iv17;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
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
        }if(z.hasExtra("idc")){
            idc= z.getIntExtra("idc",0);
        }
        back = findViewById(R.id.backC);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(cart.this,homepage.class);
                x.putExtra("user",u);
                x.putExtra("idx",idx);
                startActivity(x);
            }
        });
        rv = findViewById(R.id.rvcart);
        cad = new cartAdapter(getApplicationContext(),carts,idc);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(cad);
        iv17 = findViewById(R.id.imageView17);
        iv17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(cart.this, homepage.class);
                c.putExtra("user",u);
                c.putExtra("idx",idx);
                c.putExtra("barang",bar);
                c.putExtra("cart",carts);
                startActivity(c);
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
        }else if(item.getItemId()==R.id.optionlogout){
            Intent z = new Intent(cart.this,login.class);
            z.putExtra("user",u);
            z.putExtra("cart",carts);
            z.putExtra("barang",bar);
            startActivity(z);
        }
        return super.onOptionsItemSelected(item);
    }
}