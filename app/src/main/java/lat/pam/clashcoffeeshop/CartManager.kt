package lat.pam.clashcoffeeshop

object CartManager {
    private val quantities = LinkedHashMap<String, Int>()

    fun add(item: FoodItem) {
        quantities[item.name] = (quantities[item.name] ?: 0) + 1
    }

    fun remove(item: FoodItem) {
        val current = quantities[item.name] ?: return
        if (current <= 1) {
            quantities.remove(item.name)
        } else {
            quantities[item.name] = current - 1
        }
    }

    fun qty(item: FoodItem): Int = quantities[item.name] ?: 0

    fun isEmpty(): Boolean = quantities.isEmpty()

    fun clear() {
        quantities.clear()
    }

    fun items(): List<FoodItem> {
        val list = ArrayList<FoodItem>()
        for (item in FoodData.items) {
            if (qty(item) > 0) list.add(item)
        }
        return list
    }

    fun totalItems(): Int {
        var total = 0
        for (value in quantities.values) total += value
        return total
    }

    fun totalPrice(): Int {
        var total = 0
        for (item in FoodData.items) total += qty(item) * item.price
        return total
    }

    fun summary(): String {
        val lines = ArrayList<String>()
        for (item in FoodData.items) {
            val q = qty(item)
            if (q > 0) {
                lines.add(q.toString() + "x " + item.name + " - " + formatRupiah(q * item.price))
            }
        }
        return lines.joinToString("\n")
    }

    fun formatRupiah(value: Int): String {
        val digits = value.toString()
        val sb = StringBuilder()
        for (i in digits.indices) {
            if (i > 0 && (digits.length - i) % 3 == 0) sb.append('.')
            sb.append(digits[i])
        }
        return "Rp " + sb.toString()
    }
}
