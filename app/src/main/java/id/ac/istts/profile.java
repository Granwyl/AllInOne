package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class profile extends AppCompatActivity {

    TextView tarik,tv1;
    ArrayList<user> u;
    Integer idx=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i = getIntent();
        if(i.hasExtra("user")){
            u = i.getParcelableArrayListExtra("user");
        }else {
            u = new ArrayList<>();
        }if(i.hasExtra("idx")){
            idx = i.getIntExtra("idx",0);
        }
        tv1 = findViewById(R.id.tvnama);
        String str = "Username :"+u.get(idx).getUsername().toString() +"\n"
                +"Email : "+u.get(idx).getEmail().toString()+"\n"
                +"Phone Number : "+ u.get(idx).getPhone() +"\n"
                +"Saldo : "+u.get(idx).getSaldo();
        tv1.setText(""+str);
        tarik = findViewById(R.id.textView12);
        tarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(profile.this, Withdraw.class);
                c.putExtra("user",u);
                c.putExtra("idx",idx);
                startActivity(c);
            }
        });
    }
}