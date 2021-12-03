package co.huggingface.tokenizers;

import java.util.ArrayList;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        var str = "My name is John";

        var tk1 = TokenizerFromPretrained.create("bert-base-caseaoeuoaeuoaueoaud");
        System.err.println(tk1.error());

        var tk2 = TokenizerFromPretrained.create("bert-base-cased");
        var tokenizer = tk2.value();

        var list = "Tokenize me please!";
//        var list = new ArrayList<String>();
//        list.add("Hello world");
//        list.add("I love Java");
//        list.add("My name is Viet and Andrea");
        var encoding = tokenizer.encode(list, true);
        System.out.println(Arrays.toString(encoding.getWordIds()));
        System.out.println(Arrays.toString(encoding.getTokens()));
       // var encodings = tokenizer.encode_batch(list, true);
        //System.out.println(encodings[1].getTokens().toString());
    }
}
