package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    Connection conn;
    String connresult="";
    Button reg;
    EditText etn,etp,ete,phone;
    String TempPass,type="";
    ArrayList<user> au;
    ArrayList<cartItem> carts;
    ArrayList<barang> bar;
    RadioGroup rg;
    RadioButton rbsell;
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
        TempPass = etp.getText().toString();
        Intent z = getIntent();
        if(z.hasExtra("user")){
            au = z.getParcelableArrayListExtra("user");
        }else{
            au = new ArrayList<>();
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
            else{
                for (int i = 0; i < au.size(); i++) {
                    if(au.get(i).getUsername().toString().equals(etn.getText().toString())){
                        b = false;
                        Toast.makeText(getApplicationContext(),"Username sudah dipakai",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            if(b){
                user u = new user(etn.getText().toString(),ete.getText().toString(),etp.getText().toString(),type,
                        Integer.parseInt(phone.getText().toString()),500000);
                au.add(u);
                cartItem cm = new cartItem();
                cm.setUser(ete.getText().toString());
                carts.add(cm);
                //getTextfromSql(view);
                Intent i = new Intent(MainActivity.this,login.class);
                i.putExtra("user",au);
                i.putExtra("barang",bar);
                i.putExtra("cart",carts);
                startActivity(i);
            }

            }
        });
    }

    public void getTextfromSql(View view){
        EditText usernameTemp = findViewById(R.id.username);
        EditText emailTemp = findViewById(R.id.email);
        String usrTemp = etn.getText().toString();
        String noTelp = phone.getText().toString();
        String aTemp = ete.getText().toString();
        try {
            ConnHelper connhelper = new ConnHelper();
            conn = connhelper.connclass();
            if (conn!=null){
            String query= "select * from users where username ="+usrTemp+"or email ="+aTemp+";";
                Statement st= conn.createStatement();
                ResultSet rs= st.executeQuery(query);

                if (rs.next()){
                    Toast.makeText(getApplicationContext(),"Gagal Register, Akun sudah Terdaftar", Toast.LENGTH_SHORT).show();
                }else{
                    int temp = new Random().nextInt(100);
                    String q1 = "insert into users values ("+temp+","+usrTemp+","+TempPass+","+aTemp+","+noTelp+","+500000+")";
                    Statement esteh= conn.createStatement();
                    ResultSet rus= esteh.executeQuery(q1);
                    Toast.makeText(getApplicationContext(),"Akun sukses Terdaftar", Toast.LENGTH_SHORT).show();
                }
            }else {
                connresult= "check connection";
            }
        }catch (Exception ex){

        }
    }

    public void login_klik(View v){
        Intent i = new Intent(MainActivity.this, login.class);
        i.putExtra("user",au);
        i.putExtra("barang",bar);
        i.putExtra("cart",carts);
        startActivity(i);
    }
}