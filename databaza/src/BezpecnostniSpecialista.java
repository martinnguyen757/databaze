import java.util.Collection;

public class BezpecnostniSpecialista extends Zamestnanec {
    public BezpecnostniSpecialista(int id, String j, String p, int r) { super(id, j, p, r); }
    @Override public String getTypZamestnance() { return "Specialista"; }

    @Override
    public String provedDovednost(Collection<Zamestnanec> databaze) {
        int pocet = getSeznamSpolupraci().size();
        if (pocet == 0) return "Rizikové skóre: 0.00 (žádné vazby)";
        
        double sumaVah = getSeznamSpolupraci().stream()
                .mapToDouble(s -> s.uroven().getVaha())
                .sum();
                
        double skore = (sumaVah / pocet) * Math.log10(pocet + 1);
        return String.format("Rizikové skóre: %.2f", skore);
    }
}