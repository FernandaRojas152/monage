package edu.icesi.monage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import edu.icesi.monage.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {

    //Fragments
    private lateinit var homeFragment: HomeFragment
    private lateinit var paymentsFragment: PaymentsFragment
    private lateinit var transactionsFragment: TransactionsFragment
    private lateinit var communityFragment: CommunityFragment

    private val binding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        homeFragment = HomeFragment.newInstance()
        paymentsFragment = PaymentsFragment.newInstance()
        transactionsFragment = TransactionsFragment.newInstance()
        communityFragment = CommunityFragment.newInstance()

        showFragment(homeFragment)

        binding.navigatorBtn.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.principalItem) {
                showFragment(homeFragment)
            } else if (menuItem.itemId == R.id.pagosItem) {
                showFragment(paymentsFragment)
            }else if (menuItem.itemId == R.id.accionesItem) {
                showFragment(transactionsFragment)
            }else if (menuItem.itemId == R.id.comunidadItem) {
                showFragment(communityFragment)
            }
            true
        }
    }

    //Muestra el fragmento
    fun showFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}