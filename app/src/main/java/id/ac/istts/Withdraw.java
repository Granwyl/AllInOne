package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Withdraw extends AppCompatActivity {

    user u;

    TextView tv1,back,dewit;
    EditText nominal,rek;
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
            u = i.getParcelableExtra("user");
        }
        tv1.setText("Saldo : IDR "+u.getSaldo());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dewit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rek.getText().toString().isEmpty()){
                    if(rek.getText().length()==10){
                        int p = Integer.parseInt(nominal.getText().toString());
                        u.setSaldo(u.getSaldo()-p);
                        tv1.setText("Saldo : IDR "+u.getSaldo());
                        rek.setText("");
                        nominal.setText("");
                        Toast.makeText(getApplicationContext(),"Withdrawal sebesar "+p+
                                " berhasil dilakukan",Toast.LENGTH_SHORT).show();
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