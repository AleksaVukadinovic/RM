package dec_ps_2025_zad2;

public class ChessPlayer {
    private final int id;
    private final String name;
    private int elo;

    public ChessPlayer(int id, String name, int elo) {
        this.id = id;
        this.name = name;
        this.elo = elo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}
