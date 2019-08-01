package com.fyl.boot.util.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具
 */
public class ImageUtil {

    /**
     * 调整大小
     *
     * @param source  原图
     * @param targetW 目标宽度
     * @param targetH 目标高度
     * @return
     */
    public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        int type = 5;
        BufferedImage target = null;
        double sx = (double) targetW / (double) source.getWidth();
        double sy = (double) targetH / (double) source.getHeight();
        if (sx < sy) {
            sx = sy;
            targetW = (int) (sy * (double) source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sx * (double) source.getHeight());
        }

        if (type == 0) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }

        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        target.flush();
        return target;
    }

    /**
     * 调整大小
     *
     * @param in     输入文件流
     * @param target 目标文件
     * @param width  目标宽度
     * @param height 目标高度
     * @throws IOException
     */
    public static void resize(InputStream in, File target, int width, int height) throws IOException {
        BufferedImage srcImage = ImageIO.read(in);
        int w;
        int h;
        if (width > 0 || height > 0) {
            w = srcImage.getWidth();
            h = srcImage.getHeight();
            if (w <= width || h <= height) {
                String fileName = target.getName();
                String formatName = fileName.substring(fileName.lastIndexOf(46) + 1);
                ImageIO.write(srcImage, formatName, target);
                return;
            }

            srcImage = resize(srcImage, width, height);
        }

        w = srcImage.getWidth();
        h = srcImage.getHeight();
        if (w == width) {
            int x = 0;
            int y = h / 2 - height / 2;
            writeSubimage(srcImage, new Rectangle(x, y, width, height), target);
        } else if (h == height) {
            int x = w / 2 - width / 2;
            int y = 0;
            writeSubimage(srcImage, new Rectangle(x, y, width, height), target);
        }

        srcImage.flush();
    }

    /**
     * 调整大小
     *
     * @param src    原文件
     * @param target 目标文件
     * @param width  目标宽度
     * @param height 目标高度
     * @throws IOException
     */
    public static void resize(File src, File target, int width, int height) throws Exception {
        InputStream in = new FileInputStream(src);
        resize(in, target, width, height);
        in.close();
    }

    /**
     * 写入文件
     *
     * @param image          原文件
     * @param subImageBounds 目标文件长宽
     * @param subImageFile   目标文件
     * @throws IOException
     */
    private static void writeSubimage(BufferedImage image, Rectangle subImageBounds, File subImageFile) throws IOException {
        boolean isBounds = subImageBounds.x >= 0 && subImageBounds.y >= 0 && subImageBounds.width - subImageBounds.x <= image.getWidth() && subImageBounds.height - subImageBounds.y <= image.getHeight();
        if (!isBounds) {
            System.out.println("Bad subimage bounds");
        }
        BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y, subImageBounds.width, subImageBounds.height);
        String fileName = subImageFile.getName();
        String formatName = fileName.substring(fileName.lastIndexOf(46) + 1);
        ImageIO.write(subImage, formatName, subImageFile);
        image.flush();
    }

    /**
     * 截取
     *
     * @param src    源文件
     * @param target 目标文件
     * @param x      起点x
     * @param y      起点y
     * @param width  截取宽度
     * @param height 截取高度
     */
    public static void crop(File src, File target, int x, int y, int width, int height) {
        try {
            BufferedImage bi = ImageIO.read(src);
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            if (srcWidth >= width && srcHeight >= height) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, 1);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                ImageIO.write(tag, "JPEG", target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //压缩图片长宽
        int width = 64;
        int height = 48;
        //读取目录
        String filePath = "F:\\test";
        //输出目录
        String outFilePath = "F:\\test2\\";

        File inFile = new File(filePath);
        File[] fileList = inFile.listFiles();
        System.out.println("该目录下对象个数：" + fileList.length);
        for (int i = 0; i < fileList.length; i++) {
            File file = fileList[i];
            if (!file.isFile()) {
                continue;
            }

            //重命名
            String fileName = file.getName().replace(" ", "");
            int index = fileName.lastIndexOf('.');
            String prefix = fileName.substring(0, index);
            String suffix = fileName.substring(index);
            String outFileName = prefix + "_preview" + suffix;
            System.out.println(i + ":" + outFileName);
            //输出文件
            File outFile = new File(outFilePath + outFileName);

            //调整大小
            resize(file, outFile, width, height);
            //截取
//            crop(file,outFile,0,0,1000,1000);
        }

    }
}

