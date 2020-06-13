package com.icarocosta.endereco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText digitaCEP;
    TextView saidaCEP;
    Button endereco;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        digitaCEP = findViewById(R.id.textCEP);
        saidaCEP = findViewById(R.id.viewCEP);
        endereco = findViewById(R.id.buttonBuscaCep);
        endereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://viacep.com.br/ws/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Service api = retrofit.create(Service.class);

                Call<PesquisaCEP> chamada = api.informacao(digitaCEP.getText().toString());

                chamada.enqueue(new Callback<PesquisaCEP>() {
                    @Override
                    public void onResponse(Call<PesquisaCEP> call, Response<PesquisaCEP> response) {

                        if (response.code() != 200) {
                            Toast.makeText(MainActivity.this, "ERRO!" + response.code(), Toast.LENGTH_LONG).show();


                        } else {
                            PesquisaCEP retorno = response.body();
                            saidaCEP.setText(retorno.getCep() + "\n" +
                                    retorno.getLogradouro() + "\n" +
                                    retorno.getComplemento() + "\n" +
                                    retorno.getBairro() + "\n" +
                                    retorno.getLocalidade() + "\n" +
                                    retorno.getUf() + "\n" +
                                    retorno.getUnidade() + "\n" +
                                    retorno.getIbge() + "\n" +
                                    retorno.getGia());
                        }
                    }

                    @Override
                    public void onFailure(Call<PesquisaCEP> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "deu erro", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
}
