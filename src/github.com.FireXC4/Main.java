package github.com.FireXC4;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String newline ="";
    public static List<String>lines = new ArrayList<>();

    public static void main(String[] args) {

        String ProjectPath = System.getProperty("user.dir");
        System.out.println(ProjectPath+"\\");
        for (int j = 0;j < 3;j++)
        {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(ProjectPath+"\\"+"image"+j+".png"));

                BufferedImage resimage = resize(image,64,64);

               // BufferedImage newimage = new BufferedImage( resimage.getWidth(), resimage.getHeight(), BufferedImage.TYPE_BYTE_GRAY );

                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                ColorConvertOp op = new ColorConvertOp(cs, null);
                BufferedImage newimage = op.filter(resimage, null);

                lines.add("Image"+j);
                toAscII(newimage);
                //System.out.println("");


            } catch (IOException e) {
                System.out.println(e + "   " +ProjectPath+"\\"+"image"+j+"png");
            }
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
        /*
        This function gets the original img size and changes its size by inputed values.
        newW = new Width
        newH = new Height

        img = BufferedImage - doesnt have error exeptions! Input just right files.
         */
    }

    public static void toAscII(BufferedImage image) throws IOException {

        for (int i = 0;i < image.getHeight();i++){
            for (int j = 0;j <image.getWidth();j++){
                //System.out.println("Coordinates: X="+i+" Y="+j);

                Color c = new Color(image.getRGB(j, i));
                double red = (c.getRed() * 0.299);
                double green =(c.getGreen() * 0.587);
                double blue = (c.getBlue() *0.114);
                double color = (red + green + blue)/3;

                //System.out.println(color);

                newline = newline + GrayChar((int) Math.round(color));

            }
            //System.out.println(newline+"Break");

            lines.add(newline+"Break");
            Path file = Paths.get("Images.txt");
            Files.write(file, lines, StandardCharsets.UTF_8);
            //Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);


            newline = "";
        }
        /*
        Gets values of GrayScale form image.
        image = input BufferImage

        it is unefficient goes pixel by pixel and gets it grey value in 0-255
        Then is converts pixel using GrayChar function to symbol from ASCII
        Then it Adds it to List<Strings> adds new lines prints at end of image
         */
    }
    static String GrayChar(int grayScale)
    {
        if (grayScale > 225) return "@@";
        if (grayScale > 200) return "%%";
        if (grayScale > 175) return "##";
        if (grayScale > 150) return "**";
        if (grayScale > 125) return "++";
        if (grayScale > 100) return "==";  // .:-=+*#%@
        if (grayScale > 75) return "--";
        if (grayScale > 50) return "::";
        if (grayScale > 25) return "..";
        if (grayScale > 0) return "  ";
        return "";
    }

}
