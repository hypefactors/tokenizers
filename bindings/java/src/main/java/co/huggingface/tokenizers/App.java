package co.huggingface.tokenizers;

import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        var str = "My name is John";

        var tokenizer = new TokenizerFromPretrained("bert-base-cased");
        var list = new ArrayList<String>();
        list.add("Hello world");
        list.add("I love Java");
        list.add("My name is Viet and Andrea");
        var encoding = tokenizer.encode(list, true);
        System.out.println(encoding.getIds());
        var encodings = tokenizer.encode_batch(list, true);
        System.out.println(encodings[1].getTokens().toString());
    }
}
