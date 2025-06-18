package Design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CooldownButton extends JButton {
    private Timer cooldownTimer;
    private Timer progressTimer;
    private int cooldownTimeSeconds;
    private int remainingCooldownTime;
    private Color originalBackgroundColor;

    public CooldownButton(String text, int cooldownTimeSeconds) {
        super(text);
        this.cooldownTimeSeconds = cooldownTimeSeconds;
        this.remainingCooldownTime = 0;
        this.originalBackgroundColor = getBackground();

        // 设置按钮模板的初始背景为白色
        setBackground(Color.WHITE);
        setOpaque(true);
        setBorderPainted(false);

        // 冷却计时器（用于禁用按钮）
        cooldownTimer = new Timer(cooldownTimeSeconds * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(true);
                remainingCooldownTime = 0;
                cooldownTimer.stop();
                progressTimer.stop();
                setBackground(Color.WHITE); // 冷却结束后重置为白色
            }
        });
        cooldownTimer.setRepeats(false);

        // 进度条计时器（用于视觉效果）
        progressTimer = new Timer(100, new ActionListener() { // Update every 100ms
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingCooldownTime > 0) {
                    remainingCooldownTime -= 100; // 减少 100ms
                    float progress = (float) (cooldownTimeSeconds * 1000 - remainingCooldownTime) / (cooldownTimeSeconds * 1000);
                    int grayValue = (int) (255 * (1 - progress)); // 从灰色 (255) 到白色 (0)
                    setBackground(new Color(255, 255, grayValue)); // 灰色到白色过渡的 R, G, B 值
                } else {
                    progressTimer.stop();
                    setBackground(Color.WHITE);
                }
            }
        });
    }

    /**
     * 开始按钮的冷却计时。
     * 禁用按钮，设置剩余冷却时间，改变背景颜色，并启动冷却计时器和进度条计时器。
     */
    public void startCooldown() {
        setEnabled(false);
        remainingCooldownTime = cooldownTimeSeconds * 1000;
        setBackground(Color.LIGHT_GRAY); // 从灰色开始
        cooldownTimer.start();
        progressTimer.start();
    }

    /**
     * 绘制按钮组件。
     * 在按钮处于冷却状态时，绘制一个覆盖层来模拟进度条效果。
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isEnabled() && remainingCooldownTime > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            float progress = (float) (cooldownTimeSeconds * 1000 - remainingCooldownTime) / (cooldownTimeSeconds * 1000);
            int width = (int) (getWidth() * progress);
            g2.setColor(Color.WHITE); // 变白的部分
            g2.fillRect(0, 0, width, getHeight());
            g2.dispose();
        }
    }
}