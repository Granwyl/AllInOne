package id.ac.istts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.holder> {
    Context context;
    ArrayList<cartItem> car;
    Integer id;

    public cartAdapter(Context context, ArrayList<cartItem> car, Integer id) {
        this.context = context;
        this.car = car;
        this.id = id;
        setOnItemClickCallback(onItemClickCallback);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
            barang b = car.get(id).getBar().get(position);
            holder.tvnama.setText(b.getNama_barang());
            holder.tvpenjual.setText(b.getId_penjual());
            holder.tvharga.setText(b.getHarga()+"");

    }

    @Override
    public int getItemCount() {
        return car.get(id).getBar().size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView tvnama,tvharga,tvpenjual;
        public holder(@NonNull View itemView) {
            super(itemView);
            tvnama = itemView.findViewById(R.id.tvcart_nama);
            tvharga = itemView.findViewById(R.id.tvcart_harga);
            tvpenjual = itemView.findViewById(R.id.tvcart_penjual);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(cartItem cc);
    }
    private barangAdapter.OnItemClickCallback onItemClickCallback ;
    public void setOnItemClickCallback(barangAdapter.OnItemClickCallback callback){
        this.onItemClickCallback = callback;
    }
}
