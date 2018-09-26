package com.sigcar.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sigcar.Classes.Track;
import com.sigcar.R;

import java.util.List;

public class TrackListAdapter extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track> trackList;  // lista para armazenar as tracks

    public TrackListAdapter(Activity context, List<Track> trackList) {

        super(context, R.layout.layout_track_item, trackList);
        this.context = context;
        this.trackList = trackList;
    }

    // método que é chamado para fornecer cada item da lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // criando um objeto "inflador"
        LayoutInflater inflater = context.getLayoutInflater();

        // usando o inflador para criar uma View a partir do arquivo de layout
        // que fizemos definindo os itens da lista
        View listViewItem = inflater.inflate(R.layout.layout_track_item, null, true);

        // pegando referências para as views que definimos dentro do item da lista,
        // isto é, os 2 textviews: nome e "rating" da trilha (música)
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.textViewRating);

        // a posição da trilha na lista (armazenamento) é a mesma na lista (listview)
        // então usamos esse valor (position) para acessar o objeto "Track" correto
        // dentro da lista trackList
        Track track = trackList.get(position);

        // finalmente, colocamos os valores do objeto track recuperado
        // nas views que formam nosso item da lista
        textViewName.setText(track.getTrackName());
        //     o rating é numérico (int), convertemos para string para
        //     colocar no textView
        textViewRating.setText(String.valueOf(track.getTrackRating()));

        // a view está pronta! É só devolver para quem pediu
        return listViewItem;
    }
}