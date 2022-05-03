package utils;

import java.util.Optional;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Image {
    public static Result<BufferedImage> load(String path) {
        var file = new File(path);
        return read(file);
    }

    public static Result<BufferedImage> read(File file) {
        return Result.of(() -> ImageIO.read(file));
    }

    private static JFileChooser filterImgs(JFileChooser fc) {
        var filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fc.setFileFilter(filter);
        return fc;
    } 

    public static Optional<Result<BufferedImage>> selectImage() {
        var fc = new JFileChooser();
        var ret = filterImgs(fc).showOpenDialog(null);     

        return ret == JFileChooser.APPROVE_OPTION
            ? Optional.of(read(fc.getSelectedFile()))
            : Optional.empty();
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        var out = new BufferedImage(width, height, image.getType());
        var g2d = out.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        return out;
    }
}
