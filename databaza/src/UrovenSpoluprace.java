public enum UrovenSpoluprace {
    SPATNA(1), 
    PRUMERNA(2), 
    DOBRA(3);
    
    private final int vaha;
    
    UrovenSpoluprace(int vaha) { 
        this.vaha = vaha; 
    }

    public int getVaha() { return vaha; }
}