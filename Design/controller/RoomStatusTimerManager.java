package Design.controller;

import Design.model.GameModel;
import Design.event.EventManager;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 房间状态计时器管理类，负责管理不同游戏阶段的房间状态更新计时器
 */
public class RoomStatusTimerManager {
    // 常量定义
    private static final int PHASE1_UPDATE_INTERVAL = 10000; // 第一阶段更新间隔：10秒
    private static final int PHASE2_STATUS_UPDATE_INTERVAL = 30000; // 第二阶段状态更新间隔：30秒
    private static final int PHASE2_HEAT_DECREASE_INTERVAL = 60000; // 第二阶段温度降低间隔：60秒
    
    private GameModel model;
    private EventManager eventManager;
    private Timer roomStatusTimer; // 房间状态更新计时器
    private Timer roomHeatTimer;   // 房间温度降低计时器（仅第二阶段使用）
    
    /**
     * 构造函数
     * @param model 游戏模型
     * @param eventManager 事件管理器
     */
    public RoomStatusTimerManager(GameModel model, EventManager eventManager) {
        this.model = model;
        this.eventManager = eventManager;
        
        // 注册游戏阶段变化监听器
        this.eventManager.addGamePhaseChangeListener(this::onGamePhaseChanged);
    }
    
    /**
     * 启动房间状态更新计时器（第一阶段）
     */
    public void startPhase1Timer() {
        // 停止可能存在的计时器
        stopAllTimers();
        
        // 创建并启动第一阶段计时器（每10秒更新一次状态并降低温度）
        roomStatusTimer = new Timer(PHASE1_UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.decreaseRoomHeat();
                addMessage(model.getRoomStatusMessage());
            }
        });
        roomStatusTimer.setRepeats(true);
        roomStatusTimer.start();
    }
    
    /**
     * 启动房间状态更新计时器（第二阶段）
     */
    public void startPhase2Timers() {
        // 停止可能存在的计时器
        stopAllTimers();
        
        // 创建并启动第二阶段状态更新计时器（每30秒更新一次状态）
        roomStatusTimer = new Timer(PHASE2_STATUS_UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMessage(model.getRoomStatusMessage());
            }
        });
        roomStatusTimer.setRepeats(true);
        roomStatusTimer.start();
        
        // 创建并启动第二阶段温度降低计时器（每60秒降低一次温度）
        roomHeatTimer = new Timer(PHASE2_HEAT_DECREASE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.decreaseRoomHeat();
                // 温度变化后更新房间状态消息
                addMessage(model.getRoomStatusMessage());
            }
        });
        roomHeatTimer.setRepeats(true);
        roomHeatTimer.start();
    }
    
    /**
     * 停止所有计时器
     */
    private void stopAllTimers() {
        if (roomStatusTimer != null && roomStatusTimer.isRunning()) {
            roomStatusTimer.stop();
        }
        
        if (roomHeatTimer != null && roomHeatTimer.isRunning()) {
            roomHeatTimer.stop();
        }
    }
    
    /**
     * 游戏阶段变化事件处理
     * @param isPhase2 是否为第二阶段
     */
    private void onGamePhaseChanged(boolean isPhase2) {
        if (isPhase2) {
            startPhase2Timers();
        } else {
            startPhase1Timer();
        }
    }
    
    /**
     * 添加消息
     * @param message 消息内容
     */
    private void addMessage(String message) {
        eventManager.notifyMessageListeners(message);
    }
    
    /**
     * 清理资源
     */
    public void cleanup() {
        stopAllTimers();
        eventManager.removeGamePhaseChangeListener(this::onGamePhaseChanged);
    }
}