import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class TimeClientGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Server Time Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new FlowLayout());

        JLabel dateTimeLabel = new JLabel("Server date and time: ");
        JButton fetchButton = new JButton("Fetch Time");

        JTextArea resultTextArea = new JTextArea(3, 30);
        resultTextArea.setEditable(false);

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket socket = new Socket("localhost", 12345); // Server IP and port number
                    InputStream inputStream = socket.getInputStream();

                    byte[] buffer = new byte[1024];
                    int bytesRead = inputStream.read(buffer);

                    if (bytesRead > 0) {
                        String dateTime = new String(buffer, 0, bytesRead);
                        resultTextArea.setText(dateTime);
                    }

                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(dateTimeLabel);
        frame.add(resultTextArea);
        frame.add(fetchButton);
        frame.setVisible(true);
    }
}
