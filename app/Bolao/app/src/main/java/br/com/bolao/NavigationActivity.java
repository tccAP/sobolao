package br.com.bolao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import br.com.bolao.model.Usuario;

import static br.com.bolao.R.layout.nav_header_navigation;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    JSONObject response, profilePicData, profilePicUrl;
    static String TAG = NavigationActivity.class.getName();

    TextView tvUserNameNavBar, tvUserEmailNavBar;
    ImageView ivUserPictureNavBar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Usuario usuario;
    String userNameNavBar, userEmailNavBar, userPictureNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
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
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);


        tvUserNameNavBar = (TextView) header.findViewById(R.id.UserNameNavBar);
        ivUserPictureNavBar = (ImageView) header.findViewById(R.id.profilePicNavBar);
        tvUserEmailNavBar = (TextView) header.findViewById(R.id.emailNavBar);

        getUserData();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_meus_boloes) {

            Intent intent = new Intent(NavigationActivity.this, BolaoActivity.class);
            intent.putExtra("usuario", new Gson().toJson(usuario, Usuario.class));
            startActivity(intent);

        } else if (id == R.id.nav_participo) {

            Intent intent = new Intent(NavigationActivity.this, BolaoParticipoActivity.class);
            intent.putExtra("usuario", new Gson().toJson(usuario, Usuario.class));
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                String jsondata = intent.getStringExtra("usuario");

                Log.d(TAG, "JSON: " + jsondata);

                response = new JSONObject(jsondata);

                /*
                 * Recuperamos os respectivos campos retornados no JSON e os setamos nos componentes de tela
                 */

                userEmailNavBar = response.get("email").toString();
                userNameNavBar = response.get("name").toString();

                /*
                 * Habilitamos o modo de edição do sharedPreferences
                 */

                editor = sharedPreferences.edit();

                try {
                    profilePicData = new JSONObject(response.get("picture").toString());
                    profilePicUrl = new JSONObject(profilePicData.getString("data"));
                    userPictureNavBar = profilePicUrl.getString("url");
                    editor.putString("FB_USER_PIC", userPictureNavBar);
                } catch (Exception e) {
                    Log.i(TAG, e.getMessage());
                }
                /*
                 * Adicionamos KEYs que representam os dados do usuário ao objeto USER_DATA
                 */

                editor.putString("FB_USER_NAME", userNameNavBar);
                editor.putString("FB_USER_EMAIL", userEmailNavBar);
                editor.putBoolean("LOGIN_SESSION", true);
                /*
                 * Salvamos as keys criadas
                 */

                editor.commit();
            }

            usuario = new Usuario();
            usuario.setName(userNameNavBar);
            usuario.setEmail(userEmailNavBar);
            validateExists(usuario);


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

        finish();

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


    private void showDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(NavigationActivity.this).create();
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
