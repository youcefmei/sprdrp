package com.youcefmei.sparadrap.service;

import com.youcefmei.sparadrap.dao.PurchaseDAO;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.model.Medicament;
import com.youcefmei.sparadrap.model.Purchase;
import javafx.collections.ObservableList;
import lombok.Getter;

public class PurchaseService {

    private PurchaseDAO purchaseDAO = new PurchaseDAO();

    @Getter
    private ObservableList<Purchase> purchases ;

    public PurchaseService() {
        purchases = purchaseDAO.findAllObservable();
    }

    private void checkPurchaseDuplicate(String purchaseId ) throws DuplicateException {
        for (Purchase purchaseTemp : purchases) {
            if (purchaseTemp.getRef().equals(purchaseId)) {
                throw new DuplicateException("Il y a déja une facture avec ce numéro");
            }
        }
    }

    /**
     * Add purchase.
     *
     * @param purchase the purchase
     * @throws DuplicateException the duplicate exception
     * @throws PaymentException   the payment exception
     */
    public Purchase addPurchase(Purchase purchase) throws DuplicateException, PaymentException {
        checkPurchaseDuplicate(purchase.getRef());
        purchase = purchaseDAO.create(purchase);
        if (purchase.isPaid() ){
            purchases.add(purchase);
            return purchase;
        }
        else{
            throw new PaymentException("Le paiement n'est pas encore effectué");
        }
    }



}
