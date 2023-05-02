package codeconverters.loginpage;

import android.graphics.Bitmap;

public class itemFirebase {

    private String itemId;
    private String itemDesc;
    private String itemCategory;
    private boolean bSell;
    private boolean bDonate;
    private boolean bSold;
    private Double dPrice;
    private String imageUrl;
    private String studId;

    public itemFirebase() {
    }

    public itemFirebase(String itemId,String itemDesc, String itemCategory, boolean bSell, boolean bDonate, boolean bSold, Double dPrice, String imageUrl, String studId) {
        this.itemId = itemId;
        this.itemDesc = itemDesc;
        this.itemCategory = itemCategory;
        this.bSell = bSell;
        this.bDonate = bDonate;
        this.bSold = bSold;
        this.dPrice = dPrice;
        this.imageUrl = imageUrl;
        this.studId = studId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public boolean isbSell() {
        return bSell;
    }

    public void setbSell(boolean bSell) {
        this.bSell = bSell;
    }

    public boolean isbDonate() {
        return bDonate;
    }

    public void setbDonate(boolean bDonate) {
        this.bDonate = bDonate;
    }

    public boolean isbSold() {
        return bSold;
    }

    public void setbSold(boolean bSold) {
        this.bSold = bSold;
    }

    public Double getdPrice() {
        return dPrice;
    }

    public void setdPrice(Double dPrice) {
        this.dPrice = dPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    @Override
    public String toString() {
        return "itemFirebase{" +
                "itemId=" + itemId +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", bSell=" + bSell +
                ", bDonate=" + bDonate +
                ", bSold=" + bSold +
                ", dPrice=" + dPrice +
                ", image=" + imageUrl +
                ", studId='" + studId + '\'' +
                '}';
    }
}
