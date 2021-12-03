package co.huggingface.tokenizers;

public class SaferFFIResultTokenizer extends SaferFFIResult {
    public void drop() {
        SaferFFITokenizersLibrary.INSTANCE.tokenizer_drop(this.getPointer());
    }
}