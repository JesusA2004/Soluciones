package com.SolucionesParaPlagas.android.Modelo.Entidad;

public class Producto {

    private String ID; // UUID
    private String Code; // Código
    private String Title; // Título
    private String Description; // Descripción
    private String CreationDate; // Fecha de creación (date-time en formato String)
    private double Cost; // Costo
    private String SKU; // SKU
    private String Comments; // Comentarios
    private int CostType; // Tipo de costo (integer en Java es int)
    private String CostTypeText; // Texto del tipo de costo
    private String Category1ID; // ID de la categoría 1 (UUID)
    private String Category2ID; // ID de la categoría 2 (UUID)
    private String Category3ID; // ID de la categoría 3 (UUID)
    private double CurrentInventory; // Inventario actual (número en formato double)
    private boolean ChargeVAT; // Cobrar IVA
    private int Number; // Número (integer en Java es int)
    private int PricingType; // Tipo de precio (integer en Java es int)
    private String ImageUrl; // URL de la imagen
    private String PricingTypeText; // Texto del tipo de precio
    private String Unit; // Unidad
    private String CurrencyID; // ID de la moneda (UUID)
    private String CurrencyCode; // Código de la moneda
    private int PurchaseType; // Tipo de compra (integer en Java es int)
    private String PurchaseTypeText; // Texto del tipo de compra
    private double IEPSRate; // Tasa de IEPS
    private int Type; // Tipo (integer en Java es int)
    private String TypeText; // Texto del tipo
    private boolean ProductionAuto; // Producción automática
    private double Volume; // Volumen
    private double Weight; // Peso

    // Getters y Setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String CreationDate) {
        this.CreationDate = CreationDate;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public int getCostType() {
        return CostType;
    }

    public void setCostType(int CostType) {
        this.CostType = CostType;
    }

    public String getCostTypeText() {
        return CostTypeText;
    }

    public void setCostTypeText(String CostTypeText) {
        this.CostTypeText = CostTypeText;
    }

    public String getCategory1ID() {
        return Category1ID;
    }

    public void setCategory1ID(String Category1ID) {
        this.Category1ID = Category1ID;
    }

    public String getCategory2ID() {
        return Category2ID;
    }

    public void setCategory2ID(String Category2ID) {
        this.Category2ID = Category2ID;
    }

    public String getCategory3ID() {
        return Category3ID;
    }

    public void setCategory3ID(String Category3ID) {
        this.Category3ID = Category3ID;
    }

    public double getCurrentInventory() {
        return CurrentInventory;
    }

    public void setCurrentInventory(double CurrentInventory) {
        this.CurrentInventory = CurrentInventory;
    }

    public boolean isChargeVAT() {
        return ChargeVAT;
    }

    public void setChargeVAT(boolean ChargeVAT) {
        this.ChargeVAT = ChargeVAT;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public int getPricingType() {
        return PricingType;
    }

    public void setPricingType(int PricingType) {
        this.PricingType = PricingType;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getPricingTypeText() {
        return PricingTypeText;
    }

    public void setPricingTypeText(String PricingTypeText) {
        this.PricingTypeText = PricingTypeText;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }

    public void setCurrencyID(String CurrencyID) {
        this.CurrencyID = CurrencyID;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    public int getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(int PurchaseType) {
        this.PurchaseType = PurchaseType;
    }

    public String getPurchaseTypeText() {
        return PurchaseTypeText;
    }

    public void setPurchaseTypeText(String PurchaseTypeText) {
        this.PurchaseTypeText = PurchaseTypeText;
    }

    public double getIEPSRate() {
        return IEPSRate;
    }

    public void setIEPSRate(double IEPSRate) {
        this.IEPSRate = IEPSRate;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getTypeText() {
        return TypeText;
    }

    public void setTypeText(String TypeText) {
        this.TypeText = TypeText;
    }

    public boolean isProductionAuto() {
        return ProductionAuto;
    }

    public void setProductionAuto(boolean ProductionAuto) {
        this.ProductionAuto = ProductionAuto;
    }

    public double getVolume() {
        return Volume;
    }

    public void setVolume(double Volume) {
        this.Volume = Volume;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double Weight) {
        this.Weight = Weight;
    }

}