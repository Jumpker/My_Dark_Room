package Design.service;

import Design.event.EventManager;
import Design.model.GameModel;
import java.util.Map;

/**
 * 资源服务类，统一管理游戏中的资源操作和通知
 * 减少各个组件对EventManager的直接依赖
 */
public class ResourceService {
    private final GameModel model;
    private final EventManager eventManager;
    
    /**
     * 构造函数
     * @param model 游戏模型
     * @param eventManager 事件管理器
     */
    public ResourceService(GameModel model, EventManager eventManager) {
        this.model = model;
        this.eventManager = eventManager;
    }
    
    /**
     * 增加资源并通知变化
     * @param resourceName 资源名称
     * @param amount 增加数量
     */
    public void increaseResource(String resourceName, int amount) {
        model.increaseResource(resourceName, amount);
        notifyResourceChange();
    }
    
    /**
     * 减少资源并通知变化
     * @param resourceName 资源名称
     * @param amount 减少数量
     */
    public void decreaseResource(String resourceName, int amount) {
        model.decreaseResource(resourceName, amount);
        notifyResourceChange();
    }
    
    /**
     * 获取资源数量
     * @param resourceName 资源名称
     * @return 资源数量
     */
    public int getResource(String resourceName) {
        return model.getResource(resourceName);
    }
    
    /**
     * 获取所有资源
     * @return 资源映射
     */
    public Map<String, Integer> getResources() {
        return model.getResources();
    }
    
    /**
     * 通知资源变化
     */
    public void notifyResourceChange() {
        eventManager.notifyResourceChangeListeners(model.getResources());
    }
}