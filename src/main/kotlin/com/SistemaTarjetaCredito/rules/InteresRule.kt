package com.SistemaTarjetaCredito.rules

import org.example.com.SistemaTarjetaCredito.models.CreditCard
import java.math.BigDecimal

class InteresRule(internal val interestRate: BigDecimal): BusinessRule {
    override fun apply(card: CreditCard, amount: BigDecimal): BigDecimal {
        if (card.balance > BigDecimal.ZERO) {
            card.balance += card.balance * interestRate
        }
        return card.balance
    }
}