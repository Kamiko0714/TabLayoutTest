import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tablayouttest.IndividualResponse
import com.example.tablayouttest.R
import org.jetbrains.annotations.NotNull

class DbAdapter(private val dataSet: List<IndividualResponse>, private val onClickListener: (IndividualResponse) -> Unit) :
    RecyclerView.Adapter<DbAdapter.ViewHolder>(){

    class ViewHolder(view: View, clickAtPosition: (Int) -> Unit): RecyclerView.ViewHolder(view){
        val Name : TextView = view.findViewById(R.id.Name)
        val picture : ImageView = view.findViewById(R.id.picture)

        init {
            itemView.setOnClickListener{clickAtPosition(adapterPosition)
            }
        }
    }

    @NotNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.tile,
            parent, false)){onClickListener(dataSet[it])
        }
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dataSet[position]
        holder.Name.text = data.login.toString()
        Glide.with(holder.picture.context).load(data.avatarUrl).into(holder.picture)
    }

    override fun getItemCount() = dataSet.size
}
