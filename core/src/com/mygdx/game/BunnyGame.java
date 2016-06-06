package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GUI.GameOverMenu;
import com.mygdx.game.GUI.HighScoreMenu;
import com.mygdx.game.GUI.MainMenu;
import com.mygdx.game.GUI.PauseMenu;
import com.mygdx.game.GUI.PlayScreen;
import com.mygdx.game.Logic.GameLogic;

import java.io.IOException;

/**
 * Created by mariajoaomirapaulo on 10/05/16.
 */
public class BunnyGame extends Game{

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
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

    private FileHandle file;
    private int highscore;

    public SpriteBatch batch;
    private int atualLevel=1;

    private GameLogic logic;

    private MainMenu mainMenu;
    private GameOverMenu gameOverMenu;
    private PauseMenu pauseMenu;
    private PlayScreen playScreen;
    private HighScoreMenu highScoreMenu;

    @Override
    public void create() {
        Gdx.app.log("Render", "bunnygame 1");
        batch= new SpriteBatch();
        mainMenu = new MainMenu(this);
        gameOverMenu = new GameOverMenu(this);
        pauseMenu = new PauseMenu(this);

        //playScreen = new PlayScreen(this);
        loadFile();
        highScoreMenu = new HighScoreMenu(this);
        Gdx.app.log("Render", "bunnygame 2");
        setScreen(mainMenu);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        Gdx.app.log("Render", "bunnygame 3");
        super.render();
    }

    public int getAtualLevel() {
        return atualLevel;
    }

    public void newLevel() {
        if(this.atualLevel <2)
            this.atualLevel = 2;
    }

    public void setToMainMenu(){
        setScreen(mainMenu);
    }

    public void setToGameOverMenu(){
        setScreen(gameOverMenu);
    }

    public void setToPauseMenu(){
        setScreen(pauseMenu);
    }

    public void setToPlayScreen(){
        logic = new GameLogic(atualLevel);
        playScreen = new PlayScreen(this);
        setScreen(playScreen);
    }

    public void setToSamePlayScreen() {
        playScreen.input();
        setScreen(playScreen);
    }

    public void setToHighScoreMenu() {
        highScoreMenu.setLabel();
        setScreen(highScoreMenu);
    }

    public void loadFile(){
        file = Gdx.files.local("highscore.dat");

        if(!file.exists()){
            highscore = 0;
            Gdx.app.log("Highscore", "Passei");
        }
        else {
            java.lang.String text;
            text = file.readString();
            highscore = Integer.parseInt(text);
           // highscore = 0;
            Gdx.app.log("Highscore", "" + highscore);
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
}

