package id.ac.istts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.ac.istts.R;
import id.ac.istts.data.barang;
import id.ac.istts.data.barangAdapter;
import id.ac.istts.data.cartItem;
import id.ac.istts.data.user;
import id.ac.istts.db.AppDatabaseBarang;

public class homepage extends AppCompatActivity {


    ImageView profile;
    ArrayList<user> u;
    user ux;
    Integer idx=0;
    Integer idc = 0;
    ArrayList<cartItem> carts;
    ArrayList<barang> bar=new ArrayList<>();
    RecyclerView rv;
    barangAdapter bad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent z = getIntent();
        if(z.hasExtra("user")){
            ux = z.getParcelableExtra("user");
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
        new ShowBarangAsync(getApplicationContext(), new ShowBarangAsync.AddBarangCallback() {
            @Override
            public void preExecute() {

            }

            @Override
            public void postExecute(List<barang> barangs) {
                bar.addAll(barangs);
                //if(bar.size()<1)Toast.makeText(getApplicationContext(), "test00", Toast.LENGTH_SHORT).show();
            }
        }).execute();
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(homepage.this, id.ac.istts.ui.profile.class);
                x.putExtra("user",ux);
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
            Intent z = new Intent(homepage.this, cart.class);
            z.putExtra("user",ux);
            startActivity(z);
        }else if(item.getItemId()==R.id.optionlogout){
            Intent z = new Intent(homepage.this, login.class);
            startActivity(z);
        }else if(item.getItemId()==R.id.optionAdd){
            if(ux.getType().equals("seller")){
                Intent z = new Intent(homepage.this, AddProduct.class);
                z.putExtra("user",ux);
                startActivity(z);
            }else{
                Toast.makeText(getApplicationContext(), "Hanya seller yang bisa add product", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
class ShowBarangAsync{
    private final WeakReference<Context> weakContext;
    private final WeakReference<AddBarangCallback> weakCallback;

    ShowBarangAsync(Context weakContext, AddBarangCallback weakCallback) {
        this.weakContext = new WeakReference<>(weakContext);
        this.weakCallback = new WeakReference<>(weakCallback);
    }

    void execute(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        weakCallback.get().preExecute();
        executorService.execute(()->{
            int count = 0;
            Context context = weakContext.get();
            AppDatabaseBarang appDatabaseBarang = AppDatabaseBarang.getAppDatabaseBarang(context);
            List<barang> lb = appDatabaseBarang.barangDAO().getBarangList();

            handler.post(()->{
                weakCallback.get().postExecute(lb);
            });
        });
    }

    interface AddBarangCallback{
        void preExecute();
        void postExecute(List<barang> barangs);
    }
}