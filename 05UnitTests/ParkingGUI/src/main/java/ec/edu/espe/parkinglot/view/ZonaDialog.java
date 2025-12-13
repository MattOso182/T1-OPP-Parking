package ec.edu.espe.parkinglot.view;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import data.ParkingZoneDAO;
import ec.edu.espe.parkinglot.model.ParkingZone;
import javax.swing.*;
import java.awt.*;

public class ZonaDialog extends JDialog {
    private JTextField txtId, txtTipo, txtCap;
    private ParkingZoneDAO dao;
    private ParkingZone zone;

    public ZonaDialog(Frame owner, ParkingZone zone, ParkingZoneDAO dao) {
        super(owner, true);
        this.dao = dao;
        this.zone = zone;
        setTitle(zone==null?"Nueva Zona":"Editar Zona");
        setSize(350,220);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(4,2,5,5));

        txtId = new JTextField();
        txtTipo = new JTextField();
        txtCap = new JTextField();

        if (zone != null) {
            txtId.setText(zone.getId()); txtId.setEnabled(false);
            txtTipo.setText(zone.getTipo());
            txtCap.setText(String.valueOf(zone.getCapacidad()));
        }

        add(new JLabel("ID:")); add(txtId);
        add(new JLabel("Tipo:")); add(txtTipo);
        add(new JLabel("Capacidad:")); add(txtCap);

        JButton btnSave = new JButton("Guardar");
        JButton btnCancel = new JButton("Cancelar");
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
        add(btnSave); add(btnCancel);
    }

    private void save() {
        String id = txtId.getText().trim();
        String tipo = txtTipo.getText().trim();
        int cap;
        try { cap = Integer.parseInt(txtCap.getText().trim()); } catch (Exception e) { JOptionPane.showMessageDialog(this,"Capacidad inválida"); return; }
        if (id.isEmpty()||tipo.isEmpty()) { JOptionPane.showMessageDialog(this,"Complete todos los campos"); return; }

        if (zone == null) dao.insert(new ParkingZone(id,tipo,cap));
        else dao.update(new ParkingZone(id,tipo,cap));
        dispose();
    }
}
