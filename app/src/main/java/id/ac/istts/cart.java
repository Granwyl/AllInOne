package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class cart extends AppCompatActivity {

    Button back;
    ArrayList<user> u;
    Integer idx=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent z = getIntent();
        if(z.hasExtra("user")){
            u = z.getParcelableArrayListExtra("user");
        }else{
            u = new ArrayList<>();
        }if(z.hasExtra("idx")){
            idx = z.getIntExtra("idx",0);
        }
        back = findViewById(R.id.backC);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(cart.this,homepage.class);
                x.putExtra("user",u);
                x.putExtra("idx",idx);
                startActivity(x);
            }
        });
    }
}