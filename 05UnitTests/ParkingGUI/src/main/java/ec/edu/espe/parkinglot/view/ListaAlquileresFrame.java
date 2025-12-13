package ec.edu.espe.parkinglot.view;

import data.RentalDAO;
import ec.edu.espe.parkinglot.model.Rental;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaAlquileresFrame extends JFrame {
    private RentalDAO dao = new RentalDAO();
    private DefaultTableModel model;
    private JTable table;

    public ListaAlquileresFrame() {
        setTitle("Lista de Alquileres");
        setSize(720,360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new Object[]{"ID","ResidentID","ZoneID","StartDate","EndDate"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);

        loadData();

        JPanel buttons = new JPanel();
        JButton btnNew = new JButton("Nuevo");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Eliminar");
        buttons.add(btnNew); buttons.add(btnEdit); buttons.add(btnDelete);

        btnNew.addActionListener(e -> { RentalDialog d = new RentalDialog(this,null,dao); d.setVisible(true); loadData(); });
        btnEdit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r==-1){ JOptionPane.showMessageDialog(this,"Seleccione una fila"); return; }
            String id = model.getValueAt(r,0).toString();
            Rental rent = dao.findById(id).orElse(null);
            RentalDialog d = new RentalDialog(this,rent,dao);
            d.setVisible(true); loadData();
        });
        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r==-1){ JOptionPane.showMessageDialog(this,"Seleccione una fila"); return; }
            String id = model.getValueAt(r,0).toString();
            int conf = JOptionPane.showConfirmDialog(this,"Eliminar alquiler "+id+"?","Confirmar",JOptionPane.YES_NO_OPTION);
            if (conf==JOptionPane.YES_OPTION) { dao.delete(id); loadData(); }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Rental> list = dao.findAll();
        for (Rental r : list) model.addRow(new Object[]{r.getId(), r.getResidentId(), r.getZoneId(), r.getStartDate(), r.getEndDate()});
    }
}
