package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button reg;
    EditText etn,etp,ete,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg = findViewById(R.id.register);
        etn = findViewById(R.id.username);
        etp = findViewById(R.id.password);
        ete = findViewById(R.id.email);
        phone = findViewById(R.id.editTextNumber);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            boolean b = false;
            if(etn.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Username harus diisi",Toast.LENGTH_SHORT).show();
            }
            else if(etp.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Password harus diisi",Toast.LENGTH_SHORT).show();
            }
            else if(ete.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Email harus diisi",Toast.LENGTH_SHORT).show();
            }
            else if(phone.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Phone number harus diisi",Toast.LENGTH_SHORT).show();
            }
            else{
                b = true;
            }
            if(b==true){
                user u = new user(etn.getText().toString(),ete.getText().toString(),etp.getText().toString(),
                        Integer.parseInt(phone.getText().toString()),50000);
                Intent i = new Intent(MainActivity.this,login.class);
                i.putExtra("user",u);
                startActivity(i);
            }

            }
        });
    }

    public void login_klik(View v){
        Intent i = new Intent(MainActivity.this, login.class);
        startActivity(i);
    }
}