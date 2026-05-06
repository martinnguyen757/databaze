import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class SpravaFirmy {
    private Map<Integer, Zamestnanec> databaze = new HashMap<>();
    private int idCounter = 1;
    private final ZalohovaciStrategie zaloha = new BinarniZaloha();
    private final String SOUBOR = "firma_data.dat";
@SuppressWarnings("unchecked")
    public void init() {
        if (!new File(SOUBOR).exists()) return;
        try {
            Object[] data = zaloha.nacti(SOUBOR);
            this.databaze = (Map<Integer, Zamestnanec>) data[0];
            this.idCounter = (int) data[1];
        } catch (Exception e) {
            System.err.println("Chyba načítání databáze.");
        }
    }

    public void ulozKonec() {
        try {
            zaloha.uloz(databaze, idCounter, SOUBOR);
        } catch (Exception e) {
            System.err.println("Chyba ukladani.");
        }
    }

    public int pridej(int typ, String jmeno, String prijmeni, int rok) {
        Zamestnanec z = ZamestnanecFactory.vytvor(typ, idCounter++, jmeno, prijmeni, rok);
        databaze.put(z.getId(), z);
        return z.getId();
    }

    public void navazSpolupraci(int id1, int id2, UrovenSpoluprace u) {
        Zamestnanec z1 = Optional.ofNullable(databaze.get(id1)).orElseThrow(() -> new IllegalArgumentException("ID1 neexistuje"));
        Zamestnanec z2 = Optional.ofNullable(databaze.get(id2)).orElseThrow(() -> new IllegalArgumentException("ID2 neexistuje"));
        
        if (id1 == id2) throw new IllegalArgumentException("Nelze navázat spolupráci se sebou samým.");
        if (z1.getSeznamSpolupraci().stream().anyMatch(s -> s.idKolegy() == id2)) throw new IllegalArgumentException("Vazba již existuje.");

        z1.pridejSpolupraci(new Spoluprace(id2, u));
        z2.pridejSpolupraci(new Spoluprace(id1, u));
    }

    public boolean smaz(int id) {
        if (databaze.remove(id) == null) return false;
        databaze.values().forEach(z -> z.odstranSpolupraci(id)); 
        return true;
    }

    public Optional<Zamestnanec> najdi(int id) { return Optional.ofNullable(databaze.get(id)); }
    public Collection<Zamestnanec> getVsechny() { return databaze.values(); }

    public String ziskejStatistiky() {
        if (databaze.isEmpty()) return "Zadna data.";
        
        
        String prevazujici = databaze.values().stream()
                .flatMap(z -> z.getSeznamSpolupraci().stream())
                .collect(Collectors.groupingBy(Spoluprace::uroven, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey().toString())
                .orElse("Zadne vazby");

        Zamestnanec top = databaze.values().stream()
                .max(Comparator.comparingInt(z -> z.getSeznamSpolupraci().size()))
                .orElse(null);

        return String.format("Prevayujici uroven: %s\nNejvice vazbami disponuje: %s (vazeb: %d)", 
                prevazujici, top != null ? top.getPrijmeni() : "-", top != null ? top.getSeznamSpolupraci().size() : 0);
    }
}
