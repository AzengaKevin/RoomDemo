package com.shadow.roomdemo.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.shadow.roomdemo.R
import com.shadow.roomdemo.db.AppDatabase
import com.shadow.roomdemo.db.Note
import com.shadow.roomdemo.ui.utils.toast
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class AddNoteFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        saveFab.setOnClickListener { view ->

            val title = titleInput.text.toString().trim()
            val content = contentInput.text.toString().trim()

            if (title.isEmpty()) {
                titleInput.error = "Title is required"
                titleInput.requestFocus()
                return@setOnClickListener
            }

            if (content.isEmpty()) {
                contentInput.error = "Content is required"
                contentInput.requestFocus()
                return@setOnClickListener
            }

            launch {

                val note = Note(title, content)
                context?.let {
                    AppDatabase(it).getNoteDao().addNote(note)

                    it.toast("Note saved")

                    val action = AddNoteFragmentDirections.actionAfterNoteAdded()

                    Navigation.findNavController(view).navigate(action)
                }

            }

        }
    }


}
