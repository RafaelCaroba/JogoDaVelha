package br.senai.sp.cotia.jogodavelhaapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.senai.sp.cotia.jogodavelhaapp.R;
import br.senai.sp.cotia.jogodavelhaapp.databinding.FragmentJogoBinding;

public class FragmentJogo extends Fragment {
    // var para acessar os elementos na view
    private FragmentJogoBinding binding;

    // vetor de bt's
    private Button[] botoes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // instanciar o binding
        binding = FragmentJogoBinding.inflate(inflater, container, false);

       // instanciar o vetor
        botoes = new Button[9];

        // agrupar os botoes no vetor
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        // associar o listener aos botoes
        for (Button bt : botoes) {
            bt.setOnClickListener(listenerBotoes);
        }



        //retorna a view do fragment
        return binding.getRoot();
    }

    private View.OnClickListener listenerBotoes = btPress -> {
        // pegar o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(getId());

        // extrair posição do nomeBotao
        String posicao = nomeBotao.substring(nomeBotao.length() - 2);

        //extrair a posição em linha e coluna (posição)
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));
    };

}