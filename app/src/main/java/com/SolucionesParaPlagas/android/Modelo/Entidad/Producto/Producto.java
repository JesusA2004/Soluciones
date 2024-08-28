package com.SolucionesParaPlagas.android.Modelo.Entidad.Producto;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable{

    // Campos de la clase
    private String ID;
    private String Code;
    private String Title;
    private String Description;
    private String CreationDate;
    private double Cost;
    private String SKU;
    private String Comments;
    private int CostType;
    private String CostTypeText;
    private String Category1ID;
    private String Category2ID;
    private String Category3ID;
    private double CurrentInventory;
    private boolean ChargeVAT;
    private int Number;
    private int PricingType;
    private String ImageUrl;
    private String PricingTypeText;
    private String Unit;
    private String CurrencyID;
    private String CurrencyCode;
    private int PurchaseType;
    private String PurchaseTypeText;
    private double IEPSRate;
    private int Type;
    private String TypeText;
    private boolean ProductionAuto;
    private double Volume;
    private double Weight;

    // Constructor vacío
    public Producto() {

    }

    // Constructor que toma un Parcel y lee los datos en el mismo orden en que fueron escritos
    protected Producto(Parcel in) {
        ID = in.readString();
        Code = in.readString();
        Title = in.readString();
        Description = in.readString();
        CreationDate = in.readString();
        Cost = in.readDouble();
        SKU = in.readString();
        Comments = in.readString();
        CostType = in.readInt();
        CostTypeText = in.readString();
        Category1ID = in.readString();
        Category2ID = in.readString();
        Category3ID = in.readString();
        CurrentInventory = in.readDouble();
        ChargeVAT = in.readByte() != 0;
        Number = in.readInt();
        PricingType = in.readInt();
        ImageUrl = in.readString();
        PricingTypeText = in.readString();
        Unit = in.readString();
        CurrencyID = in.readString();
        CurrencyCode = in.readString();
        PurchaseType = in.readInt();
        PurchaseTypeText = in.readString();
        IEPSRate = in.readDouble();
        Type = in.readInt();
        TypeText = in.readString();
        ProductionAuto = in.readByte() != 0;
        Volume = in.readDouble();
        Weight = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(Code);
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeString(CreationDate);
        dest.writeDouble(Cost);
        dest.writeString(SKU);
        dest.writeString(Comments);
        dest.writeInt(CostType);
        dest.writeString(CostTypeText);
        dest.writeString(Category1ID);
        dest.writeString(Category2ID);
        dest.writeString(Category3ID);
        dest.writeDouble(CurrentInventory);
        dest.writeByte((byte) (ChargeVAT ? 1 : 0));
        dest.writeInt(Number);
        dest.writeInt(PricingType);
        dest.writeString(ImageUrl);
        dest.writeString(PricingTypeText);
        dest.writeString(Unit);
        dest.writeString(CurrencyID);
        dest.writeString(CurrencyCode);
        dest.writeInt(PurchaseType);
        dest.writeString(PurchaseTypeText);
        dest.writeDouble(IEPSRate);
        dest.writeInt(Type);
        dest.writeString(TypeText);
        dest.writeByte((byte) (ProductionAuto ? 1 : 0));
        dest.writeDouble(Volume);
        dest.writeDouble(Weight);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }
        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public String getCategory1ID() {
        return Category1ID;
    }

    public void setCategory1ID(String category1ID) {
        Category1ID = category1ID;
    }

    public String getCategory2ID() {
        return Category2ID;
    }

    public void setCategory2ID(String category2ID) {
        Category2ID = category2ID;
    }

    public String getCategory3ID() {
        return Category3ID;
    }

    public void setCategory3ID(String category3ID) {
        Category3ID = category3ID;
    }

    public boolean isChargeVAT() {
        return ChargeVAT;
    }

    public void setChargeVAT(boolean chargeVAT) {
        ChargeVAT = chargeVAT;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public int getCostType() {
        return CostType;
    }

    public void setCostType(int costType) {
        CostType = costType;
    }

    public String getCostTypeText() {
        return CostTypeText;
    }

    public void setCostTypeText(String costTypeText) {
        CostTypeText = costTypeText;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }

    public void setCurrencyID(String currencyID) {
        CurrencyID = currencyID;
    }

    public double getCurrentInventory() {
        return CurrentInventory;
    }

    public void setCurrentInventory(double currentInventory) {
        CurrentInventory = currentInventory;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getPricingType() {
        return PricingType;
    }

    public void setPricingType(int pricingType) {
        PricingType = pricingType;
    }

    public String getPricingTypeText() {
        return PricingTypeText;
    }

    public void setPricingTypeText(String pricingTypeText) {
        PricingTypeText = pricingTypeText;
    }

    public boolean isProductionAuto() {
        return ProductionAuto;
    }

    public void setProductionAuto(boolean productionAuto) {
        ProductionAuto = productionAuto;
    }

    public int getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        PurchaseType = purchaseType;
    }

    public String getPurchaseTypeText() {
        return PurchaseTypeText;
    }

    public void setPurchaseTypeText(String purchaseTypeText) {
        PurchaseTypeText = purchaseTypeText;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTypeText() {
        return TypeText;
    }

    public void setTypeText(String typeText) {
        TypeText = typeText;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public double getVolume() {
        return Volume;
    }

    public void setVolume(double volume) {
        Volume = volume;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

}
