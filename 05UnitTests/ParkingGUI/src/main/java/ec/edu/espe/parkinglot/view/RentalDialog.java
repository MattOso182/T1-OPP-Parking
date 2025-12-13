package ec.edu.espe.parkinglot.view;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import data.RentalDAO;
import ec.edu.espe.parkinglot.model.Rental;
import javax.swing.*;
import java.awt.*;

public class RentalDialog extends JDialog {
    private JTextField txtId, txtResident, txtZone, txtStart, txtEnd;
    private RentalDAO dao;
    private Rental rental;

    public RentalDialog(Frame parent, Rental rental, RentalDAO dao) {
        super(parent,true);
        this.rental = rental;
        this.dao = dao;

        setTitle(rental==null?"Nuevo Alquiler":"Editar Alquiler");
        setSize(400,260);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6,2,5,5));

        txtId = new JTextField();
        txtResident = new JTextField();
        txtZone = new JTextField();
        txtStart = new JTextField();
        txtEnd = new JTextField();

        if (rental != null) {
            txtId.setText(rental.getId()); txtId.setEnabled(false);
            txtResident.setText(rental.getResidentId());
            txtZone.setText(rental.getZoneId());
            txtStart.setText(rental.getStartDate());
            txtEnd.setText(rental.getEndDate());
        }

        add(new JLabel("ID:")); add(txtId);
        add(new JLabel("Resident ID:")); add(txtResident);
        add(new JLabel("Zone ID:")); add(txtZone);
        add(new JLabel("Start Date:")); add(txtStart);
        add(new JLabel("End Date:")); add(txtEnd);

        JButton btnSave = new JButton("Guardar");
        JButton btnCancel = new JButton("Cancelar");
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
        add(btnSave); add(btnCancel);
    }

    private void save() {
        String id = txtId.getText().trim();
        String resident = txtResident.getText().trim();
        String zone = txtZone.getText().trim();
        String start = txtStart.getText().trim();
        String end = txtEnd.getText().trim();

        if (id.isEmpty() || resident.isEmpty() || zone.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Complete los campos obligatorios");
            return;
        }

        if (rental==null) dao.insert(new Rental(id,resident,zone,start,end));
        else dao.update(new Rental(id,resident,zone,start,end));
        dispose();
    }
}
