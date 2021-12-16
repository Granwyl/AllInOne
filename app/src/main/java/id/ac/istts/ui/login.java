package id.ac.istts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.ac.istts.InternetTask;
import id.ac.istts.TResponse;
import id.ac.istts.data.ConnHelper;
import id.ac.istts.R;
import id.ac.istts.data.barang;
import id.ac.istts.data.cartItem;
import id.ac.istts.data.user;
import id.ac.istts.db.AppDatabase;
import id.ac.istts.general;


public class login extends AppCompatActivity {
    Connection conn;
    String connResult="";
    ArrayList<user> u;
    EditText ete,etp;
    Button breg;
    ArrayList<cartItem> carts;
    ArrayList<barang> bar;
     //val client = OkHttpClient()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ete = findViewById(R.id.emailL);
        etp = findViewById(R.id.PassL);
        Intent z = getIntent();
        if(z.hasExtra("user")){
            u = z.getParcelableArrayListExtra("user");
        }else{
            u = new ArrayList<>();
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
        breg = findViewById(R.id.breg);
        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(login.this, MainActivity.class);
                x.putExtra("user",u);
                x.putExtra("barang",bar);
                x.putExtra("cart",carts);
                startActivity(x);
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ete.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email harus diisi",Toast.LENGTH_SHORT).show();
                }else if(etp.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password harus diisi",Toast.LENGTH_SHORT).show();
                }else if(ete.getText().toString().equalsIgnoreCase("admin")&&
                        etp.getText().toString().equalsIgnoreCase("admin")){
                    Intent x = new Intent(login.this, adminpage.class);
                    x.putExtra("user",u);
                    startActivity(x);
                }
                else{
                    //gettextfromSQL(view);
                    /*
                    new LoginUserAsync(ete.getText().toString(),etp.getText().toString(), getApplicationContext(), new LoginUserAsync.AddUserCallback() {
                        @Override
                        public void preExecute() {

                        }

                        @Override
                        public void postExecute(user udb,boolean bt) {
                            if(udb==null){
                                Toast.makeText(getApplicationContext(), "User belum terdaftar", Toast.LENGTH_SHORT).show();
                            }else if(bt){
                                Intent x = new Intent(login.this, homepage.class);
                                x.putExtra("user",udb);
                                startActivity(x);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Password salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).execute();*/


                    String username="";
                    String password="";
                    try {
                         username= URLEncoder.encode(ete.getText().toString(),"utf-8");
                         password=URLEncoder.encode(etp.getText().toString(),"utf-8");
                    }
                    catch (Exception ex)
                    {

                    }


                    try {

                        TResponse ar=new TResponse() {
                            @Override
                            public void execute(String s) {
                                try
                                {
                                    JSONObject hasil=new JSONObject(s);
                                    System.out.println("abc");
                                    if (hasil.getString("result").equals("no"))
                                    {
                                        Toast.makeText(login.this,"Gagal login",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        MainActivity.user=hasil.getJSONObject("user");
                                        if (MainActivity.user.getString("type").equals("seller"))
                                        {
                                            Intent it=new Intent(getApplicationContext(), homepageseller.class);
                                            startActivity(it);
                                        }
                                        else {
                                            Intent it=new Intent(getApplicationContext(), homepage.class);
                                            startActivity(it);
                                        }

                                    }
                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(getApplicationContext(),"Username dan password tidak ditemukan",Toast.LENGTH_LONG).show();
                                    ex.printStackTrace();
                                }
                            }
                        };
                        InternetTask nt=new InternetTask(login.this, "Informasi", "Melakukan Login", ar,false,null);

                        //String username= URLEncoder.encode(etUsername.getText().toString(),"utf-8");
                        //String password= URLEncoder.encode(etPassword.getText().toString(),"utf-8");

                        //MainActivity.loadToken(getApplicationContext());
                        //String tokenS=URLEncoder.encode(MainActivity.token,"utf-8");
                        //url=InternetTask.ip+"api/checklogin.php?email="+username+"&password="+password+"&token="+tokenS;
                        String url=general.ip+"/checklogin.php?username="+username+"&password="+password;
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

    public void gettextfromSQL(View view){
        String tempEmail = ete.getText().toString();
        String tempPass = etp.getText().toString();
        try {
            ConnHelper connhelper = new ConnHelper();
            conn = connhelper.connclass();
            if (conn!=null){
                String query= "select * from users where email = "+tempEmail+" and password = "+tempPass+";";
                Statement st= conn.createStatement();
                ResultSet rs= st.executeQuery(query);

                while (rs.next()){
//                    Toast.makeText(getApplicationContext(),"Gagal Register, Akun sudah Terdaftar", Toast.LENGTH_SHORT).show();

                    String t2= rs.getString(1);
                    String t3= rs.getString(2);
                    String t4= rs.getString(3);
                    String t5= rs.getString(4);
                    String t6 = rs.getString(5);
                    //user ut = new user(t2,t4,t3,Integer.parseInt(t5),Integer.parseInt(t6));
                   // u.add(ut);
                }
            }else {
                connResult= "check connection";
            }
        }catch (Exception ex){

        }
    }
}

class LoginUserAsync{
    private final WeakReference<Context> weakContext;
    private final WeakReference<AddUserCallback> weakCallback;
    private String u1,u2;
    LoginUserAsync(String u1,String u2,Context weakContext, AddUserCallback weakCallback) {
        this.weakContext = new WeakReference<>(weakContext);
        this.weakCallback = new WeakReference<>(weakCallback);
        this.u1 = u1;
        this.u2 = u2;
    }

    void execute(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        weakCallback.get().preExecute();
        executorService.execute(()-> {
            boolean tempbool = false;
            Context context = weakContext.get();
            AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
            user ucheck = appDatabase.userDAO().getUserbyemail(u1);
            if(ucheck.getPassword().equals(u2)){
                tempbool = true;
            }


            boolean finalTempbool = tempbool;
            handler.post(()->{
                weakCallback.get().postExecute(ucheck,finalTempbool);
            });
        });
    }

    interface AddUserCallback{
        void preExecute();
        void postExecute(user udb,boolean bt);
    }
}