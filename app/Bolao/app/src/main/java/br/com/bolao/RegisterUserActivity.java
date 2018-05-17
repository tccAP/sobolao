package br.com.bolao;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;

public class RegisterUserActivity extends AppCompatActivity {

    static String TAG = RegisterUserActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        final Button button = findViewById(R.id.btnCadastrar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = ((EditText) findViewById(R.id.edtUsuario)).getText().toString();
                String nome = ((EditText) findViewById(R.id.edtNome)).getText().toString();
                String email = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
                String senha = ((EditText) findViewById(R.id.edtSenha)).getText().toString();
                String confirmaSenha = ((EditText) findViewById(R.id.edtConfirmaSenha)).getText().toString();

                if (!senha.equals(confirmaSenha)) {
                    showDialog("Atenção!", "As senhas não conferem");
                } else {


                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
                        senha = hash.toString();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    String url = getString(R.string.url_base) + "/usuario/";
                    //RequestQueue queue = Volley.newRequestQueue(this);

                    JSONObject postRequest = new JSONObject();
                    try {
                        postRequest.put("username", username);
                        postRequest.put("email", email);
                        postRequest.put("senha", senha);
                        postRequest.put("name", nome);
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
                    //queue.add(request);
                }

            }
        });
    }

    private void showDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterUserActivity.this).create();
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
