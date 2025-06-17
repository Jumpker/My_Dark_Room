package Design.view.scenes;

import Design.CooldownButton;
import Design.GameConstants;
import Design.controller.GameController;
import Design.view.BuildingPanel;
import Design.view.ResourcePanel;
import Design.view.SceneManager;

import javax.swing.*;
import java.awt.*;

/**
 * 生火间场景类
 */
public class FireRoomScene implements Scene {
    private JPanel panel;
    private GameController controller;
    private SceneManager sceneManager;
    
    /**
     * 构造函数
     */
    public FireRoomScene(GameController controller) {
        this.controller = controller;
        
        // 初始化面板
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // 初始化组件
        initializeComponents();
        
        // 监听游戏阶段变化
        controller.getEventManager().addGamePhaseChangeListener(this::onGamePhaseChanged);
    }
    
    /**
     * 初始化组件
     */
    private void initializeComponents() {
        // 交互面板 (中央)
        JPanel interactionPanel = new JPanel();
        interactionPanel.setBackground(Color.WHITE);
        interactionPanel.setBorder(BorderFactory.createTitledBorder(GameConstants.UI.FIRE_ROOM_TITLE));
        
        // 游戏第一阶段：只有"添柴"按钮
        CooldownButton addFuelButton = new CooldownButton("添柴", 5); // 5秒冷却
        interactionPanel.add(addFuelButton);
        
        addFuelButton.addActionListener(e -> {
            addFuelButton.startCooldown();
            controller.addFuel();
        });
        
        panel.add(interactionPanel, BorderLayout.CENTER);
    }
    
    @Override
    public JPanel getPanel() {
        return panel;
    }
    
    @Override
    public void update() {
        // 更新场景状态
    }
    
    /**
     * 设置场景管理器
     * @param sceneManager 场景管理器
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    /**
     * 游戏阶段变化处理
     * @param isPhase2 是否为阶段2
     */
    private void onGamePhaseChanged(boolean isPhase2) {
        if (isPhase2) {
            // 在第二阶段，添加建筑面板和资源面板
            if (sceneManager == null) {
                System.err.println("SceneManager未设置");
                return;
            }
            
            if (sceneManager != null) {
                // 获取建筑面板和资源面板
                BuildingPanel buildingPanel = sceneManager.getBuildingPanel();
                ResourcePanel resourcePanel = sceneManager.getResourcePanel();
                
                // 获取中央组件(交互面板)
                Component centerComponent = null;
                for (Component comp : panel.getComponents()) {
                    if (panel.getLayout() instanceof BorderLayout) {
                        BorderLayout layout = (BorderLayout) panel.getLayout();
                        if (comp == layout.getLayoutComponent(panel, BorderLayout.CENTER)) {
                            centerComponent = comp;
                            break;
                        }
                    }
                }
                
                // 创建分割面板，上方放交互面板，下方放建筑面板
                if (centerComponent != null && buildingPanel != null) {
                    // 创建分割面板
                    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                    splitPane.setTopComponent(centerComponent);
                    splitPane.setBottomComponent(buildingPanel.getPanel());
                    splitPane.setResizeWeight(0.5); // 设置分割比例为1:1
                    splitPane.setDividerSize(5); // 设置分隔条大小
                    splitPane.setContinuousLayout(true); // 拖动时连续布局
                    splitPane.setBorder(null); // 移除边框
                    
                    // 移除原来的中央组件
                    panel.remove(centerComponent);
                    
                    // 添加分割面板到中央
                    panel.add(splitPane, BorderLayout.CENTER);
                    
                    // 设置建筑面板可见
                    buildingPanel.getPanel().setVisible(true);
                } else {
                    // 如果无法获取中央组件，则使用原来的方式添加建筑面板
                    if (buildingPanel != null) {
                        panel.add(buildingPanel.getPanel(), BorderLayout.SOUTH);
                    }
                }
                
                // 添加资源面板到右侧
                if (resourcePanel != null) {
                    panel.add(resourcePanel.getPanel(), BorderLayout.EAST);
                }
                
                // 刷新面板
                panel.revalidate();
                panel.repaint();
            }
        }
    }
}