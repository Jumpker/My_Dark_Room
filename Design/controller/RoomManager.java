package Design.controller;

import Design.GameConstants;
import Design.event.EventManager;
import Design.model.GameModel;
import Design.service.MessageService;
import Design.service.ResourceService;
import Design.view.SceneManager;

/**
 * 房间管理器类，负责处理小屋相关的逻辑
 */
public class RoomManager {
    private GameModel model;
    private EventManager eventManager;
    private MessageService messageService;
    private ResourceService resourceService;
    private SceneManager sceneManager;
    
    /**
     * 构造函数
     * @param model 游戏模型
     * @param eventManager 事件管理器
     * @param messageService 消息服务
     * @param resourceService 资源服务
     */
    public RoomManager(GameModel model, EventManager eventManager, MessageService messageService, ResourceService resourceService) {
        this.model = model;
        this.eventManager = eventManager;
        this.messageService = messageService;
        this.resourceService = resourceService;
    }
    
    /**
     * 设置场景管理器
     * @param sceneManager 场景管理器
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    /**
     * 建造小屋
     */
    public void buildHut() {
        if (model.getResource(GameConstants.Resources.WOOD) >= GameConstants.BuildingCosts.HUT_COST) {
            resourceService.decreaseResource(GameConstants.Resources.WOOD, GameConstants.BuildingCosts.HUT_COST);
            model.increaseBuilding(GameConstants.Buildings.HUT);
            messageService.sendMessage(GameConstants.Messages.HUT_BUILT);
            
            // 检查是否是第一个小屋
            if (model.getBuilding(GameConstants.Buildings.HUT) == 1) {

                // 通知场景名称变化为"孤独小屋"
                eventManager.notifySceneNameChangeListeners(GameConstants.Scenes.LONELY_HUT);
            }
        } else {
            messageService.sendMessage(GameConstants.Messages.WOOD_SHORTAGE);
        }
    }
    
    /**
     * 获取小屋数量
     * @return 小屋数量
     */
    public int getHutCount() {
        return model.getBuilding(GameConstants.Buildings.HUT);
    }
    
    /**
     * 计算人口容量
     * @return 人口容量
     */
    public int getPopulationCapacity() {
        return getHutCount() * GameConstants.GameValues.HUT_POPULATION_CAPACITY;
    }
}