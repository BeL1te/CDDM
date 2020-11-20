package laba2

class Alpha(private var equation: String) {

    private var binaryAlpha: Array<Int>? = null
    fun setBinaryAlpha(value: Array<Int>?) {
        binaryAlpha = value
    }
    fun getBinaryAlpha(): Array<Int>? {
        return binaryAlpha
    }

    private var position: Int? = null
    fun setPosition(value: Int?) {
        position = value
    }
    fun getPosition(): Int? {
        return position
    }

    fun getEquation(): String {
        return equation
    }

    fun setEquation(value: String) {
        this.equation = value
    }

    override fun toString(): String {
        return "$position: $equation ${binaryAlpha.contentToString()}"
    }
}
