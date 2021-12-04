package id.ac.istts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.ac.istts.R;
import id.ac.istts.data.user;
import id.ac.istts.db.AppDatabase;

public class changeEmail extends AppCompatActivity {

    EditText et1;
    Button b1,b2;
    user ux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        Intent z = getIntent();
        if(z.hasExtra("user")){
            ux = z.getParcelableExtra("user");
        }
        et1 = findViewById(R.id.cemail);
        b1 = findViewById(R.id.savecemail);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = et1.getText().toString();
                new emailAsync(ux, str, getApplicationContext(), new emailAsync.AddUserCallback() {
                    @Override
                    public void preExecute() {

                    }

                    @Override
                    public void postExecute(String bb, user uxx) {
                        if(bb.equals("")){
                        }else{
                            Toast.makeText(getApplicationContext(), bb, Toast.LENGTH_SHORT).show();
                            ux = uxx;
                        }
                    }
                }).execute();
            }
        });
        b2 = findViewById(R.id.backcemail);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent z1 = new Intent(changeEmail.this,profile.class);
                z1.putExtra("user",ux);
                startActivity(z1);
            }
        });
    }
}

class emailAsync{
    private final WeakReference<Context> weakContext;
    private final WeakReference<emailAsync.AddUserCallback> weakCallback;
    private user u1;
    private String txt;

    emailAsync(user u1, String txt, Context weakContext, AddUserCallback weakCallback) {
        this.weakContext = new WeakReference<>(weakContext);
        this.weakCallback = new WeakReference<>(weakCallback);
        this.u1 = u1;
        this.txt = txt;
    }

    void execute(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        weakCallback.get().preExecute();
        executorService.execute(()->{
           Context context = weakContext.get();
           AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
           user ux1 = appDatabase.userDAO().getUser(u1.getUsername());
           String s;
           user ut = null;
           if(ux1==null){
               s = "no user found";
           }else{
               appDatabase.userDAO().cEmail(txt,ux1.getUsername());
               ut = appDatabase.userDAO().getUserbyemail(txt);
               s = "Email berhasil diubah";
           }
           final String bu = s;
           user finalUt = ut;
            handler.post(()->{
               weakCallback.get().postExecute(bu, finalUt);
           });
        });
    }

    interface AddUserCallback {
        void preExecute();
        void postExecute(String bb,user uxx);
    }
}