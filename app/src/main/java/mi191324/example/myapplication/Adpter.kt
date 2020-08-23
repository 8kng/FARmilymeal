package mi191324.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class Adapter(manager: FragmentManager?, messageFragment: MessageFragment) :
    FragmentStatePagerAdapter(manager!!) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()
    private val mFragmentTitleList: MutableList<String> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    fun addFrag(fragment: MessagecreateFragment) {
        mFragmentList.add(MessagecreateFragment())
        mFragmentTitleList.add("メッセージ作成")
    }

    fun addFrag(fragment: QuestioncreateFragment) {
        mFragmentList.add(QuestioncreateFragment())
        mFragmentTitleList.add("質問作成")
    }
}