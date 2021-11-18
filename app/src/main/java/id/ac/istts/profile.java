package id.ac.istts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class profile extends AppCompatActivity {

    TextView tarik,tv1;
    ImageView iv8;
    user ux;
    Integer idx=0;
    ArrayList<cartItem> carts;
    ArrayList<barang> bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent z = getIntent();
        if(z.hasExtra("user")){
            ux = z.getParcelableExtra("user");
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
        tv1 = findViewById(R.id.tvnama);
        String str = "Username :"+ux.getUsername().toString() +"\n"
                +"Email : "+ux.getEmail().toString()+"\n"
                +"Phone Number : "+ ux.getPhone() +"\n"
                +"Saldo : "+ux.getSaldo();
        tv1.setText(""+str);
        tarik = findViewById(R.id.textView12);
        tarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(profile.this, Withdraw.class);
                c.putExtra("user",ux);
                c.putExtra("idx",idx);
                c.putExtra("barang",bar);
                c.putExtra("cart",carts);
                startActivity(c);
            }
        });
        iv8 = findViewById(R.id.imageView8);
        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(profile.this, homepage.class);
                c.putExtra("user",ux);
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
            Intent z = new Intent(profile.this,cart.class);
            z.putExtra("user",ux);
            z.putExtra("idx",idx);
            z.putExtra("cart",carts);
            z.putExtra("barang",bar);
            startActivity(z);
        }else if(item.getItemId()==R.id.optionlogout){
            Intent z = new Intent(profile.this,login.class);
            z.putExtra("user",ux);
            z.putExtra("cart",carts);
            z.putExtra("barang",bar);
            startActivity(z);
        }
        return super.onOptionsItemSelected(item);
    }
}