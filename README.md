# 生火间（My_Dark_Room）

## 项目简介
### Java课程结束的课程设计作业。
生火间（英文名：My_Dark_Room）是一款基于 Java Swing 实现的文字冒险与资源管理类小游戏。玩家在黑暗寒冷的房间中，通过添柴、伐木、建造建筑、管理资源等操作，逐步发展自己的营地，体验从孤独到繁荣的过程。

## 游戏玩法

- **添柴**：保持火堆燃烧，提升房间温度，防止寒冷。
- **伐木**：获取木头资源，用于建造建筑。
- **建造建筑**：包括小屋、陷阱和货车，分别提升人口容量、捕获资源和伐木效率。
- **查看陷阱**：收集陷阱捕获的毛皮、肉和牙齿。
- **消息系统**：游戏内事件和进展通过消息面板实时推送。

## 主要功能模块

- **MVC 架构**：
  - Model（模型）：`GameModel` 管理资源、建筑、房间温度和阶段。
  - View（视图）：`FireRoomGame` 主窗口，`SceneManager` 场景切换，`ResourcePanel`、`BuildingPanel`、`MessagePanel` 等负责 UI 展示。
  - Controller（控制器）：`GameController` 负责核心逻辑，`RoomManager`、`TrapManager`、`VehicleManager` 分别管理建筑、陷阱和载具。

- **事件驱动**：
  - `EventManager` 统一管理消息、资源、建筑、阶段、场景等事件监听与通知。

- **冷却按钮**：
  - `CooldownButton` 实现带冷却时间的按钮，防止频繁操作。

- **悬浮信息按钮**：
  - `HoverInfoButton` 鼠标悬停时显示资源消耗等提示。

- **多场景切换**：
  - `FireRoomScene`（生火间）、`CurrentScaleScene`（静谧森林）、`LongJourneyScene`（漫漫尘途，敬请期待）。

## 主要类说明

- `FireRoomGame`：程序主入口，初始化窗口、MVC 组件和 UI。
- `GameController`：游戏主控制器，负责资源、建筑、消息等核心逻辑。
- `GameModel`：游戏数据模型，管理资源、建筑、温度和阶段。
- `EventManager`：事件总线，支持多种事件监听与分发。
- `RoomManager`、`TrapManager`、`VehicleManager`：分别管理小屋、陷阱、货车的建造与逻辑。
- `CooldownButton`、`HoverInfoButton`：自定义 UI 组件，提升交互体验。
- `ResourcePanel`、`BuildingPanel`、`MessagePanel`：资源、建筑、消息的 UI 展示面板。
- `SceneManager`：场景管理与切换。
- `FireRoomScene`、`CurrentScaleScene`、`LongJourneyScene`：不同游戏阶段的场景。

## 运行方式

1. 使用支持 Java 8 及以上的 JDK。
2. 进入项目根目录，编译所有 Java 文件：
   ```sh
   javac -encoding UTF-8 -d out Design/**/*.java
   ```
3. 运行主类：
   ```sh
   java -cp out Design.FireRoomGame
   ```

## 目录结构

```
Design/
├── CooldownButton.java         # 冷却按钮组件
├── FireRoomGame.java           # 主程序入口
├── GameConstants.java          # 游戏常量
├── HoverInfoButton.java        # 悬浮信息按钮
├── controller/                 # 控制器包
│   ├── GameController.java
│   ├── RoomManager.java
│   ├── RoomStatusTimerManager.java
│   ├── TrapManager.java
│   └── VehicleManager.java
├── event/                      # 事件管理包
│   └── EventManager.java
├── model/                      # 数据模型包
│   └── GameModel.java
├── service/                    # 服务包
│   ├── MessageService.java
│   └── ResourceService.java
├── view/                       # 视图包
│   ├── BuildingPanel.java
│   ├── MessagePanel.java
│   ├── ResourcePanel.java
│   ├── SceneManager.java
│   └── scenes/
│       ├── CurrentScaleScene.java
│       ├── FireRoomScene.java
│       └── LongJourneyScene.java
```

## 致谢

本项目灵感来源于经典文字冒险游戏《A Dark Room》，若有侵权请联系删除。