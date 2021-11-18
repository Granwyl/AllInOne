package id.ac.istts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddProduct extends AppCompatActivity {

    EditText et1,et2,et3;
    Button b1,b2;
    Spinner spinner;
    String[] str = {"Voucher","Top-Up","Skin"};
    String jenis = "";
    user ux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        et1 = findViewById(R.id.etbn);
        et2 = findViewById(R.id.etharga);
        et3 = findViewById(R.id.etstok);
        b1 = findViewById(R.id.button3);
        b2 = findViewById(R.id.button4);
        spinner = findViewById(R.id.spb);
        ArrayAdapter<String> as = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,str);
        as.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(as);
        Intent z = getIntent();
        if(z.hasExtra("user")){
            ux = z.getParcelableExtra("user");
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jenis = spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = et1.getText().toString();

                if(s1.equals("")||et2.getText().toString().equals("")||et3.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                }else{
                    int i1 = Integer.parseInt(et2.getText().toString());
                    int i2 = Integer.parseInt(et3.getText().toString());
                    int ctr = 0;
                    String idb = s1.substring(0,1);
                    if(s1.contains(" ")){
                        ctr = s1.indexOf(" ");
                        idb += s1.substring(ctr+1,ctr+2);
                    }else{
                        idb += s1.substring(1,2);
                    }
                    barang bb = new barang(idb,s1,ux.getUsername(),jenis,i1,i2);
                    new AddBarangAsync(idb, bb, getApplicationContext(), new AddBarangAsync.AddBarangCallback() {
                        @Override
                        public void preExecute() {

                        }

                        @Override
                        public void postExecute(String id) {
                            Toast.makeText(getApplicationContext(), "Berhasil Add Product "+id, Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent y = new Intent(AddProduct.this,homepage.class);
                y.putExtra("user",ux);
                startActivity(y);
            }
        });
    }
}

class AddBarangAsync{
    private final WeakReference<Context> weakContext;
    private final WeakReference<AddBarangCallback> weakCallback;
    private barang br;
    private String id;

    AddBarangAsync(String id,barang br,Context weakContext, AddBarangCallback weakCallback) {
        this.weakContext = new WeakReference<>(weakContext) ;
        this.weakCallback = new WeakReference<>(weakCallback) ;
        this.br = br;
        this.id = id;
    }

    void execute(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        weakCallback.get().preExecute();
        executorService.execute(()->{
            int count = 0;
            Context context = weakContext.get();
            AppDatabaseBarang appDatabaseBarang = AppDatabaseBarang.getAppDatabaseBarang(context);
            List<barang> lb = appDatabaseBarang.barangDAO().getBarangList();
            for (int i = 0; i < lb.size(); i++) {
                if(lb.get(i).getId_barang().contains(id)){
                    count++;
                }
            }

            if(count==0){
                id += "00";
            }else if(count>0&&count<10){
                id += "0"+count;
            }else{
                id += ""+count;
            }
            br.setId_barang(id);
            appDatabaseBarang.barangDAO().insert(br);
            handler.post(()->{
                weakCallback.get().postExecute(id);
            });
        });
    }

    interface AddBarangCallback{
        void preExecute();
        void postExecute(String id);
    }
}