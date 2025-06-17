package Design.service;

import Design.event.EventManager;

/**
 * 消息服务类，统一管理游戏中的消息发送
 * 减少各个组件对EventManager的直接依赖
 */
public class MessageService {
    private final EventManager eventManager;
    
    /**
     * 构造函数
     * @param eventManager 事件管理器
     */
    public MessageService(EventManager eventManager) {
        this.eventManager = eventManager;
    }
    
    /**
     * 发送消息
     * @param message 要发送的消息
     */
    public void sendMessage(String message) {
        eventManager.notifyMessageListeners(message);
    }
    
    /**
     * 发送多条消息
     * @param messages 要发送的消息数组
     */
    public void sendMessages(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }
    
    /**
     * 从消息数组中随机选择一条发送
     * @param messages 消息数组
     */
    public void sendRandomMessage(String[] messages) {
        if (messages != null && messages.length > 0) {
            String randomMessage = messages[(int) (Math.random() * messages.length)];
            sendMessage(randomMessage);
        }
    }
}