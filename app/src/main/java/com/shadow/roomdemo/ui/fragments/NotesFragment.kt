package com.shadow.roomdemo.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.shadow.roomdemo.R
import com.shadow.roomdemo.db.AppDatabase
import com.shadow.roomdemo.db.Note
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.note_item.view.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class NotesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesRecyclerView.setHasFixedSize(true)
        notesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = AppDatabase(it).getNoteDao().getAllNotes()
                notesRecyclerView.adapter = NotesAdapter(notes)
            }
        }

        addFab.setOnClickListener {

            val action = NotesFragmentDirections.actionAddNote()

            Navigation.findNavController(it).navigate(action)

        }
    }


}


class NotesAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.NoteHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.view.titleTextView.text = notes[position].title
        holder.view.contentTextView.text = notes[position].content
    }

    class NoteHolder(val view: View) : RecyclerView.ViewHolder(view)
}
