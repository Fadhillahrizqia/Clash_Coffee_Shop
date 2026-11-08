package lat.pam.clashcoffeeshop

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class OrderRecord(
    val id: String,
    val time: Long,
    val nama: String,
    val alamat: String,
    val patokan: String,
    val ringkasan: String,
    val total: Int,
    val metode: String,
    val status: String
) {
    fun timeText(): String =
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.forLanguageTag("id-ID")).format(Date(time))
}

object OrderHistory {
    private const val PREF = "clash_history"
    private const val KEY = "orders"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun getAll(context: Context): List<OrderRecord> {
        val raw = prefs(context).getString(KEY, null) ?: return emptyList()
        val result = ArrayList<OrderRecord>()
        try {
            val array = JSONArray(raw)
            for (i in 0 until array.length()) {
                val o = array.getJSONObject(i)
                result.add(
                    OrderRecord(
                        o.getString("id"),
                        o.getLong("time"),
                        o.getString("nama"),
                        o.getString("alamat"),
                        o.optString("patokan", ""),
                        o.getString("ringkasan"),
                        o.getInt("total"),
                        o.getString("metode"),
                        o.getString("status")
                    )
                )
            }
        } catch (e: Exception) {
            return emptyList()
        }
        return result
    }

    fun getById(context: Context, id: String): OrderRecord? {
        for (record in getAll(context)) {
            if (record.id == id) return record
        }
        return null
    }

    // Menyimpan isi keranjang sebagai pesanan baru (terbaru di urutan teratas), lalu mengosongkan keranjang.
    fun place(
        context: Context,
        nama: String,
        alamat: String,
        patokan: String,
        metode: String,
        status: String
    ): OrderRecord {
        val existing = getAll(context)
        val id = "CCS-" + (existing.size + 1).toString().padStart(4, '0')
        val record = OrderRecord(
            id,
            System.currentTimeMillis(),
            nama,
            alamat,
            patokan,
            CartManager.summary(),
            CartManager.totalPrice(),
            metode,
            status
        )
        val all = ArrayList<OrderRecord>()
        all.add(record)
        all.addAll(existing)
        save(context, all)
        CartManager.clear()
        return record
    }

    private fun save(context: Context, list: List<OrderRecord>) {
        val array = JSONArray()
        for (r in list) {
            val o = JSONObject()
            o.put("id", r.id)
            o.put("time", r.time)
            o.put("nama", r.nama)
            o.put("alamat", r.alamat)
            o.put("patokan", r.patokan)
            o.put("ringkasan", r.ringkasan)
            o.put("total", r.total)
            o.put("metode", r.metode)
            o.put("status", r.status)
            array.put(o)
        }
        prefs(context).edit().putString(KEY, array.toString()).apply()
    }
}
