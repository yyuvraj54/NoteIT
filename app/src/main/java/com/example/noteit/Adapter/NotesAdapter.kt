package com.example.noteit.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteit.Models.Note
import com.example.noteit.R
import kotlinx.coroutines.currentCoroutineContext
import kotlin.random.Random

class NotesAdapter(private val context :Context ,val listener:NotesClickListener):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


    private val NotesList = ArrayList<Note>()
    private val fulllist = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }
    fun updateList(newList:List<Note>){
        fulllist.clear()
        fulllist.addAll(newList)
        NotesList.clear()
        NotesList.addAll(fulllist)
        notifyDataSetChanged()
    }

    fun filterList(search:String){
        NotesList.clear()
        for(item in fulllist){
            if(item.title?.lowercase()?.contains(search.lowercase())==true ||
                item.note?.lowercase()?.contains(search.lowercase())==true ){
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun RandomColor() : Int{
        val list =ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)

        val seed=System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return  list[randomIndex]
    }




    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val curentNote = NotesList[position]
        holder.title.text= curentNote.title
        holder.title.isSelected = true
        holder.Note_tv.text= curentNote.note
        holder.date.text= curentNote.date
        holder.date.isSelected =true

        holder.note_layout.setCardBackgroundColor(holder.itemView.resources.getColor(RandomColor(),null))

        holder.note_layout.setOnClickListener{
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }
        holder.note_layout.setOnLongClickListener {
            listener.onLongItemClicked(NotesList[holder.adapterPosition],holder.note_layout)
            true
        }
    }


    inner class NoteViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        val note_layout=itemView.findViewById<CardView>(R.id.card_layout)
        val title=itemView.findViewById<TextView>(R.id.tv_title)
        val Note_tv=itemView.findViewById<TextView>(R.id.tv_note)
        val date=itemView.findViewById<TextView>(R.id.tv_date)

    }

    interface NotesClickListener{
        fun onItemClicked(note:Note)
        fun onLongItemClicked(note:Note,cardView:CardView)

    }

}