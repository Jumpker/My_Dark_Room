package Design.controller;

import Design.GameConstants;
import Design.model.GameModel;
import Design.event.EventManager;
import Design.service.MessageService;

/**
 * 车辆管理器类，负责处理车辆相关的逻辑
 */
public class VehicleManager {
    private GameModel model;
    private EventManager eventManager;
    private MessageService messageService;
    private static final int DEFAULT_WOOD_GAIN = GameConstants.GameValues.DEFAULT_WOOD_GAIN;
    private static final int CART_WOOD_GAIN = GameConstants.GameValues.CART_WOOD_GAIN;
    
    /**
     * 构造函数
     * @param model 游戏模型
     * @param eventManager 事件管理器
     * @param messageService 消息服务
     */
    public VehicleManager(GameModel model, EventManager eventManager, MessageService messageService) {
        this.model = model;
        this.eventManager = eventManager;
        this.messageService = messageService;
    }
    
    /**
     * 建造货车
     */
    public void buildCart() {
        if (hasCart()) {
            return;
        }
        
        if (model.getResource(GameConstants.Resources.WOOD) >= GameConstants.BuildingCosts.CART_COST) {
            model.decreaseResource(GameConstants.Resources.WOOD, GameConstants.BuildingCosts.CART_COST);
            model.increaseBuilding(GameConstants.Buildings.CART);
            
            // 通知资源变化
            eventManager.notifyResourceChangeListeners(model.getResources());
            
            // 发送消息
            messageService.sendMessage(GameConstants.Messages.CART_BUILT);
            
            // 通知建筑变化
            eventManager.notifyBuildingChangeListeners(model.getBuildings());
        } else {
            messageService.sendMessage(GameConstants.Messages.WOOD_SHORTAGE);
        }
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