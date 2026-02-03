package ec.edu.espe.parkinglotgui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class FrameBlocker {

    private FrameBlocker() {
        throw new IllegalStateException("Utility class");
    }

    public static void blockFrameControls(JFrame frame, boolean allowCloseFromCode) {
        if (frame == null) {
            return;
        }

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showCloseWarning(frame);
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                if ((frame.getExtendedState() & Frame.MAXIMIZED_BOTH) != 0) {
                    frame.setExtendedState(Frame.NORMAL);
                }
            }
        });

        blockKeyboardShortcuts(frame);
        frame.getRootPane().putClientProperty("ALLOW_CLOSE", allowCloseFromCode);
    }

    public static void blockInternalFrameControls(JInternalFrame internalFrame) {
        if (internalFrame == null) {
            return;
        }

        ((BasicInternalFrameUI) internalFrame.getUI()).getNorthPane().remove(0);
        ((BasicInternalFrameUI) internalFrame.getUI()).getNorthPane().remove(0);
        ((BasicInternalFrameUI) internalFrame.getUI()).getNorthPane().remove(0);

        internalFrame.setResizable(false);
        internalFrame.setClosable(false);
        internalFrame.setMaximizable(false);
        internalFrame.setIconifiable(false);
    }

    public static void blockAllExistingFrames() {
        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof JFrame && frame.isVisible()) {
                blockFrameControls((JFrame) frame, false);
            }
        }
    }

    public static void enableGlobalFrameMonitoring() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof WindowEvent) {
                    WindowEvent windowEvent = (WindowEvent) event;

                    if (windowEvent.getID() == WindowEvent.WINDOW_OPENED) {
                        Window window = windowEvent.getWindow();

                        if (window instanceof JFrame) {
                            blockFrameControls((JFrame) window, false);
                        } else if (window instanceof JDialog) {
                            blockDialogControls((JDialog) window);
                        }
                    }
                }
            }
        }, AWTEvent.WINDOW_EVENT_MASK);
    }

    public static void blockDialogControls(JDialog dialog) {
        if (dialog == null) {
            return;
        }

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setResizable(false);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showCloseWarning(dialog);
            }
        });
    }

    public static boolean safeCloseFrame(JFrame frame) {
        if (frame == null) {
            return false;
        }

        Boolean allowClose = (Boolean) frame.getRootPane().getClientProperty("ALLOW_CLOSE");
        if (allowClose != null && allowClose) {
            frame.dispose();
            return true;
        }
        return false;
    }

    private static void blockKeyboardShortcuts(JFrame frame) {
        String[][] shortcuts = {
            {"ALT F4", "Close Window"},
            {"ctrl F4", "Close Tab/Window"},
            {"ctrl W", "Close Window"},
            {"F11", "Fullscreen"},
            {"ALT ENTER", "Fullscreen"},
            {"ctrl shift F12", "Maximize"}
        };

        for (String[] shortcut : shortcuts) {
            KeyStroke keyStroke = KeyStroke.getKeyStroke(shortcut[0]);
            String actionName = "BLOCK_" + shortcut[0].replace(" ", "_");

            frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(keyStroke, actionName);

            frame.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                    .put(keyStroke, actionName);

            frame.getRootPane().getActionMap().put(actionName, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Toolkit.getDefaultToolkit().beep();
                    showShortcutWarning(frame, shortcut[1]);
                }
            });
        }
    }

    private static void showCloseWarning(Window parent) {
        JOptionPane.showMessageDialog(parent,
                "<html><div style='width: 250px; text-align: center;'>"
                + "<h3 style='color: #d32f2f;'>⚠ Acceso Restringido</h3>"
                + "<p>Los controles de ventana están deshabilitados.</p>"
                + "<p>Use las opciones del menú de la aplicación.</p>"
                + "</div></html>",
                "Controles Deshabilitados",
                JOptionPane.WARNING_MESSAGE);
    }

    private static void showShortcutWarning(Window parent, String shortcutName) {
        JOptionPane.showMessageDialog(parent,
                "<html><div style='width: 300px;'>"
                + "<b>Atajo Bloqueado:</b> " + shortcutName + "<br><br>"
                + "Este atajo ha sido deshabilitado por razones de seguridad.<br>"
                + "Use los controles proporcionados por la aplicación."
                + "</div></html>",
                "Atajo Deshabilitado",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
