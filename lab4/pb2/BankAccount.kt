class BankAccount {
    private var availableAmount: Double
    private val cardNumber: String
    private val expirationDate: Date
    private val cvvCode: Int
    private val userName: String

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
        print("availableAmount: " + "$availableAmount, ")
        print("cardNumber: " + "$cardNumber, ")
        print("expirationDate: "+ expirationDate.getData() + ", ")
        print("cvvCode: " + "$cvvCode, ")
        print("userName: " + "$userName\n")
    }

    fun updateAmount(value: Double): Boolean {
        if (value != 0.0){
            availableAmount += value
            if (value > 0){
                println("Adaugare reusita")
            }
            else{
                println("Scadere reusita")
            }
            return true
        }
        println("Operatie NEreusita: caz 0.0")
        return false

    }

    fun getAvailableAmount(): Double{
        return availableAmount
    }
}