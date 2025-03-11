fun main() {
    val d = Date("1.1.2031")
    var b = BankAccount(1000.2, "123", d, 999, "dexter")
    b.print()
    b.updateAmount(-1.2)
    b.updateAmount(1245.66)

    val cpay = CardPayment()
    b.print()
    cpay.pay(12.2)
    b.print()
}
