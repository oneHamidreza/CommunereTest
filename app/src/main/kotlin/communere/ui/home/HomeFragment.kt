package communere.ui.home

import communere.R
import communere.databinding.FragmentHomeBinding
import meow.core.ui.MeowFragment
import meow.ktx.instanceViewModel

/**
 * Home Fragment class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-12-21
 */

class HomeFragment : MeowFragment<FragmentHomeBinding>() {

    private val viewModel : HomeViewModel by instanceViewModel()
    override fun layoutId() = R.layout.fragment_home

    override fun initViewModel() {
        binding.viewModel = viewModel
        updateUI()
    }

    fun updateUI(){
//        viewModel.fetchData()
    }


}