package com.icarocosta.endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    @GET("/ws/{CEP}/json")
    Call<PesquisaCEP> informacao(@Path("CEP") String id);
}
