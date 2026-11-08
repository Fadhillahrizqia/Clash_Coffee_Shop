package lat.pam.clashcoffeeshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val onChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var items: List<FoodItem> = CartManager.items()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: TextView = view.findViewById(R.id.text_emoji)
        val name: TextView = view.findViewById(R.id.text_name)
        val unit: TextView = view.findViewById(R.id.text_unit)
        val subtotal: TextView = view.findViewById(R.id.text_subtotal)
        val minus: TextView = view.findViewById(R.id.text_minus)
        val qty: TextView = view.findViewById(R.id.text_qty)
        val plus: TextView = view.findViewById(R.id.text_plus)
    }

    fun refresh() {
        items = CartManager.items()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val jumlah = CartManager.qty(item)
        holder.emoji.text = item.emoji
        holder.name.text = item.name
        holder.unit.text = CartManager.formatRupiah(item.price) + " / item"
        holder.subtotal.text = CartManager.formatRupiah(item.price * jumlah)
        holder.qty.text = jumlah.toString()

        holder.plus.setOnClickListener {
            CartManager.add(item)
            refresh()
            onChanged()
        }
        holder.minus.setOnClickListener {
            CartManager.remove(item)
            refresh()
            onChanged()
        }
    }

    override fun getItemCount(): Int = items.size
}
