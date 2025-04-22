package utility;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DBUtility {
    public static void SetImage(JFrame frame, String imagePath, int newWidth, int newHeight) {
        // Try loading as resource first
        BufferedImage originalImage = null;
        // First try loading from resources
        try {
            originalImage = ImageIO.read(DBUtility.class.getResourceAsStream(imagePath));
        } catch (Exception e) {
            // If resource loading fails, try loading from file system
            try {
                originalImage = ImageIO.read(new File(imagePath));
            } catch (Exception ex) {
                System.err.println("Failed to load image from both resource and file path: " + imagePath);
                return;
            }
        }
        if (originalImage == null) {
            System.err.println("Image not found: " + imagePath);
            return;
        }
        // Resize the image
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        // Set as background
        ImageIcon backgroundImage = new ImageIcon(resizedImage);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, newWidth, newHeight);
        frame.getContentPane().add(backgroundLabel, BorderLayout.CENTER);
        frame.validate();
    }

    private static HashMap<String, JFrame> formMap = new HashMap<>();

    public static void openForm(String formName, JFrame fromInstance) {
        JFrame existingForm = formMap.get(formName);
        if (existingForm == null || !existingForm.isVisible()) {
            formMap.put(formName, fromInstance);
            fromInstance.setVisible(true);
        } else {
            existingForm.toFront();
        }
    }

    public static String getPath(String finalPath) {
        String projectPath = System.getProperty("user.dir");
        return projectPath + "\\src\\" + finalPath;
    }

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    public static BufferedImage scaleImage(BufferedImage originalImage, BufferedImage selectedImage) {
        int width = selectedImage.getWidth();
        int height = selectedImage.getHeight();
        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        scaledImage.createGraphics().drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        return scaledImage;
    }
}