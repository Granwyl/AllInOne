package id.ac.istts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.ac.istts.InternetTask;
import id.ac.istts.R;
import id.ac.istts.TResponse;
import id.ac.istts.data.cartItem;
import id.ac.istts.data.barang;
import id.ac.istts.data.user;
import id.ac.istts.db.AppDatabase;
import id.ac.istts.general;

public class MainActivity extends AppCompatActivity {
    Button reg;
    EditText etn,etp,ete,phone;
    String TempPass,type="";
    ArrayList<user> au;
    ArrayList<cartItem> carts;
    ArrayList<barang> bar;
    RadioGroup rg;
    RadioButton rbsell;

    public static JSONObject user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg = findViewById(R.id.register);
        etn = findViewById(R.id.username);
        etp = findViewById(R.id.password);
        ete = findViewById(R.id.email);
        rg = findViewById(R.id.rg);
        rbsell = findViewById(R.id.rbseller);
        phone = findViewById(R.id.editTextNumber);
        try {
            //phone.setText(user.getString("username"));
        }
        catch (Exception ex)
        {

        }
        TempPass = etp.getText().toString();
        Intent z = getIntent();
        if(z.hasExtra("user")){
            au = z.getParcelableArrayListExtra("user");
        }else{
            au = new ArrayList<>();
            user uu = new user("jensen","jensen","123","seller",123456,100000);
            au.add(uu);
            uu = new user("polka","polka","123","seller",123456,100000);
            au.add(uu);
            uu = new user("konis","konis","123","seller",123456,100000);
            au.add(uu);
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
            barang bb = new barang("dummy1","dummy#1","jensen","burger",30000,10);
            bar.add(bb);
            bb = new barang("dummy2","dummy#2","polka","batu",25000,10);
            bar.add(bb);
            bb = new barang("dummy3","dummy#3","konis","cloud",20000,10);
            bar.add(bb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbseller){
                    type = "seller";
                }else{
                    type = "buyer";
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            boolean b = true;
            if(etn.getText().toString().isEmpty()){
                b = false;
                Toast.makeText(getApplicationContext(),"Username harus diisi",Toast.LENGTH_SHORT).show();
            }
            else if(etp.getText().toString().isEmpty()){
                b = false;
                Toast.makeText(getApplicationContext(),"Password harus diisi",Toast.LENGTH_SHORT).show();
            }
            else if(ete.getText().toString().isEmpty()){
                b = false;
                Toast.makeText(getApplicationContext(),"Email harus diisi",Toast.LENGTH_SHORT).show();
            }
            else if(phone.getText().toString().isEmpty()){
                b = false;
                Toast.makeText(getApplicationContext(),"Phone number harus diisi",Toast.LENGTH_SHORT).show();
            }else if(type.equals("")){
                b = false;
                Toast.makeText(getApplicationContext(), "Jenis user perlu dipilih", Toast.LENGTH_SHORT).show();
            }

            if(b){
                try {

                    TResponse ar=new TResponse() {
                        @Override
                        public void execute(String s) {
                            try
                            {
                                JSONObject hasil=new JSONObject(s);
                                if (hasil.getString("hasil").equals("ok"))
                                {
                                    Toast.makeText(MainActivity.this,"Register berhasil dilakukan",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(MainActivity.this,"Username sudah digunakan",Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception ex)
                            {
                                Toast.makeText(getApplicationContext(),"Username dan password tidak ditemukan",Toast.LENGTH_LONG).show();
                                ex.printStackTrace();
                            }
                        }
                    };


                    String username= URLEncoder.encode(etn.getText().toString(),"utf-8");
                    String password= URLEncoder.encode(etp.getText().toString(),"utf-8");
                    String email= URLEncoder.encode(ete.getText().toString(),"utf-8");
                    String xphone= URLEncoder.encode(phone.getText().toString(),"utf-8");
                    type= URLEncoder.encode(type,"utf-8");
                    InternetTask nt=new InternetTask(MainActivity.this, "Informasi", "Melakukan Register", ar,false,null);

                    String url= general.ip+"checkregister.php?username="+username+"&password="+password+"&email="+email+"&phone="+xphone+"&type="+type;
                    System.out.println(url);
                    nt.execute(url);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    System.out.println("gagal");
                }
            }

            }
        });
    }

    public void login_klik(View v){
        Intent i = new Intent(MainActivity.this, login.class);
        i.putExtra("user",au);
        i.putExtra("barang",bar);
        i.putExtra("cart",carts);
        startActivity(i);
    }
}

class AddUserAsync{
    private final WeakReference<Context> weakContext;
    private final WeakReference<AddUserCallback> weakCallback;
    private user u1;
    AddUserAsync(user u1,Context weakContext, AddUserCallback weakCallback) {
        this.weakContext = new WeakReference<>(weakContext);
        this.weakCallback = new WeakReference<>(weakCallback);
        this.u1 = u1;
    }

    void execute(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        weakCallback.get().preExecute();
        executorService.execute(()-> {
            Context context = weakContext.get();
            AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
            user ucheck = appDatabase.userDAO().getUser(u1.getUsername());
            String temp = "Berhasil add user";
            if(ucheck==null){
                appDatabase.userDAO().insert(u1);
            }else{
                temp = "Username sudah terpakai";
            }

            String finalTemp = temp;
            handler.post(()->{
                weakCallback.get().postExecute(finalTemp);
            });
        });
    }

    interface AddUserCallback{
        void preExecute();
        void postExecute(String msg);
    }
}