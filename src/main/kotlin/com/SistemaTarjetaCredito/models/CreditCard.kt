package org.example.com.SistemaTarjetaCredito.models

import java.math.BigDecimal

data class CreditCard(
    val id: String,
    val type: String,
    var creditLimit: BigDecimal,
    var balance: BigDecimal = BigDecimal.ZERO,
    var interestBalance: BigDecimal = BigDecimal.ZERO,
    var rewardPoints: Int = 0,
    var minimumPayment: BigDecimal = BigDecimal.ZERO
) {
    init {
        require(creditLimit > BigDecimal.ZERO) {"El limite del crédito debe ser mayor a cero."}
        require(rewardPoints >= 0) {"Los puntos de recompensa no pueden ser negativos."}
    }

    fun addRewardPoints(points: Int) {
        require(points >= 0) {"Los puntos a añadir deben ser positivos."}
        rewardPoints += points
    }
    fun updateMinimumPayment(amount: BigDecimal) {
        minimumPayment = amount
    }
}
