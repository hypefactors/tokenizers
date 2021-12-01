package co.huggingface.tokenizers;

import java.util.ArrayList;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        var str = "My name is John";

        var tokenizer = new TokenizerFromPretrained("bert-base-cased");
        var list = new ArrayList<String>();
        list.add("Hello world");
        list.add("I love Java");
        list.add("My name is Viet");
        //var encoding = tokenizer.encode(list, true);
        //System.out.println(encoding.getIds());
        //System.out.println(Arrays.toString(encoding.getTypeIds()));
        //System.out.println(Arrays.toString(encoding.getWordIds()));
        //System.out.println(encoding.getTokens());
        var encodings = tokenizer.java_encode_batch(list, true);
//       System.out.println(encodings.get(0).getTokens().toString());
    }
}
