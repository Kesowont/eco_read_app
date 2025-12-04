package com.example.eduread.network

import com.example.eduread.data.model.request.LoginRequest
import com.example.eduread.data.model.request.RegistrarUsuarioRequest;
import com.example.eduread.data.model.request.ResponderRequest
import com.example.eduread.data.model.response.CuentosResponse
import com.example.eduread.data.model.response.LoginResponse
import com.example.eduread.data.model.response.PreguntasResponse
import com.example.eduread.data.model.response.RegistrarUsuarioResponse;
import com.example.eduread.data.model.response.ResponderResponse
import com.example.eduread.data.model.response.UsuarioResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth_service/v1/register/usuario")
    fun registrar(@Body registrarUsuarioRequest :RegistrarUsuarioRequest): Call<RegistrarUsuarioResponse>
    @POST("auth_service/v1/login/usuario")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @GET("cuento_service/v1/cuento/listar/{id_usuario}")
    fun listarCuentos(@Path("id_usuario") id_usuario: Int): Call<CuentosResponse>
    @POST("responder_service/v1/responder")
    fun RespuestaPreguntasCuento(@Body testRequest: ResponderRequest): Call<ResponderResponse>
    @GET("preguntas_service/v1/cuento/preguntas/{id_cuento}")
    fun obtenerPreguntas(@Path("id_cuento") idCuento: Int): Call<PreguntasResponse>
}
