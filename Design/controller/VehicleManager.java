package Design.controller;

import Design.GameConstants;
import Design.model.GameModel;
import Design.event.EventManager;

/**
 * 车辆管理器类，负责处理车辆相关的逻辑
 */
public class VehicleManager {
    private GameModel model;
    private EventManager eventManager;
    private static final int DEFAULT_WOOD_GAIN = GameConstants.GameValues.DEFAULT_WOOD_GAIN;
    private static final int CART_WOOD_GAIN = GameConstants.GameValues.CART_WOOD_GAIN;
    
    /**
     * 构造函数
     * @param model 游戏模型
     * @param eventManager 事件管理器
     */
    public VehicleManager(GameModel model, EventManager eventManager) {
        this.model = model;
        this.eventManager = eventManager;
    }
    
    /**
     * 建造货车
     * @return 是否成功建造
     */
    public boolean buildCart() {
        if (hasCart() || model.getResource(GameConstants.Resources.WOOD) < GameConstants.BuildingCosts.CART_COST) {
            return false;
        }
        
        model.decreaseResource(GameConstants.Resources.WOOD, GameConstants.BuildingCosts.CART_COST);
        model.increaseBuilding(GameConstants.Buildings.CART);
        
        // 通知资源变化
        eventManager.notifyResourceChangeListeners(model.getResources());
        
        return true;
    }
    
    /**
     * 获取伐木获得的木头数量
     * @return 木头数量
     */
    public int getWoodGainAmount() {
        return hasCart() ? GameConstants.GameValues.CART_WOOD_GAIN : GameConstants.GameValues.DEFAULT_WOOD_GAIN;
    }
    
    /**
     * 是否已有货车
     * @return 是否已有货车
     */
    public boolean hasCart() {
        return model.getBuilding(GameConstants.Buildings.CART) > 0;
    }
    
    /**
     * 获取货车数量
     * @return 货车数量
     */
    public int getCartCount() {
        return model.getBuilding(GameConstants.Buildings.CART);
    }
}