package br.com.bolao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.bolao.model.Usuario;

public class IndexActivity extends AppCompatActivity {

    JSONObject response, profilePicData, profilePicUrl;
    static String TAG = IndexActivity.class.getName();

    TextView tvUserName, tvUserEmail;
    ImageView ivUserPicture;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String userName, userEmail, userPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        /*
         * Instanciamos os componentes de tela do layout
         */

        tvUserName = findViewById(R.id.UserName);
        ivUserPicture = findViewById(R.id.profilePic);
        tvUserEmail = findViewById(R.id.email);

        getUserData();
    }

    private void getUserData() {

        /*
         * Inicialmente recuperamos os dados do usuário que foram enviados via Intent
         */

        try {


            /*
             * Recuperamos o objeto USER_DATA, caso ele exista o objeto sharedPreferences será setado
             */

            sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);

            /*
             * Se a sessão já tiver sido ininicida, usamos os objetos do sharedPreferences
             */

            if (sharedPreferences.contains("LOGIN_SESSION")) {

                userEmail = sharedPreferences.getString("FB_USER_EMAIL", "");
                userName = sharedPreferences.getString("FB_USER_NAME", "");
                userPicture = sharedPreferences.getString("FB_USER_PIC", "");

            } else {

                Intent intent = getIntent();
                String jsondata = intent.getStringExtra("userProfile");

                Log.d(TAG, "JSON: " + jsondata);

                response = new JSONObject(jsondata);

                /*
                 * Recuperamos os respectivos campos retornados no JSON e os setamos nos componentes de tela
                 */

                profilePicData = new JSONObject(response.get("picture").toString());
                profilePicUrl = new JSONObject(profilePicData.getString("data"));

                userEmail = response.get("email").toString();
                userName = response.get("name").toString();
                userPicture = profilePicUrl.getString("url");

                /*
                 * Habilitamos o modo de edição do sharedPreferences
                 */

                editor = sharedPreferences.edit();

                /*
                 * Adicionamos KEYs que representam os dados do usuário ao objeto USER_DATA
                 */

                editor.putString("FB_USER_NAME", userName);
                editor.putString("FB_USER_EMAIL", userEmail);
                editor.putString("FB_USER_PIC", userPicture);
                editor.putBoolean("LOGIN_SESSION", true);
                /*
                 * Salvamos as keys criadas
                 */

                editor.commit();
            }

            Usuario u = new Usuario();
            u.setName(userName);
            u.setEmail(userEmail);
            validateExists(u);



            tvUserEmail.setText(userEmail);
            tvUserName.setText(userName);
            Picasso.with(this).load(userPicture).into(ivUserPicture);

            Intent intent = new Intent(IndexActivity.this, BolaoActivity.class);
            startActivity(intent);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void logout(View view) {

        Log.d(TAG, "Finalizando sessão do usuário");

        /*
         * Método que encerra a sessão do usuário no Facebook
         */

        LoginManager.getInstance().logOut();

        /*
         * Removemos os dados do usuário que estão no sharedPreferences
         */

        //sharedPreferences.edit().remove("USER_DATA").commit();
        sharedPreferences.edit().clear().commit();

        Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    protected void onResume() {
        super.onResume();
    }
    public void validateExists(final Usuario user) {

        Log.d(TAG, "Validando se o usuário existe na base interna");
        String url = getString(R.string.url_base) + "/usuario/findByEmail/" + user.getEmail();
        RequestQueue queue = Volley.newRequestQueue(this);

        /**
         JsonObjectRequest espera 5 parâmetros
         Request Type - Tipo da requisição: GET,POST
         URL          - URL da API
         JSONObject   - Objeto JSON da requisição (parameters.null se a requisição for do tipo GET)
         Listener     - Implementação de um Response.Listener() com um callback de sucesso e de erro
         **/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "API Response: " + response);
                        showDialog("Success", "O usuário já existe na base");

                    }
                },

                /* Callback chamado em caso de erro */

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showDialog("Novo usuário", "Usuário será criado na base interna");
                        createUser(user);
                    }
                });

        queue.add(jsonObjReq);


    }

    public void createUser(Usuario usuario) {

        Log.d(TAG, "Criando usuario na base interna");
        String url = getString(R.string.url_base) + "/usuario/";
        RequestQueue queue = Volley.newRequestQueue(this);


        JSONObject postRequest = new JSONObject();

        try {
            postRequest.put("email", usuario.getEmail());
            postRequest.put("name", usuario.getName());
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




    private void showDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(IndexActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
