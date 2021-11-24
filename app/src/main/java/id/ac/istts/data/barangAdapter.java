package id.ac.istts.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.istts.R;

public class barangAdapter extends RecyclerView.Adapter<barangAdapter.holder> {
    Context context;
    ArrayList<barang> bar = new ArrayList<>();

    public barangAdapter(Context context, ArrayList<barang> bar) {
        this.context = context;
        this.bar = bar;
        setOnItemClickCallback(onItemClickCallback);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_homepage,parent,false);

        return new holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        barang temp = bar.get(position);
        holder.tv1.setText(temp.getNama_barang());
        holder.tv2.setText("Harga : IDR "+temp.getHarga());
        holder.tv3.setText("Penjual : "+temp.getId_penjual());


        holder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(bar.get(position),view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bar.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3;
        public holder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tvbarang_nama);
            tv2 = itemView.findViewById(R.id.tvbarang_harga);
            tv3 = itemView.findViewById(R.id.tvbarang_penjual);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(barang bb,View v);
    }
    private OnItemClickCallback onItemClickCallback ;
    public void setOnItemClickCallback(OnItemClickCallback  callback){
        this.onItemClickCallback = callback;
    }
}
