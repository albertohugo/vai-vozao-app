package hugo.alberto.vai_vozao.Fragment;


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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hugo.alberto.vai_vozao.Handler.JsonParseHandler;
import hugo.alberto.vai_vozao.R;


public class JogosFragment extends Fragment {
    private ProgressDialog progressDialog;
    private static final String URL = "http://globoesporte.globo.com/servico/equipe/ceara/jogos.json";
    private JsonParseHandler jsonParseHandler;

    private TextView campeonato_last;
    private TextView time1_last;
    private TextView time2_last ;
    private TextView placar1_last;
    private TextView placar2_last;
    private TextView informacoes_last;
    private ImageView imageTime1_last;
    private ImageView imageTime2_last;
    private TextView versus_last;

    private String campeonato_jogo_last;
    private String apelido_mandante_last;
    private String apelido_visitante_last;
    private String placar_oficial_mandante_last;
    private String placar_oficial_visitante_last;
    private String escudo_oficial_mandante_last;
    private String escudo_oficial_visitante_last;
    private String dataformatada_last;
    private String estadio_last;
    private String hora_last;

    private TextView campeonato_current;
    private TextView time1_current;
    private TextView time2_current ;
    private TextView placar1_current;
    private TextView placar2_current;
    private TextView informacoes_current;
    private ImageView imageTime1_current;
    private ImageView imageTime2_current;
    private TextView versus_current;

    private String campeonato_jogo_current;
    private String apelido_mandante_current;
    private String apelido_visitante_current;
    private String placar_oficial_mandante_current;
    private String placar_oficial_visitante_current;
    private String escudo_oficial_mandante_current;
    private String escudo_oficial_visitante_current;
    private String dataformatada_current;
    private String estadio_current;
    private String hora_current;

    private TextView campeonato_next;
    private TextView time1_next;
    private TextView time2_next;
    private TextView placar1_next;
    private TextView placar2_next;
    private TextView informacoes_next;
    private ImageView imageTime1_next;
    private ImageView imageTime2_next;
    private TextView versus_next;

    private String campeonato_jogo_next;
    private String apelido_mandante_next;
    private String apelido_visitante_next;
    private String placar_oficial_mandante_next;
    private String placar_oficial_visitante_next;
    private String escudo_oficial_mandante_next;
    private String escudo_oficial_visitante_next;
    private String dataformatada_next;
    private String estadio_next;
    private String hora_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        if (isNetworkAvailable(getActivity())) {
            new DataFetch().execute();

            rootView = inflater.inflate(R.layout.jogos_tab, container, false);

            campeonato_last = (TextView) rootView.findViewById(R.id.campeonato_last);
            time1_last = (TextView) rootView.findViewById(R.id.time1_last);
            time2_last = (TextView) rootView.findViewById(R.id.time2_last);
            placar1_last = (TextView) rootView.findViewById(R.id.placar1_last);
            placar2_last = (TextView) rootView.findViewById(R.id.placar2_last);
            informacoes_last = (TextView) rootView.findViewById(R.id.informacoes_last);
            imageTime1_last = (ImageView) rootView.findViewById(R.id.imageCategoria_last);
            imageTime2_last = (ImageView) rootView.findViewById(R.id.imageCategoria2_last);
            versus_last = (TextView) rootView.findViewById(R.id.X_last);

            campeonato_current = (TextView) rootView.findViewById(R.id.campeonato_current);
            time1_current = (TextView) rootView.findViewById(R.id.time1_current);
            time2_current = (TextView) rootView.findViewById(R.id.time2_current);
            placar1_current = (TextView) rootView.findViewById(R.id.placar1_current);
            placar2_current = (TextView) rootView.findViewById(R.id.placar2_current);
            informacoes_current = (TextView) rootView.findViewById(R.id.informacoes_current);
            imageTime1_current = (ImageView) rootView.findViewById(R.id.imageCategoria_current);
            imageTime2_current = (ImageView) rootView.findViewById(R.id.imageCategoria2_current);
            versus_current = (TextView) rootView.findViewById(R.id.X_current);

            campeonato_next = (TextView) rootView.findViewById(R.id.campeonato_next);
            time1_next = (TextView) rootView.findViewById(R.id.time1_next);
            time2_next = (TextView) rootView.findViewById(R.id.time2_next);
            placar1_next = (TextView) rootView.findViewById(R.id.placar1_next);
            placar2_next = (TextView) rootView.findViewById(R.id.placar2_next);
            informacoes_next = (TextView) rootView.findViewById(R.id.informacoes_next);
            imageTime1_next = (ImageView) rootView.findViewById(R.id.imageCategoria_next);
            imageTime2_next = (ImageView) rootView.findViewById(R.id.imageCategoria2_next);
            versus_next = (TextView) rootView.findViewById(R.id.X_next);

            AdView mAdView_tabela = (AdView) rootView.findViewById(R.id.adView);
            AdRequest adRequest_tabela = new AdRequest.Builder().build();
            mAdView_tabela.loadAd(adRequest_tabela);

        } else {

            rootView = inflater.inflate(R.layout.no_connection_tab, container, false);
        }
        return rootView;
    }


    private void readJsonParse(String json_data) {
        if (json_data != null) {
        try {
            JSONObject object_last = new JSONObject(json_data);

            JSONObject mandante_last = object_last.getJSONObject("anterior").getJSONObject("mandante");
            JSONObject visitante_last = object_last.getJSONObject("anterior").getJSONObject("visitante");
            JSONObject jogo_last = object_last.getJSONObject("anterior");
            apelido_mandante_last = mandante_last.getString("apelido");
            apelido_visitante_last = visitante_last.getString("apelido");
            placar_oficial_mandante_last = mandante_last.getString("placar_oficial");
            placar_oficial_visitante_last = visitante_last.getString("placar_oficial");
            escudo_oficial_mandante_last = mandante_last.getJSONObject("escudo").getString("grande");
            escudo_oficial_visitante_last = visitante_last.getJSONObject("escudo").getString("grande");
            campeonato_jogo_last = jogo_last.getString("nome_campeonato").toUpperCase();
            dataformatada_last = jogo_last.getString("data_formatada").toUpperCase();
            estadio_last = jogo_last.getString("estadio").toUpperCase();
            hora_last = jogo_last.getString("hora").toUpperCase();

            JSONObject object = new JSONObject(json_data);
            JSONArray jsonArray = object.getJSONArray("proximos");

            JSONObject mandante = jsonArray.getJSONObject(0).getJSONObject("mandante");
            JSONObject visitante = jsonArray.getJSONObject(0).getJSONObject("visitante");
            JSONObject jogo = jsonArray.getJSONObject(0);
            apelido_mandante_current = mandante.getString("apelido");
            apelido_visitante_current = visitante.getString("apelido");
            placar_oficial_mandante_current = mandante.getString("placar_oficial");
            placar_oficial_visitante_current = visitante.getString("placar_oficial");
            escudo_oficial_mandante_current = mandante.getJSONObject("escudo").getString("grande");
            escudo_oficial_visitante_current = visitante.getJSONObject("escudo").getString("grande");
            campeonato_jogo_current = jogo.getString("nome_campeonato").toUpperCase();
            dataformatada_current = jogo.getString("data_formatada").toUpperCase();
            estadio_current = jogo.getString("estadio").toUpperCase();
            hora_current = jogo.getString("hora").toUpperCase();

            JSONObject mandante_next = jsonArray.getJSONObject(1).getJSONObject("mandante");
            JSONObject visitante_next = jsonArray.getJSONObject(1).getJSONObject("visitante");
            JSONObject jogo_next = jsonArray.getJSONObject(1);
            apelido_mandante_next = mandante_next.getString("apelido");
            apelido_visitante_next = visitante_next.getString("apelido");
            placar_oficial_mandante_next = mandante_next.getString("placar_oficial");
            placar_oficial_visitante_next = visitante_next.getString("placar_oficial");
            escudo_oficial_mandante_next = mandante_next.getJSONObject("escudo").getString("grande");
            escudo_oficial_visitante_next = visitante_next.getJSONObject("escudo").getString("grande");
            campeonato_jogo_next = jogo_next.getString("nome_campeonato").toUpperCase();
            dataformatada_next = jogo_next.getString("data_formatada").toUpperCase();
            estadio_next = jogo_next.getString("estadio").toUpperCase();
            hora_next = jogo_next.getString("hora").toUpperCase();

        } catch (JSONException e) {
            e.printStackTrace();
        }  } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

    }

    public class DataFetch extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Atualizando...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonParseHandler = new JsonParseHandler();
            String json_data = jsonParseHandler.serviceCall(URL, JsonParseHandler.GET);

                Log.d("in inBG()", "fetch data" + json_data);
                readJsonParse(json_data);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            FillLastMatch();
            FillCurrentMatch();
            FillNextMatch();

        }}

    private void FillLastMatch() {
        campeonato_last.setText(campeonato_jogo_last);
        time1_last.setText(apelido_mandante_last);
        time2_last.setText(apelido_visitante_last);
        if(placar_oficial_mandante_last=="null" &&placar_oficial_visitante_last=="null"){
            placar1_last.setText("");
            placar2_last.setText("");
        }
        else{
            placar1_last.setText(placar_oficial_mandante_last);
            placar2_last.setText(placar_oficial_visitante_last);
        }

        if(dataformatada_last==null ){//&&estadio_last=="null"&&hora_last=="null"){
            informacoes_last.setText("");
            versus_last.setText("");
        }
        else{
            informacoes_last.setText(dataformatada_last + " " + estadio_last + " " + hora_last);
            versus_last.setText(" X ");
        }


        Picasso.with(getActivity()).load(escudo_oficial_mandante_last).into(imageTime1_last);
        Picasso.with(getActivity()).load(escudo_oficial_visitante_last).into(imageTime2_last);
    }

    private void FillNextMatch() {
        campeonato_next.setText(campeonato_jogo_next);
        time1_next.setText(apelido_mandante_next);
        time2_next.setText(apelido_visitante_next);
        if(placar_oficial_mandante_next=="null" &&placar_oficial_visitante_next=="null"){
            placar1_next.setText("");
            placar2_next.setText("");
        }
        else{
            placar1_next.setText(placar_oficial_mandante_next);
            placar2_next.setText(placar_oficial_visitante_next);
        }
        if(dataformatada_next==null  ){//&&estadio_next=="null"&&hora_next=="null"){
            informacoes_next.setText("");
            versus_next.setText("");
        }
        else{

            informacoes_next.setText(dataformatada_next+ " "+estadio_next+ " "+hora_next);
            versus_next.setText(" X ");
        }


        Picasso.with(getActivity()).load(escudo_oficial_mandante_next).into(imageTime1_next);
        Picasso.with(getActivity()).load(escudo_oficial_visitante_next).into(imageTime2_next);

    }

    private void FillCurrentMatch() {
        campeonato_current.setText(campeonato_jogo_current);
        time1_current.setText(apelido_mandante_current);
        time2_current.setText(apelido_visitante_current);
        if(placar_oficial_mandante_current=="null" &&placar_oficial_visitante_current=="null"){
            placar1_current.setText("");
            placar2_current.setText("");
        }
        else{
            placar1_current.setText(placar_oficial_mandante_current);
            placar2_current.setText(placar_oficial_visitante_current);
        }

        if(dataformatada_current==null ){//&& estadio_current=="null"&& hora_current=="null"){
            informacoes_current.setText("");
            versus_current.setText("");
        }
        else{
            informacoes_current.setText(dataformatada_current + " " + estadio_current + " " + hora_current);
            versus_current.setText(" X ");
        }

        Picasso.with(getActivity()).load(escudo_oficial_mandante_current).into(imageTime1_current);
        Picasso.with(getActivity()).load(escudo_oficial_visitante_current).into(imageTime2_current);

    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

}