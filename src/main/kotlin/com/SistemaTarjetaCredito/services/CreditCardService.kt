package com.SistemaTarjetaCredito.services

import com.SistemaTarjetaCredito.PenaltyRule
import com.SistemaTarjetaCredito.rules.BusinessRule
import com.SistemaTarjetaCredito.rules.InteresRule
import org.example.com.SistemaTarjetaCredito.models.CreditCard
import java.math.BigDecimal

class CreditCardService(private val rules: List<BusinessRule>,
                        private val penaltyRule: PenaltyRule
) {

    fun processTransaction(card: CreditCard, amount: BigDecimal) {
        require (amount > BigDecimal.ZERO) {"La cantidad de la transacción debe ser mayor a cero"}

        rules.forEach { rule -> rule.apply(card, amount) }
        card.balance += amount
    }

    fun applyPayment(card: CreditCard, payment: BigDecimal) {
        require(payment > BigDecimal.ZERO) {"El pago debe ser mayor a cero"}
        require(payment <= card.balance) {"El pago no puede ser mayor que el saldo actual"}
        var remainingPayment = payment

        // 1. Aplicar pago a los intereses pendientes
        if (card.interestBalance > BigDecimal.ZERO) {
            val interestPayment = remainingPayment.min(card.interestBalance)
            card.interestBalance -= interestPayment
            remainingPayment -= interestPayment
        }

        // 2. Aplicar el resto del pago al saldo principal
        if (remainingPayment > BigDecimal.ZERO && card.balance > BigDecimal.ZERO) {
            val principalPayment = remainingPayment.min(card.balance)
            card.balance -= principalPayment
            remainingPayment -= principalPayment
        }

        // 3. Liberar el crédito disponible según el pago total
        card.creditLimit += payment
    }

    fun applyMonthlyInterest(card: CreditCard) {
        //aplica interes si el balance es mayor a cero
        rules.filterIsInstance<InteresRule>().forEach { interesRule ->
            card.balance += card.balance * interesRule.interestRate
        }
    }

    fun checkAndApplyPenalty(card: CreditCard) {
        // Verificar si el pago mínimo ha sido cubierto
        if (card.minimumPayment > card.balance) {
            penaltyRule.applyPenalty(card)
            println("Penalización aplicada por no cubrir el pago mínimo.")
        } else {
            println("El pago mínimo ha sido cubierto. No se aplican penalizaciones.")
        }
    }
}