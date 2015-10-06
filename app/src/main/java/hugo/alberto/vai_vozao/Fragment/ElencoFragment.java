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

public class ElencoFragment extends Fragment {

    private ProgressDialog pDialog;
    private JsonParseHandler jsonParseHandler;

    private static String url = "http://globoesporte.globo.com/dynamo/futebol/time/291/elenco/atual.json";

    private static final String TAG_CONTACTS = "elenco";
    private static final String TAG_ID = "pessoafuncao_id";
    private static final String TAG_POSICAO = "posicao";
    private static final String TAG_NAME = "nome_popular";

    private ListView lv;
    JSONArray elenco = null;

    ArrayList<HashMap<String, String>> elencoList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        if (isNetworkAvailable(getActivity())) {

        new GetElenco().execute();
         rootView =  inflater.inflate(R.layout.elenco_tab, container, false);

        elencoList = new ArrayList<HashMap<String, String>>();
        lv = (ListView)rootView.findViewById(android.R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String posicao = ((TextView) view.findViewById(R.id.posicao))
                        .getText().toString();
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

    private class GetElenco extends AsyncTask<Void, Void, Void> {

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
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    elenco = jsonObj.getJSONArray(TAG_CONTACTS);

                    for (int i = 0; i < elenco.length(); i++) {
                        JSONObject c = elenco.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String posicao = c.getString(TAG_POSICAO);

                        HashMap<String, String> contact = new HashMap<String, String>();

                        contact.put(TAG_ID, id);
                        contact.put(TAG_NAME, name);

                        if(posicao=="null"){
                            contact.put(TAG_POSICAO, "Treinador");
                        }else{
                            contact.put(TAG_POSICAO, posicao);
                        }


                        // adding contact to contact list
                        elencoList.add(contact);
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
                    getActivity(), elencoList,
                    R.layout.list_item_elenco, new String[] { TAG_NAME, TAG_POSICAO }, new int[] { R.id.name, R.id.posicao,
            });

            lv.setAdapter(adapter);}
        }

    }
    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}