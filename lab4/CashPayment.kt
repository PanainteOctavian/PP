class CashPayment: PaymentMethod {
    private var availableAmount: Double

    constructor(){
        availableAmount = -1.0
    }

    constructor(
        _availableAmount: Double,
    ){
        availableAmount = _availableAmount
    }

    override
    fun pay(fee: Double): Boolean {
        if (availableAmount >= fee) {
            availableAmount -= fee
            println("Plata reusita")
            return true
        }
        println("Plata NEreusita")
        return false
    }

    fun print(){
        print("availableAmount: " + "$availableAmount\n")
    }
}