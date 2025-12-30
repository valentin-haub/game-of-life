package io.github.valentinhaub;


import processing.core.PApplet;

public class GameOfLifeApp extends PApplet{
    int size = 10;
    int tickRate = 5;
    Game game;
    int cellSize;
    int boardSize;
    int buttonSize;
    boolean isRunning;
    int ticks;

    public void settings(){
        size(1200, 900);
    }

    public void setup(){
        frameRate(60);
        game = new Game(size, size);
        cellSize = (10*height/12) / size;
        boardSize = cellSize * size;
        buttonSize = height/30;
    }


    void drawBoard(){
        strokeWeight(8 - size / 20);
        stroke(25);
        rect(width/12, height/12, boardSize, boardSize);
    }

    void drawWorld(){
        strokeWeight(cellSize / 100);
        stroke(25);
        for (int i = 0; i < game.world.length; i++){
            int row = i / size;
            int col = i % size;
            int cellColor = (game.world[i] == 1) ? color(0,0,255) : color(255);
            fill(cellColor);
            rect(width/12 + col * cellSize, height/12 + row * cellSize, cellSize, cellSize);
        }
    }

    void drawStartButton(int x, int y){
        fill(60,90,60);
        strokeWeight(1);
        stroke(200);
        circle(x, y, 2 * buttonSize);
        fill(180);
        strokeWeight(0);
        triangle(x-buttonSize/2, y-buttonSize/2, x-buttonSize/2, y+buttonSize/2, x+buttonSize/2, y);
    }

    void drawStepButton(int x, int y){
        fill(60,60,90);
        strokeWeight(1);
        stroke(200);
        circle(x, y, 2 * buttonSize);
        fill(180);
        strokeWeight(0);
        triangle(x, y-buttonSize/3, x, y+buttonSize/3, x+buttonSize/3, y);
        rect(x-buttonSize/2, y-buttonSize/8, buttonSize/2, buttonSize/4);
    }

    void drawStopButton(int x, int y){
        fill(90,60,60);
        strokeWeight(1);
        stroke(200);
        circle(x, y, 2 * buttonSize);
        fill(180);
        strokeWeight(0);
        rect(x-buttonSize/2, y-buttonSize/2, buttonSize, buttonSize);
    }

    void drawResetButton(int x){
        fill(60,60,60);
        strokeWeight(1);
        stroke(200);
        textSize(height/40);
        rect(x - buttonSize, boardSize, textWidth("Reset")*1.5f, height/30);
        fill(255);
        text("Reset", x - buttonSize + textWidth("Reset")*0.25f, boardSize + height/40);
    }

    void drawButtons(int x){
        drawStartButton(x, height/2 - 3*buttonSize);
        drawStepButton(x, height/2);
        drawStopButton(x, height/2 + 3*buttonSize);
        drawResetButton(x);
    }

    void drawCounter(){
        fill(0);
        textSize(height/30);
        text("Steps:", width/12 + boardSize + 2*buttonSize, height - boardSize);
        text(game.stepCounter, width/12 + boardSize + 2*buttonSize + textWidth("Steps: "), height - boardSize);
    }

    void mouseWorldClick(){
        if (!isRunning
        && mouseX > width/12 && mouseX < width/12 + boardSize
        && mouseY > height/12 && mouseY < height/12 + boardSize){
            int row = (mouseY - height/12) / cellSize;
            int col = (mouseX - width/12) / cellSize;
            int index = row * size + col; 
            game.setCell(!game.isCellAlive(index), index);
        }
    }

    void mouseStartButtonClick(){
        if (!isRunning
        && mouseX > width/12 + boardSize + 2*buttonSize && mouseX < width/12 + boardSize + 4*buttonSize
        && mouseY > height/2 - 4*buttonSize && mouseY < height/2 - 2*buttonSize){
            isRunning = true;
        }
    }

    void mouseStepButtonClick(){
        if (!isRunning
        && mouseX > width/12 + boardSize + 2*buttonSize && mouseX < width/12 + boardSize + 4*buttonSize
        && mouseY > height/2 - buttonSize && mouseY < height/2 + buttonSize){
            isRunning = false;
            if (game.stepPossible()) game.doStep();
        }
    }

    void mouseStopButtonClick(){
        if (isRunning
        && mouseX > width/12 + boardSize + 2*buttonSize && mouseX < width/12 + boardSize + 4*buttonSize
        && mouseY > height/2 + 2*buttonSize && mouseY < height/2 + 4*buttonSize){
            isRunning = false;
        }
    }

    void mouseResetButtonClick(){
        if (mouseX > width/12 + boardSize + 2*buttonSize && mouseX < width/12 + boardSize + 2*buttonSize + textWidth("Reset")*1.5f
        && mouseY > boardSize && mouseY < boardSize + height/30){
            isRunning = false;
            game = new Game(size,size);
        }
    }

    public void mouseReleased(){
        mouseWorldClick();
        mouseStartButtonClick();
        mouseStepButtonClick();
        mouseStopButtonClick();
        mouseResetButtonClick();
    }

    void running(){
        if (!game.stepPossible()){
            isRunning = false;
            ticks = 0;
        } else {
            if (ticks++ == 0) game.doStep();
            if (ticks >= frameRate / tickRate) ticks = 0;
        }
    }

    public void draw(){
        background(200);
        drawBoard();
        drawWorld();
        drawButtons(width/12 + boardSize + 3*buttonSize);
        drawCounter();
        if (isRunning){
            running();
        }
    }

    public static void main(String[] args) {
        PApplet.main(GameOfLifeApp.class);
    }
}