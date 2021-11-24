package id.ac.istts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import id.ac.istts.R;
import id.ac.istts.data.barang;
import id.ac.istts.data.cartItem;
import id.ac.istts.data.user;

public class adminpage extends AppCompatActivity {

    Button bexit;
    ArrayList<user> u;
    ArrayList<barang> bar;
    ArrayList<cartItem> carts;
    Integer idx = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
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
        bexit = findViewById(R.id.button2);
        bexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent z = new Intent(adminpage.this, login.class);
                z.putExtra("user",u);
                z.putExtra("cart",carts);
                z.putExtra("barang",bar);
                startActivity(z);
            }
        });
    }
}