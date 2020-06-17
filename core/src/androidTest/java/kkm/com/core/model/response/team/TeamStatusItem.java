package kkm.com.core.model.response.team;

import com.google.gson.annotations.SerializedName;

public class TeamStatusItem {

    @SerializedName("teamSize")
    private String teamSize;

    @SerializedName("levelName")
    private String levelName;

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return
                "TeamStatusItem{" +
                        "teamSize = '" + teamSize + '\'' +
                        ",levelName = '" + levelName + '\'' +
                        "}";
    }
}