package co.huggingface.tokenizers;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        var str = "Hello World, this is Viet and Andrea testing their new tokenizer in the JVM";

        var tokenizer = new Tokenizer("labse");
        var encoding = tokenizer.encode(str);
        System.out.println(encoding);
    }
}
