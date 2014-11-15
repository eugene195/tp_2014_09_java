package global.engine;

import org.json.JSONObject;

/**
 * Created by max on 15.11.14.
 */
public class Params {
    public int playersCnt;
    public int width;
    public int height;
    public int speed;

    public void setParams(JSONObject json) {
        playersCnt = json.getInt("playersCnt");
        width = json.getInt("width");
        height = json.getInt("height");
        speed = json.getInt("speed");
    }

    public void toJson(JSONObject json) {
        json.put("playersCnt", playersCnt);
        json.put("width", width);
        json.put("height", height);
        json.put("speed", speed);
    }
}
