package Design;

/**
 * 游戏常量类，统一管理游戏中的常量值
 */
public final class GameConstants {
    
    // 私有构造函数，防止实例化
    private GameConstants() {
        throw new UnsupportedOperationException("常量类不能被实例化");
    }
    
    // 资源名称常量
    public static final class Resources {
        public static final String WOOD = "木头";
        public static final String FUR = "毛皮";
        public static final String MEAT = "肉";
        public static final String TEETH = "牙齿";
    }
    
    // 建筑名称常量
    public static final class Buildings {
        public static final String HUT = "小屋";
        public static final String TRAP = "陷阱";
        public static final String CART = "货车";
    }
    
    // 建筑成本常量
    public static final class BuildingCosts {
        public static final int HUT_COST = 100;
        public static final int TRAP_COST = 10;
        public static final int CART_COST = 30;
    }
    
    // 时间常量（毫秒）
    public static final class Timers {
        public static final int ADD_FUEL_COOLDOWN = 5000;  // 添柴冷却时间
        public static final int CHOP_WOOD_COOLDOWN = 15; // 伐木冷却时间（秒）
        public static final int CHECK_TRAPS_COOLDOWN = 15; // 查看陷阱冷却时间（秒）
        public static final int MESSAGE_DISPLAY_TIME = 75000; // 消息显示时间
        
        // 房间状态计时器
        public static final int PHASE1_TIMER_INTERVAL = 10000; // 第一阶段计时器间隔
        public static final int PHASE2_STATUS_TIMER_INTERVAL = 30000; // 第二阶段状态计时器间隔
        public static final int PHASE2_HEAT_TIMER_INTERVAL = 60000; // 第二阶段温度计时器间隔
        
        // 游戏进程计时器
        public static final int STRANGER_ARRIVAL_DELAY = 10000; // 陌生人到达延迟
        public static final int STRANGER_MUMBLE_DELAY = 5000; // 陌生人嘟囔延迟
        public static final int STRANGER_CALM_DELAY = 5000; // 陌生人平静延迟
        public static final int STRANGER_HELP_DELAY = 5000; // 陌生人帮助延迟
        public static final int PHASE2_MESSAGES_DELAY = 15000; // 第二阶段消息延迟
        public static final int PHASE2_MESSAGE2_DELAY = 5000; // 第二阶段消息2延迟
        public static final int PHASE2_MESSAGE3_DELAY = 5000; // 第二阶段消息3延迟
    }
    
    // 游戏数值常量
    public static final class GameValues {
        public static final int DEFAULT_WOOD_GAIN = 10; // 默认伐木获得量
        public static final int CART_WOOD_GAIN = 50; // 有货车时伐木获得量
        public static final int PHASE2_INITIAL_WOOD = 5; // 第二阶段初始木头数量
        public static final int HUT_POPULATION_CAPACITY = 4; // 每个小屋的人口容量
    }
    
    // 场景名称常量
    public static final class Scenes {
        public static final String FIRE_ROOM = "FireRoom";
        public static final String CURRENT_SCALE = "CurrentScale";
        public static final String LONG_JOURNEY = "漫漫尘途";
        public static final String LONELY_HUT = "孤独小屋";
    }
    
    // 消息常量
    public static final class Messages {
        public static final String WOOD_SHORTAGE = "木头不够了.";
        public static final String CART_EXISTS = "无需再建造货车了.";
        public static final String TRAP_EMPTY = "陷阱一无所获.";
        public static final String WOOD_SCATTERED = "林地上散落着枯枝败叶.";
        public static final String HUT_BUILT = "建造者在林中建起一栋小屋，她说消息很快就会流传出去.";
        public static final String TRAP_MORE_PREY = "陷阱越多，抓到的猎物就越多.";
        public static final String CART_BUILT = "摇摇晃晃的货车满载从森林运出木头.";
        
        // 游戏进程消息
        public static final String STRANGER_ARRIVAL = "一个衣衫褴褛的陌生人步履蹒跚地步入门来，瘫倒在角落里.";
        public static final String STRANGER_MUMBLE = "陌生人瑟瑟发抖，呢喃不已，听不清在说些什么.";
        public static final String STRANGER_CALM = "角落里的陌生人不再颤抖了，她的呼吸平静了下来.";
        public static final String STRANGER_HELP = "那名陌生人站在火堆旁.她说她可以帮忙。她说她会建东西.";
        public static final String BUILDER_TRAPS = "建造者说她能够制作陷阱来捕捉那些仍在野外活动的野兽.";
        public static final String BUILDER_CART = "建造者说她能够制造出货车，用来运载木头.";
        public static final String BUILDER_WANDERERS = "建造者说这里有许多流浪者，他们也会来工作.";
    }
    
    // UI常量
    public static final class UI {
        public static final String FOREST_TITLE = "森林";
        public static final String VILLAGE_TITLE_FORMAT = "村落 %d/%d";
        public static final String WAREHOUSE_TITLE = "仓库";
        public static final String BUILDING_TITLE = "建筑";
        public static final String NO_BUILDINGS = "暂无建筑";
        public static final String RESOURCE_PANEL_TITLE = "资源";
        public static final String BUILDING_PANEL_TITLE = "建筑面板";
        public static final String FIRE_ROOM_TITLE = "生火间";
        public static final String INTERACTION_PANEL_TITLE = "交互面板";
        public static final String ACTIVE_EVENTS_TITLE = "主动事件";
        public static final String LABOR_DISTRIBUTION_TITLE = "人员分工";
        public static final String LONG_JOURNEY_MESSAGE = "漫漫尘途 敬请期待.";
    }
}