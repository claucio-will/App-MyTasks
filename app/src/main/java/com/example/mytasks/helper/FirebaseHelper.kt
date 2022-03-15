package com.example.mytasks.helper



import com.example.mytasks.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    //Usando metódos static para não precisar instanciar a classe FirebaseHelper
    companion object {

        //Pegando a instancia do firebase database
        fun getDatabase() = FirebaseDatabase.getInstance().reference

        //Pegando a instancia do firebase auth
        private fun getAuth() = FirebaseAuth.getInstance()

        //Pegando o id do usuário logado no app
        fun getIdUser() = getAuth().uid

        //Verificando se está autenticado
        fun isAutenticated() = getAuth().currentUser != null


        //Mensagem de erro que pode ocorrer durante a consuta com o serviços do firebase
        fun validError(error: String): Int {
            return when {
                error.contains("There is no user record corresponding to this identifier") -> {
                    R.string.account_not_registered_register_fragment
                }
                error.contains("The email address is badly formatted") -> {
                    R.string.invalid_email_register_fragment
                }
                error.contains("The password is invalid or the user does not have a password") -> {
                    R.string.invalid_password_register_fragment
                }
                error.contains("The email address is already in use by another account") -> {
                    R.string.email_in_use_register_fragment
                }
                error.contains("Password should be at least 6 characters") -> {
                    R.string.strong_password_register_fragment
                }
                else -> {
                    R.string.error_generic
                }
            }
        }

    }

}