fun main() {
    ///*
    val d = Date("1.1.2031")
    var b = BankAccount(10.0, "8888 8888 8888 8888", d, 999, "octavian")
    println("Initial: ")
    b.print()
    println("-".repeat(100))

    b.updateAmount(-1.2)
    b.print()
    println("-".repeat(100))

    b.updateAmount(1245.0)
    b.print()
    println("-".repeat(100))

    b.updateAmount(0.0)
    b.print()
    println("-".repeat(100))
    println("-".repeat(100))

    println("Plata cu cardul:")
    b.print()
    val cardpay = CardPayment(b)
    cardpay.pay(12.2)
    b.print()
    println("-".repeat(100))
    println("-".repeat(100))
    //*/
    println("Plata cash:")
    val cashpay = CashPayment(100.0)
    cashpay.print()
    cashpay.pay(12.2)
    cashpay.print()
}
