package id.ac.istts;

import android.os.Parcel;
import android.os.Parcelable;

public class user implements Parcelable {
    String username,email,password;
    int phone,saldo;

    public user(String username, String email, String password, int phone, int saldo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.saldo = saldo;
    }

    protected user(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
        phone = in.readInt();
        saldo = in.readInt();
    }

    public static final Creator<user> CREATOR = new Creator<user>() {
        @Override
        public user createFromParcel(Parcel in) {
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeInt(phone);
        parcel.writeInt(saldo);
    }
}
