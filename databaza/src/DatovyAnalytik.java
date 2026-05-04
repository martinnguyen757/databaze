import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class DatovyAnalytik extends Zamestnanec {
    public DatovyAnalytik(int id, String j, String p, int r) { super(id, j, p, r); }
    @Override public String getTypZamestnance() { return "Analytik"; }

    @Override
    public String provedDovednost(Collection<Zamestnanec> databaze) {
        Set<Integer> mojiKolegove = getSeznamSpolupraci().stream()
                .map(Spoluprace::idKolegy)
                .collect(Collectors.toSet());

        return databaze.stream()
                .filter(z -> z.getId() != this.getId())
                .max(Comparator.comparingLong(z -> z.getSeznamSpolupraci().stream()
                        .filter(s -> mojiKolegove.contains(s.idKolegy())).count()))
                .filter(z -> z.getSeznamSpolupraci().stream().anyMatch(s -> mojiKolegove.contains(s.idKolegy())))
                .map(z -> String.format("Nejvice spolecnzch vazeb ma: %s %s", z.getJmeno(), z.getPrijmeni()))
                .orElse("Zadne spolecne vazby s ostatnimi.");
    }
}