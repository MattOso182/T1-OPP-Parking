package ec.edu.espe.parkinglotgui.view;

/**
 *
 * @author Arelis Samantha Bonilla Cruz, Student, @ESPE
 */
public class FrmVehicleList extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmVehicleList.class.getName());

    /**
     * Creates new form FrmVehicleList
     */
    public FrmVehicleList() {
        initComponents();
        loadVehicleData();
        
        addVehicle.addActionListener(evt -> addVehicleActionPerformed());
        deleteVehicle.addActionListener(evt -> deleteVehicleActionPerformed());
        editVehicle.addActionListener(evt -> editVehicleActionPerformed());
    }
    
    private void addVehicleActionPerformed() {
        com.mongodb.client.MongoDatabase database = ec.edu.espe.parkinglotgui.utils.MongoConnectionResidents.getDatabase();
        com.mongodb.client.MongoCollection<org.bson.Document> collection = database.getCollection("Vehicles");

        java.util.List<String> residentIds = new java.util.ArrayList<>();
        for (org.bson.Document doc : collection.find()) {
            org.bson.Document r = doc.get("resident", org.bson.Document.class);
            residentIds.add(r.getString("residentId"));
        }

        String selectedResidentId = (String) javax.swing.JOptionPane.showInputDialog(
                this,
                "Selecciona un residente",
                "Agregar Vehículo",
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                residentIds.toArray(),
                null
        );

        if (selectedResidentId == null) return;

        javax.swing.JTextField plateField = new javax.swing.JTextField();
        javax.swing.JTextField colorField = new javax.swing.JTextField();
        javax.swing.JTextField brandField = new javax.swing.JTextField();
        javax.swing.JTextField modelField = new javax.swing.JTextField();
        javax.swing.JTextField typeField = new javax.swing.JTextField();

        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
        panel.add(new javax.swing.JLabel("Placa"));
        panel.add(plateField);
        panel.add(new javax.swing.JLabel("Color"));
        panel.add(colorField);
        panel.add(new javax.swing.JLabel("Marca"));
        panel.add(brandField);
        panel.add(new javax.swing.JLabel("Modelo"));
        panel.add(modelField);
        panel.add(new javax.swing.JLabel("Tipo"));
        panel.add(typeField);

        int result = javax.swing.JOptionPane.showConfirmDialog(this, panel, "Datos del Vehículo",
                javax.swing.JOptionPane.OK_CANCEL_OPTION);

        if (result != javax.swing.JOptionPane.OK_OPTION) return;

        org.bson.Document newVehicle = new org.bson.Document("plate", plateField.getText())
                .append("color", colorField.getText())
                .append("brand", brandField.getText())
                .append("model", modelField.getText())
                .append("type", typeField.getText());

        collection.updateOne(
                new org.bson.Document("resident.residentId", selectedResidentId),
                new org.bson.Document("$push", new org.bson.Document("resident.vehicles", newVehicle))
        );

        javax.swing.JOptionPane.showMessageDialog(this, "Vehículo agregado exitosamente");
        loadVehicleData();
    }
    private void deleteVehicleActionPerformed() {
        int row = vehicleList.getSelectedRow();
        if (row == -1) return;

        String residentId = vehicleList.getValueAt(row, 0).toString();
        String plate = vehicleList.getValueAt(row, 6).toString();

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar este vehículo?",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

        com.mongodb.client.MongoDatabase database = ec.edu.espe.parkinglotgui.utils.MongoConnectionResidents.getDatabase();
        com.mongodb.client.MongoCollection<org.bson.Document> collection = database.getCollection("Vehicles");

        collection.updateOne(
                new org.bson.Document("resident.residentId", residentId),
                new org.bson.Document("$pull", new org.bson.Document("resident.vehicles",
                        new org.bson.Document("plate", plate)))
        );

        javax.swing.JOptionPane.showMessageDialog(this, "Vehículo eliminado exitosamente");
        loadVehicleData();
    }
    private void editVehicleActionPerformed() {
        com.mongodb.client.MongoDatabase database = ec.edu.espe.parkinglotgui.utils.MongoConnectionResidents.getDatabase();
        com.mongodb.client.MongoCollection<org.bson.Document> collection = database.getCollection("Vehicles");

        java.util.List<org.bson.Document> residents = new java.util.ArrayList<>();
        java.util.List<String> residentIds = new java.util.ArrayList<>();

        for (org.bson.Document doc : collection.find()) {
            org.bson.Document r = doc.get("resident", org.bson.Document.class);
            residents.add(r);
            residentIds.add(r.getString("residentId"));
        }

        String selectedResidentId = (String) javax.swing.JOptionPane.showInputDialog(
                this,
                "Selecciona el residente",
                "Editar Vehículo",
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                residentIds.toArray(),
                null
        );

        if (selectedResidentId == null) return;

        org.bson.Document resident = null;
        for (org.bson.Document r : residents) {
            if (r.getString("residentId").equals(selectedResidentId)) {
                resident = r;
                break;
            }
        }

        java.util.List<org.bson.Document> vehicles = resident.getList("vehicles", org.bson.Document.class);
        java.util.List<String> plates = new java.util.ArrayList<>();
        for (org.bson.Document v : vehicles) plates.add(v.getString("plate"));

        String selectedPlate = (String) javax.swing.JOptionPane.showInputDialog(
                this,
                "Selecciona el vehículo a editar",
                "Editar Vehículo",
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                plates.toArray(),
                null
        );

        if (selectedPlate == null) return;

        org.bson.Document vehicleToEdit = null;
        for (org.bson.Document v : vehicles) {
            if (v.getString("plate").equals(selectedPlate)) {
                vehicleToEdit = v;
                break;
            }
        }

        javax.swing.JTextField plateField = new javax.swing.JTextField(vehicleToEdit.getString("plate"));
        javax.swing.JTextField colorField = new javax.swing.JTextField(vehicleToEdit.getString("color"));
        javax.swing.JTextField brandField = new javax.swing.JTextField(vehicleToEdit.getString("brand"));
        javax.swing.JTextField modelField = new javax.swing.JTextField(vehicleToEdit.getString("model"));
        javax.swing.JTextField typeField = new javax.swing.JTextField(vehicleToEdit.getString("type"));

        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
        panel.add(new javax.swing.JLabel("Placa"));
        panel.add(plateField);
        panel.add(new javax.swing.JLabel("Color"));
        panel.add(colorField);
        panel.add(new javax.swing.JLabel("Marca"));
        panel.add(brandField);
        panel.add(new javax.swing.JLabel("Modelo"));
        panel.add(modelField);
        panel.add(new javax.swing.JLabel("Tipo"));
        panel.add(typeField);

        int result = javax.swing.JOptionPane.showConfirmDialog(
                this,
                panel,
                "Editar Vehículo",
                javax.swing.JOptionPane.OK_CANCEL_OPTION
        );

        if (result != javax.swing.JOptionPane.OK_OPTION) return;

        org.bson.Document newVehicle = new org.bson.Document("plate", plateField.getText())
                .append("color", colorField.getText())
                .append("brand", brandField.getText())
                .append("model", modelField.getText())
                .append("type", typeField.getText());

        collection.updateOne(
                new org.bson.Document("resident.residentId", selectedResidentId),
                new org.bson.Document("$pull", new org.bson.Document("resident.vehicles",
                        new org.bson.Document("plate", selectedPlate)))
        );

        collection.updateOne(
                new org.bson.Document("resident.residentId", selectedResidentId),
                new org.bson.Document("$push", new org.bson.Document("resident.vehicles", newVehicle))
        );

        javax.swing.JOptionPane.showMessageDialog(this, "Vehículo modificado exitosamente");
        loadVehicleData();
    }
    
    private void loadVehicleData() {
        com.mongodb.client.MongoDatabase database = ec.edu.espe.parkinglotgui.utils.MongoConnectionResidents.getDatabase();
        com.mongodb.client.MongoCollection<org.bson.Document> collection = database.getCollection("Vehicles");

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) vehicleList.getModel();
        model.setRowCount(0);

        for (org.bson.Document doc : collection.find()) {
            org.bson.Document resident = doc.get("resident", org.bson.Document.class);

            String residentId = resident.getString("residentId");
            String name = resident.getString("name");
            String apartment = resident.getString("apartmentNumber");
            String email = resident.getString("email");
            String phone = resident.getString("phone");
            String userType = resident.getString("userType");

            java.util.List<org.bson.Document> vehicles = resident.getList("vehicles", org.bson.Document.class);

            for (org.bson.Document vehicle : vehicles) {
                String plate = vehicle.getString("plate");

                String details = 
                    vehicle.getString("color") + ", " +
                    vehicle.getString("brand") + ", " +
                    vehicle.getString("model") + ", " +
                    vehicle.getString("type");


                model.addRow(new Object[]{
                    residentId,
                    name,
                    apartment,
                    email,
                    phone,
                    userType,
                    plate,
                    details
                });
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        tittle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        addVehicle = new javax.swing.JButton();
        deleteVehicle = new javax.swing.JButton();
        editVehicle = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        vehicleList = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itemReturnMenu = new javax.swing.JMenuItem();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tittle.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        tittle.setText("Lista de Vehículos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(364, 364, 364)
                .addComponent(tittle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(tittle)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        addVehicle.setText("Agregar");

        deleteVehicle.setText("Eliminar");
        deleteVehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteVehicleActionPerformed(evt);
            }
        });

        editVehicle.setText("Editar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(addVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171)
                .addComponent(deleteVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(editVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addVehicle)
                    .addComponent(deleteVehicle)
                    .addComponent(editVehicle))
                .addGap(22, 22, 22))
        );

        vehicleList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Resident ID", "Nombre", "N° de Apartamento", "Email", "Celular", "Tipo de Usuario", "Placa", "Detalles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(vehicleList);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Sistema");

        itemReturnMenu.setText("Regresar al menu");
        itemReturnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemReturnMenuActionPerformed(evt);
            }
        });
        jMenu1.add(itemReturnMenu);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteVehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteVehicleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteVehicleActionPerformed

    private void itemReturnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemReturnMenuActionPerformed
        FrmSecurityGuardMenu frmSecurityGuardMenu = new FrmSecurityGuardMenu();
        frmSecurityGuardMenu.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_itemReturnMenuActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FrmVehicleList().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addVehicle;
    private javax.swing.JButton deleteVehicle;
    private javax.swing.JButton editVehicle;
    private javax.swing.JMenuItem itemReturnMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel tittle;
    private javax.swing.JTable vehicleList;
    // End of variables declaration//GEN-END:variables
}
