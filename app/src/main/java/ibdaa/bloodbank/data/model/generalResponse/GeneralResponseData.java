
package ibdaa.bloodbank.data.model.generalResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralResponseData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("governorate_id")
    @Expose
    private String governorateId;
    @SerializedName("governorate")
    @Expose
    private GeneralResponseData governorate;

    public GeneralResponseData(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGovernorateId() {
        return governorateId;
    }

    public void setGovernorateId(String governorateId) {
        this.governorateId = governorateId;
    }

    public GeneralResponseData getGovernorate() {
        return governorate;
    }

    public void setGovernorate(GeneralResponseData governorate) {
        this.governorate = governorate;
    }
}
