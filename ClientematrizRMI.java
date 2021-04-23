import java.rmi.Naming;

public class ClientematrizRMI {
    static final int N = 8;
    static float[][] A = new float[N][N];
    static float[][] B = new float[N][N];
    static float[][] Bo = new float[N][N];
    static float[][] C = new float[N][N];


    static float[][] A1;
    static float[][] A2;
    static float[][] B1;
    static float[][] B2;

    static float[][] C1;
    static float[][] C2; 
    static float[][] C3; 
    static float[][] C4;
    public static void main(String[] args) throws Exception {
        String url0 = "rmi://localhost/matrices";
        String url1 = "rmi://10.0.0.5/matrices";
        String url2 = "rmi://10.0.0.6/matrices";
        
        for (int i = 0; i < N; i++){ 
            for (int j = 0; j < N; j++){
                A[i][j] = i - 2*j; 
                B[i][j] = i + 2*j;
                Bo[i][j] =i + 2*j;
            }
        } 
        
        for (int i = 0; i < N; i++){
            for (int j = 0; j < i; j++){
                float t = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = t;
            }
        }
        
        InterfacematrizRMI r1 = (InterfacematrizRMI)Naming.lookup(url0);
        InterfacematrizRMI r2 = (InterfacematrizRMI)Naming.lookup(url0);

        A1 = separa_matriz(A, 0);
        A2 = separa_matriz(A, N/2);
        B1 = separa_matriz(B, 0); 
        B2 = separa_matriz(B, N/2);

        C1 = r1.multiplica_matrices(A1, B1);
        C2 = r1.multiplica_matrices(A1, B2);
        C3 = r2.multiplica_matrices(A2, B1);
        C4 = r2.multiplica_matrices(A2, B2);

        acomoda_matriz(C, C1, 0, 0);
        acomoda_matriz(C, C2, 0, N/2);
        acomoda_matriz(C, C3, N/2, 0);
        acomoda_matriz(C, C4, N/2, N/2);
        
        if (N == 8){

            imprimir_matriz(A, N, N, "A");
            imprimir_matriz(Bo, N, N, "B ");
            imprimir_matriz(C, N, N, "C");
            System.out.println("checksum = " + checksum(C));

        }
         else {
            System.out.println("checksum = " + checksum(C));
        }
    }    
    /**Método acomoda_matriz
     * @param C
     * @param A
     * @param renglon
     * @param columna
     */
    static void acomoda_matriz (float[][] C,float[][] A, int renglon, int columna) {
        
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++){
                C[i + renglon][j + columna] = A[i][j];}
    
    }
    /**
     * Imprime una matriz
     * @param m  
     * @param filas
     * @param columnas
     * @param s
     * @throws RemoteException
     */
    static void imprimir_matriz(float[][] m, int filas, int columnas, String s) {
       
        System.out.println("\nMatriz " + s);
        for (int i = 0; i< filas; i++){
            for (int j = 0; j < columnas; j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Método que calcula el checksum
     * @param m
     * @return double checksum
     */
    static double checksum(float[][] m) {
        
        double s = 0;
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                s += m[i][j];
        return s;    
    }
    /**
     * Método que divide una matriz A dado un renglón inicial.
     * @param A
     * @param inicio
     * @return An[N/2][N]
     */
    static float[][] separa_matriz(float[][] A,int inicio) {
        
        float[][] M = new float[N/2][N];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N; j++)
                M[i][j] = A[i + inicio][j];
        return M;
    
    }   
} 
