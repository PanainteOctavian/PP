class CardPayment: PaymentMethod {
    private var bankAccount: BankAccount

    constructor() {
        bankAccount = BankAccount()
    }

    constructor(ba: BankAccount) {
        bankAccount = ba
    }

    override
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