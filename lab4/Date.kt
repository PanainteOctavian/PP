class Date {
    private var data: String

    constructor(){
        data = ""
    }

    constructor(
        _data: String
    ) {
        data = _data
    }

    fun getData(): String {
        return data.toString()
    }

}