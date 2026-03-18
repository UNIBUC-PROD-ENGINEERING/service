package ro.unibuc.prodeng.request;

import jakarta.validation.constraints.NotNull;
import ro.unibuc.prodeng.model.MasinaStatus;

public class UpdateMasinaStatusRequest {

    @NotNull(message = "Statusul este obligatoriu")
    private MasinaStatus status;

    public UpdateMasinaStatusRequest() {}

    public UpdateMasinaStatusRequest(MasinaStatus status) {
        this.status = status;
    }

    public MasinaStatus getStatus() {
        return status;
    }

    public void setStatus(MasinaStatus status) {
        this.status = status;
    }
}
