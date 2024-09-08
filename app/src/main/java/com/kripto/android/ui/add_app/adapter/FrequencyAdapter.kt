package com.kripto.android.ui.add_app.adapter

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kripto.android.R

class FrequencyAdapter(
    context: Context,
    list: List<String>
) : ArrayAdapter<String>(context, 0, list) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: layoutInflater.inflate(R.layout.item_frequency, parent, false)
        getItem(position)?.let { country ->
            setItemForHour(view, country)
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.item_header_frequency, parent, false)
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
        } else {
            view = layoutInflater.inflate(R.layout.item_frequency_dropdown, parent, false)
            getItem(position)?.let { country ->
                setItemForHour(view, country)
            }
        }
        return view
    }

    override fun getCount(): Int {
        return super.getCount() + 1 // Esto añade un item adicional (por ejemplo, una cabecera)
    }

    override fun getItem(position: Int): String? {
        return if (position == 0) {
            // Aquí devuelves lo que se quiere mostrar en la cabecera, por ejemplo un título
            null
        } else {
            super.getItem(position - 1) // Para todos los demás elementos, ajusta la posición
        }
    }

    override fun isEnabled(position: Int) = position != 0

    private fun setItemForHour(view: View, hour: String) {

        val tvCountry = view.findViewById<TextView>(R.id.tvHour)
        val ivCountry = view.findViewById<ImageView>(R.id.ivArrowDown)

        tvCountry.text = hour
    }
}