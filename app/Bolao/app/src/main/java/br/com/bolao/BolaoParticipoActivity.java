package br.com.bolao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.bolao.model.Bolao;
import br.com.bolao.model.Usuario;

public class BolaoParticipoActivity extends AppCompatActivity {

    static String TAG = BolaoParticipoActivity.class.getName();
    private List<Bolao> boloesParticipo = new ArrayList<>();

    ListView listByOwner, listByParticipant;
    ArrayAdapter<Bolao> adapterByOwner, adapterByParticipant;

    JSONObject response, profilePicData, profilePicUrl;

    TextView tvUserNameNavBar, tvUserEmailNavBar;
    ImageView ivUserPictureNavBar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String userNameNavBar, userEmailNavBar, userPictureNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolao_participo);
        this.setTitle("Bolões que participo");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);


        tvUserNameNavBar = (TextView) header.findViewById(R.id.UserNameNavBar);
        ivUserPictureNavBar = (ImageView) header.findViewById(R.id.profilePicNavBar);
        tvUserEmailNavBar = (TextView) header.findViewById(R.id.emailNavBar);

        getUserData();


        listByOwner = (ListView) findViewById(R.id.listViewParticipo);
        preencheGerenciados();
    }

    private void preencheGerenciados() {

        try {
            sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            Intent intent = getIntent();
            String jsondata = intent.getStringExtra("usuario");
            response = new JSONObject(jsondata);





            String email = response.get("email").toString();

            Log.d(TAG, "Buscando boloes gerenciados por mim");
            String url = getString(R.string.url_base) + "/bolao/findByParticipant/" + email;
            RequestQueue queue = Volley.newRequestQueue(this);


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                //JSONArray jsonArray = new JSONArray(response.getJSONArray("boloes"));

                                JSONArray jsonArray = (JSONArray) response.getJSONArray("boloes");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jresponse =
                                            jsonArray.getJSONObject(i);

                                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                                    System.out.println(jresponse.toString());

                                    Gson gson = new Gson();

                                    Bolao b = gson.fromJson(jresponse.toString(), Bolao.class);
                                    System.out.println(b.toString());
                                    boloesParticipo.add(b);
                                    adapterByOwner = new ArrayAdapter<Bolao>(BolaoParticipoActivity.this,
                                            android.R.layout.simple_list_item_1, boloesParticipo);

                                    listByOwner.setAdapter(adapterByOwner);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "API Response: " + response);

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            showDialog("Erro", error.getMessage());
                            error.printStackTrace();
                        }
                    });

            queue.add(jsonObjReq);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(BolaoParticipoActivity.this).create();
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

                userEmailNavBar = sharedPreferences.getString("FB_USER_EMAIL", "");
                userNameNavBar = sharedPreferences.getString("FB_USER_NAME", "");
                userPictureNavBar = sharedPreferences.getString("FB_USER_PIC", "");

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

                userEmailNavBar = response.get("email").toString();
                userNameNavBar = response.get("name").toString();
                userPictureNavBar = profilePicUrl.getString("url");

                /*
                 * Habilitamos o modo de edição do sharedPreferences
                 */

                editor = sharedPreferences.edit();

                /*
                 * Adicionamos KEYs que representam os dados do usuário ao objeto USER_DATA
                 */

                editor.putString("FB_USER_NAME", userNameNavBar);
                editor.putString("FB_USER_EMAIL", userEmailNavBar);
                editor.putString("FB_USER_PIC", userPictureNavBar);
                editor.putBoolean("LOGIN_SESSION", true);
                /*
                 * Salvamos as keys criadas
                 */

                editor.commit();
            }

            Usuario u = new Usuario();
            u.setName(userNameNavBar);
            u.setEmail(userEmailNavBar);
            validateExists(u);


            tvUserEmailNavBar.setText(userEmailNavBar);
            tvUserNameNavBar.setText(userNameNavBar);
            Picasso.with(this).load(userPictureNavBar).into(ivUserPictureNavBar);

/*            Intent intent = new Intent(NavigationActivity.this, BolaoActivity.class);
            startActivity(intent);*/
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

        Intent intent = new Intent(BolaoParticipoActivity.this, LoginActivity.class);
        startActivity(intent);
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

}
