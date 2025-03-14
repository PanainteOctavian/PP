open class BankAccount {
    private var availableAmount: Double
    private val cardNumber: String
    private val expirationDate: Date
    private val cvvCode: Int
    private val userName: String
    var cardPayments: Array<CardPayment> = emptyArray()

    constructor() {
        availableAmount = -1.0
        cardNumber = ""
        expirationDate = Date("-")
        cvvCode = -1
        userName = ""
    }

    constructor(
        _availableAmount: Double,
        _cardNumber: String,
        _expirationDate: Date,
        _cvvCode: Int,
        _userName: String
    ) {
        availableAmount = _availableAmount
        cardNumber = _cardNumber
        expirationDate = _expirationDate
        cvvCode = _cvvCode
        userName = _userName
    }

    fun print(){
        print("$availableAmount $cardNumber " + expirationDate.data + " $cvvCode $userName\n")
    }

    fun updateAmount(value: Double): Boolean {
        if (value > 0) {
            availableAmount += value
            // println("Adaugare reusita")
            return true
        }
        // println("Adaugare NEreusita")
        return false
    }

    fun getAvailableAmount(): Double{
        return availableAmount
    }
}