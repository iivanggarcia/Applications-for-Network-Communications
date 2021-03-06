
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author divan
 */
public class DriveJFrame extends javax.swing.JFrame {
    ArrayList arrayC = new ArrayList();
    ArrayList arrayS = new ArrayList();
    DefaultListModel modeloC = new DefaultListModel();
    DefaultListModel modeloS = new DefaultListModel();
    String rutaS = Cliente.getRutaServidor();
    String rutaC = Cliente.getRutaCliente();

    /**
     * Creates new form DriveJFrame
     */
    public DriveJFrame() {
        this.setLocation(500, 200);
        initComponents();
        mostrarLista();
        listCliente.setModel(modeloC);
        listServidor.setModel(modeloS);
        listCliente.addMouseListener(mouseListenerC);
        listServidor.addMouseListener(mouseListenerS);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clienteSubir3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnClienteBorrar = new javax.swing.JButton();
        btnClienteSubir = new javax.swing.JButton();
        btnServidorBorrar = new javax.swing.JButton();
        btnServidorDescargar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCliente = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listServidor = new javax.swing.JList<>();
        btnClienteBorrar1 = new javax.swing.JButton();

        clienteSubir3.setBackground(new java.awt.Color(121, 237, 18));
        clienteSubir3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload.png"))); // NOI18N
        clienteSubir3.setText("Subir");
        clienteSubir3.setAutoscrolls(true);
        clienteSubir3.setBorder(null);
        clienteSubir3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        clienteSubir3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteSubir3ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Drive Simulator");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new ImageIcon(getClass().getResource("/icons/Drive_logo.png")).getImage());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cliente");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 3, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Servidor");

        btnClienteBorrar.setBackground(new java.awt.Color(255, 51, 51));
        btnClienteBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        btnClienteBorrar.setText("Eliminar");
        btnClienteBorrar.setAutoscrolls(true);
        btnClienteBorrar.setBorder(null);
        btnClienteBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClienteBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteBorrarActionPerformed(evt);
            }
        });

        btnClienteSubir.setBackground(new java.awt.Color(121, 237, 18));
        btnClienteSubir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload.png"))); // NOI18N
        btnClienteSubir.setText("Subir");
        btnClienteSubir.setAutoscrolls(true);
        btnClienteSubir.setBorder(null);
        btnClienteSubir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClienteSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteSubirActionPerformed(evt);
            }
        });

        btnServidorBorrar.setBackground(new java.awt.Color(255, 51, 51));
        btnServidorBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        btnServidorBorrar.setText("Eliminar");
        btnServidorBorrar.setAutoscrolls(true);
        btnServidorBorrar.setBorder(null);
        btnServidorBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnServidorBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServidorBorrarActionPerformed(evt);
            }
        });

        btnServidorDescargar.setBackground(new java.awt.Color(121, 237, 18));
        btnServidorDescargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/download.png"))); // NOI18N
        btnServidorDescargar.setText("Descargar");
        btnServidorDescargar.setAutoscrolls(true);
        btnServidorDescargar.setBorder(null);
        btnServidorDescargar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnServidorDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServidorDescargarActionPerformed(evt);
            }
        });

        listCliente.setPreferredSize(new java.awt.Dimension(40, 80));
        jScrollPane1.setViewportView(listCliente);

        jScrollPane2.setViewportView(listServidor);

        btnClienteBorrar1.setBackground(new java.awt.Color(255, 255, 255));
        btnClienteBorrar1.setText("Actualizar");
        btnClienteBorrar1.setAutoscrolls(true);
        btnClienteBorrar1.setBorder(null);
        btnClienteBorrar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClienteBorrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteBorrar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(btnClienteSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(btnClienteBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190)
                .addComponent(btnServidorDescargar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(btnServidorBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClienteBorrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(403, 403, 403))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 89, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClienteSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClienteBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnServidorDescargar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnServidorBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnClienteBorrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clienteSubir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteSubir3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteSubir3ActionPerformed

    private void btnServidorDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServidorDescargarActionPerformed
        Cliente.servidorDescargar();
        modeloS.clear();
        rutaC = Cliente.getRutaCliente();
        mostrarLista();
    }//GEN-LAST:event_btnServidorDescargarActionPerformed

    private void btnServidorBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServidorBorrarActionPerformed
        Cliente.servidorBorrar();
        modeloS.clear();
        modeloC.clear();
        mostrarLista();
    }//GEN-LAST:event_btnServidorBorrarActionPerformed

    private void btnClienteSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteSubirActionPerformed
        Cliente.clienteSubir();
        modeloS.clear();
        rutaS= Cliente.getRutaServidor();
        mostrarLista();
    }//GEN-LAST:event_btnClienteSubirActionPerformed

    private void btnClienteBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteBorrarActionPerformed
        Cliente.clienteBorrar();
        modeloS.clear();
        modeloC.clear();
        mostrarLista();
    }//GEN-LAST:event_btnClienteBorrarActionPerformed

    private void btnClienteBorrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteBorrar1ActionPerformed
        modeloS.clear();
        modeloC.clear();
        rutaS = Cliente.getRutaServidor();
        rutaC = Cliente.getRutaCliente();
        mostrarLista();
    }//GEN-LAST:event_btnClienteBorrar1ActionPerformed
    
    private void mostrarLista(){
        modeloS.removeAllElements();
        arrayS = Cliente.getArrayS(rutaS, rutaS);
        for(int i = 0; i < arrayS.size(); i++){
            modeloS.addElement(arrayS.get(i));
        }
        modeloC.removeAllElements();
        arrayC = Cliente.getArrayC(rutaC, rutaC);
        for(int i = 0; i < arrayC.size(); i++){
            modeloC.addElement(arrayC.get(i));
        }
    }        
    
    MouseListener mouseListenerC = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                String selectedItem = (String) listCliente.getSelectedValue();
                rutaC = rutaC+selectedItem+File.separator;
                mostrarLista();
            }
        }
    };
    
    MouseListener mouseListenerS = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                String selectedItem = (String) listServidor.getSelectedValue();
                rutaS = rutaS+selectedItem+File.separator;
                mostrarLista();
            }
        }
    };
    
    
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
            java.util.logging.Logger.getLogger(DriveJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DriveJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DriveJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DriveJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DriveJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClienteBorrar;
    private javax.swing.JButton btnClienteBorrar1;
    private javax.swing.JButton btnClienteSubir;
    private javax.swing.JButton btnServidorBorrar;
    private javax.swing.JButton btnServidorDescargar;
    private javax.swing.JButton clienteSubir3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listCliente;
    private javax.swing.JList<String> listServidor;
    // End of variables declaration//GEN-END:variables
}
