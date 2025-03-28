package mx.edu.itson.practicaautenticaciongutierreza

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class SingInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        var email: EditText = findViewById(R.id.etrEmail)
        var password: EditText = findViewById(R.id.etrPassword)
        var confirmPassword: EditText = findViewById(R.id.etrConfirmPassword)
        val errorTv: TextView = findViewById(R.id.tvrError)

        val button: Button = findViewById(R.id.btnRegister)
        errorTv.visibility = View.INVISIBLE

        button.setOnClickListener ({
            if(email.text.isEmpty() || password.text.isEmpty() || confirmPassword.text.isEmpty()){
                errorTv.text = "Todos los campos deben de ser llamados"
                errorTv.visibility = View.VISIBLE
            } else if(!password.text.toString().equals(confirmPassword.text.toString())){
                errorTv.text = "Las contraseñas no coinciden"
                errorTv.visibility = View.VISIBLE
            }else{
                errorTv.visibility = View.INVISIBLE
                singIn(email.text.toString(), password.text.toString())
            }
        })

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun singIn(email: String, password: String){
        Log.d("INFO", "email: ${email}, password: ${password}")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Log.d("INFO", "singInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    Log.w("ERROR", "singInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "El registro falló.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}