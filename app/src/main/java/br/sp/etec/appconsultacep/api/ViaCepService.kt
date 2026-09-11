package br.sp.etec.appconsultacep.api

import br.sp.etec.appconsultacep.model.ResponseEndereco
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {
    @GET("/ws/{cep}/json/")
    suspend fun buscarEndereco(
        @Path("cep") cep: String
    ) : ResponseEndereco
}