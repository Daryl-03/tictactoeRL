package main;

public class TictatctoeBoard implements BoardView{
    private final int size;
    private GameToken[][] grid;
    private int remainingBoxes;

    public TictatctoeBoard(int size){
        this.size = size;
        initGrid(size);
        remainingBoxes = size * size;
    }

    public TictatctoeBoard(GameToken[][] grid){
        this.size = grid.length;
        this.grid = grid;
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(grid[i][j] == GameToken.N)
                    count++;
            }
        }
        this.remainingBoxes = count;
    }

    private void initGrid(int size) {
        grid = new GameToken[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = GameToken.N;
            }
        }
    }

    private boolean isBetween(int value, int min, int max){
        return (value >= min) && (value <= max );
    }

    public int placeValue(GameToken value, int line, int col){
        if(!isBetween(line, 1, size) || !isBetween(col, 1, size) || remainingBoxes == 0 || grid[line-1][col-1] != GameToken.N)
            return -1;

        grid[line-1][col-1] = value;
        remainingBoxes--;
        return 0;
    }

    public boolean canPlaceMore(){
        return remainingBoxes != 0;
    }



    @Override
    public GameToken getTokenAt(int line, int col) {
        if(!isBetween(line, 1, size) || !isBetween(col, 1, size) )
            throw new IllegalArgumentException("Board limits breached");
        return grid[line-1][col-1];
    }

    @Override
    public int getSize() {
        return size;
    }
}