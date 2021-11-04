package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class Dtransaksi extends AppCompatActivity {

    ArrayList<cartItem> carts;
    ArrayList<barang> bar;
    ArrayList<user> u;
    int idx = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtransaksi);
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
    }
}