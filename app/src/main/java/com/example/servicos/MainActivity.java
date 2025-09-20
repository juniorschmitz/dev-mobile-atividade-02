    package com.example.servicos;

    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.RadioGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.example.servicos.api.InvertextoApi;
    import com.example.servicos.model.Empresa;
    import com.example.servicos.model.Logradouro;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            // Cria as variáveis ligadas a View (XML)
            EditText inputCep = findViewById(R.id.inputCep);
            Button btnBuscar = findViewById(R.id.btnBuscar);
            RadioGroup radioGroupTipo = findViewById(R.id.radioGroupTipo);

            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String valorDigitado = inputCep.getText().toString();

                    int selectedId = radioGroupTipo.getCheckedRadioButtonId();

                    if (selectedId == R.id.radioCep) {
                        // Usuário escolheu CEP
                        consultarCep(valorDigitado);
                    } else if (selectedId == R.id.radioCnpj) {
                        // Usuário escolheu CNPJ
                        consultarCnpj(valorDigitado);
                    } else {
                        Toast.makeText(MainActivity.this, "Selecione CEP ou CNPJ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void consultarCep(String numeroCep) {
            TextView textViewInfo = findViewById(R.id.textViewInfo);
            ProgressBar progressBar = findViewById(R.id.progressBar);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

            String token = EnvHelper.getValue(this, "TOKEN");

            Call<Logradouro> call = invertextoApi.getLogradouro(
                    numeroCep, token
            );

            progressBar.setVisibility(View.VISIBLE);
            textViewInfo.setText("");

            call.enqueue(new Callback<Logradouro>() {
                @Override
                public void onResponse(Call<Logradouro> call, Response<Logradouro> response) {
                    progressBar.setVisibility(View.GONE);

                    if(response.isSuccessful()) {
                        Logradouro logradouro  = response.body();
                        if (logradouro != null) {
                            textViewInfo.setText(logradouro.apresentacaoFormatada());
                        } else {
                            Toast.makeText(MainActivity.this, "Resposta vazia da API", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "erro ao buscar infos" + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Logradouro> call, Throwable throwable) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(MainActivity.this, "Verifique sua conexão com a internet", Toast.LENGTH_LONG).show();
                }
            });
        }

        private void consultarCnpj(String numeroCnpj) {
            TextView textViewInfo = findViewById(R.id.textViewInfo);
            ProgressBar progressBar = findViewById(R.id.progressBar);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);

            String token = EnvHelper.getValue(this, "TOKEN");

            Call<Empresa> call = invertextoApi.getEmpresa(
                    numeroCnpj, token
            );

            progressBar.setVisibility(View.VISIBLE);
            textViewInfo.setText("");

            call.enqueue(new Callback<Empresa>() {
                @Override
                public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                    progressBar.setVisibility(View.GONE);

                    if(response.isSuccessful()) {
                        Empresa empresa  = response.body();
                        if (empresa != null) {
                            textViewInfo.setText(empresa.apresentacaoFormatada());
                        } else {
                            Toast.makeText(MainActivity.this, "Resposta vazia da API", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "erro ao buscar infos" + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Empresa> call, Throwable throwable) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(MainActivity.this, "Verifique sua conexão com a internet", Toast.LENGTH_LONG).show();
                }
            });
        }
    }