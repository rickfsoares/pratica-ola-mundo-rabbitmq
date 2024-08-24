package br.edu.ifpb.hello.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Classe responsavel por enviar itens à fila
 */
public class Produtor {
    public static void main(String[] args) throws Exception {
        //Criacao de uma factory de conexao, responsavel por criar as conexoes
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //localiza gestor da fila (Queue Manager)
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        //credenciais para o rabbitmq executando no docker
        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("Admin123XX_");

        String NOME_FILA = "filaOla";
        try(
                //criar conecção
                Connection conn = connectionFactory.newConnection();
                //criar um canal na conecção
                Channel channel = conn.createChannel()
           ) {
                //Esse corpo especifica o envio da mensagem para a fila

                //Declaracao da fila. Se nao existir ainda no queue manager, serah criada. Se jah existir, e foi criada com
                // os mesmos parametros, pega a referencia da fila. Se foi criada com parametros diferentes, lanca excecao
                channel.queueDeclare(NOME_FILA, false, false, false, null);
                String msg = "Olá mundo";
                //publica msg na fila
                channel.basicPublish("", NOME_FILA, null, msg.getBytes());
                System.out.println("Enviei mesagem: " + msg);
           }
    }
}

