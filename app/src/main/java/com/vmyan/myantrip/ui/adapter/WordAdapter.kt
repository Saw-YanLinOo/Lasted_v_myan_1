package com.vmyan.myantrip.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.entities.Word
import com.vmyan.myantrip.ui.fragment.Communication
import kotlinx.android.synthetic.main.word_item_recyclerview.view.*

class WordAdapter(private val listener: WordAdapter.ItemClickListener, _wordList:MutableList<Word>) : RecyclerView.Adapter<WordAdapter.WordViewHolder>(){

    var wordList:MutableList<Word> = _wordList

    interface ItemClickListener {
        fun onWordClick(position : Int)
    }

    fun setItems(wordList:List<Word>){
        this.wordList.clear()
        this.wordList.addAll(wordList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_item_recyclerview,parent,false)
        return WordViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordList[position])
    }

    class WordViewHolder(private val view: View, private val listener: WordAdapter.ItemClickListener) : RecyclerView.ViewHolder(view),View.OnClickListener{

        init {
            view.setOnClickListener(this)
        }

        fun bind(word:Word){
            when(Communication().language.value.toString()){
                "myanmar" -> {
                    itemView.parent_font.text = word.myn
                }
                "english" -> {
                itemView.parent_font.text = word.eng
                }
                else -> itemView.parent_font.text = word.myn
            }
//            itemView.parent_font.text = word.eng
//            itemView.child_font.text = word.myn
            itemView.setOnClickListener{

//                var gson: Gson = GsonBuilder().create()
//                val intent = Intent(context, CommunicationActivity::class.java)
//                intent.putExtra("Word", gson.toJson(word))
//                context.startActivity(intent)
            }
        }

        override fun onClick(p0: View?) {
            listener.onWordClick(adapterPosition)
        }
    }

}