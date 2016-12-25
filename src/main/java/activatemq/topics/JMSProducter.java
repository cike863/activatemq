package activatemq.topics;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created zhoulong XY on 2016/12/25.
 */
public class JMSProducter {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认密码
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接url
    private static final int SENDNUM = 30;//发送消息的数量

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection = null;//连接
        Session session;//会话或者接受发送消息的线程
        Destination destination;//消息目的地
        MessageProducer messageProducer;//消息生产者
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSProducter.USERNAME, JMSProducter.PASSWORD, JMSProducter.BROKEURL);
        try {
            connection = connectionFactory.createConnection();//通过连接工厂获取连接
            connection.start();//启动连接
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);//创建session
            //destination = session.createQueue("FristQueueTopic");
            destination = session.createTopic("FristTopic1");//创建消息队列
            messageProducer = session.createProducer(destination);//创建消息生产者
            sendMessage(session, messageProducer);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 发送消息
     *
     * @param session
     * @param messageProducer
     * @throws JMSException
     */
    public static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException {
        for (int i = 0; i < JMSProducter.SENDNUM; i++) {
            TextMessage textMessage = session.createTextMessage("ActiveMQ 发送消息" + i);
            System.out.println(textMessage.toString());
            messageProducer.send(textMessage);
        }
    }
}
