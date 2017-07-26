
package com.batchmates.android.makingrestcalls.Model.weather;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys implements Serializable
{

    @SerializedName("pod")
    @Expose
    private String pod;
    private final static long serialVersionUID = -3182105543841342161L;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

}
