
package ec.edu.espe.parkinglot.view;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import data.ParkingZoneDAO;
import ec.edu.espe.parkinglot.model.ParkingZone;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaZonasFrame extends JFrame {
    private ParkingZoneDAO dao = new ParkingZoneDAO();
    private DefaultTableModel model;
    private JTable table;

    public ListaZonasFrame() {
        setTitle("Lista de Zonas");
        setSize(640, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultTableModel(new Object[]{"ID", "Tipo", "Capacidad"}, 0) {
            @Override
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);

        loadData();

        JPanel buttons = new JPanel();
        JButton btnNew = new JButton("Nuevo");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Eliminar");
        buttons.add(btnNew); buttons.add(btnEdit); buttons.add(btnDelete);

        btnNew.addActionListener(e -> {
            ZonaDialog d = new ZonaDialog(this, null, dao);
            d.setVisible(true);
            loadData();
        });
        btnEdit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this,"Seleccione una fila"); return; }
            String id = model.getValueAt(r,0).toString();
            ParkingZone p = dao.findById(id).orElse(null);
            ZonaDialog d = new ZonaDialog(this, p, dao);
            d.setVisible(true);
            loadData();
        });
        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this,"Seleccione una fila"); return; }
            String id = model.getValueAt(r,0).toString();
            int confirm = JOptionPane.showConfirmDialog(this,"Eliminar zona "+id+"?","Confirmar",JOptionPane.YES_NO_OPTION);
            if (confirm==JOptionPane.YES_OPTION) {
                dao.delete(id);
                loadData();
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        List<ParkingZone> list = dao.findAll();
        for (ParkingZone p : list) model.addRow(new Object[]{p.getId(), p.getTipo(), p.getCapacidad()});
    }
}
