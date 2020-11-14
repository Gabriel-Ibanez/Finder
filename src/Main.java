import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("\nPROGRAMA FINDER IBANEZ - 1812130004\n");

        Map<Integer,Pessoa> mapaProfissionais = new HashMap<>();
        Map<Integer,Pessoa> mapaProfissionaisSelecionados = new HashMap<>();

        int selecionados[];
        int cont1 = 1, cont2 = 0;

        Pessoa IESB = new Pessoa("IESB",-15.836073,-47.912019," - ",true);

        String s1, s2, nomeArquivo = "C:\\Users\\ibane\\Desktop\\1GABRIEL\\2019\\IESB 2019\\2019-1\\GRA\\FinderIbanez\\dados.txt";
        String linha = new String();

        File arq = new File(nomeArquivo);
        if (arq.exists()){
            Scanner scanner = new Scanner(new FileReader(nomeArquivo)).useDelimiter(";|\\n");
            while (scanner.hasNext()) {

                String nomeP = scanner.next();

                String d1 = scanner.next();
                double latitudeP = Double.parseDouble(d1);

                String d2 = scanner.next();
                double longitudeP = Double.parseDouble(d2);

                String profissaoP = scanner.next();

                Boolean b = true;
                String b1 = scanner.next();
                String b2 = "False\r";
                String b3 = "false\r";

                if (b1.equals(b2) || b1.equals(b3)) b = false;
                Boolean disponibilidadeP = b;

                Pessoa p = new Pessoa(nomeP,latitudeP,longitudeP,profissaoP,disponibilidadeP);
                mapaProfissionais.put(cont1,p);
                cont1++;
                if (p.getDisponibilidade()){
                    cont2++;
                }

            }
            scanner.close();
        } else {
            System.out.println(" Arquivo não encontrado!\n");
        }

        Grafo g1 = new Grafo();

        System.out.println("Dados dos profissionais contidos no arquivo dados.txt: ");

        for (int i = 0; i <= mapaProfissionais.size(); i++){
            if (mapaProfissionais.containsKey(i)){
                System.out.print("\tProfissional id nº "+i+": ");
                System.out.print(mapaProfissionais.get(i).getNome());
                System.out.print(", " + mapaProfissionais.get(i).getLatitude());
                System.out.print(", " + mapaProfissionais.get(i).getLongitude());
                System.out.print(", " + mapaProfissionais.get(i).getProfissao());
                System.out.print(", " + mapaProfissionais.get(i).getDisponibilidade());
                System.out.println("");
            }
        }
        System.out.println("\tTOTAL: " + (mapaProfissionais.size()) + " profissionais listados, sendo " + cont2 + " disponíveis.");

        int selecao = 0;
        Scanner scanner2 = new Scanner(System.in);
        do {
            System.out.println("\nDigite o raio máximo (em km) para a busca dos profissionais disponíveis: ");
            int raioMaximo = scanner2.nextInt();

            System.out.println("\nProfissionais disponíveis dentro do raio de "+ raioMaximo+ " km:");
            int cont = 0;

            for (int i = 0; i <= mapaProfissionais.size(); i++) {
                if (mapaProfissionais.containsKey(i)){ // verificação se o profissional está dentro do raio mínimo
                    if ((g1.getDistancia(IESB, mapaProfissionais.get(i)) <= raioMaximo) && mapaProfissionais.get(i).disponibilidade) {
                        System.out.printf("\tProfissional id nº " + (i) + ": ");
                        System.out.printf(mapaProfissionais.get(i).getNome() + ", ");
                        System.out.printf(mapaProfissionais.get(i).getProfissao() + ". ");
                        System.out.printf("Distância até %s: %.2f km.\n", IESB.getNome(), g1.getDistancia(mapaProfissionais.get(i), IESB));
                        cont++;
                    }
                }
            }

            s1 = new String();  // ajuste do plural
            if (cont == 1 ) {
                s1 = "l";
            } else s1 = "is";

            System.out.println("\tTOTAL: " + cont + " profissiona"+ s1 + " disponíve"+s1 + ".");

            System.out.println("\nDigite 1 para confirmar ou 0 para inserir outro raio máximo: ");
            selecao = scanner2.nextInt();

            if (selecao == 1 ) {
                for (int i = 0; i <= mapaProfissionais.size(); i++) {
                    if (mapaProfissionais.containsKey(i)) {
                        if ((g1.getDistancia(IESB, mapaProfissionais.get(i)) > raioMaximo))
                            mapaProfissionais.get(i).setDisponibilidade(false);
                    }
                }
            } else if (selecao != 0) {
                System.err.print("\tOpção inválida!");
                Toolkit.getDefaultToolkit().beep();
                selecao = 0;
            }

        } while (selecao == 0);

        do {
            System.out.println("\nDigite o id de um profissional para acrescentá-lo à lista de selecionados ou 0 para terminar: ");
            selecao = scanner2.nextInt();

            if (selecao > 0 && selecao <= mapaProfissionais.size()) {
                if (mapaProfissionaisSelecionados.containsKey(selecao)) {
                    System.err.printf("\tO profissional id nº " + selecao + " já foi selecionado!\n");
                    Toolkit.getDefaultToolkit().beep();
                } else{
                    if (mapaProfissionais.get(selecao).getDisponibilidade() == false) {
                        System.err.printf("\tO profissional id nº " + selecao + " não está disponível!\n");
                        Toolkit.getDefaultToolkit().beep();
                    } else {
                        mapaProfissionaisSelecionados.put(selecao, mapaProfissionais.get(selecao));
                        System.out.printf("\tProfissional id nº " + selecao + " inserido na lista!\n");
                    }
                }
            } else if (selecao > mapaProfissionais.size() || selecao < 0 ) {
                System.err.printf("\tNão existe o id digitado!!\n");
                Toolkit.getDefaultToolkit().beep();
            }


            // imprimindo lista de Profissionais selecionados
            System.out.println("\nLista de profissionais selecionados:");

            for (int i = 0; i <= mapaProfissionais.size(); i++) {
                if (mapaProfissionaisSelecionados.containsKey(i)){
                    System.out.printf("\tProfissional id nº " + (i) + ": ");
                    System.out.printf(mapaProfissionaisSelecionados.get(i).getNome() + ", ");
                    System.out.printf(mapaProfissionaisSelecionados.get(i).getProfissao() + ". ");
                    System.out.printf("Distância até %s: %.3f km.\n", IESB.getNome(), g1.getDistancia(mapaProfissionaisSelecionados.get(i), IESB));
                }
            }

            // ajuste do plural
            s1 = new String();
            s2 = new String();
            if (mapaProfissionaisSelecionados.size() == 1 ) {
                s1 = "l";
                s2 = "" ;
            } else {
                s1 = "is";
                s2 = "s" ;
            }

            System.out.println("\tTOTAL: " + mapaProfissionaisSelecionados.size() + " profissiona" + s1+ " selecionado" + s2 +"." );

        } while (selecao != 0);

        System.out.println("\t Inserindo o 'IESB' no grafo (id nº 0)...");
        mapaProfissionaisSelecionados.put(0,IESB);

        System.out.println("\nCalculando a melhor rota...\n");
        Grafo g = new Grafo(mapaProfissionais.size()+1);

        g.popularGrafo(mapaProfissionaisSelecionados);
        g.mostrarGrafo();
        g.mostrarLista();
        int verticieDeOrigem = 0;
        int rota[] = new int[mapaProfissionaisSelecionados.size()+1]; //somamos 2 para conter a origem e o destino;

        g.calcularMelhorCaminhoGuloso(verticieDeOrigem);

        int selecao2 = 0;
        boolean ficarNoDO = false;
        do {
            System.out.println("\nRotas por caminhos aleatórios. Digite o número de buscas: ");
            int numeroDeBuscas = scanner2.nextInt();
            g.calcularPorCaminhoAleatorio(verticieDeOrigem, numeroDeBuscas);

            do {
                System.out.println("\nDigite 1 para fazer mais buscas aleatórias ou 0 para sair: ");
                selecao2 = scanner2.nextInt();

                if (selecao2 != 1 && selecao2 != 0) {
                    System.err.print("\tOpção inválida!\n");
                    Toolkit.getDefaultToolkit().beep();
                    ficarNoDO = true;
                } else {
                    ficarNoDO = false;
                }

            } while (ficarNoDO);

        } while (selecao2 == 1);
        scanner2.close();

        System.err.print("Fim!\n");

    }
}
