package sql;

public class Orders {
    int id;
    String fullName;
    String address;
    int poCode;
    String city;
    String county;
    int totalPrice;
    int idUserOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPoCode() {
        return poCode;
    }

    public void setPoCode(int poCode) {
        this.poCode = poCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getIdUserOrder() {
        return idUserOrder;
    }

    public void setIdUserOrder(int idUserOrder) {
        this.idUserOrder = idUserOrder;
    }


}
