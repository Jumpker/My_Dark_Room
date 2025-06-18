package Design;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class HoverInfoButton extends JButton {
    private String resourceInfo;
    private JLabel infoLabel;

    public HoverInfoButton(String text, String resourceInfo) {
        super(text);
        this.resourceInfo = resourceInfo;
        this.infoLabel = new JLabel();
        this.infoLabel.setVisible(false);

        // 添加鼠标监听器以显示/隐藏信息标签
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                infoLabel.setText(resourceInfo);
                infoLabel.setVisible(true);
                // 定位信息标签在按钮旁边
                // 这部分可能需要根据实际布局管理器进行调整
                // 目前，我们假设它被添加到一个可以管理其位置的父容器中
            }

            @Override
            public void mouseExited(MouseEvent e) {
                infoLabel.setVisible(false);
            }
        });
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }
}