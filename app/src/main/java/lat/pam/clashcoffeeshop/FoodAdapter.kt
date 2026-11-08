package lat.pam.clashcoffeeshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(
    private val items: List<FoodItem>,
    private val onAddClick: (FoodItem) -> Unit,
    private val onRemoveClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: TextView = view.findViewById(R.id.text_emoji)
        val name: TextView = view.findViewById(R.id.text_name)
        val desc: TextView = view.findViewById(R.id.text_desc)
        val price: TextView = view.findViewById(R.id.text_price)
        val minus: TextView = view.findViewById(R.id.text_minus)
        val qty: TextView = view.findViewById(R.id.text_qty)
        val add: TextView = view.findViewById(R.id.text_add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.emoji.text = item.emoji
        holder.name.text = item.name
        holder.desc.text = item.description
        holder.price.text = CartManager.formatRupiah(item.price)

        val jumlah = CartManager.qty(item)
        if (jumlah > 0) {
            holder.qty.text = jumlah.toString()
            holder.qty.visibility = View.VISIBLE
            holder.minus.visibility = View.VISIBLE
        } else {
            holder.qty.visibility = View.GONE
            holder.minus.visibility = View.GONE
        }

        holder.add.setOnClickListener {
            onAddClick(item)
            notifyItemChanged(position)
        }
        holder.minus.setOnClickListener {
            onRemoveClick(item)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = items.size
}
