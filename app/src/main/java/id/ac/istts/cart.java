package id.ac.istts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cart extends AppCompatActivity {

    Button back,bayar;
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
        cad.setOnItemClickCallback(new cartAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(barang cc, View v) {
                PopupMenu pop = new PopupMenu(getApplicationContext(),v);
                pop.inflate(R.menu.popupcart);
                pop.show();
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.cartDel){
                            for (int i = 0; i < carts.get(idc).getBar().size(); i++) {
                                if(carts.get(idc).getBar().get(i).getNama_barang().equals(cc.getNama_barang())){
                                    carts.get(idc).getBar().remove(i);
                                    cad.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), "Item dihapus dari cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        return true;
                    }
                });
            }
        });
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
        bayar = findViewById(R.id.bayar);
        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < carts.get(idc).getBar().size(); i++) {
                    for (int j = 0; j < u.size(); j++) {
                        if(u.get(j).getEmail().equals(carts.get(idc).getBar().get(i).getId_penjual())){
                            u.get(j).setSaldo(u.get(j).getSaldo()+carts.get(idc).getBar().get(i).getHarga());

                            Toast.makeText(getApplicationContext(), "Barang berhasil dibeli", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                carts.get(idc).setBar(new ArrayList<>());
                cad.notifyDataSetChanged();
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