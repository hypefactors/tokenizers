package co.huggingface.tokenizers;

public class App {

    public static void main(String[] args) {
        var str = "Hello World, this is Viet and Andrea testing their new tokenizer in the JVM";

        var tokenizer = new Tokenizer("labse");
        var encoding = tokenizer.encode(str);
        //System.out.println(Arrays.toString(encoding.getIds()));
        //System.out.println(Arrays.toString(encoding.getTypeIds()));
        //System.out.println(Arrays.toString(encoding.getWordIds()));
        //System.out.println(Arrays.toString(encoding.getTokens()));
    }
}
