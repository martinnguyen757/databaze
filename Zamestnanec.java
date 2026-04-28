import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Zamestnanec implements Serializable, Comparable<Zamestnanec> {
    private static final long serialVersionUID = 1L;
    
    private final int id;
    private final String jmeno;
    private final String prijmeni;
    private final int rokNarozeni;
    private final List<Spoluprace> seznamSpolupraci;

    protected Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
        this.seznamSpolupraci = new ArrayList<>();
    }

    public abstract String provedDovednost(Collection<Zamestnanec> databaze);
    public abstract String getTypZamestnance();

    public void pridejSpolupraci(Spoluprace s) {
        this.seznamSpolupraci.add(s);
    }

    public void odstranSpolupraci(int idKolegy) {
        this.seznamSpolupraci.removeIf(s -> s.idKolegy() == idKolegy);
    }

    public int getId() { return id; }
    public String getJmeno() { return jmeno; }
    public String getPrijmeni() { return prijmeni; }
    
    public List<Spoluprace> getSeznamSpolupraci() { 
        return Collections.unmodifiableList(seznamSpolupraci); 
    }

    @Override
    public int compareTo(Zamestnanec jiny) {
        int porovnaniPrijmeni = this.prijmeni.compareToIgnoreCase(jiny.prijmeni);
        return (porovnaniPrijmeni != 0) ? porovnaniPrijmeni : this.jmeno.compareToIgnoreCase(jiny.jmeno);
    }

    @Override
    public String toString() {
        
        return String.format("[%d] %s %s (nar. %d) | %s | Vazby: %d", 
            id, prijmeni, jmeno, rokNarozeni, getTypZamestnance(), seznamSpolupraci.size());
    }
}