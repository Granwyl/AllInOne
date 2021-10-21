package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Withdraw extends AppCompatActivity {

    ArrayList<user> u;
    Integer idx=0;
    Connection conn;
    String connresult="";
    TextView tv1,back,dewit;
    EditText nominal,rek;
    int o = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        tv1 = findViewById(R.id.textView);
        back = findViewById(R.id.backW);
        dewit = findViewById(R.id.withdrawW);
        nominal = findViewById(R.id.editTextNumber3);
        rek = findViewById(R.id.editTextNumber2);
        Intent i = getIntent();
        if(i.hasExtra("user")){
            u = i.getParcelableArrayListExtra("user");
        }else{
            u = new ArrayList<>();
        }
        if(i.hasExtra("idx")){
            idx = i.getIntExtra("idx",0);
        }
        tv1.setText("Saldo : IDR "+u.get(idx).getSaldo());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent z = new Intent(Withdraw.this,profile.class);
                z.putExtra("user",u);
                z.putExtra("idx",idx);
                startActivity(z);
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
                            u.get(idx).setSaldo(u.get(idx).getSaldo()-p);
                            o = u.get(idx).getSaldo();
                            //getTextfromSql(view);
                            tv1.setText("Saldo : IDR "+u.get(idx).getSaldo());
                            rek.setText("");
                            nominal.setText("");
                            Toast.makeText(getApplicationContext(),"Withdrawal sebesar "+p+
                                    " berhasil dilakukan",Toast.LENGTH_SHORT).show();
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
    public void getTextfromSql(View view){
        try {
            ConnHelper connhelper = new ConnHelper();
            conn = connhelper.connclass();
            if (conn!=null){
                String query= "update users set saldo = "+o+" where username = "+u.get(idx).getUsername()+";";
                Statement st= conn.createStatement();
                ResultSet rs= st.executeQuery(query);
            }else {
                connresult= "check connection";
            }
        }catch (Exception ex){

        }
    }
}