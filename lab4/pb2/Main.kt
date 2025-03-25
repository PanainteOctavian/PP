fun main() {
    print("cash = ")
    val cash  = readln().toDouble()
    val cashpay = CashPayment(cash)
    println("-".repeat(30))

    print("card = ")
    val card  = readln().toDouble()
    val d = Date("1.1.2031")
    var b = BankAccount(card, "8888 8888 8888 8888", d, 999, "octavian")
    println("-".repeat(30))

    while (true) {
        println("1. Cumpara bilet")
        println("2. Iesire")
        print("Alegere: ")

        when (readlnOrNull()) {
            "1" -> {
                println("-".repeat(30))
                println("Cum doriti sa platiti: ")
                println("1. Cash")
                println("2. Card")
                print("Alegeti: ")
                val paymentChoice = readlnOrNull()
                when (paymentChoice){
                    "1" ->{
                        cashpay.print()
                        cashpay.pay(10.0) // val bilet
                        cashpay.print()
                    }
                    "2" ->{
                        println(b.getAvailableAmount())
                        val cardpay = CardPayment(b)
                        cardpay.pay(10.0) // val bilet
                        println(b.getAvailableAmount())
                    }
                }
                println("-".repeat(30))
            }
            "2" -> {
                println("O zi buna!")
                return
            }
            else -> println("Nu exista aceasta optiune.")
        }
    }
}