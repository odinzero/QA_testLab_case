package models;

public class PriceData {

    private String regularPrice;
    private String discount;
    private String priceAfterDiscount;

    public PriceData(String regularPrice, String discount, String priceAfterDiscount) {
        this.regularPrice = regularPrice;
        this.discount = discount;
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public String getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(String priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((regularPrice == null) ? 0 : regularPrice.hashCode());
        result = result + ((discount == null) ? 0 : discount.hashCode());
        result = result + ((priceAfterDiscount == null) ? 0 : priceAfterDiscount.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PriceData other = (PriceData) obj;
        if (regularPrice == null) {
            if (other.regularPrice != null) {
                return false;
            }
        } else if (!regularPrice.equals(other.regularPrice)) {
            return false;
        } 
        
        if (discount == null) {
            if (other.discount != null) {
                return false;
            }
        } else if (!discount.equals(other.discount)) {
            return false;
        } 
        
        if (priceAfterDiscount == null) {
            if (other.priceAfterDiscount != null) {
                return false;
            }
        } else if (!priceAfterDiscount.equals(other.priceAfterDiscount)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "PriceData{" + "regularPrice='" + regularPrice + "'"
                + ", discount='" + discount + "'"
                + ", priceAfterDiscount='" + priceAfterDiscount + "'}";
    }

}
