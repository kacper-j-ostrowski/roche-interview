package pl.ostrowski.rocheproject.dto;

import java.io.Serializable;

public class IpVigilanteResponseWrapper implements Serializable {

    private String status;
    private IpVigilanteResponse data;

    public IpVigilanteResponseWrapper() {}

    public IpVigilanteResponseWrapper(String status, IpVigilanteResponse data) {
        this.status = status;
        this.data = data;
    }

    public IpVigilanteResponse getData() {
        return data;
    }

    public void setData(IpVigilanteResponse data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
