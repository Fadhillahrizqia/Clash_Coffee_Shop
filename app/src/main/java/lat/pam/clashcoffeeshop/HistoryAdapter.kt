package lat.pam.clashcoffeeshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    private val orders: List<OrderRecord>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderId: TextView = view.findViewById(R.id.text_order_id)
        val status: TextView = view.findViewById(R.id.text_status)
        val time: TextView = view.findViewById(R.id.text_time)
        val ringkasan: TextView = view.findViewById(R.id.text_items)
        val address: TextView = view.findViewById(R.id.text_address)
        val method: TextView = view.findViewById(R.id.text_method)
        val total: TextView = view.findViewById(R.id.text_total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        val context = holder.itemView.context

        holder.orderId.text = order.id
        holder.time.text = order.timeText()
        holder.ringkasan.text = order.ringkasan
        holder.address.text = "Dikirim ke: " + order.alamat
        holder.method.text = order.metode
        holder.total.text = CartManager.formatRupiah(order.total)
        holder.status.text = order.status

        if (order.status.startsWith("Dibayar")) {
            holder.status.setBackgroundResource(R.drawable.bg_chip_paid)
            holder.status.setTextColor(context.getColor(R.color.mint))
        } else {
            holder.status.setBackgroundResource(R.drawable.bg_price)
            holder.status.setTextColor(context.getColor(R.color.accent_light))
        }
    }

    override fun getItemCount(): Int = orders.size
}
