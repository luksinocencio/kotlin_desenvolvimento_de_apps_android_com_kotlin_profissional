package br.com.devmeist3r.bancoapp.domain.auth

import br.com.devmeist3r.bancoapp.data.repository.auth.AuthFirebaseDataSourceImpl
import javax.inject.Inject

class RecoverUsecase @Inject constructor(
    private val authFirabaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {
    suspend operator fun invoke(email: String) {
        return authFirabaseDataSourceImpl.recover(email)
    }
}