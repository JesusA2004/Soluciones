package com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

import android.os.Parcel;
import android.os.Parcelable;

public class ClienteIndividual implements Parcelable {

    private String ID;
    private int Number;
    private String ClientName;
    private String LegalName;
    private String RFC;
    private String Email;
    private String Phone;
    private String NextContactDate;
    private String LocationID;

    // Constructor vacío
    public ClienteIndividual() {
    }

    // Constructor con parámetros
    public ClienteIndividual(String ID, int Number, String ClientName, String LegalName, String RFC, String Email, String Phone, String NextContactDate, String LocationID) {
        this.ID = ID;
        this.Number = Number;
        this.ClientName = ClientName;
        this.LegalName = LegalName;
        this.RFC = RFC;
        this.Email = Email;
        this.Phone = Phone;
        this.NextContactDate = NextContactDate;
        this.LocationID = LocationID;
    }

    // Getters y Setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }

    public String getLegalName() {
        return LegalName;
    }

    public void setLegalName(String LegalName) {
        this.LegalName = LegalName;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getNextContactDate() {
        return NextContactDate;
    }

    public void setNextContactDate(String NextContactDate) {
        this.NextContactDate = NextContactDate;
    }

    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String LocationID) {
        this.LocationID = LocationID;
    }

    @Override
    public String toString() {
        return "ClienteIndividual{" +
                "ClientName='" + ClientName + '\'' +
                ", ID='" + ID + '\'' +
                ", Number=" + Number +
                ", LegalName='" + LegalName + '\'' +
                ", RFC='" + RFC + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", NextContactDate='" + NextContactDate + '\'' +
                ", LocationID='" + LocationID + '\'' +
                '}';
    }

    // Métodos Parcelable

    protected ClienteIndividual(Parcel in) {
        ID = in.readString();
        Number = in.readInt();
        ClientName = in.readString();
        LegalName = in.readString();
        RFC = in.readString();
        Email = in.readString();
        Phone = in.readString();
        NextContactDate = in.readString();
        LocationID = in.readString();
    }

    public static final Creator<ClienteIndividual> CREATOR = new Creator<ClienteIndividual>() {
        @Override
        public ClienteIndividual createFromParcel(Parcel in) {
            return new ClienteIndividual(in);
        }

        @Override
        public ClienteIndividual[] newArray(int size) {
            return new ClienteIndividual[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeInt(Number);
        dest.writeString(ClientName);
        dest.writeString(LegalName);
        dest.writeString(RFC);
        dest.writeString(Email);
        dest.writeString(Phone);
        dest.writeString(NextContactDate);
        dest.writeString(LocationID);
    }

}
