class CardPayment: BankAccount{
    private var bankAccount = BankAccount()

    constructor(): super()
    constructor(ba: BankAccount): super(ba.getAvailableAmount())

    fun pay(fee: Double): Boolean{
        // de adaugat in vectori cu plati
        cardPayments += this
        if (bankAccount.getAvailableAmount() > fee){
            println("Plata reusita")
            bankAccount.updateAmount(-fee)
            return true
        }
        println("Plata NEreusita")
        return false
    }
}