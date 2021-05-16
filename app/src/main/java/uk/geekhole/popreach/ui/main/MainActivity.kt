package uk.geekhole.popreach.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import uk.geekhole.popreach.R
import uk.geekhole.popreach.ui.global.FragmentManagementInterface

class MainActivity : AppCompatActivity(), FragmentManagementInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(RandomShapesFragment.newInstance(), true)
    }

    override fun showFragment(fragment: Fragment, isRoot: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if (!isRoot) {
            transaction.add(R.id.fragment_container, fragment, fragment.javaClass.canonicalName)
            transaction.addToBackStack(fragment.javaClass.canonicalName)

        } else {
            transaction.replace(R.id.fragment_container, fragment, fragment.javaClass.canonicalName)
            removeAllBackstack()
        }

        transaction.commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }

    override fun removeAllBackstack() {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}