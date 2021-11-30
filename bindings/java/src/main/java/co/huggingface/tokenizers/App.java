package co.huggingface.tokenizers;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        var str = "Hello World, this is Viet and Andrea testing their new tokenizer in the JVM";
        System.out.println("hello");
        var tokenizer = SaferFFITokenizersLibrary.INSTANCE.tokenizer_new();
        var encoding = SaferFFITokenizersLibrary.INSTANCE.tokenizer_encode(tokenizer, str);
        System.out.println(encoding);
        System.out.println(Arrays.toString(encoding.ids.getArray()));
        System.out.println(Arrays.toString(encoding.type_ids.getArray()));
        System.out.println(Arrays.toString(encoding.words.getArray()));
        System.out.println(Arrays.toString(encoding.tokens.getArray()));
        SaferFFITokenizersLibrary.INSTANCE.encoding_drop(encoding.getPointer());
        SaferFFITokenizersLibrary.INSTANCE.tokenizer_drop(tokenizer);
    }
}
