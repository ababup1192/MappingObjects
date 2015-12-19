package org.ababup1192;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapping {

    public final List<Class<?>> packageClasses = new ArrayList<>();

    public Mapping(List<String> packages) throws Exception {
        if (packages.isEmpty()) throw new RuntimeException("パッケージを指定してください。");

        // 対象のパッケージからクラスを集める。
        packages.parallelStream().forEach(pkg -> {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                Set<Class<?>> allClasses = ClassPath.from(loader)
                        .getTopLevelClasses(pkg).parallelStream()
                        .map(ClassPath.ClassInfo::load)
                        .collect(Collectors.toSet());
                packageClasses.addAll(allClasses);
                if (allClasses.isEmpty()) throw new RuntimeException("パッケージが空です。");
            } catch (IOException e) {
                throw new RuntimeException(e.toString());
            }
        });
    }

    /**
     * オブジェクト
     * @param target 変換対象のオブジェクト
     * @return 変換後のオブジェクト
     */
    public Optional<?> convertFromPackageObjects(Object target) {
        for (Class<?> clazz : packageClasses) {
            Optional<?> convertOpt = convert(target, clazz);
            if (convertOpt.isPresent()) {
                return convertOpt;
            }
        }
        return Optional.empty();
    }

    /**
     * <T>型のオブジェクトから、<C>型のインスタンスへと変換する
     *
     * @param target       <T>型のオブジェクト
     * @param convertClass <C>型のClassクラス
     * @param <T>          変換前の型
     * @param <C>          変換後の型
     * @return Optional<C>型
     */
    @SuppressWarnings("unchecked")
    public static <T, C> Optional<C> convert(T target, Class<C> convertClass) {
        try {
            C convert = convertClass.newInstance();
            for (Field convertField : convertClass.getFields()) {
                // targetのフィールドを取得
                Field targetField = target.getClass().getField(convertField.getName());
                // targetのフィールドの値をconvertへ代入
                convertField.set(convert, targetField.get(target));
            }
            return Optional.ofNullable(convert);
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException ignored) {
        }
        return Optional.empty();
    }

}
