package prototype

class GenericRequest(var url : String, var params : Map<String, String>?): Clonable {
    override fun clone() : Clonable {
        return GenericRequest(url, params?.toMutableMap())
    }
}