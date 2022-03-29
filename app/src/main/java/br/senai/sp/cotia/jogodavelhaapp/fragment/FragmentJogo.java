package br.senai.sp.cotia.jogodavelhaapp.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.jogodavelhaapp.R;
import br.senai.sp.cotia.jogodavelhaapp.databinding.FragmentJogoBinding;

@SuppressWarnings("ALL")
public class FragmentJogo extends Fragment {
    // var para acessar os elementos na view
    private FragmentJogoBinding binding;

    // vetor de bt's
    private Button[] botoes;

    //variável que representa o tabuleiro
    private String[][] tabuleiro;

    //variável para os símbolos
    private String simbJog1, simbJog2, simbolo;

    //variável Random para sortear quem começa.... gera valor boolean
    private Random random;

    // var contador de jogadas
    private int numJogadas = 0;

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

        //inicializa o tabuleiro
        tabuleiro = new String[3][3];

        // preencher o tabuleiro com ""
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }

        //instancia o Random
        random = new Random();
        //define os simbolos dos jogadores
        simbJog1 = "X";
        simbJog2 = "O";

        //sorteia o jogador
        sorteia();

        // atualiza a vez
        atualizaVez();

        //retorna a view do Fragment
        return binding.getRoot();
    }

    private void sorteia() {
        //caso random gere um valor V, jogador 1 começa
        //caso contrario o jogador 2 começa
        if (random.nextBoolean()) {
            simbolo = simbJog1;
        } else {
            simbolo = simbJog2;
        }
    }

    private void atualizaVez() {
        // verifica o jogador da vez e destaca o placar do mesmo
        if (simbolo.equals(simbJog1)) {
            binding.linear1.setBackgroundColor(getResources().getColor(R.color.gray_300));
            binding.linear2.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            binding.linear2.setBackgroundColor(getResources().getColor(R.color.gray_300));
            binding.linear1.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private boolean venceu() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                Log.w("MATRIZ", tabuleiro[i][j]);
            }
        }



        // verifica as linhas procurando vencedor
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0].equals(simbolo)
                    && tabuleiro[i][1].equals(simbolo)
                    && tabuleiro[i][2].equals(simbolo)) {
                return true;
            }
        }

        // verifica as colunas procurando vencedor
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[0][i].equals(simbolo)
                    && tabuleiro[1][i].equals(simbolo)
                    && tabuleiro[2][i].equals(simbolo)) {
                return true;
            }
        }

        // verifica se venceu nas diagonais
        if (tabuleiro[0][0].equals(simbolo)
                && tabuleiro[1][1].equals(simbolo)
                && tabuleiro[2][2].equals(simbolo)) {
            return true;
        }

        if (tabuleiro[0][2].equals(simbolo)
                && tabuleiro[1][1].equals(simbolo)
                && tabuleiro[2][0].equals(simbolo)) {
            return true;
        }
        return false;
    }

    private void resetGame() {
//     for (int i = 0; i < 9; i++){
//         numJogadas = 0;
//         botoes[i].setClickable(true);
//         botoes[i].setText("");
//         botoes[i].setBackgroundColor(getResources().getColor(R.color.blue_600));
//     }
//         sorteia();
//         atualizaVez();

        for (Button botao : botoes) {
            botao.setClickable(true);
            botao.setText("");
            botao.setBackgroundColor(getResources().getColor(R.color.blue_600));
        }
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }
        sorteia();
        atualizaVez();
        numJogadas = 0;
    }

    private View.OnClickListener listenerBotoes = btPress -> {
        // incrementa as jogadas
        numJogadas++;

        // pegar o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());

        // extrair posição do nomeBotao
        String posicao = nomeBotao.substring(nomeBotao.length() - 2);

        //extrair a posição em linha e coluna (posição)
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));


        // marcar no tabuleiro o símbolo que foi jogado
        tabuleiro[linha][coluna] = simbolo;
        //faz um casting de View pra Button
        Button botao = (Button) btPress;
        //trocar o texto do botão que foi clicado
        botao.setText(simbolo);

        // desabilita botão clicado
        botao.setClickable(false);

        // trocar background do bt
        botao.setBackgroundColor(Color.DKGRAY);

        // verifica se venceu
        if (numJogadas >= 5 && venceu()) {
            Toast.makeText(getContext(), R.string.venceu, Toast.LENGTH_LONG).show();
            resetGame();

        } else if (numJogadas == 9) {
            Toast.makeText(getContext(), R.string.velha, Toast.LENGTH_LONG).show();
            resetGame();
        } else {
            // inverter a vez
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;
            atualizaVez();
        }


//        if (numJogadas > 8  && venceu() == false){
//            Toast.makeText(getContext(), R.string.velha, Toast.LENGTH_LONG).show();
//            resetGame();
//        }
    };

}