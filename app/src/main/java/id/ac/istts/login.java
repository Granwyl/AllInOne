package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class login extends AppCompatActivity {
    Connection conn;
    String connResult="";
    ArrayList<user> u;
    EditText ete,etp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ete = findViewById(R.id.emailL);
        etp = findViewById(R.id.PassL);
        Intent i = getIntent();
        if(i.hasExtra("user")){
            u = i.getParcelableArrayListExtra("user");
        }else{
            u = new ArrayList<>();
        }
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ete.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email harus diisi",Toast.LENGTH_SHORT).show();
                }else if(etp.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password harus diisi",Toast.LENGTH_SHORT).show();
                }else{
                    //gettextfromSQL(view);
                    for (int i = 0; i < u.size(); i++) {
                        if(u.get(i).getEmail().equals(ete.getText().toString())){
                            if(u.get(i).getPassword().equals(etp.getText().toString())){
                                Intent x = new Intent(login.this,homepage.class);
                                x.putExtra("user",u);
                                x.putExtra("idx",i);
                                startActivity(x);
                            }
                        }
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
                    user ut = new user(t2,t4,t3,Integer.parseInt(t5),Integer.parseInt(t6));
                    u.add(ut);
                }
            }else {
                connResult= "check connection";
            }
        }catch (Exception ex){

        }
    }
}