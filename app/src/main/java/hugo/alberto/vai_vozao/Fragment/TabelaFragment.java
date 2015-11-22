package hugo.alberto.vai_vozao.Fragment;

/**
 * Created by Alberto on 30/09/2015.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import hugo.alberto.vai_vozao.Handler.JsonParseHandler;
import hugo.alberto.vai_vozao.R;

public class TabelaFragment extends Fragment {

    private ProgressDialog pDialog;
    private JsonParseHandler jsonParseHandler;

    private static String url = "http://centraldejogos.lance.com.br/api/classificacao/brasileiro-serie-b-2015/";

    private static final String TAG_CLASSIFICACAO= "posicao";
    private static final String TAG_CLUBE = "nome_equipe";
    private static final String TAG_PONTOS = "pontos";
    private static final String TAG_VITORIAS = "num_vitorias";
    private static final String TAG_JOGOS= "num_jogos";
    private static final String TAG_EMPATES = "num_empates";
    private static final String TAG_DERROTAS = "num_derrotas";
    private static final String TAG_SALDO = "saldo_gols";

    private ListView lv;
    JSONArray elenco = null;

    private LinearLayout mainLayout;

    ArrayList<HashMap<String, String>> tabelaList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;
        if (isNetworkAvailable(getActivity())) {
        new GetTabela().execute();
        rootView =  inflater.inflate(R.layout.tabela_tab, container, false);


        tabelaList = new ArrayList<HashMap<String, String>>();
        lv = (ListView)rootView.findViewById(android.R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String classificacao = ((TextView) view.findViewById(R.id.classificacao)).getText().toString();
                String clube = ((TextView) view.findViewById(R.id.clube)).getText().toString();
                String pontos = ((TextView) view.findViewById(R.id.pontos)).getText().toString();
                String vitorias = ((TextView) view.findViewById(R.id.vitorias)).getText().toString();
                String jogos = ((TextView) view.findViewById(R.id.jogos)).getText().toString();
                String empates = ((TextView) view.findViewById(R.id.empates)).getText().toString();
                String derrotas = ((TextView) view.findViewById(R.id.derrotas)).getText().toString();
                String saldo = ((TextView) view.findViewById(R.id.saldo)).getText().toString();
            }
        });
            AdView mAdView_tabela = (AdView) rootView.findViewById(R.id.adView);
            AdRequest adRequest_tabela = new AdRequest.Builder().build();
            mAdView_tabela.loadAd(adRequest_tabela);
        } else {
            rootView = inflater.inflate(R.layout.no_connection_tab, container, false);
        }
        return rootView;
    }

    private class GetTabela extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Atualizando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            JsonParseHandler sh = new JsonParseHandler();

            String jsonStr = sh.serviceCall(url, JsonParseHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray elenco = new JSONArray(jsonStr);
                   // elenco = jsonObj.getJSONArray(TAG_CONTACTS);

                    for (int i = 0; i < elenco.length(); i++) {
                        JSONObject c = elenco.getJSONObject(i);

                        String posicao = c.getString(TAG_CLASSIFICACAO);
                        String nome_equipe = c.getString(TAG_CLUBE);
                        String pontos = c.getString(TAG_PONTOS);
                        String num_vitorias = c.getString(TAG_VITORIAS);
                        String num_jogos = c.getString(TAG_JOGOS);
                        String num_empates = c.getString(TAG_EMPATES);
                        String num_derrotas = c.getString(TAG_DERROTAS);
                        String saldo_gols = c.getString(TAG_SALDO);

                        HashMap<String, String> contact = new HashMap<String, String>();

                        contact.put(TAG_CLASSIFICACAO, posicao);
                        //contact.put(TAG_CLUBE, nome_equipe);
                        contact.put(TAG_PONTOS, pontos);
                        contact.put(TAG_VITORIAS, num_vitorias);
                        contact.put(TAG_JOGOS, num_jogos);
                        contact.put(TAG_EMPATES, num_empates);
                        contact.put(TAG_DERROTAS, num_derrotas);
                        contact.put(TAG_SALDO, saldo_gols);

                        if(nome_equipe.equals("VitÃ³ria")){
                            contact.put(TAG_CLUBE, "Vitória");
                        }else if(nome_equipe.equals("AmÃ©rica-MG")){
                            contact.put(TAG_CLUBE, "America-MG");
                        }else if(nome_equipe.equals("Sampaio CorrÃªa")){
                            contact.put(TAG_CLUBE, "Sampaio Corrêa");
                        }else if(nome_equipe.equals("ParanÃ¡")){
                            contact.put(TAG_CLUBE, "Paraná");
                        }else if(nome_equipe.equals("CriciÃºma")){
                            contact.put(TAG_CLUBE, "Criciúma");
                        }else if(nome_equipe.equals("AtlÃ©tico-GO")){
                            contact.put(TAG_CLUBE, "Atlético-GO");
                        }else if(nome_equipe.equals("MacaÃ©")){
                            contact.put(TAG_CLUBE, "Macaé");
                        }else if(nome_equipe.equals("CearÃ¡")){
                            contact.put(TAG_CLUBE, "Ceará");
                        }else if(nome_equipe.equals("NÃ¡utico")){
                           contact.put(TAG_CLUBE, "Náutico");
                        }else {
                            contact.put(TAG_CLUBE, nome_equipe);
                        }

                        tabelaList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (getActivity() != null){

            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), tabelaList,
                    R.layout.list_item_tabela, new String[] { TAG_CLASSIFICACAO, TAG_CLUBE, TAG_PONTOS, TAG_VITORIAS, TAG_JOGOS, TAG_EMPATES, TAG_DERROTAS, TAG_SALDO }, new int[] { R.id.classificacao, R.id.clube, R.id.pontos, R.id.vitorias, R.id.jogos, R.id.empates, R.id.derrotas, R.id.saldo,
            });

            lv.setAdapter(adapter);
            }
        }

    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
