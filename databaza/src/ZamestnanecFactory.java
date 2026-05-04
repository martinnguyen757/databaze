public class ZamestnanecFactory {
    public static Zamestnanec vytvor(int typ, int id, String jmeno, String prijmeni, int rok) {
        return switch (typ) {
            case 1 -> new DatovyAnalytik(id, jmeno, prijmeni, rok);
            case 2 -> new BezpecnostniSpecialista(id, jmeno, prijmeni, rok);
            default -> throw new IllegalArgumentException("Neznámý typ zaměstnance");
        };
    }
}