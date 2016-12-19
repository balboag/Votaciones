package cdm.gbalbamon.votaciones;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragBoton extends Fragment {

    private int numClicksTotales;
    private int numClicks;
    private String textButton;
    private Button buttonVote;

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onClickBotonVotar(int numClicks);
        void lastClick();
    }

    public void click() {
        if (numClicks < numClicksTotales) {
            mListener.onClickBotonVotar(++numClicks);
            updateButtonText();
            if (numClicks == numClicksTotales) {
                buttonVote.setEnabled(false);
                mListener.lastClick();
            }
        }
    }

    public void updateButtonText() {
        buttonVote.setText(textButton + " " + numClicks + "/" + numClicksTotales);
    }

    public void setOnFragmentInteraction(OnFragmentInteractionListener mListener, String text, int numClicks) {
        this.mListener = mListener;
        this.textButton = text;
        this.numClicksTotales = numClicks;
        this.numClicks = 0;
        updateButtonText();
    }

    public FragBoton() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_boton, container, false);
        buttonVote = (Button) view.findViewById(R.id.btnVotar);

        buttonVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
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
