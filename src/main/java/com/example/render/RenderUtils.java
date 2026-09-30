package com.example.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.Color;

public class RenderUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static VertexConsumerProvider.Immediate beginLines() {
        return VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
    }

    public static void drawBox(MatrixStack matrices, VertexConsumerProvider vcp,
                               Box box, Color color, float lineWidth) {
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        Box b = box.offset(-cam.x, -cam.y, -cam.z);

        VertexConsumer buf = vcp.getBuffer(RenderLayer.getDebugQuads());
        float r = color.getRed() / 255f, g = color.getGreen() / 255f, bc = color.getBlue() / 255f, a = color.getAlpha() / 255f;

        drawLine(buf, mat, b.minX, b.minY, b.minZ, b.maxX, b.minY, b.minZ, r, g, bc, a);
        drawLine(buf, mat, b.maxX, b.minY, b.minZ, b.maxX, b.minY, b.maxZ, r, g, bc, a);
        drawLine(buf, mat, b.maxX, b.minY, b.maxZ, b.minX, b.minY, b.maxZ, r, g, bc, a);
        drawLine(buf, mat, b.minX, b.minY, b.maxZ, b.minX, b.minY, b.minZ, r, g, bc, a);
        drawLine(buf, mat, b.minX, b.maxY, b.minZ, b.maxX, b.maxY, b.minZ, r, g, bc, a);
        drawLine(buf, mat, b.maxX, b.maxY, b.minZ, b.maxX, b.maxY, b.maxZ, r, g, bc, a);
        drawLine(buf, mat, b.maxX, b.maxY, b.maxZ, b.minX, b.maxY, b.maxZ, r, g, bc, a);
        drawLine(buf, mat, b.minX, b.maxY, b.maxZ, b.minX, b.maxY, b.minZ, r, g, bc, a);
        drawLine(buf, mat, b.minX, b.minY, b.minZ, b.minX, b.maxY, b.minZ, r, g, bc, a);
        drawLine(buf, mat, b.maxX, b.minY, b.minZ, b.maxX, b.maxY, b.minZ, r, g, bc, a);
        drawLine(buf, mat, b.maxX, b.minY, b.maxZ, b.maxX, b.maxY, b.maxZ, r, g, bc, a);
        drawLine(buf, mat, b.minX, b.minY, b.maxZ, b.minX, b.maxY, b.maxZ, r, g, bc, a);
    }

    private static void drawLine(VertexConsumer buf, Matrix4f mat,
                                 double x1, double y1, double z1,
                                 double x2, double y2, double z2,
                                 float r, float g, float b, float a) {
        buf.vertex(mat, (float) x1, (float) y1, (float) z1).color(r, g, b, a).normal(0, 1, 0).next();
        buf.vertex(mat, (float) x2, (float) y2, (float) z2).color(r, g, b, a).normal(0, 1, 0).next();
    }

    public static void drawTracer(MatrixStack matrices, VertexConsumerProvider vcp,
                                  Vec3d target, Color color) {
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Vec3d cam = mc.gameRenderer.getCamera().getPos();
        Vec3d t = target.subtract(cam);

        VertexConsumer buf = vcp.getBuffer(RenderLayer.getDebugQuads());
        float r = color.getRed() / 255f, g = color.getGreen() / 255f, b = color.getBlue() / 255f, a = color.getAlpha() / 255f;

        buf.vertex(mat, 0, 0, 0).color(r, g, b, a).normal(0, 1, 0).next();
        buf.vertex(mat, (float) t.x, (float) t.y, (float) t.z).color(r, g, b, a).normal(0, 1, 0).next();
    }

    public static Color getPlayerColor(PlayerEntity p, Color normal, Color friend) {
        return p.isTeammate(mc.player) ? friend : normal;
    }
}
