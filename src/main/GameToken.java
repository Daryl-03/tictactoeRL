package main;

public enum GameToken {
    X("X"), O("O"), N(" ");

    private final String symbol;

    GameToken(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}