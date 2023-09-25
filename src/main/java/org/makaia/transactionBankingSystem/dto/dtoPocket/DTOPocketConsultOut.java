package org.makaia.transactionBankingSystem.dto.dtoPocket;

import java.util.List;

public class DTOPocketConsultOut {
    private List<DTOPocketConsultIn> pockets;

    public DTOPocketConsultOut() {
    }

    public DTOPocketConsultOut(List<DTOPocketConsultIn> pockets) {
        this.pockets = pockets;
    }

    public List<DTOPocketConsultIn> getPockets() {
        return pockets;
    }

    public void setPockets(List<DTOPocketConsultIn> pockets) {
        this.pockets = pockets;
    }
}
