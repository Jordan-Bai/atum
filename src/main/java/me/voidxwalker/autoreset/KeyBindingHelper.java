package me.voidxwalker.autoreset;

import com.google.common.collect.Lists;
import me.voidxwalker.autoreset.mixin.hotkey.KeyBindingAccessor;
import net.minecraft.client.options.KeyBinding;

import java.util.*;

public final class KeyBindingHelper {
    private static final List<KeyBinding> moddedKeyBindings = Lists.newArrayList();

    private static Map<String, Integer> getCategoryMap() {
        return KeyBindingAccessor.invokeGetCategoryMap();
    }

    private static boolean hasCategory(String categoryTranslationKey) {
        return getCategoryMap().containsKey(categoryTranslationKey);
    }

    public static void addCategory(String categoryTranslationKey) {
        Map<String, Integer> map = getCategoryMap();

        if (map.containsKey(categoryTranslationKey)) {
            return;
        }

        Optional<Integer> largest = map.values().stream().max(Integer::compareTo);
        int largestInt = largest.orElse(0);
        map.put(categoryTranslationKey, largestInt + 1);
    }

    public static KeyBinding registerKeyBinding(KeyBinding binding) {
        if (!hasCategory(binding.getCategory())) {
            addCategory(binding.getCategory());
        }

        moddedKeyBindings.add(binding);
        return binding;
    }

    public static KeyBinding[] process(KeyBinding[] keysAll) {
        List<KeyBinding> newKeysAll = Lists.newArrayList(keysAll);
        newKeysAll.removeAll(moddedKeyBindings);
        newKeysAll.addAll(moddedKeyBindings);
        return newKeysAll.toArray(new KeyBinding[0]);
    }
}