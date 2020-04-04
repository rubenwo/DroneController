package com.example.dronecontroller.Services.Conn;

import com.example.dronecontroller.Constants;
import com.example.dronecontroller.Listeners.TcpErrorListener;
import com.example.dronecontroller.Listeners.TcpMessageListener;
import com.example.dronecontroller.Services.Conn.Messages.InfoMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class TcpManagerService {
    /**
     *
     */
    private volatile static TcpManagerService instance;
    /**
     *
     */
    private TcpConnectionService connectionService;

    /**
     *
     */
    private TcpManagerService() {
        this.connectionService = new TcpConnectionService();
        CompletableFuture.runAsync(this.connectionService.createConnection());
    }


    /**
     * @return
     */
    public static TcpManagerService getInstance() {
        if (instance == null)
            instance = new TcpManagerService();
        return instance;
    }

    /**
     * @param message
     */
    public CompletableFuture submitMessage(IMessage message) {
        return CompletableFuture.runAsync(this.connectionService.writeMessageToServer(message));
    }

    /**
     * @param tcpErrorListener
     */
    public void subscribeToErrorEvents(TcpErrorListener tcpErrorListener) {
        this.connectionService.addErrorListener(tcpErrorListener);
    }

    /**
     * @param tcpErrorListener
     */
    public void unsubscribeFromErrorEvents(TcpErrorListener tcpErrorListener) {
        this.connectionService.removeErrorListener(tcpErrorListener);
    }

    /**
     * @param TcpMessageListener
     */
    public void subscribeToMessageEvents(TcpMessageListener TcpMessageListener) {
        this.connectionService.addMessageReceiverListener(TcpMessageListener);
    }

    /**
     * @param TcpMessageListener
     */
    public void unsubscribeFromMessageEvents(TcpMessageListener TcpMessageListener) {
        this.connectionService.removeMessageReceiverListener(TcpMessageListener);
    }

    private class TcpConnectionService {
        /**
         * TcpMessageListener variable has a callback to send the decoded message back to the app.
         */
        private ArrayList<TcpMessageListener> messageReceiverListeners;
        /**
         * TcpErrorListener variable has a callback that get's called when something breaks in this class.
         */
        private ArrayList<TcpErrorListener> errorListeners;
        /**
         * TcpSocket
         */
        private Socket socket;
        /**
         * Sends data to the server
         */
        private DataOutputStream toServer;
        /**
         * Receives data from the server.
         */
        private DataInputStream fromServer;
        /**
         * Thread used for listening on the background for incoming messages
         */
        private Thread communicationListeningThread;
        /**
         * boolean variable indicating whether this class is running.
         */
        private boolean running;

        /**
         * TcpConnectionService constructor
         * Creates the connection when it's called
         */
        private TcpConnectionService() {
            messageReceiverListeners = new ArrayList<>();
            errorListeners = new ArrayList<>();
        }


        /**
         * Initializes the connection with the server and starts listening to messages from the server.
         *
         * @throws IOException thrown when the socket disconnects during the DataOutputStream flushing.
         */
        private Runnable createConnection() {
            return () -> {
                try {
                    socket = new Socket(Constants.HOST, Constants.PORT);
                    toServer = new DataOutputStream(socket.getOutputStream());
                    toServer.flush();
                    fromServer = new DataInputStream(socket.getInputStream());
                    setRunning(true);
                    startMessageReceiver();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }

        /**
         * Method for sending messages to the server.
         *
         * @param message The message that will be send.
         */
        private Runnable writeMessageToServer(IMessage message) {
            return () -> {
                try {
                    toServer.write(message.toBytes());
                    toServer.flush();
                } catch (IOException e) {
                    this.errorListeners.forEach(tcpErrorListener -> tcpErrorListener.onTcpError(new Error(e.getMessage())));
                }
            };
        }

        /**
         * @param errorListener
         */
        private void addErrorListener(TcpErrorListener errorListener) {
            this.errorListeners.add(errorListener);
        }

        /**
         * @param errorListener
         */
        private void removeErrorListener(TcpErrorListener errorListener) {
            this.errorListeners.remove(errorListener);
        }

        /**
         * Starts the communicationListeningThread for listening to the server for messages.
         */
        private void startMessageReceiver() {
            communicationListeningThread = new Thread(receiveMessageFromServer());
            communicationListeningThread.start();
        }

        /**
         * @return runnable listening object
         */
        private Runnable receiveMessageFromServer() {
            return () -> {
                while (running) {
                    IMessage message = getMessage();
                    this.messageReceiverListeners.forEach(tcpMessageListener -> tcpMessageListener.onMessageReceived(message));
                }
            };
        }

        /**
         * Cuz java's a bitch
         *
         * @param data A byte array of 4 bytes containing the length of the prefix.
         * @return an integer value converted from the byte array
         */
        private int byteArrayToInt(byte[] data) {
            ByteBuffer wrapped = ByteBuffer.wrap(data);
            return wrapped.getInt();
        }

        /**
         * @return
         */
        private IMessage getMessage() {
            byte[] data = new byte[36];
            int bytesRead = 0;

            while (bytesRead < data.length) {
                try {
                    bytesRead += fromServer.read(data, bytesRead, data.length - bytesRead);
                    if (bytesRead < 0)
                        return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return InfoMessage.fromBytes(data);
        }

        /**
         * safely disconnects the TcpConnectionService from the server
         */
        private void disconnect() {
            setRunning(false);
        }

        /**
         * Setter
         *
         * @param messageReceiverListener sets the TcpMessageListener
         */
        private void addMessageReceiverListener(TcpMessageListener messageReceiverListener) {
            this.messageReceiverListeners.add(messageReceiverListener);
        }

        /**
         * @param messageReceiverListener
         */
        private void removeMessageReceiverListener(TcpMessageListener messageReceiverListener) {
            this.messageReceiverListeners.remove(messageReceiverListener);
        }

        /**
         * setter
         *
         * @param running sets the running variable
         */
        private void setRunning(boolean running) {
            this.running = running;
        }

        /**
         * getter
         *
         * @return CommunicationListeningThread
         */
        private Thread getCommunicationListeningThread() {
            return communicationListeningThread;
        }
    }
}
