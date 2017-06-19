package test.pylogy.com.mygroupen.entity;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public class YouLike {
    private String title;
    private String distance;
    private String info;
    private String price;
    private String sales;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public YouLike() {
    }

    public YouLike(String title, String distance, String info, String price, String sales) {
        this.title = title;
        this.distance = distance;
        this.info = info;
        this.price = price;
        this.sales = sales;
    }
}
