package org.runejs.client;

import org.runejs.client.cache.def.ItemDefinition;
import org.runejs.client.cache.def.ActorDefinition;
import org.runejs.client.cache.def.OverlayDefinition;
import org.runejs.client.cache.media.TypeFace;
import org.runejs.client.cache.media.gameInterface.GameInterface;
import org.runejs.client.frame.ChatBox;
import org.runejs.client.frame.ScreenController;
import org.runejs.client.frame.ScreenMode;
import org.runejs.client.language.English;
import org.runejs.client.language.Native;
import org.runejs.client.media.Rasterizer;
import org.runejs.client.media.VertexNormal;
import org.runejs.client.media.renderable.Item;
import org.runejs.client.media.renderable.actor.Player;
import org.runejs.client.scene.InteractiveObject;
import org.runejs.client.scene.tile.FloorDecoration;
import org.runejs.client.scene.tile.SceneTile;
import org.runejs.client.scene.util.CollisionMap;

public class Class43 {
    public static int cameraYawOffset = 0;
    public static LinkedList projectileQueue = new LinkedList();
    public static int bankInsertMode = 0;

    public static void threadSleep(long ms) {
        if (ms <= 0L) {
            return;
        }

        try {
            Thread.sleep(ms);
        } catch(InterruptedException interruptedexception) {
            /* empty */
        }
    }

    public static void drawTabArea(int arg0) {
        MovedStatics.showSidePanelRedrawnText = true;
        MovedStatics.method996(19655);
        if(GameInterface.tabAreaInterfaceId != -1) {
            boolean bool = Main.drawParentInterface(1, 0, 0, 190, 261, GameInterface.tabAreaInterfaceId);
            if(!bool)
                GameInterface.redrawTabArea = true;
        } else if(Player.tabWidgetIds[Player.currentTabId] != -1) {
            boolean bool = Main.drawParentInterface(1, 0, 0, 190, 261, Player.tabWidgetIds[Player.currentTabId]);
            if(!bool)
                GameInterface.redrawTabArea = true;
        }
        if(MovedStatics.menuOpen && Projectile.menuScreenArea == 1) {
            if(MovedStatics.anInt848 == 1)
                method398(-2);
            else if(ScreenController.frameMode == ScreenMode.FIXED)
                MovedStatics.drawMenu(0,0);
        }
        if(arg0 >= -22)
            cameraYawOffset = 80;

        if(ScreenController.frameMode == ScreenMode.FIXED) {
            Class55.drawTabGraphics();
        }
    }


    public static void processRightClick() {
        if(SceneTile.activeInterfaceType == 0) {
            Landscape.menuActionTexts[0] = English.cancel;
            MovedStatics.menuActionTypes[0] = 1005;
            ActorDefinition.menuActionRow = 1;
            if(GameInterface.fullscreenInterfaceId == -1) {
                MovedStatics.method445(9767);
                Item.anInt3065 = -1;
                OverlayDefinition.hoveredWidgetChildId = -1;
                boolean bool = false;
                // Right game screen
                if(ScreenController.isCoordinatesIn3dScreen(Class13.mouseX ,Landscape.mouseY )) {
                    if(GameInterface.gameScreenInterfaceId == -1) {
                        MovedStatics.method1013();
                    } else {
                        int yOffset = (ScreenController.drawHeight /2) - (334/2) - (184/2);
                        int xOffset = (ScreenController.drawWidth /2) - (512/2) - (234/3);
                        if(ScreenController.frameMode == ScreenMode.FIXED) {
                            yOffset = 0;
                            xOffset = 0;
                        }
                        Class13.handleInterfaceActions(0, Class13.mouseX - xOffset, Landscape.mouseY - yOffset, 4, 4, 516, 338, GameInterface.gameScreenInterfaceId);
                    }
                }

                MovedStatics.anInt573 = Item.anInt3065;
                ItemDefinition.anInt2850 = OverlayDefinition.hoveredWidgetChildId;
                Item.anInt3065 = -1;
                OverlayDefinition.hoveredWidgetChildId = -1;
                // Right click tab
                if(ScreenController.isCoordinatesInTabArea(Class13.mouseX, Landscape.mouseY)) {
                    ScreenController.handleTabClick(Class13.mouseX, Landscape.mouseY);
                }
                if(OverlayDefinition.hoveredWidgetChildId != CollisionMap.currentHoveredWidgetChildId) {
                    GameInterface.redrawTabArea = true;
                    CollisionMap.currentHoveredWidgetChildId = OverlayDefinition.hoveredWidgetChildId;
                }
                OverlayDefinition.hoveredWidgetChildId = -1;
                if(Item.anInt3065 != FloorDecoration.anInt614) {
                    FloorDecoration.anInt614 = Item.anInt3065;
                    GameInterface.redrawTabArea = true;
                }
                Item.anInt3065 = -1;
                // right click chatbox
                if(ScreenController.isCoordinatesInChatArea(Class13.mouseX ,Landscape.mouseY)) {
                    ScreenController.handleChatBoxMouse(Class13.mouseX ,Landscape.mouseY);
                }

                // Set hovering for chat widgets
                if((GameInterface.chatboxInterfaceId != -1 || ChatBox.dialogueId != -1) && Class55.currentHoveredChatboxWidgetChildId != OverlayDefinition.hoveredWidgetChildId) {
                    ChatBox.redrawChatbox = true;
                    Class55.currentHoveredChatboxWidgetChildId = OverlayDefinition.hoveredWidgetChildId;
                }

                if((GameInterface.chatboxInterfaceId != -1 || ChatBox.dialogueId != -1) && Item.anInt3065 != MovedStatics.anInt1586) {
                    ChatBox.redrawChatbox = true;
                    MovedStatics.anInt1586 = Item.anInt3065;
                }
                while(!bool) {
                    bool = true;
                    for(int i = 0; -1 + ActorDefinition.menuActionRow > i; i++) {
                        if(MovedStatics.menuActionTypes[i] < 1000 && MovedStatics.menuActionTypes[1 + i] > 1000) {
                            bool = false;
                            String class1 = Landscape.menuActionTexts[i];
                            Landscape.menuActionTexts[i] = Landscape.menuActionTexts[i + 1];
                            Landscape.menuActionTexts[i + 1] = class1;
                            int i_90_ = MovedStatics.menuActionTypes[i];
                            MovedStatics.menuActionTypes[i] = MovedStatics.menuActionTypes[i + 1];
                            MovedStatics.menuActionTypes[i + 1] = i_90_;
                            i_90_ = InteractiveObject.firstMenuOperand[i];
                            InteractiveObject.firstMenuOperand[i] = InteractiveObject.firstMenuOperand[1 + i];
                            InteractiveObject.firstMenuOperand[1 + i] = i_90_;
                            i_90_ = Class59.secondMenuOperand[i];
                            Class59.secondMenuOperand[i] = Class59.secondMenuOperand[1 + i];
                            Class59.secondMenuOperand[i + 1] = i_90_;
                            i_90_ = Class33.selectedMenuActions[i];
                            Class33.selectedMenuActions[i] = Class33.selectedMenuActions[i + 1];
                            Class33.selectedMenuActions[1 + i] = i_90_;
                        }
                    }
                }
            } else {
                Item.anInt3065 = -1;
                OverlayDefinition.hoveredWidgetChildId = -1;
                Class13.handleInterfaceActions(0, Class13.mouseX, Landscape.mouseY, 0, 0, 765, 503, GameInterface.fullscreenInterfaceId);
                ItemDefinition.anInt2850 = OverlayDefinition.hoveredWidgetChildId;
                MovedStatics.anInt573 = Item.anInt3065;
            }
        }
    }

    public static void method398(int arg0) {
        String class1 = null;
        for(int i = 0; ActorDefinition.menuActionRow > i; i++) {
            if(Landscape.menuActionTexts[i].contains(Native.lightRed)) {
                class1 = Landscape.menuActionTexts[i].substring(Landscape.menuActionTexts[i].indexOf(Native.lightRed));
                break;
            }
        }
        if(class1 == null)
            MovedStatics.drawMenu(0,0);
        else {
            int i = VertexNormal.menuWidth;
            int i_0_ = InteractiveObject.menuOffsetX;
            if(i > 190)
                i = 190;
            int i_1_ = CollisionMap.menuHeight;
            int i_2_ = Main.menuOffsetY;
            if(i_0_ < 0)
                i_0_ = 0;
            int i_3_ = 6116423;
            Rasterizer.drawFilledRectangle(i_0_, i_2_, i, i_1_, i_3_);
            Rasterizer.drawFilledRectangle(i_0_ + 1, i_2_ + 1, arg0 + i, 16, 0);
            Rasterizer.drawUnfilledRectangle(i_0_ + 1, 18 + i_2_, -2 + i, i_1_ + -19, 0);
            TypeFace.fontBold.drawShadowedString(class1, 3 + i_0_, 14 + i_2_, false, i_3_);
            int i_4_ = Class13.mouseX;
            int i_5_ = Landscape.mouseY;
            if(Projectile.menuScreenArea == 0) {
                i_4_ -= 4;
                i_5_ -= 4;
            }
            if(Projectile.menuScreenArea == 1) {
                i_4_ -= 553;
                i_5_ -= 205;
            }
            if(Projectile.menuScreenArea == 2) {
                i_5_ -= 357;
                i_4_ -= 17;
            }
            for(int i_6_ = 0; i_6_ < ActorDefinition.menuActionRow; i_6_++) {
                int i_7_ = 31 + i_2_ + (ActorDefinition.menuActionRow + -1 + -i_6_) * 15;
                String class1_8_ = Landscape.menuActionTexts[i_6_];
                int i_9_ = 16777215;
                if(class1_8_.endsWith(class1)) {
                    class1_8_ = class1_8_.substring(0, class1_8_.length() - class1.length());
                    if(class1_8_.endsWith(Native.whitespace))
                        class1_8_ = class1_8_.substring(0, class1_8_.length() + -Native.whitespace.length());
                }
                if(i_0_ < i_4_ && i_4_ < i_0_ + i && -13 + i_7_ < i_5_ && 3 + i_7_ > i_5_)
                    i_9_ = 16776960;
                TypeFace.fontBold.drawShadowedString(class1_8_, 3 + i_0_, i_7_, true, i_9_);
            }
        }
    }
}
