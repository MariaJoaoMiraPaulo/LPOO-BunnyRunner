package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GUI.FinalMenu;
import com.mygdx.game.GUI.GameOverMenu;
import com.mygdx.game.GUI.HelpMenu;
import com.mygdx.game.GUI.HighScoreMenu;
import com.mygdx.game.GUI.MainMenu;
import com.mygdx.game.GUI.PauseMenu;
import com.mygdx.game.GUI.PlayScreen;
import com.mygdx.game.Logic.GameLogic;

import java.io.IOException;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 10/05/16.
 */
public class BunnyGame extends Game{

    public static final float PPM = 100;

    public static final short DEFAULT_BIT = 1;
    public static final short BUNNY_BIT = 2;
    public static final short CARROT_BIT = 4;
    public static final short DESTROYED_BIT = 8;
    public static final short GROUND_BIT = 16;
    public static final short PLATFORM_BIT = 32;
    public static final short SPIKE_BIT = 64;
    public static final short DOOR_BIT = 128;
    public static final short ROCK_BIT = 256;
    public static final short HUNTER_BIT = 512;
    public static final short HUNTER_HEAD_BIT = 1024;

    public static final short MAX_LEVEL = 3;


    private FileHandle file;
    private int highscore;

    public SpriteBatch batch;
    private int currentLevel =1;

    private boolean won;

    private GameLogic logic;

    private MainMenu mainMenu;
    private GameOverMenu gameOverMenu;
    private PauseMenu pauseMenu;
    private PlayScreen playScreen;
    private HighScoreMenu highScoreMenu;
    private FinalMenu finalMenu;
    private HelpMenu helpMenu;

    @Override
    public void create() {
        batch= new SpriteBatch();
        mainMenu = new MainMenu(this);
        gameOverMenu = new GameOverMenu(this);
        pauseMenu = new PauseMenu(this);
        finalMenu = new FinalMenu(this);
        helpMenu = new HelpMenu(this);
        loadFile(currentLevel);
        highScoreMenu = new HighScoreMenu(this);
        won = false;
        setScreen(mainMenu);
    }

    @Override
    public void dispose() {
        super.dispose();
        mainMenu.dispose();
        gameOverMenu.dispose();
        pauseMenu.dispose();
        finalMenu.dispose();
        highScoreMenu.dispose();
        helpMenu.dispose();
        if(playScreen != null)
            playScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void newLevel() {
        if(this.currentLevel < MAX_LEVEL)
            this.currentLevel++;
        else if(this.currentLevel == MAX_LEVEL){
            won = true;
        }
        saveHighscore();
        loadFile(currentLevel);
    }

    public void setToMainMenu(){
        setScreen(mainMenu);
    }

    public void setToGameOverMenu(){
        setScreen(gameOverMenu);
    }

    public void setToPauseMenu(){
        logic.setPause(true);
        setScreen(pauseMenu);
    }

    public void setToFinalMenu(){
        setScreen(finalMenu);
    }

    public void setToPlayScreen(){
        logic = new GameLogic(currentLevel);
        playScreen = new PlayScreen(this);
        setScreen(playScreen);
    }

    public void setToSamePlayScreen() {
        logic.setPause(false);
        playScreen.input();
        setScreen(playScreen);
    }

    public void setToHighScoreMenu() {
        highScoreMenu.setLabel();
        setScreen(highScoreMenu);
    }

    public void setToHelpMenu() {
        setScreen(helpMenu);
    }

    public void loadFile(int level){
        String filename;
        filename = "highscore"+level+".dat";
        file = Gdx.files.local(filename);

        if(!file.exists()){
            highscore = 0;
        }
        else {
            java.lang.String text;
            text = file.readString();
            highscore = Integer.parseInt(text);
        }
    }

    public void saveHighscore(){
        if(file.exists()){
            if(highscore < logic.getBunny().getNumberOfCarrots()){
                file.writeString(java.lang.String.format("%d",logic.getBunny().getNumberOfCarrots()), false);
                highscore=logic.getBunny().getNumberOfCarrots();
            }
        }
        else {
            try {
                file.file().createNewFile();
                file.writeString(java.lang.String.format("%d",logic.getBunny().getNumberOfCarrots()), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public GameLogic getLogic() {
        return logic;
    }

    public int getHighscore() {
        return highscore;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}

