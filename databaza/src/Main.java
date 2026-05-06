import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SpravaFirmy sprava = new SpravaFirmy();
        sprava.init();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== ENTERPRISE HR SYSTEM ===");

        while (true) {
            System.out.println("\n1. Pridat | 2. Spoluprace | 3. Smazat | 4. Hledat | 5. Dovednost | 6. Vypis | 7. Statistiky | 9. Konec");
            System.out.print("Volba: ");
            String volba = sc.nextLine().trim();

            try {
                switch (volba) {
                    case "1" -> {
                        System.out.print("Typ (1=Analytik, 2=Specialista): "); int t = Integer.parseInt(sc.nextLine());
                        System.out.print("Jmeno: "); String j = sc.nextLine();
                        System.out.print("Prijmeni: "); String p = sc.nextLine();
                        System.out.print("Rok: "); int r = Integer.parseInt(sc.nextLine());
                        System.out.println("Pridano ID: " + sprava.pridej(t, j, p, r));
                    }
                    case "2" -> {
                        System.out.print("ID prvniho: "); int id1 = Integer.parseInt(sc.nextLine());
                        System.out.print("ID druheho: "); int id2 = Integer.parseInt(sc.nextLine());
                        System.out.print("Uroven (1-Spatna, 2-Prumerna, 3-Dobra): ");
                        UrovenSpoluprace u = UrovenSpoluprace.values()[Integer.parseInt(sc.nextLine()) - 1];
                        sprava.navazSpolupraci(id1, id2, u);
                        System.out.println("Propojeno.");
                    }
                    case "3" -> {
                        System.out.print("ID ke smazani: ");
                        System.out.println(sprava.smaz(Integer.parseInt(sc.nextLine())) ? "Smazano" : "Nenalezeno");
                    }
                    case "4" -> {
                        System.out.print("Hledane ID: ");
                        sprava.najdi(Integer.parseInt(sc.nextLine())).ifPresentOrElse(
                                System.out::println, () -> System.out.println("Nenalezeno"));
                    }
                    case "5" -> {
                        System.out.print("ID pro dovednost: ");
                        sprava.najdi(Integer.parseInt(sc.nextLine())).ifPresentOrElse(
                                z -> System.out.println(z.provedDovednost(sprava.getVsechny())), 
                                () -> System.out.println("Nenalezeno"));
                    }
                    case "6" -> sprava.getVsechny().stream().sorted().forEach(System.out::println);
                    case "7" -> System.out.println(sprava.ziskejStatistiky());
                    case "9" -> {
                        sprava.ulozKonec();
                        System.out.println("Ukonceno.");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Neplatna volba.");
                }
            } catch (Exception e) {
                System.out.println("Chyba zadani: " + e.getMessage());
            }
        }
    }
}
