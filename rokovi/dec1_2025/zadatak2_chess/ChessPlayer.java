package zadatak2_chess;

public class ChessPlayer {
    private final String name;
    private int elo;

    public ChessPlayer(String name) {
        this.name = name;
        this.elo = 1300;
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
