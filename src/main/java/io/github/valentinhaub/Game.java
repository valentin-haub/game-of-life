package io.github.valentinhaub;


public class Game {
    int[] world;
    int n;
    int m;
    int stepCounter;

    Game(int n, int m){
        this.n = n;
        this.m = m;
        this.world = new int[n*m];
    }

    boolean isCellAlive(int cell){
        return world[cell] == 1;
    }

    int checkBorderX(int cell){ // Rand links/rechts
        if (cell % this.n == 0) return -1;
        if (cell % this.n == n-1) return 1;
        return 0;
    }

    int checkBorderY(int cell){ // Rand oben/unten
        if (cell < this.n) return -1;
        if (cell >= this.n * (m-1)) return 1;
        return 0;
    }

    int countNeighbours(int cell){
        int a = 0;

        if (checkBorderX(cell) == -1){ // LINKS
            if (checkBorderY(cell) == -1){ // links oben
                a += world[cell+1] + world[cell+n] + world[cell+n+1];
            } else if (checkBorderY(cell) == 0){ // links mitte
                a += world[cell-n] + world[cell-n+1] + world[cell+1] + world[cell+n] + world[cell+n+1];
            }
            else if (checkBorderY(cell) == 1){ // links unten
                a += world[cell-n] + world[cell-n+1] + world[cell+1];
            }
        }
        else if (checkBorderX(cell) == 0){ // MITTE
            if (checkBorderY(cell) == -1){ // mitte oben
                a += world[cell-1] + world[cell+1] + world[cell+n-1] + world[cell+n] + world[cell+n+1];
            } else if (checkBorderY(cell) == 0){ // mitte
                a += world[cell-n-1] + world[cell-n] + world[cell-n+1] + world[cell-1] + world[cell+1] + world[cell+n-1] + world[cell+n] + world[cell+n+1];
            }
            else if (checkBorderY(cell) == 1){ // mitte unten
                a += world[cell-n-1] + world[cell-n] + world[cell-n+1] + world[cell-1] + world[cell+1];
            }
        }
        else if (checkBorderX(cell) == 1){ // RECHTS
            if (checkBorderY(cell) == -1){ // rechts oben
                a += world[cell-1] + world[cell+n-1] + world[cell+n];
            } else if (checkBorderY(cell) == 0){ // rechts mitte
                a += world[cell-n-1] + world[cell-n] + world[cell-1] + world[cell+n-1] + world[cell+n];
            }
            else if (checkBorderY(cell) == 1){ // rechts unten
                a += world[cell-n-1] + world[cell-n] + world[cell-1];
            }
        }
        return a;
    }

    int[] runAlgorithm(int[] world){
        int[] tempWorld = new int[world.length];

        for (int i = 0; i < world.length; i++){
            if (!isCellAlive(i)){ // unbelebte Zelle
                if (countNeighbours(i) == 3){
                    tempWorld[i] = 1;
                }
            }
            else { // belebte Zelle
                if (countNeighbours(i) == 2 || countNeighbours(i) == 3){
                    tempWorld[i] = 1;
                }
            }
        }
        return tempWorld;
    }

    void doStep(int times){
        for (int i = 0; i < times; i++){
            this.world = this.runAlgorithm(this.world);
            this.stepCounter++;
        }
        System.out.println(this);
    }

    void doStep(){
        this.doStep(1);
    }

    boolean stepPossible(){
        int[] newWorld = this.runAlgorithm(this.world);
        for (int i = 0; i < this.world.length; i++){
            if (this.world[i] != newWorld[i]) return true;
        }
        return false;
    }

    boolean setCell (boolean live, int... pos){
        for (int p : pos){
            if (p < 0 || p >= this.world.length) return false;
        }
        for (int p : pos){
            this.world[p] = live ? 1 : 0;
        }
        return true;
    }

    void setCell(int... pos){
        this.setCell(true, pos);
    }

    int[] preview(int times){
        int[] tempWorld = new int[this.world.length];
        for (int i = 0; i < this.world.length; i++){
            tempWorld[i] = this.world[i];
        }
        for (int i = 0; i < times; i++){
            tempWorld = this.runAlgorithm(tempWorld);
        }
        return tempWorld;
    }

    @Override
    public String toString(){
        String s = "";
        String t = "-".repeat(4 * this.n + 1);
        for (int i = 0; i < this.world.length; i++){
            if (i % this.n == 0){
                if (i > 0) s += "|";
                s += "\n" + t + "\n";
            }
            s += "| " + (this.world[i] == 1 ? "*" : " ") + " ";
        }
        s += "|" + "\n" + t;
        return s;
    }
}


//Game a = new Game(10,10);