package org.runejs.client.scene.tile;

import org.runejs.client.HuffmanEncoding;
import org.runejs.client.Landscape;
import org.runejs.client.MovedStatics;
import org.runejs.client.cache.CacheArchive;
import org.runejs.client.cache.def.GameObjectDefinition;
import org.runejs.client.cache.def.OverlayDefinition;
import org.runejs.client.cache.media.ImageRGB;
import org.runejs.client.cache.media.TypeFace;
import org.runejs.client.frame.ScreenController;
import org.runejs.client.frame.ScreenMode;
import org.runejs.client.input.MouseHandler;
import org.runejs.client.io.Buffer;
import org.runejs.client.language.English;
import org.runejs.client.media.VertexNormal;
import org.runejs.client.media.renderable.actor.Npc;
import org.runejs.client.media.renderable.actor.Player;
import org.runejs.client.scene.util.CollisionMap;
import org.runejs.client.Class57;

import java.awt.*;

public class GenericTile {
    public static int anInt1214 = 0;
    public static volatile boolean clientFocused = true;
    public static byte[][] objectData;
    public static int fps = 0;
    public static int carryWeight = 0;
    public static int anInt1233 = 0;

    // TODO move this out, it's used for debugging
    public static int hoveredWidgetId = -1;

    public int texture;
    public int colourB;
    public int rgbColor;
    public boolean flat;
    public int colourA;
    public int colourC;
    public int colourD;

    public GenericTile(int colourA, int colourB, int colourC, int colourD, int texture, int rgbColor, boolean flat) {
        this.colourA = colourA;
        this.colourB = colourB;
        this.colourC = colourC;
        this.colourD = colourD;
        this.texture = texture;
        this.rgbColor = rgbColor;
        this.flat = flat;
    }

    public static void method943(int arg0, TypeFace arg2, int arg3, int arg4) {
        HuffmanEncoding.chatModes.prepareRasterizer();
        Class57.bottomChatBack.drawImage(0, 0);
        arg2.drawShadowedStringCenter(English.publicChat, 55, 28, 16777215, true);
        if(arg4 == 0)
            arg2.drawShadowedStringCenter(English.on, 55, 41, 65280, true);
        if(arg4 == 1)
            arg2.drawShadowedStringCenter(English.friends, 55, 41, 16776960, true);
        if(arg4 == 2)
            arg2.drawShadowedStringCenter(English.off, 55, 41, 16711680, true);
        if(arg4 == 3)
            arg2.drawShadowedStringCenter(English.hide, 55, 41, 65535, true);
        arg2.drawShadowedStringCenter(English.privateChat, 184, 28, 16777215, true);
        if(arg3 == 0)
            arg2.drawShadowedStringCenter(English.on, 184, 41, 65280, true);
        if(arg3 == 1)
            arg2.drawShadowedStringCenter(English.friends, 184, 41, 16776960, true);
        if(arg3 == 2)
            arg2.drawShadowedStringCenter(English.off, 184, 41, 16711680, true);
        arg2.drawShadowedStringCenter(English.trade, 324, 28, 16777215, true);
        if(arg0 == 0)
            arg2.drawShadowedStringCenter(English.on, 324, 41, 65280, true);
        if(arg0 == 1)
            arg2.drawShadowedStringCenter(English.friends, 324, 41, 16776960, true);
        if(arg0 == 2)
            arg2.drawShadowedStringCenter(English.off, 324, 41, 16711680, true);
        arg2.drawText(English.reportAbuse, 417, 17, 85, 25, 16777215, true, 1, 1, 0);
        try {
            Graphics graphics = MouseHandler.gameCanvas.getGraphics();
            if(ScreenController.frameMode == ScreenMode.FIXED) {
                HuffmanEncoding.chatModes.drawGraphics(0, 453, graphics);
            }
        } catch(Exception exception) {
            MouseHandler.gameCanvas.repaint();
        }
    }

    public static ImageRGB[] method944(byte arg0, int arg1, CacheArchive arg2, int arg3) {
        if(!ImageRGB.spriteExists(arg3, arg1, arg2))
            return null;
        int i = -59 % ((-60 - arg0) / 34);
        return MovedStatics.method319((byte) -62);
    }

    public static void method945(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
        if(arg5 != 103)
            anInt1214 = -81;
        if(arg2 >= 1 && arg0 >= 1 && arg2 <= 102 && arg0 <= 102) {
            if(!VertexNormal.lowMemory || Player.worldLevel == arg7) {
                int i = -1;
                int i_0_ = 0;
                boolean bool = false;
                boolean bool_1_ = false;
                if(arg6 == 0)
                    i_0_ = Npc.currentScene.method122(arg7, arg2, arg0);
                if(arg6 == 1)
                    i_0_ = Npc.currentScene.method91(arg7, arg2, arg0);
                if(arg6 == 2)
                    i_0_ = Npc.currentScene.getLocationHash(arg7, arg2, arg0);
                if(arg6 == 3)
                    i_0_ = Npc.currentScene.getFloorDecorationHash(arg7, arg2, arg0);
                if(i_0_ != 0) {
                    i = (i_0_ & 0x1ffff138) >> 14;
                    int i_2_ = Npc.currentScene.getArrangement(arg7, arg2, arg0, i_0_);
                    int i_3_ = i_2_ >> 6 & 0x3;
                    int i_4_ = 0x1f & i_2_;
                    if(arg6 == 0) {
                        Npc.currentScene.method124(arg7, arg2, arg0);
                        GameObjectDefinition gameObjectDefinition = GameObjectDefinition.getDefinition(i);
                        if(gameObjectDefinition.solid)
                            Landscape.currentCollisionMap[arg7].markWall(arg2, i_3_, gameObjectDefinition.walkable, arg0, i_4_);
                    }
                    if(arg6 == 1)
                        Npc.currentScene.method127(arg7, arg2, arg0);
                    if(arg6 == 2) {
                        Npc.currentScene.removeInteractiveObject(arg7, arg2, arg0);
                        GameObjectDefinition gameObjectDefinition = GameObjectDefinition.getDefinition(i);
                        if(arg2 + gameObjectDefinition.sizeX > 103 || arg0 + gameObjectDefinition.sizeX > 103 || arg2 + gameObjectDefinition.sizeY > 103 || gameObjectDefinition.sizeY + arg0 > 103)
                            return;
                        if(gameObjectDefinition.solid)
                            Landscape.currentCollisionMap[arg7].markSolidOccupant(arg2, arg0, gameObjectDefinition.sizeX, gameObjectDefinition.sizeY, i_3_, gameObjectDefinition.walkable);
                    }
                    if(arg6 == 3) {
                        Npc.currentScene.method131(arg7, arg2, arg0);
                        GameObjectDefinition gameObjectDefinition = GameObjectDefinition.getDefinition(i);
                        if(gameObjectDefinition.solid && gameObjectDefinition.hasActions == 1)
                            Landscape.currentCollisionMap[arg7].unmarkConcealed(arg2, arg0);
                    }
                }
                if(arg1 >= 0) {
                    int i_5_ = arg7;
                    if(i_5_ < 3 && (OverlayDefinition.tile_flags[1][arg2][arg0] & 0x2) == 2)
                        i_5_++;
                    CollisionMap.method543(Landscape.currentCollisionMap[arg7], arg1, i_5_, arg7, arg3, Npc.currentScene, -22078, arg4, arg0, arg2);
                }
            }
        }
    }



    public static void method947(int arg0) {
        synchronized(CollisionMap.anObject162) {
            if((Buffer.anInt1987 ^ 0xffffffff) != arg0) {
                Buffer.anInt1987 = 1;
                try {
                    CollisionMap.anObject162.wait();
                } catch(InterruptedException interruptedexception) {
                    /* empty */
                }
            }
        }
    }

}
