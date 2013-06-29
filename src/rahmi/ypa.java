/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ypa.java
 *
 * Created on 05.Haz.2012, 02:15:29
 */
package rahmi;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import java.util.Random;
import java.lang.Math;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;
import java.io.*;

class x {
    public static int sinifi = 0;
    public static ArrayList<Integer> yasak = new ArrayList<Integer>(10);
}
class neural { 
    public static double net = 0;
    public static double sonuc = 0;   
}
class process {
    double net;
    double cikti;
}


public class ypa extends javax.swing.JFrame {

    /** Creates new form ypa */
    public ypa() {
        initComponents();
    }
    
    double[][] Gateileri;
    double[][] Gategeri;
    double[] y;
    double benzerlik;
    String[][] girdiler;
    int M, N;
    x[] girdi;
    int index = 0;
    
    double[] Gate;
    double[] bias;
    double alfa = 0.5;
   // String[][] girdi;
    String[] beklenen;
    boolean uygunluk = false;
   // File file;
    neural[] neron;
    String[][] all;
    Random generator = new Random();
    //adaline bitimi
    //----ADALİNE ALGORİTMASI----
   
    public void adalineilklendir() {
        int i, j;
        String[] lines;
        String[][] all;
        lines = jTextArea1.getText().split("\n");
        all = new String[lines.length][];
        M = lines.length;
        System.out.println("M : " + M);
        
        for( i = 0; i < M ; i++){
            all[i] = lines[i].split(" ");
        }
        N=all.length;
        System.out.println("N :" + N);
        System.out.println(all[0][0]);
        System.out.println(all[0][1]);
        neron = new neural[N-1];        
        Gate = new double[N-1];
        bias = new double[N-1];
        girdiler = new String[M][N-1];
        beklenen = new String[M];
        for( i = 0; i < N-1; i++ ){
            bias[i] = generator.nextDouble();
            //for( j = 0; j < N-1; j++){
            Gate[i]= generator.nextDouble();
            
        }
        lines = jTextArea2.getText().split("\n");
        for(i = 0;i < lines.length;i++) {
            beklenen[i] = lines[i];
        }
        System.out.println("beklenen1:" + beklenen[0]);
        System.out.println("beklenen2:" + beklenen[1]);
        for( i = 0; i < M; i++ ){
            for( j = 0; j < N-1; j++ ){
                System.out.println("hata");
                girdiler[i][j] = all[i][j];
                System.out.println("all :" + all[0][0]);
                System.out.println("all :" + all[0][1]);
                System.out.println("all :" + all[1][0]);
                System.out.println("all :" + all[1][1]);
                jTextArea2.append(girdiler[i][j]+" ");
            }
            //beklenen[i] = all[i][j];
        }
        
    }
     public void adalineciktihesap(){
        int i, j;
        
        for( j = 0; j < N-1; j++){
         //   for( i = 0; i < N-1; i++){
                neron[j].net = 0;
           // }
        }
        for( j = 0; j < N-1; j++){
            for( i = 0; i < N-1; i++){
                neron[j].net += Gate[j]*Double.parseDouble(girdiler[index][j]);
            }
            neron[j].net += bias[j];
            
            if( neron[j].net < 0){
                neron[j].sonuc = -1;
            }
            else {
                neron[j].sonuc = 1;
            }
            if( Double.parseDouble(beklenen[index]) != neron[j].sonuc){
                uygunluk = true;
            }
        }
    }
    public void adalineagirlikdegis(){
        int i, j;
        
        for( i = 0; i < N-1; i++){
           for( j = 0; j < N-1; j++){
                 Gate[i] = Gate[i] + alfa*(Double.parseDouble(beklenen[index]) - neron[i].sonuc)*Double.parseDouble(girdiler[index][j]);
           }
            bias[i] = bias[i] + alfa*(Double.parseDouble(beklenen[index]) - neron[i].sonuc);
        }
    }
    public int adalinesonuc(){
       int i, j, result = 0;

        for( j = 0; j < N-1; j++){
            //for( i = 0; i < N-1; i++){
                neron[j].net = 0;
            //}
       }
       for( j = 0; j < N-1; j++){
            for( i = 0; i < N-1; i++){
                neron[j].net += Gate[j]*Double.parseDouble(all[index][i]);
            }
            neron[j].net += bias[j];
       
            
            if( neron[j].net < 0){
                neron[j].sonuc = -1;
            }
            else {
                neron[j].sonuc = 1;
            }
            result += neron[j].sonuc;
       }
        return result/(N-1);
    }
    public String[][] ilklendir(){
        int i, j;
        String[] lines;
        String[][] all;
        lines = jTextArea1.getText().split("\n");
        all = new String[lines.length][];
        M = lines.length;
        for( i = 0; i < M ; i++){
            all[i] = lines[i].split(" ");
        }            
      
        //System.out.println(M);
        N = 3;
        for( i = 0; i < all.length; i++){
           for( j = 0; j < all[i].length; j++){
               jTextArea2.append(all[i][j]+" ");
           }
           jTextArea2.append("\n");
        }
        girdi = new x[M];        
        Gateileri = new double[N][M];
        Gategeri = new double[M][N];
        y = new double[M];
        
        for( i = 0; i < N; i++ ){
            for( j = 0; j < M; j++){
                y[j] = 0;
                Gategeri[j][i] = 1;
                Gateileri[i][j] = (double)1/(1+N);
            }
        }
        return all;
    }
    public void prossescikti(){
        int i, j;

        for( i = 0; i < N; i++){
            for( j = 0; j < M; j++){
                y[j] += Gateileri[i][j]*Double.parseDouble(girdiler[index][i]);
            }
        }
    }
    public void maxfind(){
        int i;
        double max = 0;
        
        for( i = 0; i < M; i++){
            if( y[i] > max && (girdi[index].yasak.indexOf(i) == -1)){
                max = y[i];
                girdi[index].sinifi = i;
            }
        }
    }
    public int uygunluk(){
        int i, j;
        double s1 = 0, s2 = 0;
        
        for( i = 0; i < N; i++){
            s1 += Double.parseDouble(girdiler[index][i]);
            s2 += Gategeri[girdi[index].sinifi][i]*Double.parseDouble(girdiler[index][i]);
        }
        if ((double)(s2/s1) > benzerlik )
            return 0;
        else
            return -1;
    }
    public void agirlikdegis(){
        int i, toplam = 0;
        
        for( i = 0; i < N; i++){
            Gategeri[girdi[index].sinifi][i] = Gategeri[girdi[index].sinifi][i]*Double.parseDouble(girdiler[index][i]);
            toplam += Gategeri[girdi[index].sinifi][i];
        }
        
        for( i = 0; i < N; i++){
            Gateileri[i][girdi[index].sinifi] = Gategeri[girdi[index].sinifi][i] /(0.5 +(double)toplam);
        }
    }
    
    double[] sigmacikti;
    double[] sigmaara;
    
    double[][] Gate1;
    double[] esik1;
    double[] esik1degisim;
    double[][] Gate1degisim;
    
    double[][] Gate2;
    double[][] Gate2orjinal;
    double[] esik2;
    double[] esik2degisim;
    double[][] Gate2degisim;
    
    double[][] hata;
    //Girdilen örnek sayımız, xsayısı ise girdideki x'ler
   // public static int girdilen, xsayisi, index = 0;
   // double alfa = 0.5, momentum = 0.8;
    
  //  String[][] girdi;
    //String[][] beklenen;
    process[] arakatman;
    process[] ciktikatman;
    public static int xsayisi,girdilen;
    double momentum = 0.8;
    
    //String[][] all;
    String[] beklenend;
    int arakatlen,ciktikatlen;
    
 
    public void perceptronilklendir() {
        int i, j;
        String[] lines;
      
       
        beklenen = new String[M];
        
        lines = jTextArea1.getText().split("\n");
        girdilen = lines.length;
        j = lines.length;
        System.out.println("satır sayisi :" + j);
        all = new String[lines.length][];

        for( i = 0; i < j; i++){
            all[i] = lines[i].split(" ");
        }  
        //for( i = 0; i < all.length; i++){
           //for( j = 0; j < all[i].length; j++){
            //   jTextArea2.append(all[i][j]+" ");
          // }
        //   jTextArea2.append("\n");
      //  }
        //String[] lines2; 
        xsayisi = all[girdilen-1].length;
        System.out.println("xsayisi :" + xsayisi);
            
       // System.out.println(all[0][1]);
        //System.out.println(all[1][1]);
       
        //for( i = 0; i < lines.length; i++ ){
            //for( j = 0; j < all[i].length; j++ ){
               // System.out.println("hata");
               // girdiler[i][j] = all[i][j];
              //  System.out.println("all :" + girdiler[i][j]);
            //    jTextArea2.append(girdiler[i][j]+" ");
          //  }
            //beklenen[i] = all[i][j];
        //}
        beklenen = new String[lines.length];
        lines = jTextArea2.getText().split("\n");
        for( i = 0; i < lines.length; i++){
             System.out.println(lines[i]);
             beklenen[i] = lines[i];
             System.out.println("beklenen :" + beklenen[i]);
        }  
        
        Gate1 = new double[xsayisi][arakatlen];
        esik1 = new double[arakatlen];
        esik1degisim = new double[arakatlen];
        Gate1degisim = new double[xsayisi][arakatlen];
        
        Gate2 = new double[arakatlen][ciktikatlen];
        Gate2orjinal = new double[arakatlen][ciktikatlen];
        
        esik2 = new double[ciktikatlen];
        esik2degisim = new double[ciktikatlen];        
        Gate2degisim = new double[arakatlen][ciktikatlen];
        //hatanın gidisini gormek için girdi sayımız kadar yapıyoruz.
        hata = new double[girdilen][ciktikatlen];
        
        arakatman = new process[arakatlen];
        ciktikatman = new process[ciktikatlen];
        

        for( i = 0; i < xsayisi; i++){           
            for( j = 0; j < arakatlen; j++){
                esik1[j] = generator.nextDouble();
                esik1degisim[j] = 0;                
                Gate1[i][j] = generator.nextDouble();
                Gate1degisim[i][j] = 0;
            }
        }
        for( i = 0; i < arakatlen; i++){
            for( j = 0; j < ciktikatlen; j++){
                esik2[j] = generator.nextDouble();
                esik2degisim[j] = 0;
                Gate2[i][j] = generator.nextDouble();
                Gate2degisim[i][j] = 0;
            }
        }
      //  System.out.println("hata");
    }
     
    public void perceptrongerihesap(){
        perceptronciktiagirlik();
        perceptronaraagirlik();    
    }
    public void perceptronilerihesap(){
        perceptronarasurec();
        perceptronciktisurec();
    }
    public void perceptronaraagirlik(){
        sigmaara = new double[arakatlen];
        int i, j;
        double toplam;
        
        for ( i = 0; i < arakatlen; i++){
            toplam = 0;
            for ( j = 0; j < ciktikatlen; j++ ){
                toplam += sigmacikti[j]*Gate2orjinal[i][j];
            }            
            sigmaara[i] = arakatman[i].cikti*(1-arakatman[i].cikti)*toplam;
        }
        for( i = 0; i < xsayisi; i++){
            for( j = 0; j < arakatlen; j++){
                Gate1degisim[i][j] = alfa*sigmaara[j]*Double.parseDouble(all[index][i]) + momentum*Gate1degisim[i][j];
                Gate1[i][j] += Gate1degisim[i][j];
            }
        }
        for( i = 0; i < arakatlen; i++){
            esik1degisim[i] = alfa*sigmaara[i] + momentum*esik1degisim[i];
            esik1[i] += esik1degisim[i];         
        }
    }
    public void perceptronciktiagirlik(){
        sigmacikti = new double[ciktikatlen];
        int i, j;
        
        for( i = 0; i < ciktikatlen; i++){
            sigmacikti[i] = ciktikatman[i].cikti*(1-ciktikatman[i].cikti)*hata[index][i];
        }
        for( i = 0; i < arakatlen; i++){
            for( j = 0; j < ciktikatlen; j++){
                Gate2degisim[i][j] = alfa*sigmacikti[j]*arakatman[i].cikti + momentum*Gate2degisim[i][j];
                Gate2[i][j] += Gate2degisim[i][j];
            }
        }
        for( j = 0; j < ciktikatlen; j++){
            esik2degisim[j] = alfa*sigmacikti[j] + momentum*esik2degisim[j];
            esik2[j] += esik2degisim[j];
        }
    }
    public void perceptronciktisurec(){
        int j, i;
        double toplam;
        for( j = 0; j < ciktikatlen; j++){
            toplam = 0;
            for( i = 0; i < arakatlen; i++){
                toplam += (arakatman[i].cikti)*(Gate2[i][j]);
            }
            ciktikatman[j] = new process();
            ciktikatman[j].net = toplam + esik2[j];
            ciktikatman[j].cikti = 1/(1 + Math.pow(Math.E,-1*ciktikatman[j].net));
            hata[index][j] = Double.parseDouble(beklenen[j]) - ciktikatman[j].cikti;
        }        
    }
    public void perceptronarasurec(){
        int j, i;
        double toplam;
        
        for(j = 0; j < arakatlen; j++){
            toplam = 0;
            for( i = 0; i < xsayisi; i++){
                toplam += Double.parseDouble(all[index][i])*Gate1[i][j];
                System.out.println("son:"+toplam);
            }
            arakatman[j] = new process();
            arakatman[j].net = toplam + esik1[j];
            arakatman[j].cikti = 1/(1 + Math.pow(Math.E,-1*arakatman[j].net));
           // index++;
        }
    }
    public void perceptronguncelleme(){
        
        int i, j;
        
        for ( i = 0; i < arakatlen; i++){
            for (j = 0; j < ciktikatlen; j++){
                Gate2orjinal[i][j] = Gate1[i][j];
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.SystemColor.desktop);
        setFont(new java.awt.Font("Liberation Mono", 1, 10)); // NOI18N
        setForeground(new java.awt.Color(245, 6, 6));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("egit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("test");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("girdi");

        jLabel2.setText("çıktı");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Adaline", "Art", "Backpropagation" }));

        jLabel3.setText("ağ tipini seç");

        jButton3.setText("sınıflandır");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("hata oranı");

        jLabel5.setText("benzerlik katsayısı");

        jLabel6.setText("katmansayısı");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addGap(131, 131, 131)
                        .addComponent(jLabel2)
                        .addGap(172, 172, 172)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jButton2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(jButton3))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel4)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel6)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGap(50, 50, 50))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(64, 64, 64)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// TODO add your handling code here:
    int i, j, durum = 0;
    int epoch = 0;    
    index = 0;
    jTextArea2.setText("");
    benzerlik = Double.parseDouble(jTextField2.getText());
    girdiler = ilklendir();
       
    while( index < M ){
        prossescikti();
        maxfind();
        while(true){
            if (uygunluk() == 0){
                agirlikdegis();
                jTextArea2.append(Integer.toString(index+1)+". girdinin sınıfı :  "+Integer.toString(girdi[index].sinifi)+"\n");
                break;
            }
            else{
                girdi[index].yasak.add(girdi[index].sinifi);
            }
        }
        index++;
    }
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    int epoch = 0,i, durum = 0;
    double hataorani;
    String[] lines;
   // int ciktikatlen;
    String Name = (String)jComboBox1.getSelectedItem();
   
    if(Name == "Adaline") {
        adalineilklendir();
        while( true ){
            durum += 1;
            adalineciktihesap();
            if( uygunluk ){
                adalineagirlikdegis();
                durum = 0;
            }
            uygunluk = false;
            if( index == M-1){
                index = 0;
                epoch += 1;
            }
            else{
                index += 1;
            }     
            if ( durum == M)
                break;
        }
        jTextArea2.setText(Integer.toString(epoch)+ "  " + "Epoch 'da tamamlandı.");
     }
     if (Name == "Backpropagation") { 
        arakatlen = (int) Double.parseDouble(jTextField3.getText().trim());
        hataorani = Double.parseDouble(jTextField1.getText().trim());
        ciktikatlen = 1;
        //arakatman=2;
        System.out.println("arakatlen:" + arakatlen);
            
        perceptronilklendir();
        while(true){
            durum += 1;
            perceptronilerihesap();
            if ( Math.abs(hata[index][0]) > hataorani){
                perceptrongerihesap();
                perceptronguncelleme();
                durum = 0;
            }
            if (index == (girdilen-1)){
                epoch += 1;
                index = 0;
            }
            else
                index += 1;
            if ( durum == girdilen)
                break;
            }    
        jTextArea2.setText(Integer.toString(epoch)+ "  " + "Epoch 'da tamamlandı.");     
    }
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
    int i,j;
    String[] lines;
    index = 0;   
    lines = jTextArea1.getText().split("\n");
    System.out.println("lines : " + lines[0]);
    j = lines.length;
       // System.out.println("satır sayisi :" + j);
    all = new String[lines.length][];

    for( i = 0; i < j; i++){
        all[i] = lines[i].split(" ");
    }  
      
    lines = jTextArea1.getText().split("\n");
        girdilen = lines.length;
     j = lines.length;
       // System.out.println("satır sayisi :" + j);
     all = new String[lines.length][];

     for( i = 0; i < j; i++){
        all[i] = lines[i].split(" ");
     }  
    // System.out.println("all: " + all[0]);
    // System.out.println("all :" + all[1]);
     String Name = (String)jComboBox1.getSelectedItem();
     if(Name == "Adaline") {
        // System.out.println("bura");
        jTextArea2.setText(Integer.toString(adalinesonuc()));
     }
     else{
       for( j = 0; j < ciktikatlen; j++){
            jTextArea2.setText(Double.toString(ciktikatman[j].cikti));  
       }
     } 
}//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ypa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ypa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ypa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ypa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ypa().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
