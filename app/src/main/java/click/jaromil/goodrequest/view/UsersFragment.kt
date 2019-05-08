package click.jaromil.goodrequest.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import click.jaromil.goodrequest.R
import click.jaromil.goodrequest.databinding.FragmentUsersBinding
import click.jaromil.goodrequest.view.adapter.UsersAdapter
import click.jaromil.goodrequest.model.User
import click.jaromil.goodrequest.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.retry_view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class UsersFragment : Fragment() {
    private lateinit var binding: FragmentUsersBinding
    private val usersViewModel: UsersViewModel by sharedViewModel()
    private lateinit var usersAdapter: UsersAdapter
    
    companion object {
        fun create(): UsersFragment {
            return UsersFragment()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersViewModel.error.observe(this, Observer<String> { error ->
            error?.let {
                showRetry(error)
                Timber.e(error)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        usersViewModel.error.value = null
    }

    private fun showRetry(msg: String) {
        noData.text = msg
        retryLayout.visibility = View.VISIBLE
        retry.setOnClickListener {
            usersAdapter.clearData()
            usersViewModel.userPage.value = 0
            usersViewModel.getAllUsers(0)
            hideRetry()
        }
    }

    private fun hideRetry() {
        retryLayout.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        binding.viewModel = usersViewModel
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        
        if (usersViewModel.userPage.value == 0)
            usersViewModel.getAllUsers(0)
    }
    
    private fun initRecycler() {
        usersAdapter = UsersAdapter { user ->
            usersViewModel.currentUser.value = user
            showRepoDetails(user)
        }
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            usersRecycler.layoutManager = LinearLayoutManager(context)
            usersAdapter.setUsersListener {
                usersViewModel.userPage.value?.let {
                    usersViewModel.getAllUsers(it)
                }
            }
            usersRecycler.adapter = usersAdapter
            usersRecycler.addOnScrollListener(UsersScrollListener())
            with(swipeRefreshLayout) {
                setColorSchemeColors(ContextCompat.getColor(context!!, R.color.primary))
                setOnRefreshListener {
                    usersAdapter.clearData()
                    usersViewModel.userPage.value = 0
                    usersViewModel.getAllUsers(0)
                }
            }

        }
    }
    
    private fun showRepoDetails(repo: User) {
        activity?.let {
            val repoDetailFragment = UserDetailsFragment.create()
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, repoDetailFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}