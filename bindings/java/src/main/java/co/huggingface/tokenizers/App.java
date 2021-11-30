package co.huggingface.tokenizers;

public class App {

    public static void main(String[] args) {
        var str = "My name is John";

        var tokenizer = new TokenizerFromPretrained("bert-base-cased");
        var encoding = tokenizer.encode(str, true);
        System.out.println(encoding.getIds());
        //System.out.println(Arrays.toString(encoding.getTypeIds()));
        //System.out.println(Arrays.toString(encoding.getWordIds()));
        System.out.println(encoding.getTokens());
    }
}
