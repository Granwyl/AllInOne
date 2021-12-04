package id.ac.istts.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "cart")
public class cartItem implements Parcelable {
    @ColumnInfo(name = "listbarang")
    ArrayList<barang> bar;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "usercart")
    String user;

    public cartItem() {
    }

    public cartItem(ArrayList<barang> bar, String user) {
        this.bar = bar;
        this.user = user;
    }

    protected cartItem(Parcel in) {
        bar = in.createTypedArrayList(barang.CREATOR);
        user = in.readString();
    }

    public static final Creator<cartItem> CREATOR = new Creator<cartItem>() {
        @Override
        public cartItem createFromParcel(Parcel in) {
            return new cartItem(in);
        }

        @Override
        public cartItem[] newArray(int size) {
            return new cartItem[size];
        }
    };

    public ArrayList<barang> getBar() {
        return bar;
    }

    public void setBar(ArrayList<barang> bar) {
        this.bar = bar;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(bar);
        parcel.writeString(user);
    }

}
