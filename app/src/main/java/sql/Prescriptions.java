package sql;

public class Prescriptions {
    int id;
    int user_id;
    int doctor_id;
    int drug_id;
    int drug_no;
    int drug_fr;
    int drug_price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(int drug_id) {
        this.drug_id = drug_id;
    }

    public int getDrug_no() {
        return drug_no;
    }

    public void setDrug_no(int drug_no) {
        this.drug_no = drug_no;
    }

    public int getDrug_fr() {
        return drug_fr;
    }

    public void setDrug_fr(int drug_fr) {
        this.drug_fr = drug_fr;
    }

    public int getDrug_price() {
        return drug_price;
    }

    public void setDrug_price(int drug_price) {
        this.drug_price = drug_price;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    String drug_name;




}
