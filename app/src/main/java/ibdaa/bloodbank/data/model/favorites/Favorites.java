
package ibdaa.bloodbank.data.model.favorites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorites {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private FavoritesPagination data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FavoritesPagination getData() {
        return data;
    }

    public void setData(FavoritesPagination data) {
        this.data = data;
    }

}
