package com.SistemaTarjetaCredito

import org.example.com.SistemaTarjetaCredito.models.CreditCard
import java.math.BigDecimal

class PenaltyRule(
    private val lateFee: BigDecimal,
    private val interestPenaltyRate: BigDecimal
){
    fun applyPenalty(card: CreditCard) {
        // Aplica un cargo por retraso y un aumento en la tasa de interés
        card.balance += lateFee
        card.balance += card.balance * interestPenaltyRate
    }
}