package com.Nxer.TwistSpaceTechnology.common.material;

import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_MAX;
import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_UEV;
import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_UHV;
import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_UIV;
import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_UMV;
import static gregtech.api.recipe.RecipeMaps.blastFurnaceRecipes;
import static gregtech.api.recipe.RecipeMaps.fusionRecipes;
import static gregtech.api.recipe.RecipeMaps.mixerRecipes;
import static gregtech.api.recipe.RecipeMaps.transcendentPlasmaMixerRecipes;
import static gregtech.api.recipe.RecipeMaps.vacuumFreezerRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeConstants.COIL_HEAT;
import static gregtech.api.util.GTRecipeConstants.FOG_PLASMA_MULTISTEP;
import static gregtech.api.util.GTRecipeConstants.FOG_PLASMA_TIER;
import static gtPlusPlus.api.recipe.GTPPRecipeMaps.alloyBlastSmelterRecipes;
import static tectech.recipe.TecTechRecipeMaps.godforgePlasmaRecipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.metatileentity.implementations.MTECable;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.material.MaterialsElements;

public class MaterialFix {

    // spotless:off
    public static void load() {

        // Holmium Garnet
        GTValues.RA.stdBuilder()
            .itemInputs(MaterialPool.HolmiumGarnet.get(OrePrefixes.dust, 1))
            .fluidOutputs(MaterialPool.HolmiumGarnet.getMolten(144))
            .noOptimize()
            .eut(96)
            .duration(72)
            .addTo(RecipeMaps.fluidExtractionRecipes);

        // Neutronium Alloy

        addBlastRecipe(MaterialsTST.NeutroniumAlloy, (int) RECIPE_UIV, 54 * 20, 12500, true);
        addVacuumFreezerRecipe( MaterialsTST.NeutroniumAlloy,(int)RECIPE_UEV,18 * 20);

        GTValues.RA.stdBuilder()
            .itemInputs(
                Materials.Neutronium.getDust(7),
                Materials.Duranium.getDust(2),
                Materials.Flerovium.getDust(1),
                MaterialsElements.STANDALONE.WHITE_METAL.getDust(1),
                Materials.DarkIron.getDust(1),
                GTUtility.getIntegratedCircuit(2)
            )
            .fluidInputs(Materials.Hydrogen.getPlasma(1000 * 14))
            .itemOutputs(MaterialsTST.NeutroniumAlloy.getDust(12))
            .eut(RECIPE_UHV)
            .duration(12 * 20)
            .addTo(mixerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                Materials.Neutronium.getDust(7),
                Materials.Duranium.getDust(2),
                Materials.Flerovium.getDust(1),
                MaterialsElements.STANDALONE.WHITE_METAL.getDust(1),
                Materials.DarkIron.getDust(1))
            .fluidInputs(Materials.Hydrogen.getGas(1000 * 14))
            .fluidOutputs(MaterialsTST.NeutroniumAlloy.getMolten(16 * 144))
            .eut(RECIPE_UIV)
            .duration(660 * 20)
            .addTo(alloyBlastSmelterRecipes);

        // Axonis Alloy

        addBlastRecipe(MaterialsTST.AxonisAlloy, (int) RECIPE_UMV, 720, 13200, true);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                MaterialsElements.STANDALONE.DRAGON_METAL.getDust(5),
                MaterialsElements.STANDALONE.HYPOGEN.getDust(3),
                MaterialsUEVplus.Creon.getDust(2),
                Materials.Ichorium.getDust(1),
                Materials.Terbium.getDust(1),
                GGMaterial.shirabon.get(OrePrefixes.dust,1))
            .fluidOutputs(MaterialsTST.AxonisAlloy.getMolten(144 * 12))
            .eut(RECIPE_UMV)
            .duration(720 * 20)
            .addTo(alloyBlastSmelterRecipes);

        // Axonium
        makeWires(MaterialsTST.Axonium,20000,0L, 0L,Integer.MAX_VALUE, Integer.MAX_VALUE, false, true);
        GTValues.RA.stdBuilder()
            .fluidInputs(
                MaterialsTST.AxonisAlloy.getMolten(144),
                MaterialsUEVplus.Protomatter.getFluid(1000))
            .fluidOutputs(MaterialsTST.Axonium.getMolten(144))
            .eut(RECIPE_UEV)
            .duration(20 * 20)
            .specialValue(2_000_000_000)
            .addTo(fusionRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(20))
            .fluidInputs(
                new FluidStack( MaterialsElements.STANDALONE.DRAGON_METAL.getPlasma(),5000),
                new FluidStack( MaterialsElements.STANDALONE.HYPOGEN.getPlasma(),3000),
                MaterialsUEVplus.Creon.getPlasma(2000),
                Materials.Ichorium.getPlasma(1000),
                Materials.Terbium.getPlasma(1000),
                GGMaterial.shirabon.getMolten(1000),
                MaterialsUEVplus.PrimordialMatter.getFluid(1000))
            .fluidOutputs(MaterialsTST.Axonium.getPlasma(120000))
            .eut(RECIPE_MAX)
            .duration(45 * 20)
            .addTo(transcendentPlasmaMixerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(MaterialsTST.Axonium.getDust(144))
            .fluidOutputs(MaterialsTST.Axonium.getPlasma(144))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_MAX)
            .metadata(FOG_PLASMA_MULTISTEP, true)
            .metadata(FOG_PLASMA_TIER, 2)
            .noOptimize()
            .addTo(godforgePlasmaRecipes);

        GTValues.RA.stdBuilder()
            .fluidInputs(MaterialsTST.Axonium.getMolten(144))
            .fluidOutputs(MaterialsTST.Axonium.getPlasma(144))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_MAX)
            .metadata(FOG_PLASMA_MULTISTEP, true)
            .metadata(FOG_PLASMA_TIER, 2)
            .noOptimize()
            .addTo(godforgePlasmaRecipes);

        // Concentrated UU Matter
        GTValues.RA.stdBuilder()
            .fluidInputs(
                Materials.UUMatter.getFluid(1000000),
                MaterialsTST.Axonium.getMolten(72))
            .fluidOutputs(MaterialPool.ConcentratedUUMatter.getFluidOrGas(1))
            .eut(RECIPE_UEV)
            .duration(20 * 60)
            .specialValue(2_000_000_000)
            .addTo(fusionRecipes);

        GTValues.RA.stdBuilder()
            .fluidInputs(
                Materials.UUMatter.getFluid(1000000),
                MaterialsTST.Axonium.getPlasma(18))
            .fluidOutputs(MaterialPool.ConcentratedUUMatter.getFluidOrGas(1))
            .eut(RECIPE_UEV)
            .duration(20 * 10)
            .specialValue(2_000_000_000)
            .addTo(fusionRecipes);
    }

    // spotless:on
    public static void addBlastRecipe(Materials aMaterial, int EUt, int duration, int level, boolean gas) {
        ItemStack input = aMaterial.getDust(1);
        ItemStack output = level > 1750 ? GTOreDictUnificator.get(OrePrefixes.ingotHot, aMaterial, 1)
            : aMaterial.getIngots(1);
        if (gas) {
            GTValues.RA.stdBuilder()
                .itemInputs(input, GTUtility.getIntegratedCircuit(11))
                .fluidInputs(Materials.Helium.getGas(1000))
                .itemOutputs(output)
                .eut(EUt)
                .duration(duration * TICKS)
                .metadata(COIL_HEAT, level)
                .addTo(blastFurnaceRecipes);
        } else {
            GTValues.RA.stdBuilder()
                .itemInputs(input, GTUtility.getIntegratedCircuit(1))
                .itemOutputs(output)
                .eut(EUt)
                .duration(duration * TICKS)
                .metadata(COIL_HEAT, level)
                .addTo(blastFurnaceRecipes);
        }
    }

    public static void addVacuumFreezerRecipe(Materials aMaterial, FluidStack[] fluidIn, FluidStack[] fluidOut, int eut,
        int ticks) {
        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.ingotHot, aMaterial, 1))
            .fluidInputs(fluidIn)
            .itemOutputs(aMaterial.getIngots(1))
            .fluidOutputs(fluidOut)
            .eut(eut)
            .duration(ticks)
            .addTo(vacuumFreezerRecipes);
    }

    public static void addVacuumFreezerRecipe(Materials aMaterial, int eut, int ticks) {
        addVacuumFreezerRecipe(aMaterial, new FluidStack[] {}, new FluidStack[] {}, eut, ticks);
    }

    // copy from GT_Loader_wires
    public static void makeWires(Materials aMaterial, int aStartID, long aLossInsulated, long aLoss, long aAmperage,
        long aVoltage, boolean aInsulatable, boolean aAutoInsulated) {
        String displayName = GTLanguageManager.i18nPlaceholder ? "%material" : aMaterial.mDefaultLocalName;
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt01,
            aMaterial,
            new MTECable(
                aStartID + 0,
                "wire." + aMaterial.mName.toLowerCase() + ".01",
                "1x " + displayName + " Wire",
                0.125F,
                aMaterial,
                aLoss,
                1L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt02,
            aMaterial,
            new MTECable(
                aStartID + 1,
                "wire." + aMaterial.mName.toLowerCase() + ".02",
                "2x " + displayName + " Wire",
                0.25F,
                aMaterial,
                aLoss,
                2L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt04,
            aMaterial,
            new MTECable(
                aStartID + 2,
                "wire." + aMaterial.mName.toLowerCase() + ".04",
                "4x " + displayName + " Wire",
                0.375F,
                aMaterial,
                aLoss,
                4L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt08,
            aMaterial,
            new MTECable(
                aStartID + 3,
                "wire." + aMaterial.mName.toLowerCase() + ".08",
                "8x " + displayName + " Wire",
                0.5F,
                aMaterial,
                aLoss,
                8L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt12,
            aMaterial,
            new MTECable(
                aStartID + 4,
                "wire." + aMaterial.mName.toLowerCase() + ".12",
                "12x " + displayName + " Wire",
                0.625F,
                aMaterial,
                aLoss,
                12L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated).getStackForm(1L));
        GTOreDictUnificator.registerOre(
            OrePrefixes.wireGt16,
            aMaterial,
            new MTECable(
                aStartID + 5,
                "wire." + aMaterial.mName.toLowerCase() + ".16",
                "16x " + displayName + " Wire",
                0.75F,
                aMaterial,
                aLoss,
                16L * aAmperage,
                aVoltage,
                false,
                !aAutoInsulated).getStackForm(1L));
        if (aInsulatable) {
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt01,
                aMaterial,
                new MTECable(
                    aStartID + 6,
                    "cable." + aMaterial.mName.toLowerCase() + ".01",
                    "1x " + displayName + " Cable",
                    0.25F,
                    aMaterial,
                    aLossInsulated,
                    1L * aAmperage,
                    aVoltage,
                    true,
                    false).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt02,
                aMaterial,
                new MTECable(
                    aStartID + 7,
                    "cable." + aMaterial.mName.toLowerCase() + ".02",
                    "2x " + displayName + " Cable",
                    0.375F,
                    aMaterial,
                    aLossInsulated,
                    2L * aAmperage,
                    aVoltage,
                    true,
                    false).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt04,
                aMaterial,
                new MTECable(
                    aStartID + 8,
                    "cable." + aMaterial.mName.toLowerCase() + ".04",
                    "4x " + displayName + " Cable",
                    0.5F,
                    aMaterial,
                    aLossInsulated,
                    4L * aAmperage,
                    aVoltage,
                    true,
                    false).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt08,
                aMaterial,
                new MTECable(
                    aStartID + 9,
                    "cable." + aMaterial.mName.toLowerCase() + ".08",
                    "8x " + displayName + " Cable",
                    0.625F,
                    aMaterial,
                    aLossInsulated,
                    8L * aAmperage,
                    aVoltage,
                    true,
                    false).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt12,
                aMaterial,
                new MTECable(
                    aStartID + 10,
                    "cable." + aMaterial.mName.toLowerCase() + ".12",
                    "12x " + displayName + " Cable",
                    0.75F,
                    aMaterial,
                    aLossInsulated,
                    12L * aAmperage,
                    aVoltage,
                    true,
                    false).getStackForm(1L));
            GTOreDictUnificator.registerOre(
                OrePrefixes.cableGt16,
                aMaterial,
                new MTECable(
                    aStartID + 11,
                    "cable." + aMaterial.mName.toLowerCase() + ".16",
                    "16x " + displayName + " Cable",
                    0.875F,
                    aMaterial,
                    aLossInsulated,
                    16L * aAmperage,
                    aVoltage,
                    true,
                    false).getStackForm(1L));
        }
    }
}
