package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 8/21/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class VersionControl {

    @SerializedName("Version")
    @Expose
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
