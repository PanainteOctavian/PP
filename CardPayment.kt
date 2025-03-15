class CardPayment {
    private var bankAccount: BankAccount

    constructor() {
        bankAccount = BankAccount()
    }

    constructor(ba: BankAccount) {
        bankAccount = ba
    }

    fun pay(fee: Double): Boolean {
        if (bankAccount.getAvailableAmount() >= fee) {
            bankAccount.updateAmount(-fee)
            println("Plata reusita")
            return true
        }
        println("Plata NEreusita")
        return false
    }

}