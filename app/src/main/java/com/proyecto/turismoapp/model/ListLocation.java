package com.proyecto.turismoapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListLocation implements Parcelable {
    // Aqui puse las propiedades de la clase
    String image;
    String nameLocation;
    String directionLocation;
    String paisLocation;
    String latitudeLocation;
    String longitudeLocation;
    String descriptions;

    //Este seria el constructor
    public ListLocation() {
    }

    public ListLocation(String image, String nameLocation, String directionLocation, String paisLocation, String latitudeLocation, String longitudeLocation, String descriptions) {
        this.image = image;
        this.nameLocation = nameLocation;
        this.directionLocation = directionLocation;
        this.paisLocation = paisLocation;
        this.latitudeLocation = latitudeLocation;
        this.longitudeLocation = longitudeLocation;
        this.descriptions = descriptions;
    }

    //Este constructor se usa para crear una instancia desde el objeto parcelado

    protected ListLocation(Parcel in) {
        image = in.readString();
        nameLocation = in.readString();
        directionLocation = in.readString();
        paisLocation = in.readString();
        latitudeLocation = in.readString();
        longitudeLocation = in.readString();
        descriptions = in.readString();
    }
    //estos son los metodos para escribir y leer un objeto parcelado
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(nameLocation);
        dest.writeString(directionLocation);
        dest.writeString(paisLocation);
        dest.writeString(latitudeLocation);
        dest.writeString(longitudeLocation);
        dest.writeString(descriptions);
    }

    //el creatir que se utilizo es para crear instancias de la clase desde el objeto parcelado
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListLocation> CREATOR = new Creator<ListLocation>() {
        @Override
        public ListLocation createFromParcel(Parcel in) {
            return new ListLocation(in);
        }

        @Override
        public ListLocation[] newArray(int size) {
            return new ListLocation[size];
        }
    };

    //respectivos setter y getters, pero los setter no se ocupan ya que se modifican en firebase
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public String getDirectionLocation() {
        return directionLocation;
    }

    public void setDirectionLocation(String directionLocation) {
        this.directionLocation = directionLocation;
    }

    public String getPaisLocation() {
        return paisLocation;
    }

    public void setPaisLocation(String paisLocation) {
        this.paisLocation = paisLocation;
    }

    public String getLatitudeLocation() {
        return latitudeLocation;
    }

    public void setLatitudeLocation(String latitudeLocation) {
        this.latitudeLocation = latitudeLocation;
    }

    public String getLongitudeLocation() {
        return longitudeLocation;
    }

    public void setLongitudeLocation(String longitudeLocation) {
        this.longitudeLocation = longitudeLocation;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}