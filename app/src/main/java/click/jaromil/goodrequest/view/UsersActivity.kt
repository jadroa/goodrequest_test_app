package click.jaromil.goodrequest.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import click.jaromil.goodrequest.R

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
    
        if (savedInstanceState != null) {
            return
        }
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, UsersFragment.create())
            .commit()
    }
}
