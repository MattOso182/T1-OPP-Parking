package ec.edu.espe.parkinglotgui.view;

import ec.edu.espe.parkinglotgui.model.ResidentDAO;
import org.bson.Document;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Arelis Samantha Bonilla Cruz, Student, @ESPE
 */
public class FrmResidentList extends javax.swing.JFrame {
    private final ResidentDAO residentDAO = new ResidentDAO();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmResidentList.class.getName());

    /**
     * Creates new form FrmResidentList
     */
    public FrmResidentList() {
        initComponents();
        loadResidentData();
        
        addResident.addActionListener(evt -> addResidentActionPerformed(evt));
        editResident.addActionListener(evt -> editResidentActionPerformed(evt));
        deleteResident.addActionListener(evt -> deleteResidentActionPerformed(evt));

    }
    
private void addResidentActionPerformed(java.awt.event.ActionEvent evt) {                                            
    javax.swing.JTextField nameField = new javax.swing.JTextField();
    javax.swing.JTextField aptField = new javax.swing.JTextField();
    javax.swing.JTextField emailField = new javax.swing.JTextField();
    javax.swing.JTextField phoneField = new javax.swing.JTextField();
    javax.swing.JTextField typeField = new javax.swing.JTextField();
    javax.swing.JTextField parkingField = new javax.swing.JTextField();

    javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 2, 5, 5));

    panel.add(new javax.swing.JLabel("Nombre:"));
    panel.add(nameField);

    panel.add(new javax.swing.JLabel("N° Apartamento:"));
    panel.add(aptField);

    panel.add(new javax.swing.JLabel("Email:"));
    panel.add(emailField);

    panel.add(new javax.swing.JLabel("Celular:"));
    panel.add(phoneField);

    panel.add(new javax.swing.JLabel("Tipo de Usuario:"));
    panel.add(typeField);

    panel.add(new javax.swing.JLabel("Parqueo Asignado:"));
    panel.add(parkingField);

    int result = javax.swing.JOptionPane.showConfirmDialog(
            this,
            panel,
            "Agregar Residente",
            javax.swing.JOptionPane.OK_CANCEL_OPTION,
            javax.swing.JOptionPane.PLAIN_MESSAGE
    );

    if (result != javax.swing.JOptionPane.OK_OPTION) {
        return;
    }

    String name = nameField.getText().trim();
    String apt = aptField.getText().trim();
    String email = emailField.getText().trim();
    String phone = phoneField.getText().trim();
    String type = typeField.getText().trim();
    String parking = parkingField.getText().trim();

    if (name.isEmpty() || apt.isEmpty() || email.isEmpty() || phone.isEmpty() || type.isEmpty() || parking.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Complete todos los campos.");
        return;
    }

    long count = residentDAO.count();

    String newID = String.format("RES-%03d", count + 1);

    org.bson.Document doc = new org.bson.Document()
            .append("residentID", newID)
            .append("name", name)
            .append("apartmentNumber", apt)
            .append("email", email)
            .append("phone", phone)
            .append("userType", type)
            .append("assignedParkingSpace", parking);

    residentDAO.insert(doc);

    loadResidentData();
    javax.swing.JOptionPane.showMessageDialog(this, "Residente agregado con ID: " + newID);
}      
private void deleteResidentActionPerformed(java.awt.event.ActionEvent evt) {                                              
    int row = listResident.getSelectedRow();
    if (row == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un residente");
        return;
    }

    String id = listResident.getValueAt(row, 0).toString();

    int confirm = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar al residente con ID: " + id + "?",
            "Confirmar Eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

    residentDAO.deleteById(id);

    loadResidentData();
    javax.swing.JOptionPane.showMessageDialog(this, "Residente eliminado");
}
private void editResidentActionPerformed(java.awt.event.ActionEvent evt) {                                             
    int row = listResident.getSelectedRow();
    if (row == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un residente para editar.");
        return;
    }

    String originalID = listResident.getValueAt(row, 0).toString();
    String originalName = listResident.getValueAt(row, 1).toString();
    String originalApt = listResident.getValueAt(row, 2).toString();
    String originalEmail = listResident.getValueAt(row, 3).toString();
    String originalPhone = listResident.getValueAt(row, 4).toString();
    String originalType = listResident.getValueAt(row, 5).toString();
    String originalParking = listResident.getValueAt(row, 6).toString();

    javax.swing.JTextField idField = new javax.swing.JTextField(originalID);
    idField.setEnabled(false);

    javax.swing.JTextField nameField = new javax.swing.JTextField(originalName);
    javax.swing.JTextField aptField = new javax.swing.JTextField(originalApt);
    javax.swing.JTextField emailField = new javax.swing.JTextField(originalEmail);
    javax.swing.JTextField phoneField = new javax.swing.JTextField(originalPhone);
    javax.swing.JTextField typeField = new javax.swing.JTextField(originalType);
    javax.swing.JTextField parkingField = new javax.swing.JTextField(originalParking);

    javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 2, 5, 5));

    panel.add(new javax.swing.JLabel("ID (No editable):"));
    panel.add(idField);

    panel.add(new javax.swing.JLabel("Nombre:"));
    panel.add(nameField);

    panel.add(new javax.swing.JLabel("N° Apartamento:"));
    panel.add(aptField);

    panel.add(new javax.swing.JLabel("Email:"));
    panel.add(emailField);

    panel.add(new javax.swing.JLabel("Celular:"));
    panel.add(phoneField);

    panel.add(new javax.swing.JLabel("Tipo de Usuario:"));
    panel.add(typeField);

    panel.add(new javax.swing.JLabel("Parqueo Asignado:"));
    panel.add(parkingField);

    int result = javax.swing.JOptionPane.showConfirmDialog(
            this,
            panel,
            "Editar Residente",
            javax.swing.JOptionPane.OK_CANCEL_OPTION,
            javax.swing.JOptionPane.PLAIN_MESSAGE
    );

    if (result != javax.swing.JOptionPane.OK_OPTION) {
        return;
    }

    String newName = nameField.getText().trim();
    String newApt = aptField.getText().trim();
    String newEmail = emailField.getText().trim();
    String newPhone = phoneField.getText().trim();
    String newType = typeField.getText().trim();
    String newParking = parkingField.getText().trim();

    boolean changed =
            !newName.equals(originalName) ||
            !newApt.equals(originalApt) ||
            !newEmail.equals(originalEmail) ||
            !newPhone.equals(originalPhone) ||
            !newType.equals(originalType) ||
            !newParking.equals(originalParking);

    if (!changed) {
        javax.swing.JOptionPane.showMessageDialog(this, "No se realizaron cambios.");
        return;
    }

    org.bson.Document update = new org.bson.Document()
            .append("name", newName)
            .append("apartmentNumber", newApt)
            .append("email", newEmail)
            .append("phone", newPhone)
            .append("userType", newType)
            .append("assignedParkingSpace", newParking);

    residentDAO.update(originalID, update);

    loadResidentData();
    javax.swing.JOptionPane.showMessageDialog(this, "Residente actualizado correctamente.");
}


private void loadResidentData() {
    DefaultTableModel model = (DefaultTableModel) listResident.getModel();
    model.setRowCount(0);

    for (Document doc : residentDAO.findAll()) {
        model.addRow(new Object[]{
            doc.getString("residentID"),
            doc.getString("name"),
            doc.getString("apartmentNumber"),
            doc.getString("email"),
            doc.getString("phone"),
            doc.getString("userType"),
            doc.getString("assignedParkingSpace")
        });
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        residentList = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listResident = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        addResident = new javax.swing.JButton();
        editResident = new javax.swing.JButton();
        deleteResident = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        residentList.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        residentList.setText("Lista de Residentes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(residentList)
                .addGap(184, 184, 184))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(residentList)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        listResident.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "N° de Apartamento", "Email", "Celular", "Tipo de Usuario", "Espacio de Parqueo Asignado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(listResident);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );

        addResident.setText("Agregar");

        editResident.setText("Editar");

        deleteResident.setText("Eliminar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(addResident, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(editResident, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120)
                .addComponent(deleteResident, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteResident)
                    .addComponent(editResident)
                    .addComponent(addResident))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmResidentList().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addResident;
    private javax.swing.JButton deleteResident;
    private javax.swing.JButton editResident;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable listResident;
    private javax.swing.JLabel residentList;
    // End of variables declaration//GEN-END:variables
}
