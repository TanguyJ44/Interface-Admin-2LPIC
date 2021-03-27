package application.pkg2lpic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class BackgroundWorker extends SwingWorker<Void, Void> {

    private JProgressBar pb;
    private JDialog dialog;
    
    int type;

    public BackgroundWorker(int type) {
        this.type = type;
        
        addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equalsIgnoreCase(evt.getPropertyName())) {
                    if (dialog == null) {
                        dialog = new JDialog();
                        dialog.setTitle("Action");
                        dialog.setLayout(new GridBagLayout());
                        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(2, 2, 2, 2);
                        gbc.weightx = 1;
                        gbc.gridy = 0;
                        dialog.add(new JLabel("Merci de patienter"), gbc);
                        pb = new JProgressBar();
                        gbc.gridy = 1;
                        dialog.add(pb, gbc);
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                    pb.setValue(getProgress());
                }
            }

        });
    }

    @Override
    protected void done() {
        if (dialog != null) {
            dialog.dispose();
            Interface.callBack(type);
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        for (int index = 0; index < 100; index++) {
            setProgress(index);
            Thread.sleep(10);
        }
        return null;
    }

}
