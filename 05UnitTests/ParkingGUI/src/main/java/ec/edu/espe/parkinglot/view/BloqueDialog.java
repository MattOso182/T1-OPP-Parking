package ec.edu.espe.parkinglot.view;

import data.BuildingBlockDAO;
import ec.edu.espe.parkinglot.model.BuildingBlock;
import javax.swing.*;
import java.awt.*;

public class BloqueDialog extends JDialog {
    private JTextField txtId, txtZoneId, txtFloorCount;
    private BuildingBlockDAO dao;
    private BuildingBlock block;

    public BloqueDialog(Frame parent, BuildingBlock block, BuildingBlockDAO dao) {
        super(parent,true);
        this.block = block;
        this.dao = dao;

        setTitle(block==null?"Nuevo Bloque":"Editar Bloque");
        setSize(350,220);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4,2,5,5));

        txtId = new JTextField();
        txtZoneId = new JTextField();
        txtFloorCount = new JTextField();

        if (block != null) {
            txtId.setText(block.getId()); txtId.setEnabled(false);
            txtZoneId.setText(block.getZoneId());
            txtFloorCount.setText(String.valueOf(block.getFloorCount()));
        }

        add(new JLabel("ID:")); add(txtId);
        add(new JLabel("Zone ID:")); add(txtZoneId);
        add(new JLabel("Floor count:")); add(txtFloorCount);

        JButton btnSave = new JButton("Guardar");
        JButton btnCancel = new JButton("Cancelar");
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
        add(btnSave); add(btnCancel);
    }

    private void save() {
        String id = txtId.getText().trim();
        String zoneId = txtZoneId.getText().trim();
        int floors;
        try { floors = Integer.parseInt(txtFloorCount.getText().trim()); } catch (Exception e) { JOptionPane.showMessageDialog(this,"FloorCount inválido"); return; }
        if (id.isEmpty() || zoneId.isEmpty()) { JOptionPane.showMessageDialog(this,"Complete todos los campos"); return; }

        if (block==null) dao.insert(new BuildingBlock(id, zoneId, floors));
        else dao.update(new BuildingBlock(id, zoneId, floors));
        dispose();
    }
}
