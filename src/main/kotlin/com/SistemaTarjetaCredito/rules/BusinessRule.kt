package com.SistemaTarjetaCredito.rules

import org.example.com.SistemaTarjetaCredito.models.CreditCard
import java.math.BigDecimal

interface BusinessRule {
    fun apply(card: CreditCard, amount: BigDecimal): BigDecimal
}