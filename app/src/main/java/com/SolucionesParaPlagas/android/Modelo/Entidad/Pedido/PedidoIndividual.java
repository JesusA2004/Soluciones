package com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido;

public class PedidoIndividual {

    private String id;
    private String clientID;
    private String clientName;
    private String priceListName;
    private String priceListID;
    private String locationName;
    private String locationID;
    private String warehouseID;
    private String warehouseName;
    private String orderDate;
    private String comments;
    private long number;
    private String clientContact;
    private String phoneNumber;
    private String employeeName;
    private String employeeID;
    private String purchaseOrder;
    private int status;
    private String currencyName;
    private String currencyID;
    private double exchangeRate;
    private String RFC;
    private double serviceSubtotal;
    private double productSubtotal;
    private String serie;
    private double discount;
    private double VATRate;
    private double VAT;
    private int discountType;
    private double IEPS;
    private double IEPSRate;
    private double ISRRet;
    private double ISRRetRate;
    private double vatRet;
    private double vatRetRate;
    private double total;

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getIEPS() {
        return IEPS;
    }

    public void setIEPS(double IEPS) {
        this.IEPS = IEPS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPriceListID() {
        return priceListID;
    }

    public void setPriceListID(String priceListID) {
        this.priceListID = priceListID;
    }

    public double getProductSubtotal() {
        return productSubtotal;
    }

    public void setProductSubtotal(double productSubtotal) {
        this.productSubtotal = productSubtotal;
    }

    public String getPriceListName() {
        return priceListName;
    }

    public void setPriceListName(String priceListName) {
        this.priceListName = priceListName;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public double getServiceSubtotal() {
        return serviceSubtotal;
    }

    public void setServiceSubtotal(double serviceSubtotal) {
        this.serviceSubtotal = serviceSubtotal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
        return vatRet;
    }

    public void setVatRet(double vatRet) {
        this.vatRet = vatRet;
    }

    public double getVatRetRate() {
        return vatRetRate;
    }

    public void setVatRetRate(double vatRetRate) {
        this.vatRetRate = vatRetRate;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public String toString() {
        return "PedidoIndividual{" +
                "clientContact='" + clientContact + '\'' +
                ", id='" + id + '\'' +
                ", clientID='" + clientID + '\'' +
                ", clientName='" + clientName + '\'' +
                ", priceListName='" + priceListName + '\'' +
                ", priceListID='" + priceListID + '\'' +
                ", locationName='" + locationName + '\'' +
                ", locationID='" + locationID + '\'' +
                ", warehouseID='" + warehouseID + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", comments='" + comments + '\'' +
                ", number=" + number +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeID='" + employeeID + '\'' +
                ", purchaseOrder='" + purchaseOrder + '\'' +
                ", status=" + status +
                ", currencyName='" + currencyName + '\'' +
                ", currencyID='" + currencyID + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", RFC='" + RFC + '\'' +
                ", serviceSubtotal=" + serviceSubtotal +
                ", productSubtotal=" + productSubtotal +
                ", serie='" + serie + '\'' +
                ", discount=" + discount +
                ", VATRate=" + VATRate +
                ", VAT=" + VAT +
                ", discountType=" + discountType +
                ", IEPS=" + IEPS +
                ", IEPSRate=" + IEPSRate +
                ", ISRRet=" + ISRRet +
                ", ISRRetRate=" + ISRRetRate +
                ", vatRet=" + vatRet +
                ", vatRetRate=" + vatRetRate +
                ", total=" + total +
                '}';
    }

}
