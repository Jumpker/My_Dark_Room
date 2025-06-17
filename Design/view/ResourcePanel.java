package Design.view;

import Design.controller.GameController;
import Design.event.EventManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

/**
 * 资源面板类，用于显示游戏中的资源信息和建筑数量
 */
public class ResourcePanel {
    private JPanel panel;
    private JPanel resourcesPanel; // 资源子面板
    private JPanel buildingsPanel; // 建筑子面板
    private GameController controller;
    
    /**
     * 构造函数
     * @param controller 游戏控制器
     */
    public ResourcePanel(GameController controller) {
        this.controller = controller;
        
        // 初始化主面板
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("资源"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(250, panel.getPreferredSize().height));
        panel.setVisible(false); // 初始不可见，等待游戏阶段2触发
        
        // 初始化资源子面板
        resourcesPanel = new JPanel();
        resourcesPanel.setBackground(Color.WHITE);
        resourcesPanel.setLayout(new BoxLayout(resourcesPanel, BoxLayout.Y_AXIS));
        resourcesPanel.setBorder(BorderFactory.createTitledBorder("仓库"));
        
        // 初始化建筑子面板
        buildingsPanel = new JPanel();
        buildingsPanel.setBackground(Color.WHITE);
        buildingsPanel.setLayout(new BoxLayout(buildingsPanel, BoxLayout.Y_AXIS));
        buildingsPanel.setBorder(BorderFactory.createTitledBorder("建筑"));
        
        // 添加子面板到主面板
        panel.add(resourcesPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加间距
        panel.add(buildingsPanel);
        
        // 监听资源变化事件
        controller.getEventManager().addResourceChangeListener(this::updateResources);
        
        // 监听游戏阶段变化事件
        controller.getEventManager().addGamePhaseChangeListener(this::onGamePhaseChanged);
        
        // 监听场景名称变化事件
        controller.getEventManager().addSceneNameChangeListener(this::onSceneNameChanged);
    }
    
    /**
     * 获取面板
     * @return 资源面板
     */
    public JPanel getPanel() {
        return panel;
    }
    
    /**
     * 更新资源显示
     * @param resources 资源映射
     */
    private void updateResources(Map<String, Integer> resources) {
        resourcesPanel.removeAll();
        
        // 获取建筑数量
        Map<String, Integer> buildings = controller.getModel().getBuildings();
        
        // 更新资源显示（排除建筑）
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            String resourceName = entry.getKey();
            // 跳过建筑类型的资源
            if (!isBuilding(resourceName)) {
                JLabel resourceLabel = new JLabel(resourceName + ": " + entry.getValue());
                resourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                resourcesPanel.add(resourceLabel);
            }
        }
        
        // 更新建筑显示
        updateBuildings(buildings);
        
        // 这三行是因为资源太少，导致子面板显示不全，故添加一个空的标签来占位
        JLabel specialResourceLabel = new JLabel("                              ");
        specialResourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resourcesPanel.add(specialResourceLabel);

        // 刷新面板
        resourcesPanel.revalidate();
        resourcesPanel.repaint();
        panel.revalidate();
        panel.repaint();
    }
    
    /**
     * 更新建筑显示
     * @param buildings 建筑映射
     */
    private void updateBuildings(Map<String, Integer> buildings) {
        buildingsPanel.removeAll();
        
        // 添加建筑数量标签
        if (buildings != null && !buildings.isEmpty()) {
            for (Map.Entry<String, Integer> entry : buildings.entrySet()) {
                JLabel buildingLabel = new JLabel(entry.getKey() + ": " + entry.getValue());
                buildingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                buildingsPanel.add(buildingLabel);
            }
        } else {
            JLabel noBuildingsLabel = new JLabel("暂无建筑");
            noBuildingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            buildingsPanel.add(noBuildingsLabel);
        }
        
        // 这三行是因为资源太少，导致子面板显示不全，故添加一个空的标签来占位
        JLabel specialBuildingLabel = new JLabel("                              ");
        specialBuildingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buildingsPanel.add(specialBuildingLabel);

        // 刷新面板
        buildingsPanel.revalidate();
        buildingsPanel.repaint();
    }
    
    /**
     * 判断资源名称是否为建筑
     * @param resourceName 资源名称
     * @return 是否为建筑
     */
    private boolean isBuilding(String resourceName) {
        // 判断资源是否为建筑类型
        return resourceName.equals("小屋") || 
               resourceName.equals("陷阱") || 
               resourceName.equals("货车");
    }
    
    /**
     * 游戏阶段变化处理
     * @param isPhase2 是否为阶段2
     */
    private void onGamePhaseChanged(boolean isPhase2) {
        if (isPhase2) {
            // 显示面板
            panel.setVisible(true);
            
            // 初始化建筑面板
            updateBuildings(controller.getModel().getBuildings());
            
            // 初始化资源面板
            updateResources(controller.getModel().getResources());
            
            // 更新建筑子面板标题为"森林"
            updateBuildingsPanelTitle("森林");
        }
    }
    
    /**
     * 场景名称变化处理
     * @param sceneName 场景名称
     */
    private void onSceneNameChanged(String sceneName) {
        if ("孤独小屋".equals(sceneName)) {
            // 获取小屋数量
            int hutCount = controller.getModel().getBuilding("小屋");
            // 计算人数上限（每个小屋4人）
            int populationLimit = hutCount * 4;
            // 当前人数（初始为0）
            int currentPopulation = 0;
            
            // 更新建筑子面板标题为"村落 X/Y"
            updateBuildingsPanelTitle(String.format("村落 %d/%d", currentPopulation, populationLimit));
        }
    }
    
    /**
     * 更新建筑子面板标题
     * @param title 新标题
     */
    private void updateBuildingsPanelTitle(String title) {
        buildingsPanel.setBorder(BorderFactory.createTitledBorder(title));
         buildingsPanel.revalidate();
         buildingsPanel.repaint();
    }
}