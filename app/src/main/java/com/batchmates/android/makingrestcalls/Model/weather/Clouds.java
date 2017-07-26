
package com.batchmates.android.makingrestcalls.Model.weather;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds implements Serializable
{

    @SerializedName("all")
    @Expose
    private Integer all;
    private final static long serialVersionUID = -994574131711848853L;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

}
