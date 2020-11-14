import java.util.*;

import static java.lang.Math.*;

public class Grafo {

    int numeroDeVertices = 0;
    double infinito = 100000;

    public double getDistancia(Pessoa p1, Pessoa p2) {
        Double d;
        double dLat = Math.toRadians(p2.getLatitude() - p1.getLatitude());
        double dLng = Math.toRadians(p2.getLongitude() - p1.getLongitude());
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(p1.getLatitude()))
                * Math.cos(Math.toRadians(p2.getLatitude()));
        d = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * 6371; // Raio da terra em Km.
        return d;
    }

    double matrizAdjacência[][];

    public Grafo() {
    }

    public Grafo(int numeroDeVertices){
        matrizAdjacência = new double[numeroDeVertices][numeroDeVertices];
        this.numeroDeVertices = numeroDeVertices;
    }

    public void popularGrafo(Map m){
        for (int i = 0; i < numeroDeVertices; i++){
            for (int j = 0; j < numeroDeVertices; j++){
                matrizAdjacência[i][j] = 0.0;
                if (m.containsKey(i) && m.containsKey(j)) {
                    Pessoa p1 = (Pessoa) m.get(i);
                    Pessoa p2 = (Pessoa) m.get(j);
                    matrizAdjacência[i][j] = getDistancia(p1,p2);
                }
            }
        }
    }


    public void mostrarGrafo() {
        System.out.println("Matriz de Adjacência:");
        System.out.print("\t");
        for (int i = 0; i < numeroDeVertices; i++) {
            System.out.print("\t  [ " + i + " ]" + "\t\t");
        }
        System.out.println("");
        for (int i = 0; i < numeroDeVertices; i++) {
            System.out.print("[ " + i + " ]" + "\t");
            for (int j = 0; j < numeroDeVertices; j++) {
                if (matrizAdjacência[i][j] == 0.0) {
                    System.out.printf(" %.5f", matrizAdjacência[i][j]);
                } else {
                    if (matrizAdjacência[i][j] < 100.0) {
                        System.out.printf(" %.4f", matrizAdjacência[i][j]);
                    } else {
                        if (matrizAdjacência[i][j] < 1000.0) {
                            System.out.printf(" %.3f", matrizAdjacência[i][j]);
                        } else {
                            if (matrizAdjacência[i][j] < 10000.0) {
                                System.out.printf(" %.2f", matrizAdjacência[i][j]);
                            } else {
                                if (matrizAdjacência[i][j] < 100000.0) {
                                    System.out.printf(" %.1f", matrizAdjacência[i][j]);
                                }
                            }
                        }
                    }
                }
                System.out.print("\t\t");
            }
            System.out.println("");
        }
    }
    int cont = 0;
    double custo;

    public List<Integer> vizinhos (int n){
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < numeroDeVertices; i++){
            if (matrizAdjacência[n][i] > 0.0) lista.add(i);
            cont++;
        }
        return lista;
    }

    public void mostrarLista() {
        System.out.println("\nLista de Adjacência:");
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < numeroDeVertices; i++) {
            System.out.print("[" + i + "]: ");
            l = vizinhos(i);
            for (int j = 0; j < l.size(); j++) {
                System.out.print("[" + l.get(j) + "]" + " ");
            }
            System.out.print("\n");
        }
    }


    //  Contrução do método que apresenta o melhor caminho guloso:


    public void calcularMelhorCaminhoGuloso(int origem) { // solução para encontrar um caminho euleriano, buscando o caminho mais próximo a cada vértice
        int caminho[] = new int[vizinhos(origem).size()+2]; // criação de um vetor que receberá os vértices e também o por último o vértice origem;
        for (int i = 0; i< caminho.length; i++) {
            caminho[i] = -1;
        }

        boolean vetorVisitados[] = new boolean [numeroDeVertices]; // vetor que armazeanará o nós inseridos

        for (int i = 0; i< numeroDeVertices; i++){
            vetorVisitados[i]= false;// preenche o vetor dos visitados com false
        }
        int cont1 = 0;
        caminho[cont1] = origem;
        vetorVisitados[0] = true;




        for (int i = 0; i< numeroDeVertices; i++){
            double valor_de_referencia = infinito;
            int vizinho_selecionado = 0;

            for (int j = 0; j< numeroDeVertices; j++){

                if ( matrizAdjacência[i][j]!= 0 && !vetorVisitados[j] && valor_de_referencia > matrizAdjacência[i][j]){
                    vizinho_selecionado = j;                        // aqui acima se verifica se o os vizinhos já foram visitados
                    valor_de_referencia = matrizAdjacência[i][j];   // e se tem o menor valor, para decidir para qual vértice ir.
                }
            }
            if (vizinho_selecionado != origem ){
                cont1++;
                caminho[cont1] = vizinho_selecionado;
                vetorVisitados[vizinho_selecionado] = true;
            }
        }

        // aqui indicamos que iremos retornar à origem
        caminho[caminho.length-1]= origem;

        System.out.print("\nRota encontrada pelo algoritmo de busca gulosa: \n");
        for (int i = 0; i< caminho.length; i++) {
            System.out.print("[" + caminho[i]+ "]" + "  ");
        }


        // calculando o custo
        custo = 0;
        for (int i = 0; i < caminho.length-1; i++) {
            custo = custo + matrizAdjacência[caminho[i]][caminho[i+1]];
        }
        System.out.printf("\t Custo total: %.2f",custo);
        System.out.println(" km.");
    }


    public void calcularPorCaminhoAleatorio(int origem, int buscas) { // solução para encontrar um caminho euleriano, indo aleatoriamente a um Vértice não visitado

        System.out.println("Mostrando outras rotas pelo argoritmo de busca aleatória: ");
        int iteracaoSelecionada = 0;
        double custoMinimo = infinito;
        for (int k = 0; k < buscas; k++ ){
            int caminho[] = new int[vizinhos(origem).size()+2]; // criação de um vetor que receberá os vértices e também o por último o vértice origem;

            for (int i = 0; i< caminho.length; i++) {
                caminho[i] = -1;
            }

            List<Integer> listaElementos = new ArrayList<>();

            for (int i = 0; i< numeroDeVertices; i++){
                if (matrizAdjacência[origem][i] != 0) listaElementos.add(i);
            }

            int cont1 = 0;
            caminho[cont1] = origem;
            Random numeroaAleatorio = new Random();

            while (!listaElementos.isEmpty()) {
                int i = numeroaAleatorio.nextInt(listaElementos.size());
                cont1++;
                caminho[cont1] = listaElementos.get(i);
                listaElementos.remove(i);
            };


            // aqui indicamos que iremos retornar à origem
            caminho[caminho.length-1]= origem;


            System.out.print("Rota nº "+ (k+1));
            if (k <100) System.out.print("\t");
            for (int i = 0; i< caminho.length; i++) {
                System.out.print("\t[" + caminho[i]+ "]" + "  ");
            }

            // calculando o custo
            double custo = 0;
            for (int i = 0; i < caminho.length-1; i++) {
                custo = custo + matrizAdjacência[caminho[i]][caminho[i+1]];
            }

            System.out.printf("\t Custo total: %.2f",custo);
            System.out.print(" km.\n");
            if (custo < custoMinimo) {
                custoMinimo = custo;
                iteracaoSelecionada = k;
            }
        }

        System.out.printf("\t\n%d buscas realizadas. O menor custo encontrado foi de %.2f km.",buscas,custoMinimo);
        System.out.println(" (rota nº "+ (iteracaoSelecionada+1) + ").");

        System.out.print("Este custo foi ");

        if (custoMinimo < custo) {
            System.out.printf("%.2f",(custo-custoMinimo));
            System.out.print(" km menor");
        } else if (custoMinimo == custo) {
            System.out.print("igual");
        } else {
            System.out.printf("%.2f",(custoMinimo-custo));
            System.out.print(" km maior");
        }
        System.out.print(" que o custo encontrado pela busca gulosa (");
        System.out.printf("%.2f km).\n",custo);

    }

}
