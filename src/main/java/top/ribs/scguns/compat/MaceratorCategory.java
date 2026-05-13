package top.ribs.scguns.compat;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import top.ribs.scguns.Reference;
import top.ribs.scguns.client.screen.MaceratorRecipe;
import top.ribs.scguns.init.ModBlocks;

import java.awt.*;

public class MaceratorCategory implements IRecipeCategory<RecipeHolder<MaceratorRecipe>> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "macerating");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "textures/gui/macerator_gui.png");
    public static final RecipeType<RecipeHolder<MaceratorRecipe>> MACERATING_TYPE = RecipeType.createRecipeHolderType(UID);
    private final IDrawable background;
    private final IDrawable icon;
    private final int offsetX = 40;
    private final int offsetY = 26;

    public MaceratorCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, offsetX, offsetY, 93, 54);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MACERATOR.get()));
    }

    @Override
    public RecipeType<RecipeHolder<MaceratorRecipe>> getRecipeType() {
        return MACERATING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.scguns.macerator");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MaceratorRecipe> holder, IFocusGroup focuses) {
    	MaceratorRecipe recipe = holder.value();
        int inputBaseX = 4;
        int inputBaseY = 1;
        int inputSpacing = 18;
        for (int i = 0; i < 4; i++) {
            int row = i / 2;
            int col = i % 2;
            int x = inputBaseX + col * inputSpacing;
            int y = inputBaseY + row * inputSpacing;
            if (i < recipe.getIngredients().size()) {
                builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                        .addIngredients(recipe.getIngredients().get(i));
            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 74, 1)
                .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }
    @Override
    public void draw(RecipeHolder<MaceratorRecipe> holder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(holder, recipeSlotsView, guiGraphics, mouseX, mouseY);
    	MaceratorRecipe recipe = holder.value();
        int processingTime = recipe.getProcessingTime();
        if (processingTime > 0) {
            int seconds = processingTime / 20;
            String processingTimeText = String.format("%ds", seconds);
            guiGraphics.drawString(Minecraft.getInstance().font, processingTimeText, 30 + offsetX, 44, Color.gray.getRGB(), false);
        }
    }
}
