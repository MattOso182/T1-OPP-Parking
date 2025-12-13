package ec.edu.espe.parkinglot.view;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import data.BuildingBlockDAO;
import ec.edu.espe.parkinglot.model.BuildingBlock;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaBloquesFrame extends JFrame {
    private BuildingBlockDAO dao = new BuildingBlockDAO();
    private DefaultTableModel model;
    private JTable table;

    public ListaBloquesFrame() {
        setTitle("Lista de Bloques");
        setSize(640,360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new Object[]{"ID","ZoneID","FloorCount"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);

        loadData();

        JPanel buttons = new JPanel();
        JButton btnNew = new JButton("Nuevo");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Eliminar");
        buttons.add(btnNew); buttons.add(btnEdit); buttons.add(btnDelete);

        btnNew.addActionListener(e -> { BloqueDialog d = new BloqueDialog(this,null,dao); d.setVisible(true); loadData(); });
        btnEdit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r==-1){ JOptionPane.showMessageDialog(this,"Seleccione una fila"); return; }
            String id = model.getValueAt(r,0).toString();
            BuildingBlock b = dao.findById(id).orElse(null);
            BloqueDialog d = new BloqueDialog(this,b,dao);
            d.setVisible(true);
            loadData();
        });
        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r==-1){ JOptionPane.showMessageDialog(this,"Seleccione una fila"); return; }
            String id = model.getValueAt(r,0).toString();
            int conf = JOptionPane.showConfirmDialog(this,"Eliminar bloque "+id+"?","Confirmar",JOptionPane.YES_NO_OPTION);
            if (conf==JOptionPane.YES_OPTION) { dao.delete(id); loadData(); }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        List<BuildingBlock> list = dao.findAll();
        for (BuildingBlock b : list) model.addRow(new Object[]{b.getId(), b.getZoneId(), b.getFloorCount()});
    }
}
