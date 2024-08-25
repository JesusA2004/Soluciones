package com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

import java.util.Arrays;
import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

public class DetalleCliente implements Parcelable{

    private String id;
    private String rfc;
    private String legalName;
    private String commercialName;
    private int creditDays;
    private double creditAmount;
    private String paymentMethod;
    private Date creationDate;
    private String status;
    private String salesContact;
    private String creditContact;
    private String location;
    private String locationID;
    private String comments;
    private String priceList;
    private String priceListID;
    private String paymentTermType;
    private String email;
    private String telephones;
    private long number;
    private String accountNumber;
    private double defaultDiscount;
    private String clientSource;
    private String account;
    private String city;
    private String state;
    private String[] addresses;

    public DetalleCliente() {
        id = "";
        rfc = "";
        legalName = "";
        commercialName = "";
        creditDays = 0;
        creditAmount = 0.0;
        paymentMethod = "";
        creationDate = new Date(0); // Fecha inicializada en 1 de enero de 1970
        status = "";
        salesContact = "";
        creditContact = "";
        location = "";
        locationID = "";
        comments = "";
        priceList = "";
        priceListID = "";
        paymentTermType = "";
        email = "";
        telephones = "";
        number = 0L;
        accountNumber = "";
        defaultDiscount = 0.0;
        clientSource = "";
        account = "";
        city = "";
        state = "";
        addresses = new String[0]; // Arreglo vacío
    }

    protected DetalleCliente(Parcel in) {
        id = in.readString();
        rfc = in.readString();
        legalName = in.readString();
        commercialName = in.readString();
        creditDays = in.readInt();
        creditAmount = in.readDouble();
        paymentMethod = in.readString();
        creationDate = new Date(in.readLong()); // Assuming Date is stored as timestamp
        status = in.readString();
        salesContact = in.readString();
        creditContact = in.readString();
        location = in.readString();
        locationID = in.readString();
        comments = in.readString();
        priceList = in.readString();
        priceListID = in.readString();
        paymentTermType = in.readString();
        email = in.readString();
        telephones = in.readString();
        number = in.readLong();
        accountNumber = in.readString();
        defaultDiscount = in.readDouble();
        clientSource = in.readString();
        account = in.readString();
        city = in.readString();
        state = in.readString();
        addresses = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(rfc);
        dest.writeString(legalName);
        dest.writeString(commercialName);
        dest.writeInt(creditDays);
        dest.writeDouble(creditAmount);
        dest.writeString(paymentMethod);
        dest.writeLong(creationDate.getTime()); // Store Date as timestamp
        dest.writeString(status);
        dest.writeString(salesContact);
        dest.writeString(creditContact);
        dest.writeString(location);
        dest.writeString(locationID);
        dest.writeString(comments);
        dest.writeString(priceList);
        dest.writeString(priceListID);
        dest.writeString(paymentTermType);
        dest.writeString(email);
        dest.writeString(telephones);
        dest.writeLong(number);
        dest.writeString(accountNumber);
        dest.writeDouble(defaultDiscount);
        dest.writeString(clientSource);
        dest.writeString(account);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeStringArray(addresses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<DetalleCliente> CREATOR = new Creator<DetalleCliente>() {
        @Override
        public DetalleCliente createFromParcel(Parcel in) {
            return new DetalleCliente(in);
        }
        @Override
        public DetalleCliente[] newArray(int size) {
            return new DetalleCliente[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public int getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(int creditDays) {
        this.creditDays = creditDays;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesContact() {
        return salesContact;
    }

    public void setSalesContact(String salesContact) {
        this.salesContact = salesContact;
    }

    public String getCreditContact() {
        return creditContact;
    }

    public void setCreditContact(String creditContact) {
        this.creditContact = creditContact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPriceList() {
        return priceList;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }

    public String getPriceListID() {
        return priceListID;
    }

    public void setPriceListID(String priceListID) {
        this.priceListID = priceListID;
    }

    public String getPaymentTermType() {
        return paymentTermType;
    }

    public void setPaymentTermType(String paymentTermType) {
        this.paymentTermType = paymentTermType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephones() {
        return telephones;
    }

    public void setTelephones(String telephones) {
        this.telephones = telephones;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getDefaultDiscount() {
        return defaultDiscount;
    }

    public void setDefaultDiscount(double defaultDiscount) {
        this.defaultDiscount = defaultDiscount;
    }

    public String getClientSource() {
        return clientSource;
    }

    public void setClientSource(String clientSource) {
        this.clientSource = clientSource;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "DetalleCliente{" +
                "account='" + account + '\'' +
                ", id='" + id + '\'' +
                ", rfc='" + rfc + '\'' +
                ", legalName='" + legalName + '\'' +
                ", commercialName='" + commercialName + '\'' +
                ", creditDays=" + creditDays +
                ", creditAmount=" + creditAmount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", creationDate=" + creationDate +
                ", status='" + status + '\'' +
                ", salesContact='" + salesContact + '\'' +
                ", creditContact='" + creditContact + '\'' +
                ", location='" + location + '\'' +
                ", locationID='" + locationID + '\'' +
                ", comments='" + comments + '\'' +
                ", priceList='" + priceList + '\'' +
                ", priceListID='" + priceListID + '\'' +
                ", paymentTermType='" + paymentTermType + '\'' +
                ", email='" + email + '\'' +
                ", telephones='" + telephones + '\'' +
                ", number=" + number +
                ", accountNumber='" + accountNumber + '\'' +
                ", defaultDiscount=" + defaultDiscount +
                ", clientSource='" + clientSource + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", addresses=" + Arrays.toString(addresses) +
                '}';
    }

}
