package capital.novum.concordia.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import capital.novum.concordia.R
import kotlinx.android.synthetic.main.setting_item.view.*

class SettingItem: LinearLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.setting_item, this, true)
    }

    fun setSettingIcon(src: Int) {
        settingIcon.setImageResource(src)
    }

    fun setSettingName(name: String) {
        settingName.text = name
    }
}