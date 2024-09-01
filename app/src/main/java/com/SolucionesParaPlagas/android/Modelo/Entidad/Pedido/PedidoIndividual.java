package com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido;

public class PedidoIndividual {

    private String ID;
    private String ClientID;
    private String ClientName;
    private String PriceListName;
    private String PriceListID;
    private String LocationName;
    private String LocationID;
    private String WarehouseID;
    private String WarehouseName;
    private String OrderDate;
    private String Comments;
    private long Number;
    private String ClientContact;
    private String PhoneNumber;
    private String EmployeeName;
    private String EmployeeID;
    private String PurchaseOrder;
    private int Status;
    private String CurrencyName;
    private String CurrencyID;
    private double ExchangeRate;
    private String RFC;
    private double ServiceSubtotal;
    private double ProductSubtotal;
    private String Serie;
    private double Discount;
    private double VATRate;
    private double VAT;
    private int DiscountType;
    private double IEPS;
    private double IEPSRate;
    private double ISRRet;
    private double ISRRetRate;
    private double VatRet;
    private double VatRetRate;
    private double Total;

    // Getters y setters

    public String getClientContact() {
        return ClientContact;
    }

    public void setClientContact(String ClientContact) {
        this.ClientContact = ClientContact;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }

    public void setCurrencyID(String CurrencyID) {
        this.CurrencyID = CurrencyID;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String CurrencyName) {
        this.CurrencyName = CurrencyName;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double Discount) {
        this.Discount = Discount;
    }

    public int getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(int DiscountType) {
        this.DiscountType = DiscountType;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }

    public double getExchangeRate() {
        return ExchangeRate;
    }

    public void setExchangeRate(double ExchangeRate) {
        this.ExchangeRate = ExchangeRate;
    }

    public double getIEPS() {
        return IEPS;
    }

    public void setIEPS(double IEPS) {
        this.IEPS = IEPS;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getIEPSRate() {
        return IEPSRate;
    }

    public void setIEPSRate(double IEPSRate) {
        this.IEPSRate = IEPSRate;
    }

    public double getISRRet() {
        return ISRRet;
    }

    public void setISRRet(double ISRRet) {
        this.ISRRet = ISRRet;
    }

    public double getISRRetRate() {
        return ISRRetRate;
    }

    public void setISRRetRate(double ISRRetRate) {
        this.ISRRetRate = ISRRetRate;
    }

    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String LocationID) {
        this.LocationID = LocationID;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String LocationName) {
        this.LocationName = LocationName;
    }

    public long getNumber() {
        return Number;
    }

    public void setNumber(long Number) {
        this.Number = Number;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getPriceListID() {
        return PriceListID;
    }

    public void setPriceListID(String PriceListID) {
        this.PriceListID = PriceListID;
    }

    public double getProductSubtotal() {
        return ProductSubtotal;
    }

    public void setProductSubtotal(double ProductSubtotal) {
        this.ProductSubtotal = ProductSubtotal;
    }

    public String getPriceListName() {
        return PriceListName;
    }

    public void setPriceListName(String PriceListName) {
        this.PriceListName = PriceListName;
    }

    public String getPurchaseOrder() {
        return PurchaseOrder;
    }

    public void setPurchaseOrder(String PurchaseOrder) {
        this.PurchaseOrder = PurchaseOrder;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String Serie) {
        this.Serie = Serie;
    }

    public double getServiceSubtotal() {
        return ServiceSubtotal;
    }

    public void setServiceSubtotal(double ServiceSubtotal) {
        this.ServiceSubtotal = ServiceSubtotal;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    public double getVATRate() {
        return VATRate;
    }

    public void setVATRate(double VATRate) {
        this.VATRate = VATRate;
    }

    public double getVatRet() {
        return VatRet;
    }

    public void setVatRet(double VatRet) {
        this.VatRet = VatRet;
    }

    public double getVatRetRate() {
        return VatRetRate;
    }

    public void setVatRetRate(double VatRetRate) {
        this.VatRetRate = VatRetRate;
    }

    public String getWarehouseID() {
        return WarehouseID;
    }

    public void setWarehouseID(String WarehouseID) {
        this.WarehouseID = WarehouseID;
    }

    public String getWarehouseName() {
        return WarehouseName;
    }

    public void setWarehouseName(String WarehouseName) {
        this.WarehouseName = WarehouseName;
    }

    @Override
    public String toString() {
        return "PedidoIndividual{" +
                "ClientContact='" + ClientContact + '\'' +
                ", ID='" + ID + '\'' +
                ", ClientID='" + ClientID + '\'' +
                ", ClientName='" + ClientName + '\'' +
                ", PriceListName='" + PriceListName + '\'' +
                ", PriceListID='" + PriceListID + '\'' +
                ", LocationName='" + LocationName + '\'' +
                ", LocationID='" + LocationID + '\'' +
                ", WarehouseID='" + WarehouseID + '\'' +
                ", WarehouseName='" + WarehouseName + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                ", Comments='" + Comments + '\'' +
                ", Number=" + Number +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", EmployeeName='" + EmployeeName + '\'' +
                ", EmployeeID='" + EmployeeID + '\'' +
                ", PurchaseOrder='" + PurchaseOrder + '\'' +
                ", Status=" + Status +
                ", CurrencyName='" + CurrencyName + '\'' +
                ", CurrencyID='" + CurrencyID + '\'' +
                ", ExchangeRate=" + ExchangeRate +
                ", RFC='" + RFC + '\'' +
                ", ServiceSubtotal=" + ServiceSubtotal +
                ", ProductSubtotal=" + ProductSubtotal +
                ", Serie='" + Serie + '\'' +
                ", Discount=" + Discount +
                ", VATRate=" + VATRate +
                ", VAT=" + VAT +
                ", DiscountType=" + DiscountType +
                ", IEPS=" + IEPS +
                ", IEPSRate=" + IEPSRate +
                ", ISRRet=" + ISRRet +
                ", ISRRetRate=" + ISRRetRate +
                ", VatRet=" + VatRet +
                ", VatRetRate=" + VatRetRate +
                ", Total=" + Total +
                '}';
    }

}
