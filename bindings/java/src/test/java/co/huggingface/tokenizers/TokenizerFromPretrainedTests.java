package co.huggingface.tokenizers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenizerFromPretrainedTests {


    @Test
    void testEncodeWordpiece() {
        var tokenizer = new TokenizerFromPretrained("bert-base-cased");
        var input = "Tokenize me please!";
        var encodings = tokenizer.encode(input, true);

        var expectedTokens = new String[] {"[CLS]", "To", "##ken", "##ize", "me", "please", "!", "[SEP]"};
        var expectedIds = new long[] {101L, 1706L, 6378L, 3708L, 1143L, 4268L, 106L, 102L};
        var expectedTypeIds = new long[] {0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};
        var expectedWordIds = new long[] {-1L, 0L, 0L, 0L, 1L, 2L, 3L, -1L};

        var tokens = encodings.getTokens();
        var ids = encodings.getIds();
        var typeIds = encodings.getTypeIds();
        var wordIds = encodings.getWordIds();

        assertArrayEquals(expectedTokens,tokens);
        assertArrayEquals(expectedIds, ids);
        assertArrayEquals(expectedTypeIds, typeIds);
        assertArrayEquals(expectedWordIds, wordIds);

    }

    //cannot fetch model from "google/mt5-base"
    @Test
    void testEncodeUnigram() {
//        System.out.println("foo");
        assertEquals(0,0);
    }

    void testEncodeBPE() {
//        System.out.println("foo");
        assertEquals(0,0);
    }

    //test from pretrained wordpiece
    //test from pretrained unigram
    //test labse
    //test BPE
    //test pair thingie (it should crash)


}
