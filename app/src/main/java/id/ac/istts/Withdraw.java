package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Withdraw extends AppCompatActivity {

    ArrayList<user> u;
    user ux;
    Integer idx=0;
    Connection conn;
    String connresult="";
    TextView tv1,back,dewit;
    EditText nominal,rek;
    int o = 0;
    ArrayList<cartItem> carts;
    ArrayList<barang> bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        tv1 = findViewById(R.id.textView);
        back = findViewById(R.id.backW);
        dewit = findViewById(R.id.withdrawW);
        nominal = findViewById(R.id.editTextNumber3);
        rek = findViewById(R.id.editTextNumber2);
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
        if(z.hasExtra("barang")){
            bar = z.getParcelableArrayListExtra("barang");
        }else{
            bar = new ArrayList<>();
        }
        tv1.setText("Saldo : IDR "+ux.getSaldo());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent z = new Intent(Withdraw.this,profile.class);
                z.putExtra("user",ux);
                z.putExtra("idx",idx);
                startActivity(z);
                finish();
            }
        });
        dewit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rek.getText().toString().isEmpty()){
                    if(rek.getText().length()==10){
                        if(nominal.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"Nominal harus diisi",Toast.LENGTH_SHORT).show();
                        }else{
                            int p = Integer.parseInt(nominal.getText().toString());
                            ux.setSaldo(ux.getSaldo()-p);
                            o = ux.getSaldo();
                            new SaldoAsync(ux, o, getApplicationContext(), new SaldoAsync.AddUserCallback() {
                                @Override
                                public void preExecute() {

                                }

                                @Override
                                public void postExecute() {
                                    tv1.setText("Saldo : IDR "+ux.getSaldo());
                                    rek.setText("");
                                    nominal.setText("");
                                    Toast.makeText(getApplicationContext(),"Withdrawal sebesar "+p+
                                            " berhasil dilakukan",Toast.LENGTH_SHORT).show();
                                }
                            }).execute();




                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"No Rek hanya terdiri dari 10 digit",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"No Rek harus diisi",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


class SaldoAsync{
    private final WeakReference<Context> weakContext;
    private final WeakReference<AddUserCallback> weakCallback;
    private user u1;
    private int ctr;
    SaldoAsync(user u1,int ctr,Context weakContext, AddUserCallback weakCallback) {
        this.weakContext = new WeakReference<>(weakContext);
        this.weakCallback = new WeakReference<>(weakCallback);
        this.u1 = u1;
        this.ctr = ctr;
    }

    void execute(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        weakCallback.get().preExecute();
        executorService.execute(()-> {
            Context context = weakContext.get();
            AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
            user ucheck = appDatabase.userDAO().getUser(u1.getUsername());
            if(ucheck==null){

            }else{
                appDatabase.userDAO().setSaldo(ctr,ucheck.getUsername());
            }
            handler.post(()->{
                weakCallback.get().postExecute();
            });
        });
    }

    interface AddUserCallback{
        void preExecute();
        void postExecute();
    }
}