package capital.novum.concordia.customview

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.TextView
import capital.novum.concordia.R
import capital.novum.concordia.transaction.SpinnerAdapter
import kotlinx.android.synthetic.main.edit_spinner.view.*

class EditSpinner : FrameLayout, AdapterView.OnItemSelectedListener {
    private val tag = javaClass.toString()
    lateinit var source: List<String>
    var delegate: OnEditSpinnerChanged? = null
    var selected = 0

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.edit_spinner, this, true)
        spinner.onItemSelectedListener = this
        dropdownBtn.setOnClickListener { spinner.performClick() }
    }

    fun setData(data: List<String>) {
        source = data
        spinner.adapter = SpinnerAdapter(context!!, data)
    }

    fun setText(content: String) {
        edittext.setText(content, TextView.BufferType.EDITABLE)
    }

    fun preventEdit() {
        edittext.isEnabled = false
    }

    fun changeBackground(background: Int) {
        mainLayout.setBackgroundResource(background)
    }

    fun changeTextColor(color: Int) {
        edittext.setTextColor(color)
    }

    fun getSelected() : String {
        return edittext.text.toString()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.e("EditSpinner", source[position])
        edittext.setText(source[position], TextView.BufferType.EDITABLE)
        if (selected++ > 0) {
            delegate?.onEditSpinnerChanged(source[position])
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Log.e(tag, "Keyboard show")
        } else if (newConfig?.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Log.e(tag, "Keyboard hide")
        }
    }

    interface OnEditSpinnerChanged {
        fun onEditSpinnerChanged(selectedItem: String)
    }
}