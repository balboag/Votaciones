package cdm.gbalbamon.votaciones;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class frag_boton extends Fragment {

    private int maxNumClicks;
    private int numClicks;
    private String textButton;
    private Button botonVotar;

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onClickBotonVotar();
        void heAcabado();
    }

    public void onClickBoton() {
        numClicks--;
        if (numClicks > 0) {
            botonVotar.setText(textButton + " " + numClicks + "/" + maxNumClicks);
        } else {
            botonVotar.setText(textButton + " " + numClicks + "/" + maxNumClicks);
            botonVotar.setEnabled(false);
            mListener.heAcabado();
        }
    }

    public void setOnFragmentInteraction(OnFragmentInteractionListener onFragmentInteractionListener, String text, int numClicks) {
        this.mListener = onFragmentInteractionListener;
        this.textButton = text;
        this.numClicks = numClicks;
        maxNumClicks = numClicks;
        botonVotar.setText(text + " " + numClicks + "/" + maxNumClicks);
    }

    public frag_boton() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_boton, container, false);
        botonVotar = (Button) view.findViewById(R.id.btnVotar);

        botonVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickBotonVotar();
                onClickBoton();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
