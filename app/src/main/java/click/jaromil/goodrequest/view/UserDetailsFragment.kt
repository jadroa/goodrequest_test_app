package click.jaromil.goodrequest.view

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import click.jaromil.goodrequest.R
import click.jaromil.goodrequest.databinding.FragmentUserDetailsBinding
import click.jaromil.goodrequest.view.adapter.PostsAdapter
import click.jaromil.goodrequest.viewmodel.UsersViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class UserDetailsFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailsBinding
    private val usersViewModel: UsersViewModel by sharedViewModel()
    
    companion object {
        fun create(): UserDetailsFragment {
            return UserDetailsFragment()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersViewModel.error.observe(this, Observer<String> { error ->
            Timber.e(error)
        })
        setHasOptionsMenu(true)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            it.window.statusBarColor = Color.TRANSPARENT
        }
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = usersViewModel
        return binding.root
    }
    
    override fun onDestroyView() {
        activity?.let {
            it.window.statusBarColor = it.getColor(R.color.status_bar)
        }
        super.onDestroyView()
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        usersViewModel.currentUser?.let {
            binding.toolbar.title = it.value?.name
        }
        
        initRecycler()
        
        usersViewModel.currentUser?.value?.id?.let {
            usersViewModel.getUserDetails(it)
        }
    }
    
    private fun initRecycler() {
        val postsAdapter = PostsAdapter()
    
        with(binding) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            postsRecycler.layoutManager = LinearLayoutManager(context)
            postsRecycler.adapter = postsAdapter
            swipeRefreshLayout.isEnabled = false
            swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.primary))
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> (activity as AppCompatActivity).supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}