package sql;

public class Retete {
    int id;
    int user_id;
    int doctor_id;
    int medicament_id;
    int medicament_nr;
    int medicament_fr;
    int medicament_pret;
    String medicament_nume;

    public String getMedicament_nume() {
        return medicament_nume;
    }

    public void setMedicament_nume(String medicament_nume) {
        this.medicament_nume = medicament_nume;
    }

    public int getMedicament_pret() {
        return medicament_pret;
    }

    public void setMedicament_pret(int medicament_pret) {
        this.medicament_pret = medicament_pret;
    }



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

    public int getMedicament_id() {
        return medicament_id;
    }

    public void setMedicament_id(int medicament_id) {
        this.medicament_id = medicament_id;
    }

    public int getMedicament_nr() {
        return medicament_nr;
    }

    public void setMedicament_nr(int medicament_nr) {
        this.medicament_nr = medicament_nr;
    }

    public int getMedicament_fr() {
        return medicament_fr;
    }

    public void setMedicament_fr(int medicament_frecventa) {
        this.medicament_fr = medicament_frecventa;
    }


}
