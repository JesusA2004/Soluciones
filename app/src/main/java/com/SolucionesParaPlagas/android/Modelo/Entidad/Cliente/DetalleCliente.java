package com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

import java.util.Arrays;
import java.util.Date;

public class DetalleCliente{

    private String ID;
    private String RFC;
    private String LegalName;
    private String CommercialName;
    private int CreditDays;
    private double CreditAmount;
    private String PaymentMethod;
    private Date CreationDate;
    private String Status;
    private String SalesContact;
    private String CreditContact;
    private String Loctaion; // Si es un error tipográfico, debería ser "Location"
    private String LoctaionID; // Si es un error tipográfico, debería ser "LocationID"
    private String Comments;
    private String PriceList;
    private String PriceListID;
    private String PaymentTermType;
    private String Email;
    private String Telephones;
    private long Number;
    private String AccountNumber;
    private double DefaultDiscount;
    private String ClientSource;
    private String Account;
    private String City;
    private String State;
    private String[] Addresses;

    public DetalleCliente() {
        ID = "";
        RFC = "";
        LegalName = "";
        CommercialName = "";
        CreditDays = 0;
        CreditAmount = 0.0;
        PaymentMethod = "";
        CreationDate = new Date(0); // Fecha inicializada en 1 de enero de 1970
        Status = "";
        SalesContact = "";
        CreditContact = "";
        Loctaion = ""; // Si es un error tipográfico, debería ser "Location"
        LoctaionID = ""; // Si es un error tipográfico, debería ser "LocationID"
        Comments = "";
        PriceList = "";
        PriceListID = "";
        PaymentTermType = "";
        Email = "";
        Telephones = "";
        Number = 0L;
        AccountNumber = "";
        DefaultDiscount = 0.0;
        ClientSource = "";
        Account = "";
        City = "";
        State = "";
        Addresses = new String[0]; // Arreglo vacío
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String[] getAddresses() {
        return Addresses;
    }

    public void setAddresses(String[] addresses) {
        Addresses = addresses;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getClientSource() {
        return ClientSource;
    }

    public void setClientSource(String clientSource) {
        ClientSource = clientSource;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getCommercialName() {
        return CommercialName;
    }

    public void setCommercialName(String commercialName) {
        CommercialName = commercialName;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public double getCreditAmount() {
        return CreditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        CreditAmount = creditAmount;
    }

    public String getCreditContact() {
        return CreditContact;
    }

    public void setCreditContact(String creditContact) {
        CreditContact = creditContact;
    }

    public int getCreditDays() {
        return CreditDays;
    }

    public void setCreditDays(int creditDays) {
        CreditDays = creditDays;
    }

    public double getDefaultDiscount() {
        return DefaultDiscount;
    }

    public void setDefaultDiscount(double defaultDiscount) {
        DefaultDiscount = defaultDiscount;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLegalName() {
        return LegalName;
    }

    public void setLegalName(String legalName) {
        LegalName = legalName;
    }

    public String getLoctaion() {
        return Loctaion;
    }

    public void setLoctaion(String loctaion) {
        Loctaion = loctaion;
    }

    public String getLoctaionID() {
        return LoctaionID;
    }

    public void setLoctaionID(String loctaionID) {
        LoctaionID = loctaionID;
    }

    public long getNumber() {
        return Number;
    }

    public void setNumber(long number) {
        Number = number;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getPaymentTermType() {
        return PaymentTermType;
    }

    public void setPaymentTermType(String paymentTermType) {
        PaymentTermType = paymentTermType;
    }

    public String getPriceList() {
        return PriceList;
    }

    public void setPriceList(String priceList) {
        PriceList = priceList;
    }

    public String getPriceListID() {
        return PriceListID;
    }

    public void setPriceListID(String priceListID) {
        PriceListID = priceListID;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getSalesContact() {
        return SalesContact;
    }

    public void setSalesContact(String salesContact) {
        SalesContact = salesContact;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTelephones() {
        return Telephones;
    }

    public void setTelephones(String telephones) {
        Telephones = telephones;
    }

    @Override
    public String toString() {
        return  "RFC: " + RFC + "\n" +
                "Nombre Legal: " + LegalName + "\n" +
                "Nombre Comercial: " + CommercialName + "\n" +
                "Email: " + Email + "\n" +
                "Teléfono: " + Telephones + "\n";
    }

}