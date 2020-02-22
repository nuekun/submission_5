package com.nuedevlop.dicoding.favorit;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorit" , indices =  @Index(value = {"tittle"}, unique = true))

public class Favorit implements Parcelable {

    @PrimaryKey(autoGenerate = true) private int idFav;
    @SerializedName(value = "tittle", alternate = {"name"}) private String tittle;
    @SerializedName(value = "date") private String first;
    @SerializedName(value = "mature", alternate = {"language"}) private String second;
    @SerializedName(value = "popular") private String third;
    @SerializedName(value = "overview") private String overview;
    @SerializedName(value = "poster") private String poster;
    @SerializedName(value = "type") private String type;

    int getIdFav() {
        return idFav;
    }

    public void setIdFav(int idFav) {
        this.idFav = idFav;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

     String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

     String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

     String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

     String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idFav);
        dest.writeString(this.tittle);
        dest.writeString(this.first);
        dest.writeString(this.second);
        dest.writeString(this.third);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeString(this.type);
    }

    public Favorit(){
    //req constructor
    }

    protected Favorit(Parcel in){
        this.idFav = in.readInt();
        this.tittle = in.readString();
        this.second = in.readString();
        this.third = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.type = in.readString();
    }

    public static final Creator<Favorit> CREATOR = new Creator<Favorit>(){

        @Override
        public Favorit createFromParcel(Parcel source) {
            return new Favorit(source);
        }

        @Override
        public Favorit[] newArray(int size) {
            return new Favorit[size];
        }
    };

}
