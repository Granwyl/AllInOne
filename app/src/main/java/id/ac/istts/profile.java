package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class profile extends AppCompatActivity {

    TextView tarik,tv1;
    user u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i = getIntent();
        if(i.hasExtra("user")){
            u = i.getParcelableExtra("user");
        }
        tv1 = findViewById(R.id.tvnama);
        String str = "Username :"+u.getUsername().toString() +"\n"
                +"Email : "+u.getEmail().toString()+"\n"
                +"Phone Number : "+ u.getPhone() +"\n"
                +"Saldo : "+u.getSaldo();
        tv1.setText(""+str);
        tarik = findViewById(R.id.textView12);
        tarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(profile.this, Withdraw.class);
                c.putExtra("user",u);
                startActivity(c);
            }
        });
    }
}