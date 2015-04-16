package evasion.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * A class that manages all the save data for a game.
 */
public class SaveData {

    private static Preferences saveData = Gdx.app.getPreferences("saveData");

    private static Difficulty difficulty;

    public static float getScore() {
        update();
        return saveData.getFloat("score_for_"+difficulty.toString(), 0f);
    }

    public static float getMoney () {
        update();
        return saveData.getFloat("money_for_" + difficulty.toString(), 0f);
    }

    public static float getTime() {
        update();
        return saveData.getFloat("time_for_"+difficulty.toString(), 0f);
    }

    public static float getSensitivity() {
        update();
        return saveData.getFloat("sensitivity", 0.5f);
    }

    public static Difficulty getDifficulty() {
        update();
        return difficulty;
    }

    public static void setScore(float score) {
        saveData.putFloat("score_for_"+difficulty.toString(), score);
        saveData.flush();
    }

    public static void setMoney(float money) {
        saveData.putFloat("money_for_"+difficulty.toString(), money);
        saveData.flush();
    }

    public static void setTime(float time) {
        saveData.putFloat("time_for_"+difficulty.toString(), time);
        saveData.flush();
    }

    public static void setDifficulty(Difficulty difficulty) {
        saveData.putString("difficulty", difficulty.toString());
        saveData.flush();
    }

    public static void setSensitivity(float sensitivity) {
        saveData.putFloat("sensitivity", sensitivity);
        saveData.flush();
    }

    public static void update() {
        difficulty = Difficulty.valueOf(saveData.getString("difficulty", "NORMAL"));
    }

}
