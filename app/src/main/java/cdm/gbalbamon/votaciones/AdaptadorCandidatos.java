package cdm.gbalbamon.votaciones;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorCandidatos extends ArrayAdapter<Candidato> {

    public static class ViewHolder {
        TextView nombreCandidato;
        TextView nombrePartido;
        ImageView imagenPartido;
    }

    private Context context;
    private ArrayList<Candidato> datos;

    public AdaptadorCandidatos(Context context, ArrayList<Candidato> datos) {
        super(context, R.layout.spinner_candidatos, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View item = convertView;
        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.spinner_candidatos, null);
            holder = new ViewHolder();
            holder.imagenPartido = (ImageView) item.findViewById(R.id.ivImagenPartido);
            holder.nombreCandidato = (TextView) item.findViewById(R.id.tvNombreCandidato);
            holder.nombrePartido = (TextView) item.findViewById(R.id.tvNombrePartido);
            item.setTag(holder);
        } else
            holder = (ViewHolder) item.getTag();

        int resourceId = context.getResources().getIdentifier(datos.get(position).getNombrePartido().toLowerCase(), "drawable", context.getPackageName());
        holder.imagenPartido.setImageResource(resourceId);
        holder.nombreCandidato.setText(datos.get(position).getNombre());
        holder.nombrePartido.setText(datos.get(position).getNombrePartido());
        holder.nombrePartido.setTextColor(datos.get(position).getColorPartido());

        return item;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
