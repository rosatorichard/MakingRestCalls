
package com.batchmates.android.makingrestcalls.Model.weather;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain implements Serializable
{

    @SerializedName("3h")
    @Expose
    private Double _3h;
    private final static long serialVersionUID = 3596600987557278781L;

    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }

}
