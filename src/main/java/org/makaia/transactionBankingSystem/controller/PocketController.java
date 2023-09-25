package org.makaia.transactionBankingSystem.controller;

import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketConsultOut;
import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketCreation;
import org.makaia.transactionBankingSystem.dto.dtoPocket.DTOPocketTransfer;
import org.makaia.transactionBankingSystem.exception.ApiException;
import org.makaia.transactionBankingSystem.service.PocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pockets")
public class PocketController {

    PocketService pocketService;
    @Autowired
    public PocketController(PocketService pocketService){
        this.pocketService = pocketService;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<DTOPocketConsultOut> getPockets (@PathVariable Long accountNumber) {
        return this.pocketService.getPocketsByAccountNumber(accountNumber);
    }

    @PostMapping
    public ResponseEntity<DTOPocketCreation> createPocket(@RequestBody DTOPocketCreation dtoPocketCreation) throws ApiException {
        return this.pocketService.createPocket(dtoPocketCreation);
    }

    @PostMapping("/transfer")
    public ResponseEntity<DTOPocketTransfer> transferToPocket(@RequestBody DTOPocketTransfer dtoPocketTransfer) throws ApiException{
        return this.pocketService.transferToPocket(dtoPocketTransfer);
    }
}
