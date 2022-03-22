package sql;

public class Comenzi {
    int id;
    String numeComplet;
    String adresa;
    int codPostal;
    String oras;
    String judet;
    int pretTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeComplet() {
        return numeComplet;
    }

    public void setNumeComplet(String numeComplet) {
        this.numeComplet = numeComplet;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public int getPretTotal() {
        return pretTotal;
    }

    public void setPretTotal(int pretTotal) {
        this.pretTotal = pretTotal;
    }

    public int getIdUserComanda() {
        return idUserComanda;
    }

    public void setIdUserComanda(int idUserComanda) {
        this.idUserComanda = idUserComanda;
    }

    int idUserComanda;
}
