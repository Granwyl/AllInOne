package id.ac.istts;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "barang")
public class barang implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_barang")
    String id_barang;
    @ColumnInfo(name = "nama")
    String nama_barang;
    @ColumnInfo(name = "id_penjual")
    String id_penjual;
    @ColumnInfo(name = "jenis_barang")
    String jenis_barang;
    @ColumnInfo(name = "harga")
    Integer harga;
    @ColumnInfo(name = "stok")
    Integer stok;

    public barang() {
    }

    public barang(String id_barang, String nama_barang, String id_penjual, String jenis_barang, Integer harga, Integer stok) {
        this.id_barang = id_barang;
        this.nama_barang = nama_barang;
        this.id_penjual = id_penjual;
        this.jenis_barang = jenis_barang;
        this.harga = harga;
        this.stok = stok;
    }

    protected barang(Parcel in) {
        id_barang = in.readString();
        nama_barang = in.readString();
        id_penjual = in.readString();
        jenis_barang = in.readString();
        if (in.readByte() == 0) {
            harga = null;
        } else {
            harga = in.readInt();
        }
        if (in.readByte() == 0) {
            stok = null;
        } else {
            stok = in.readInt();
        }
    }

    public static final Creator<barang> CREATOR = new Creator<barang>() {
        @Override
        public barang createFromParcel(Parcel in) {
            return new barang(in);
        }

        @Override
        public barang[] newArray(int size) {
            return new barang[size];
        }
    };

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getId_penjual() {
        return id_penjual;
    }

    public void setId_penjual(String id_penjual) {
        this.id_penjual = id_penjual;
    }

    public String getJenis_barang() {
        return jenis_barang;
    }

    public void setJenis_barang(String jenis_barang) {
        this.jenis_barang = jenis_barang;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_barang);
        parcel.writeString(nama_barang);
        parcel.writeString(id_penjual);
        parcel.writeString(jenis_barang);
        if (harga == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(harga);
        }
        if (stok == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(stok);
        }
    }
}
