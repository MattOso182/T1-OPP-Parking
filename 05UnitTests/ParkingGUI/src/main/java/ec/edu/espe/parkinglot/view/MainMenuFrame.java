package ec.edu.espe.parkinglot.view;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */


import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    public MainMenuFrame() {
        setTitle("ParkingLot - Menú Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnZonas = new JButton("Administrar Zonas");
        JButton btnBloques = new JButton("Administrar Bloques");
        JButton btnAlquileres = new JButton("Administrar Alquileres");
        JButton btnSalir = new JButton("Salir");

        btnZonas.addActionListener(e -> new ListaZonasFrame().setVisible(true));
        btnBloques.addActionListener(e -> new ListaBloquesFrame().setVisible(true));
        btnAlquileres.addActionListener(e -> new ListaAlquileresFrame().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));

        add(btnZonas);
        add(btnBloques);
        add(btnAlquileres);
        add(btnSalir);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuFrame().setVisible(true));
    }
}
