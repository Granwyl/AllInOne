package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    user u;
    EditText ete,etp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ete = findViewById(R.id.emailL);
        etp = findViewById(R.id.PassL);
        Intent i = getIntent();
        if(i.hasExtra("user")){
            u = i.getParcelableExtra("user");
        }
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ete.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email harus diisi",Toast.LENGTH_SHORT).show();
                }else if(etp.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password harus diisi",Toast.LENGTH_SHORT).show();
                }else{
                    if(u.getEmail().equals(ete.getText().toString())){
                        if(u.getPassword().equals(etp.getText().toString())){
                            Intent x = new Intent(login.this,homepage.class);
                            x.putExtra("user",u);
                            startActivity(x);
                        }
                    }
                }
            }
        });
    }
}