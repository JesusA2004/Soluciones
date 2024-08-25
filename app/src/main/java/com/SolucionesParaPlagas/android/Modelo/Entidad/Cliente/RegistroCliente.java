package com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

public class RegistroCliente {

    private String LegalName;
    private String CommercialName;
    private String RFC;
    private int CreditDays;
    private double CreditAmount;
    private String PriceListID;
    private String AccountingNumber;
    private Direccion Direccion; // Clase Address
    private int PaymentMethod;
    private int PaymentTerm;
    private String LocationID;
    private String SalesEmployeeID;
    private String CreditEmployeeID;
    private String Comment;
    private String Telephone;
    private String Email;
    private String AccountNumber;
    private Double DefaultDiscount;
    private String Source;

    public RegistroCliente(){
        LegalName = "";
        CommercialName = "";
        RFC = "";
        CreditDays = 0;
        CreditAmount = 0;
        PriceListID = "";
        AccountingNumber = "";
        Direccion = new Direccion();
        PaymentMethod  = 0;
        PaymentTerm = 0;
        LocationID = "";
        SalesEmployeeID = "";
        CreditEmployeeID = "";
        Comment = "";
        Telephone = "";
        Email = "";
        AccountNumber = "";
        DefaultDiscount = 0.0;
        Source = "";
    }

    // Getters y Setters
    public String getLegalName() {
        return LegalName;
    }

    public void setLegalName(String LegalName) {
        this.LegalName = LegalName;
    }

    public String getCommercialName() {
        return CommercialName;
    }

    public void setCommercialName(String CommercialName) {
        this.CommercialName = CommercialName;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public int getCreditDays() {
        return CreditDays;
    }

    public void setCreditDays(int CreditDays) {
        this.CreditDays = CreditDays;
    }

    public double getCreditAmount() {
        return CreditAmount;
    }

    public void setCreditAmount(double CreditAmount) {
        this.CreditAmount = CreditAmount;
    }

    public String getPriceListID() {
        return PriceListID;
    }

    public void setPriceListID(String PriceListID) {
        this.PriceListID = PriceListID;
    }

    public String getAccountingNumber() {
        return AccountingNumber;
    }

    public void setAccountingNumber(String AccountingNumber) {
        this.AccountingNumber = AccountingNumber;
    }

    public Direccion getAddress() {
        return Direccion;
    }

    public void setAddress(Direccion Direccion) {
        this.Direccion = Direccion;
    }

    public int getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(int PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public int getPaymentTerm() {
        return PaymentTerm;
    }

    public void setPaymentTerm(int PaymentTerm) {
        this.PaymentTerm = PaymentTerm;
    }

    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String LocationID) {
        this.LocationID = LocationID;
    }

    public String getSalesEmployeeID() {
        return SalesEmployeeID;
    }

    public void setSalesEmployeeID(String SalesEmployeeID) {
        this.SalesEmployeeID = SalesEmployeeID;
    }

    public String getCreditEmployeeID() {
        return CreditEmployeeID;
    }

    public void setCreditEmployeeID(String CreditEmployeeID) {
        this.CreditEmployeeID = CreditEmployeeID;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public Double getDefaultDiscount() {
        return DefaultDiscount;
    }

    public void setDefaultDiscount(Double DefaultDiscount) {
        this.DefaultDiscount = DefaultDiscount;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

}