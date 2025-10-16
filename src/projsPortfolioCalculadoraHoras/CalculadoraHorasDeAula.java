package projsPortfolioCalculadoraHoras;

/**
 * CalculadoraHorasDeAula
 * 
 * Programa que soma tempos de vídeo/aula e ajusta o total conforme a velocidade de reprodução.
 * Exemplo: 3h02 + 5h48 a 1.75x → mostra o tempo total e o tempo ajustado.
 * 
 * @author Vinicius
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculadoraHorasDeAula {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bem-vindo!");
        System.out.println("Essa é uma calculadora que soma o tempo das suas aulas e ajusta conforme a velocidade de reprodução.");
        System.out.println("Formato de tempo: HHhMM (ex: 0h36, 2h31, 4h03)");
        System.out.println("Quando terminar, digite 0 para encerrar a lista de tempos.\n");

        List<String> tempos = new ArrayList<>();

        while (true) {
            System.out.print("Digite o tempo da aula (ou 0 para finalizar): ");
            String entrada = sc.nextLine().trim();

            if (entrada.equals("0")) break;
            if (!entrada.matches("\\d+h\\d{2}")) {
                System.out.println("Formato inválido! Use HHhMM (ex: 1h30).");
                continue;
            }
            tempos.add(entrada);
        }

        System.out.print("\nDigite a velocidade de reprodução (ex: 1.5): ");
        String velInput = sc.nextLine().trim().replace(",", "."); // também aceita vírgula

        double velocidade;
        try {
            velocidade = Double.parseDouble(velInput);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida! Digite um número válido (ex: 1.25).");
            return;
        }

        if (velocidade <= 0) {
            System.out.println("Velocidade inválida! Use um número maior que zero.");
            return;
        }

        int totalMinutos = somarTempos(tempos);
        int horasTotais = totalMinutos / 60;
        int minutosTotais = totalMinutos % 60;

        System.out.printf("\nTempo total normal: %dh%02d\n", horasTotais, minutosTotais);

        int minutosAjustados = ajustarPorVelocidade(totalMinutos, velocidade);
        int horasAjustadas = minutosAjustados / 60;
        int minutosRestantes = minutosAjustados % 60;

        System.out.printf("Tempo ajustado (%.2fx): %dh%02d\n", velocidade, horasAjustadas, minutosRestantes);

        // 🔹 Cálculo da economia
        int economia = totalMinutos - minutosAjustados;
        int horasEcon = economia / 60;
        int minEcon = economia % 60;
        System.out.printf("\nVocê economizou %dh%02d assistindo a %.2fx!\n", horasEcon, minEcon, velocidade);

        sc.close();
    }

    public static int somarTempos(List<String> tempos) {
        int totalMinutos = 0;

        for (String tempo : tempos) {
            tempo = tempo.toLowerCase().replaceAll(" ", "");
            String[] partes = tempo.split("h");

            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);

            totalMinutos += horas * 60 + minutos;
        }

        return totalMinutos;
    }

    public static int ajustarPorVelocidade(int totalMinutos, double velocidade) {
        return (int) Math.round(totalMinutos / velocidade);
    }
}
