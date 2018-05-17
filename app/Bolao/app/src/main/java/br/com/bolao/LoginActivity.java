package br.com.bolao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;

import br.com.bolao.model.Usuario;


public class LoginActivity extends AppCompatActivity {


    static String TAG = LoginActivity.class.getName();
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final Button btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String email = null;
                String usuario = null;
                String valor = ((EditText) findViewById(R.id.edtEmail)).getText().toString();

                if (valor.contains("@")) {
                    email = valor;
                } else {
                    usuario = valor;
                }

                String senha = ((EditText) findViewById(R.id.edtSenha)).getText().toString();
                validateLogin(usuario, email, senha);


            }
        });


        checkUserSession();

        info = findViewById(R.id.info);
        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                getUserDetails(loginResult);
                Log.d(TAG, "Login realizado com sucesso");
            }

            @Override
            public void onCancel() {

                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
                info.setText("Login attempt failed.");
            }
        });
    }


    private void checkUserSession() {

        SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        if (sharedPreferences.contains("LOGIN_SESSION")) {

            Log.d(TAG, "O usário já está logado, direcionando ele para a UserProfileActivity");

            //Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void getUserDetails(LoginResult loginResult) {

        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        //Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                        intent.putExtra("usuario", json_object.toString());
                        startActivity(intent);
                    }

                });

        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(240).height(240)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }

    public void createUser(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
        startActivity(intent);
    }

    public void validateLogin(String usuario, String email, String senha) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
            senha = hash.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String url = getString(R.string.url_base) + "/usuario/validateLogin/";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject postRequest = new JSONObject();
        try {
            postRequest.put("username", usuario);
            postRequest.put("email", email);
            postRequest.put("senha", senha);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postRequest,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.d(TAG, "API Response: " + response);
                        Integer id = null;
                        String username = null;
                        String nome = null;
                        String email = null;
                        try {
                            id = ((JSONObject) response).getInt("id");
                            username = ((JSONObject) response).getString("username");
                            nome = ((JSONObject) response).getString("name");
                            email = ((JSONObject) response).getString("email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Usuario user = new Usuario(id, username, nome, email);

                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                        //intent.putExtra("usuario", response.toString());
                        intent.putExtra("usuario", new Gson().toJson(user, Usuario.class));
                        startActivity(intent);


                        Log.d(TAG, "Login realizado com sucesso");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Ocorreu um erro ao chamar a API " + error);
                    }
                });
        queue.add(request);
    }
}
