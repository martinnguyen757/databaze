import java.io.Serializable;

/**
 * Moderní DTO pro vazbu spolupráce (Immutable Record).
 */
public record Spoluprace(int idKolegy, UrovenSpoluprace uroven) implements Serializable {
    private static final long serialVersionUID = 1L;
}