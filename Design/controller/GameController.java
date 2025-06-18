package Design.controller;

import Design.GameConstants;
import Design.event.EventManager;
import Design.model.GameModel;
import Design.service.MessageService;
import Design.service.ResourceService;
import Design.view.SceneManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * 游戏控制器类，负责处理游戏逻辑
 */
public class GameController {
    private GameModel model;
    private EventManager eventManager;
    private MessageService messageService;
    private ResourceService resourceService;
    private SceneManager sceneManager;
    private TrapManager trapManager;
    private VehicleManager vehicleManager;
    private RoomManager roomManager;
    private RoomStatusTimerManager roomStatusTimerManager;
    
    /**
     * 构造函数
     * @param model 游戏模型
     * @param eventManager 事件管理器
     */
    public GameController(GameModel model, EventManager eventManager) {
        this.model = model; // 初始化游戏模型
        this.eventManager = eventManager; // 初始化事件管理器
        this.messageService = new MessageService(eventManager); // 初始化消息服务，用于发送游戏内消息
        this.resourceService = new ResourceService(model, eventManager); // 初始化资源服务，用于管理游戏资源
        this.trapManager = new TrapManager(model, messageService, resourceService); // 初始化陷阱管理器，处理陷阱相关逻辑
        this.vehicleManager = new VehicleManager(model, eventManager, messageService); // 初始化载具管理器，处理载具相关逻辑
        this.roomManager = new RoomManager(model, eventManager, messageService, resourceService); // 初始化房间管理器，处理小屋相关逻辑和场景切换
        this.roomStatusTimerManager = new RoomStatusTimerManager(model, eventManager, messageService); // 初始化房间状态计时器管理器，处理房间状态更新计时
        
        // 初始化游戏
        initializeGame();
    }
    
    /**
     * 初始化游戏
     */
    protected void initializeGame() {
        // 添加初始木头资源（不显示）
        model.increaseResource("木头", 10);
        
        // 使用计时器延迟添加初始消息，确保消息监听器已经注册
        Timer initialMessageTimer = new Timer(500, e -> {
            // 添加初始消息
            addMessage("火堆熄灭了.");
            addMessage("房间冰冷刺骨.");
            
            // 启动第一阶段房间状态更新计时器
            roomStatusTimerManager.startPhase1Timer();
        });
        initialMessageTimer.setRepeats(false);
        initialMessageTimer.start();
    }
    

    
    /**
     * 添加燃料（添柴）
     */
    public void addFuel() {
        if (model.isGamePhase2()) {
            // 第二阶段：需要消耗木头
            int currentWood = model.getResource("木头");
            if (currentWood >= 1) {
                model.decreaseResource("木头", 1);
                model.increaseRoomHeat();
                String[] messages = {"火堆冒出火苗.", "火光映出窗外，射入黑暗之中.", "火堆燃烧着.", "火堆熊熊燃烧."};
                addMessage(messages[(int) (Math.random() * messages.length)]);
                eventManager.notifyResourceChangeListeners(model.getResources());
            } else {
                addMessage(GameConstants.Messages.WOOD_SHORTAGE);
            }
        } else {
            // 第一阶段：不需要消耗木头，可以无限添柴
            model.increaseRoomHeat();
            String[] messages = {"火堆冒出火苗.", "火光映出窗外，射入黑暗之中.", "火堆燃烧着.", "火堆熊熊燃烧."};
            addMessage(messages[(int) (Math.random() * messages.length)]);
            
            // 游戏进程基于第一次点击
            if (!model.isGamePhase2()) {
                triggerGameProgression();
            }
        }
    }
    
    /**
     * 触发游戏进程
     */
    private void triggerGameProgression() {
        model.setGamePhase2(true);
        Timer strangerArrivalTimer = new Timer(GameConstants.Timers.STRANGER_ARRIVAL_DELAY, ev -> {
            addMessage(GameConstants.Messages.STRANGER_ARRIVAL);
            Timer strangerMumbleTimer = new Timer(GameConstants.Timers.STRANGER_MUMBLE_DELAY, ev2 -> {
                addMessage(GameConstants.Messages.STRANGER_MUMBLE);
                Timer strangerCalmTimer = new Timer(GameConstants.Timers.STRANGER_CALM_DELAY, ev3 -> {
                    addMessage(GameConstants.Messages.STRANGER_CALM);
                    Timer strangerHelpTimer = new Timer(GameConstants.Timers.STRANGER_HELP_DELAY, ev4 -> {
                        addMessage(GameConstants.Messages.STRANGER_HELP);
                        // 触发游戏第二阶段
                        triggerGamePhase2();
                    });
                    strangerHelpTimer.setRepeats(false);
                    strangerHelpTimer.start();
                });
                strangerCalmTimer.setRepeats(false);
                strangerCalmTimer.start();
            });
            strangerMumbleTimer.setRepeats(false);
            strangerMumbleTimer.start();
        });
        strangerArrivalTimer.setRepeats(false);
        strangerArrivalTimer.start();
    }
    
    /**
     * 触发游戏第二阶段
     */
    private void triggerGamePhase2() {
        // 重置木头资源为5
        model.getResources().clear();
        model.increaseResource("木头", 5);
        eventManager.notifyResourceChangeListeners(model.getResources());
        
        // 通知游戏阶段变化
        eventManager.notifyGamePhaseChangeListeners(true);
        
        // 第二阶段消息
        Timer phase2MessagesTimer = new Timer(GameConstants.Timers.PHASE2_MESSAGES_DELAY, e -> {
            addMessage(GameConstants.Messages.BUILDER_TRAPS);
            Timer message2Timer = new Timer(GameConstants.Timers.PHASE2_MESSAGE2_DELAY, ev2 -> {
                addMessage(GameConstants.Messages.BUILDER_CART);
                Timer message3Timer = new Timer(GameConstants.Timers.PHASE2_MESSAGE3_DELAY, ev3 -> {
                    addMessage(GameConstants.Messages.BUILDER_WANDERERS);
                });
                message3Timer.setRepeats(false);
                message3Timer.start();
            });
            message2Timer.setRepeats(false);
            message2Timer.start();
        });
        phase2MessagesTimer.setRepeats(false);
        phase2MessagesTimer.start();
    }
    
    /**
     * 伐木
     */
    public void chopWood() {
        int woodAmount = vehicleManager.getWoodGainAmount();
        model.increaseResource("木头", woodAmount);
        eventManager.notifyResourceChangeListeners(model.getResources());
        addMessage(GameConstants.Messages.WOOD_SCATTERED);
    }
    
    /**
     * 建造小屋
     */
    public void buildHut() {
        roomManager.buildHut();
    }
    
    /**
     * 建造陷阱
     */
    public void buildTrap() {
        trapManager.buildTrap();
    }
    
    /**
     * 建造货车
     */
    public void buildCart() {
        vehicleManager.buildCart();
    }
    
    /**
     * 添加消息
     * @param message 消息内容
     */
    public void addMessage(String message) {
        messageService.sendMessage(message);
    }
    
    /**
     * 获取游戏模型
     * @return 游戏模型
     */
    public GameModel getModel() {
        return model;
    }
    
    /**
     * 获取陷阱管理器
     * @return 陷阱管理器
     */
    public TrapManager getTrapManager() {
        return trapManager;
    }
    
    /**
     * 获取房间管理器
     * @return 房间管理器
     */
    public RoomManager getRoomManager() {
        return roomManager;
    }
    
    /**
     * 获取车辆管理器
     * @return 车辆管理器
     */
    public VehicleManager getVehicleManager() {
        return vehicleManager;
    }
    
    /**
     * 检查陷阱
     */
    public void checkTraps() {
        trapManager.checkTraps();
    }
    
    /**
     * 获取事件管理器
     * @return 事件管理器
     */
    public EventManager getEventManager() {
        return eventManager;
    }
    
    /**
     * 设置场景管理器
     * @param sceneManager 场景管理器
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.roomManager.setSceneManager(sceneManager);
    }
    
    /**
     * 获取场景管理器
     * @return 场景管理器
     */
    public SceneManager getSceneManager() {
        return sceneManager;
    }
}